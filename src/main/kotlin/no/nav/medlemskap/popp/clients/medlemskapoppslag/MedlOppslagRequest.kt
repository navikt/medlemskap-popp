package no.nav.medlemskap.hjelpemidler.clients.medlemskapoppslag

import java.time.LocalDate
import java.util.*

data class MedlOppslagRequest(
    val fnr: String,
    val førsteDagForYtelse:String,
    val periode: Periode,
    val brukerinput: Brukerinput
)

data class Periode(val fom: String, val tom: String= LocalDate.now().toString())

data class Brukerinput(
    val arbeidUtenforNorge: Boolean
)