package no.nav.medlemskap.popp.jackson

import no.nav.medlemskap.popp.domain.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

class parserTests {
    @Test
    fun parseRequestObject() {
        val request = PoppRequest(
            omsorgsyter = "12345678901",
            perioder = listOf(
                TrygdePeriode(
                    fraOgMed = YearMonth.now().minusYears(1),
                    tilOgMed = YearMonth.now(),
                    omsorgsmottaker = "01987654321",
                    landstilknytning = "NOR"
                )

            ),
            referanse = UUID.randomUUID().toString(),
        )
        println(JacksonParser().parse(request))

    }

    @Test
    fun parseRespons() {
        val respons = PoppRespons(
            fnr = "01987654321",
            referanse = UUID.randomUUID().toString(),
            medlemperioder = listOf(
                Medlemperioder(
                    fom = LocalDate.parse("2019-09-01"),
                    tom = LocalDate.parse("2019-09-30"),
                    status = Status.JA
                ),
                Medlemperioder(
                    fom = LocalDate.parse("2019-10-01"),
                    tom = LocalDate.parse("2019-10-25"),
                    status = Status.NEI
                ),
                Medlemperioder(
                    fom = LocalDate.parse("2019-10-26"),
                    tom = LocalDate.parse("2019-11-30"),
                    status = Status.UAVKLART
                )
            ),
            årsaker = listOf("REGEL_19","REGEL_21"),
            status = Status.UAVKLART,
        )
        println(JacksonParser().parse(respons))
    }
}