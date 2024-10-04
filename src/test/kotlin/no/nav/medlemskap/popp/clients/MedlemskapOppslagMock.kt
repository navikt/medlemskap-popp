package no.nav.medlemskap.popp.clients

import io.ktor.client.plugins.*
import io.ktor.http.*
import no.nav.medlemskap.hjelpemidler.clients.medlemskapoppslag.MedlOppslagRequest
import org.apache.http.client.HttpResponseException


class MedlemskapOppslagMock():LovemeAPI {

    override suspend fun vurderMedlemskap(medlOppslagRequest: MedlOppslagRequest, callId: String): String {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        return fileContent
    }
}

class MedlemskapOppslagTimeOutMock():LovemeAPI{
    override suspend fun vurderMedlemskap(medlOppslagRequest: MedlOppslagRequest, callId: String): String {

        throw HttpRequestTimeoutException(url = "",timeoutMillis = 10)
    }
}
class MedlemskapOException():LovemeAPI{
    override suspend fun vurderMedlemskap(medlOppslagRequest: MedlOppslagRequest, callId: String): String {

        throw HttpResponseException(HttpStatusCode.RequestTimeout.value,"")
    }
}

class MedlemskapOppslagGradertAdresseExceptionMock():LovemeAPI{
    override suspend fun vurderMedlemskap(medlOppslagRequest: MedlOppslagRequest, callId: String): String {
        throw HttpResponseException(503,"GradertAdresseException. Lovme skal ikke  kalles for personer med kode 6/7")
    }
}