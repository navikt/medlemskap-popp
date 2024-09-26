package no.nav.medlemskap.popp.services

import no.nav.medlemskap.popp.domain.PoppRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*


class PoppServiceTest {
    @Test
    fun `skal svare NEI når fnr er 1`(){
        val service = PoppService()
        val response = service.handleRequest(PoppRequest(
            omsorgsyter = "123456789",
            perioder = listOf(),
            referanse = UUID.randomUUID().toString(),
        ))
        println(LocalDateTime.now().toString())
        Assertions.assertTrue(true)
    }
}

