package no.nav.medlemskap.popp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.nav.medlemskap.popp.clients.AzureAdClient
import no.nav.medlemskap.popp.config.Configuration

fun Routing.devgcpRoutes() {
    get("/token") {
        try {
            val client = AzureAdClient(Configuration())

            call.respondText(client.hentTokenScopetMotSelf().token, ContentType.Text.Plain, HttpStatusCode.OK)
        }
        catch (e:Exception){
            call.respond(status = HttpStatusCode.InternalServerError,e.message!!)
        }

    }

}