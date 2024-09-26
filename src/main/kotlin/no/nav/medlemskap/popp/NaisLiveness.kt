package no.nav.medlemskap.popp

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.prometheus.client.exporter.common.TextFormat
import mu.KotlinLogging
import no.nav.medlemskap.popp.Metrics
import no.nav.medlemskap.popp.rest.writeMetrics004

private val logger = KotlinLogging.logger { }
private val secureLogger = KotlinLogging.logger("tjenestekall")

fun Routing.naisRoutes(

) {
    get("/isAlive") {

            call.respondText("Alive!", ContentType.Text.Plain, HttpStatusCode.OK)

    }
    get("/isReady") {
        call.respondText("Ready!", ContentType.Text.Plain, HttpStatusCode.OK)
    }
    get("/metrics") {
        call.respondTextWriter(ContentType.parse(TextFormat.CONTENT_TYPE_004)) {
            writeMetrics004(this, Metrics.registry)
        }
    }



}
data class Dependencies(val hashMap: MutableMap<String,Boolean>, val message:String?)