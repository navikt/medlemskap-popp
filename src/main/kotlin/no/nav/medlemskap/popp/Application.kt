package no.nav.medlemskap.popp

import no.nav.medlemskap.popp.config.Environment
import no.nav.medlemskap.popp.rest.createHttpServer
import no.nav.medlemskap.popp.services.PoppService
import org.slf4j.Logger
import org.slf4j.LoggerFactory


fun main() {
    Application().start()
}

class Application(private val env: Environment = System.getenv()) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(Application::class.java)
    }

    fun start() {
        log.info("Start application")
        val PoppService = PoppService()
        createHttpServer(PoppService).start(wait = true)
    }
}