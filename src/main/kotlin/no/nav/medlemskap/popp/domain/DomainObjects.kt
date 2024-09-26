package no.nav.medlemskap.popp.domain

data class PoppRequest (val fnr:String)

data class PoppRespons (val status: Status)

enum class Status{
    JA,
    NEI,
    UAVKLART
}