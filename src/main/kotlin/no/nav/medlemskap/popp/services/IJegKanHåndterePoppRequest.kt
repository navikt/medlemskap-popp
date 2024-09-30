package no.nav.medlemskap.popp.services

import no.nav.medlemskap.popp.domain.PoppRequest
import no.nav.medlemskap.popp.domain.PoppRespons

interface IJegKanHåndterePoppRequest {
    suspend fun handleRequest(poppRequest: PoppRequest):PoppRespons
}
