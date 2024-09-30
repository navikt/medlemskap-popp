package no.nav.medlemskap.popp.domain

import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID


enum class Status{
    JA,
    NEI,
    UAVKLART
}

data class PoppRespons (
    val fnr: String,
    val referanse: String,
    val medlemperioder: List<Medlemperioder>,
    val årsaker: List<String> = emptyList(),
    val status: Status

)

data class Medlemperioder (
    val fom: LocalDate,
    val tom: LocalDate,
    val status: Status
)

data class PoppRequest (
    val omsorgsyter:String,
    val perioder: List<TrygdePeriode>,
    val referanse: String

)

data class TrygdePeriode(
    val fraOgMed: YearMonth,
    val tilOgMed: YearMonth,
    val omsorgsmottaker: String,
    val landstilknytning: String,
)
