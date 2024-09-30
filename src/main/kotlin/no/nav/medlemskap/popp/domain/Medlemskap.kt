package no.nav.medlemskap.popp.domain

import java.time.LocalDate

data class Medlemskap(
    val resultat:Resultat,
    val datagrunnlag: Datagrunnlag
)
data class Datagrunnlag(
    val fnr: String,
    val førsteDagForYtelse: LocalDate
)

data class Resultat(
    val svar:String,
    val årsaker :List<Arsak> = emptyList()
)
data class Arsak(
    val regelId : String,
    val avklaring : String,
    val svar: String,
    val begrunnelse:String
)