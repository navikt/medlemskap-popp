package no.nav.medlemskap.popp.services

import no.nav.medlemskap.popp.domain.PoppRequest
import no.nav.medlemskap.popp.domain.TrygdePeriode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*


class PoppServiceTest {

    @Test
    fun `Tester perioder fra request med hull`(){
        val poppRequest = PoppRequest(omsorgsyter = "12345678901", perioder = listOf(
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 1),
                tilOgMed = YearMonth.of(2018, 8),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" ),
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 9),
                tilOgMed = YearMonth.of(2019, 9),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
        ),
            referanse = "ref")
        Assertions.assertFalse(poppRequest.validerPerioder())
    }

}

