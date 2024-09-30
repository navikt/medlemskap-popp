package no.nav.medlemskap.popp.clients

import io.github.resilience4j.retry.Retry
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments
import no.nav.medlemskap.hjelpemidler.clients.medlemskapoppslag.MedlOppslagRequest
import no.nav.medlemskap.popp.http.runWithRetryAndMetrics
import no.nav.medlemskap.popp.jackson.JacksonParser

class MedlemskapOppslagClient(private val baseUrl: String,
                              private val azureAdClient: AzureAdClient,
                              private val httpClient: HttpClient,
                              private val retry: Retry? = null):LovemeAPI{
    private val secureLogger = KotlinLogging.logger("tjenestekall")
    override suspend fun vurderMedlemskap(medlOppslagRequest: MedlOppslagRequest, callId:String):String{
        secureLogger.info ("kaller regelmotor",
            StructuredArguments.kv("request", JacksonParser().ToJson(medlOppslagRequest).toPrettyString()),
            StructuredArguments.kv("callId", callId)
        )

        val token = azureAdClient.hentTokenScopetMotMedlemskapOppslag()
        return runWithRetryAndMetrics("MEDL-OPPSLAG", "vurdermedlemskap", retry) {
            httpClient.post {
                url("$baseUrl/vurdering")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${token.token}")
                header("Nav-Call-Id", callId)
                header("X-Correlation-Id", callId)
                setBody(medlOppslagRequest)
            }.body()
        }
    }
}


interface LovemeAPI{
    suspend fun vurderMedlemskap(medlOppslagRequest: MedlOppslagRequest,callId: String):String
}