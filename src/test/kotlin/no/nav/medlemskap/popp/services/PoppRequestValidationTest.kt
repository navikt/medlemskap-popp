package no.nav.medlemskap.popp.services

import no.nav.medlemskap.popp.domain.PoppRequest
import no.nav.medlemskap.popp.domain.TrygdePeriode
import no.nav.medlemskap.popp.domain.erPerioderSammenhengende
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.YearMonth


class PoppRequestValidationTest {

    @Test
    fun `Tester perioder fra request uten hull`(){
        val poppRequest = PoppRequest(omsorgsyter = "12345678901", perioder = listOf(
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 1),
                tilOgMed = YearMonth.of(2018, 8),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" ),
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 9),
                tilOgMed = YearMonth.of(2018, 10),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
        ,
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 11),
                tilOgMed = YearMonth.of(2018, 12),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
        ),
            referanse = "ref")
        Assertions.assertTrue(poppRequest.erPerioderSammenhengende(),"Perioder er sammengenhengende og skal svare TRUE")
    }

    @Test
    fun `Tester perioder fra request med hull`(){
        val poppRequest = PoppRequest(omsorgsyter = "12345678901", perioder = listOf(
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 1),
                tilOgMed = YearMonth.of(2018, 8),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" ),
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 11),
                tilOgMed = YearMonth.of(2018, 12),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
        ),
            referanse = "ref")
        Assertions.assertFalse(poppRequest.erPerioderSammenhengende(),"Perioder er sammengenhengende og skal svare TRUE")
    }
    @Test
    fun `Tester perioder fra request uten hull med med flere barn`(){
        val poppRequest = PoppRequest(omsorgsyter = "12345678901", perioder = listOf(
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 1),
                tilOgMed = YearMonth.of(2018, 8),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" ),
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 9),
                tilOgMed = YearMonth.of(2018, 10),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
            ,
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 11),
                tilOgMed = YearMonth.of(2018, 12),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
        ,
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 1),
                tilOgMed = YearMonth.of(2018, 8),
                omsorgsmottaker = "987654321",
                landstilknytning = "Norge" ),
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 9),
                tilOgMed = YearMonth.of(2018, 10),
                omsorgsmottaker = "987654321",
                landstilknytning = "Norge" )
            ,
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 11),
                tilOgMed = YearMonth.of(2018, 12),
                omsorgsmottaker = "987654321",
                landstilknytning = "Norge" )
        ),
            referanse = "ref")
        Assertions.assertTrue(poppRequest.erPerioderSammenhengende(),"Perioder er sammengenhengende og skal svare TRUE")
    }
    @Test
    fun `Tester perioder fra request uten hull med med flere barn med ulike perioder`(){
        val poppRequest = PoppRequest(omsorgsyter = "12345678901", perioder = listOf(
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 1),
                tilOgMed = YearMonth.of(2018, 8),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" ),
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 9),
                tilOgMed = YearMonth.of(2018, 10),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
            ,
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 11),
                tilOgMed = YearMonth.of(2018, 12),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
            ,
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 1),
                tilOgMed = YearMonth.of(2018, 10),
                omsorgsmottaker = "987654321",
                landstilknytning = "Norge" ),
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 11),
                tilOgMed = YearMonth.of(2018, 12),
                omsorgsmottaker = "987654321",
                landstilknytning = "Norge" )
        ),
            referanse = "ref")
        Assertions.assertTrue(poppRequest.erPerioderSammenhengende(),"Perioder er sammengenhengende og skal svare TRUE")
    }
    @Test
    fun `Tester perioder med bare ett innslag`(){
        val poppRequest = PoppRequest(omsorgsyter = "12345678901", perioder = listOf(
            TrygdePeriode(
                fraOgMed = YearMonth.of(2018, 1),
                tilOgMed = YearMonth.of(2018, 8),
                omsorgsmottaker = "12345678901",
                landstilknytning = "Norge" )
        ),
            referanse = "ref")
        Assertions.assertTrue(poppRequest.erPerioderSammenhengende(),"Perioder er sammengenhengende og skal svare TRUE")
    }

}

