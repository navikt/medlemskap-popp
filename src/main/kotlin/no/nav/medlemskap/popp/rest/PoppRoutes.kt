package no.nav.medlemskap.popp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.cancel
import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments
import no.nav.medlemskap.popp.domain.PoppRequest
import no.nav.medlemskap.popp.services.PoppService
import no.nav.medlemskap.popp.services.erPerioderSammenhengende

import java.util.*
private val logger = KotlinLogging.logger { }
private val secureLogger = KotlinLogging.logger("tjenestekall")
fun Routing.PoppRoutes(PoppService: PoppService) {
    authenticate("azureAuth") {
        post("/vurdering") {
            val callerPrincipal: JWTPrincipal = call.authentication.principal()!!
            val azp = callerPrincipal.payload.getClaim("azp").asString()
            secureLogger.info("EvalueringRoute: azp-claim i principal-token: {} ", azp)
            val callId = call.callId ?: UUID.randomUUID().toString()
            logger.info(
                "kall autentisert, url : /vurdering",
                StructuredArguments.kv("callId", callId),
                StructuredArguments.kv("endpoint", "vurdering")
            )
            val request = call.receive<PoppRequest>()
            if (request.perioder.isEmpty()){
                call.respond(HttpStatusCode.BadRequest,"ingen perioder i request")
                return@post
            }
            if (!request.erPerioderSammenhengende()){
                call.respond(HttpStatusCode.NotImplemented,"tjenesten støtter ikke vurderinger med hull i periodene")
                return@post
            }
            try {
                val respons = PoppService.handleRequest(request)
                call.respond(HttpStatusCode.OK,respons)
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.InternalServerError, t.message!!)
            }
        }
    }
}
