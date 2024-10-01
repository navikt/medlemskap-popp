package no.nav.medlemskap.popp.services

import no.nav.medlemskap.hjelpemidler.clients.medlemskapoppslag.Brukerinput
import no.nav.medlemskap.hjelpemidler.clients.medlemskapoppslag.MedlOppslagRequest
import no.nav.medlemskap.hjelpemidler.clients.medlemskapoppslag.Periode
import no.nav.medlemskap.hjelpemidler.domain.GradertAdresseException
import no.nav.medlemskap.popp.clients.AzureAdClient
import no.nav.medlemskap.popp.clients.LovemeAPI
import no.nav.medlemskap.popp.clients.MedlemskapOppslagClient
import no.nav.medlemskap.popp.config.Configuration
import no.nav.medlemskap.popp.domain.Medlemperioder
import no.nav.medlemskap.popp.domain.PoppRequest
import no.nav.medlemskap.popp.domain.PoppRespons
import no.nav.medlemskap.popp.domain.Status
import no.nav.medlemskap.popp.http.cioHttpClient
import no.nav.medlemskap.popp.http.httpClient
import no.nav.medlemskap.popp.jackson.JacksonParser
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.random.Random

class PoppService:IJegKanHåndterePoppRequest {
    var lovmeClient: LovemeAPI
    var configuration = Configuration()
    val azuraADClient = AzureAdClient(configuration)
    val httpClient = cioHttpClient
    init {

        lovmeClient = MedlemskapOppslagClient(
            baseUrl = configuration.register.medlemskapOppslagBaseUrl,
            azureAdClient = azuraADClient,
            httpClient = httpClient
        )
    }
    override suspend fun handleRequest(poppRequest: PoppRequest):PoppRespons {
        //Lag requestObjekt
        val medlemskapsRequest: MedlOppslagRequest = mapMedlemskapRequestFromPoppRequest(poppRequest)
        //Kall regelmotor

        val respons = kallMedlemskapOppslag(request= medlemskapsRequest, callId = poppRequest.referanse)
        val poppRespons: PoppRespons = mapRegelmotorResponsTilPoppRespons(respons, referanse = poppRequest.referanse)
        return poppRespons
    }

    suspend fun kallMedlemskapOppslag(request: MedlOppslagRequest, callId: String): String {
        runCatching { lovmeClient.vurderMedlemskap(request, callId) }
            .onFailure {
                if (it.message?.contains("GradertAdresseException") == true) {
                    throw GradertAdresseException("Medlemskapsvurdering kan ikke utføres på personer med kode 6/7")
                } else {
                    throw Exception("Teknisk feil ved kall mot Lovme. Årsak : ${it.message}")
                }
            }
            .onSuccess { return it }
        return "" //umulig å komme hit?

    }


}

fun mapMedlemskapRequestFromPoppRequest(poppRequest: PoppRequest): MedlOppslagRequest {
    val fnr = poppRequest.omsorgsyter
    val yearMonthFom = poppRequest.perioder.sortedBy { it.fraOgMed }.first().fraOgMed
    val yearMonthTom = poppRequest.perioder.sortedByDescending { it.fraOgMed }.first().tilOgMed
    val førsteDagForYtelse: LocalDate = LocalDate.of(yearMonthFom.year, yearMonthFom.month, 1)
    val sisteDagForYtelse: LocalDate = LocalDate.of(yearMonthTom.year, yearMonthTom.month, 1)

    return MedlOppslagRequest(fnr = fnr, førsteDagForYtelse = førsteDagForYtelse.toString(), periode = Periode(førsteDagForYtelse.toString(), sisteDagForYtelse.toString()), brukerinput = Brukerinput(false))


}

fun mapRegelmotorResponsTilPoppRespons(regelmotorRespons: String, referanse: String): PoppRespons {
    val regelmotorRespons = JacksonParser().parseMedlemskap(regelmotorRespons)
    val svar = regelmotorRespons.resultat.svar
    val fom = regelmotorRespons.datagrunnlag.førsteDagForYtelse.minusYears(1)
    val tom = regelmotorRespons.datagrunnlag.førsteDagForYtelse
    //TODO denne businesslogikken må bekreftes
    when(svar) {
        "JA" -> return PoppRespons(regelmotorRespons.datagrunnlag.fnr, referanse = referanse, medlemperioder = listOf(Medlemperioder(fom, tom, Status.JA)), status = Status.JA)

    }
    return PoppRespons(regelmotorRespons.datagrunnlag.fnr, referanse = referanse, medlemperioder = emptyList(), status = Status.UAVKLART)
}

fun erDatoerSammenhengende(sluttDato: LocalDate, startDato: LocalDate?, tillatDagersHullIPeriode: Long): Boolean =
    sluttDato.isAfter(startDato?.minusDays(tillatDagersHullIPeriode))

fun PoppRequest.validerPerioder(): Boolean{
    var forrigeTilDato: LocalDate = 0
    val sortertePerioder = this.perioder.sortedBy { it.fraOgMed }.first().fraOgMed
    return sortertePerioder
}
