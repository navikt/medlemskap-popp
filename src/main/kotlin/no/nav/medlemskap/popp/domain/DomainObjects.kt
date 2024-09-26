package no.nav.medlemskap.popp.domain

import java.time.LocalDate
import java.util.UUID


data class PoppRespons (val status: Status)

enum class Status{
    JA,
    NEI,
    UAVKLART
}

data class PoppRequest (
    val omsorgsyter:String,
    val perioder: List<TrygdePeriode>,
    val referanse: String

)

data class TrygdePeriode(
    val fraOgMed: LocalDate,
    val tilOgMed: LocalDate,
    val omsorgsmottaker: String,
    val landstilknytning: String,
)