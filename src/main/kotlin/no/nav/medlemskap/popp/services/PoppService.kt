package no.nav.medlemskap.popp.services

import no.nav.medlemskap.popp.domain.PoppRequest
import no.nav.medlemskap.popp.domain.PoppRespons
import no.nav.medlemskap.popp.domain.Status
import kotlin.random.Random

class PoppService:IJegKanHåndterePoppRequest {
    override fun handleRequest(PoppRequest: PoppRequest):PoppRespons {


        if (Random.nextBoolean()){
            return PoppRespons(Status.NEI)
        }
        return PoppRespons(Status.JA)
    }
}