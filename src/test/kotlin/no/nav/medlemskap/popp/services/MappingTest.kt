package no.nav.medlemskap.popp.services

import no.nav.medlemskap.hjelpemidler.clients.medlemskapoppslag.Periode
import no.nav.medlemskap.popp.domain.PoppRequest
import no.nav.medlemskap.popp.domain.PoppRespons
import no.nav.medlemskap.popp.domain.Status
import no.nav.medlemskap.popp.domain.TrygdePeriode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class MappingTest {
    @Test
    fun mapperPoppRequestTilMedlemskapRequest() {
        val fnr = "12345678901"
        val fom = YearMonth.now()
        val tom = YearMonth.now().plusMonths(1)
        val ref = UUID.randomUUID().toString()
        val landstilknytning = "Norge"
        val poppRequest = PoppRequest(omsorgsyter = fnr, perioder = listOf(
            TrygdePeriode(
                fraOgMed = fom,
                tilOgMed = tom,
                omsorgsmottaker = fnr,
                landstilknytning = landstilknytning)
        ),
                referanse = ref)
        val request = mapMedlemskapRequestFromPoppRequest(poppRequest)
        val expectedFomDato = LocalDate.of(fom.year, fom.month, 1).toString()
        val expectedTomDato = LocalDate.of(tom.year, tom.month, 1).toString()
        Assertions.assertEquals(fnr, request.fnr)
        Assertions.assertTrue(expectedFomDato == request.førsteDagForYtelse)
        Assertions.assertFalse(request.brukerinput.arbeidUtenforNorge)
        Assertions.assertTrue(request.periode.fom == expectedFomDato)
        Assertions.assertTrue(request.periode.tom == expectedTomDato)
    }

    @Test
    fun mapperPoppRequestTilMedlemskapRequestMedFlerePerioder() {
        val fnr = "12345678901"
        val fom = YearMonth.now()
        val tom = YearMonth.now().plusMonths(1)
        val ref = UUID.randomUUID().toString()
        val landstilknytning = "Norge"
        val periode1 = TrygdePeriode(
            fraOgMed = fom,
            tilOgMed = tom,
            omsorgsmottaker = fnr,
            landstilknytning = landstilknytning)
        val periode2 = TrygdePeriode(
            fraOgMed = fom.plusMonths(2),
            tilOgMed = tom.plusMonths(3),
            omsorgsmottaker = fnr,
            landstilknytning = landstilknytning)
        val poppRequest = PoppRequest(omsorgsyter = fnr, perioder = listOf(periode1, periode2,),
            referanse = ref)
        val request = mapMedlemskapRequestFromPoppRequest(poppRequest)
        val expectedFomDato = LocalDate.of(periode1.fraOgMed.year, periode1.fraOgMed.month, 1).toString()
        val expectedTomDato = LocalDate.of(periode2.tilOgMed.year, periode2.tilOgMed.month, 1).toString()
        Assertions.assertEquals(fnr, request.fnr)
        Assertions.assertTrue(expectedFomDato == request.førsteDagForYtelse)
        Assertions.assertFalse(request.brukerinput.arbeidUtenforNorge)
        Assertions.assertTrue(request.periode.fom == expectedFomDato)
        Assertions.assertTrue(request.periode.tom == expectedTomDato)
    }
    @Test
    fun mapLovmeResponsTilPoppResponsTest() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val PoppRespons = mapRegelmotorResponsTilPoppRespons(fileContent, "1")
        Assertions.assertEquals("12345678901", PoppRespons.fnr, "Fødselsnummer er ikke mappet korrekt")
        Assertions.assertEquals("1", PoppRespons.referanse, "Referanse er ikke mappet korrekt")
        Assertions.assertEquals(Status.JA, PoppRespons.status, "Status er ikke mappet korrekt")
        Assertions.assertTrue(PoppRespons.årsaker.isEmpty(), "Årsaker er ikke mappet korrekt")

    }

}
