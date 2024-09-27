package no.nav.medlemskap.popp.services

import no.nav.medlemskap.popp.domain.Medlemperioder
import no.nav.medlemskap.popp.domain.PoppRequest
import no.nav.medlemskap.popp.domain.PoppRespons
import no.nav.medlemskap.popp.domain.Status
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

class PoppService:IJegKanHåndterePoppRequest {
    override fun handleRequest(PoppRequest: PoppRequest):PoppRespons {


        if (Random.nextBoolean()){
            return PoppRespons(
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
        }
        return PoppRespons(
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
                    status = Status.JA
                ),
                Medlemperioder(
                    fom = LocalDate.parse("2019-10-26"),
                    tom = LocalDate.parse("2019-11-30"),
                    status = Status.JA
                )
            ),
            årsaker = emptyList(),
            status = Status.JA,
        )
    }
}