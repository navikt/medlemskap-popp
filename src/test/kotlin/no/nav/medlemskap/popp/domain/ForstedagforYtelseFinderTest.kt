package no.nav.medlemskap.popp.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.util.*


class ForstedagforYtelseFinderTest {


    @Test
    fun `perode kortere en 12 mnd, men mer en 6 mnd`() {
        val fnr = "12345678901"
        val fom = YearMonth.of(2023,Month.MARCH)
        val tom = YearMonth.of(2023,Month.DECEMBER)
        val ref = UUID.randomUUID().toString()
        val landstilknytning = "Norge"
        val periode1 = TrygdePeriode(
            fraOgMed = fom,
            tilOgMed = tom,
            omsorgsmottaker = fnr,
            landstilknytning = landstilknytning)
        val poppRequest = PoppRequest(omsorgsyter = fnr, perioder = listOf(periode1),
            referanse = ref)
        fom.atDay(1).plusYears(1)
        val expectedRespons = fom.atDay(1).plusYears(1)
        val forstedagForYtelse = ForstedagforYtelseFinder().finnFørsteDagForYtelse(poppRequest)
        Assertions.assertEquals(expectedRespons,forstedagForYtelse,"førstedag for ytelse skal være 1 år fra første periode fom ved perioder <12 mnd >6 mnd")

    }
    @Test
    fun `perode kortere en 6 mnd på staren av året`() {
        val fnr = "12345678901"
        val fom = YearMonth.of(2023,Month.JANUARY)
        val tom = YearMonth.of(2023,Month.MAY)
        val ref = UUID.randomUUID().toString()
        val landstilknytning = "Norge"
        val periode1 = TrygdePeriode(
            fraOgMed = fom,
            tilOgMed = tom,
            omsorgsmottaker = fnr,
            landstilknytning = landstilknytning)
        val poppRequest = PoppRequest(omsorgsyter = fnr, perioder = listOf(periode1),
            referanse = ref)
        val expectedRespons = fom.atDay(1).plusYears(1)
        val forstedagForYtelse = ForstedagforYtelseFinder().finnFørsteDagForYtelse(poppRequest)
        Assertions.assertEquals(expectedRespons,forstedagForYtelse,"førstedag for ytelse skal være 1 år fra første periode fom ved perioder <12 mnd >6 mnd")

    }
    @Test
    fun `perode kortere en 6 mnd på slutten av året`() {
        val fnr = "12345678901"
        val fom = YearMonth.of(2023,Month.JULY)
        val tom = YearMonth.of(2023,Month.DECEMBER)
        val ref = UUID.randomUUID().toString()
        val landstilknytning = "Norge"
        val periode1 = TrygdePeriode(
            fraOgMed = fom,
            tilOgMed = tom,
            omsorgsmottaker = fnr,
            landstilknytning = landstilknytning)
        val poppRequest = PoppRequest(omsorgsyter = fnr, perioder = listOf(periode1),
            referanse = ref)
        val expectedRespons = LocalDate.of(fom.year,fom.monthValue.toInt(),1).plusYears(1)
        val forstedagForYtelse = ForstedagforYtelseFinder().finnFørsteDagForYtelse(poppRequest)
        Assertions.assertEquals(expectedRespons,forstedagForYtelse,"førstedag for ytelse skal være 1 år fra første periode fom ved perioder <12 mnd >6 mnd")

    }
    @Test
    fun `Scenario 1 - barnetrygd hele året`() {
        val fnr = "12345678901"
        val fom = YearMonth.of(2023,Month.JANUARY)
        val tom = YearMonth.of(2023,Month.DECEMBER)
        val ref = UUID.randomUUID().toString()
        val landstilknytning = "Norge"
        val periode1 = TrygdePeriode(
            fraOgMed = fom,
            tilOgMed = tom,
            omsorgsmottaker = fnr,
            landstilknytning = landstilknytning)
        val poppRequest = PoppRequest(omsorgsyter = fnr, perioder = listOf(periode1),
            referanse = ref)
        val expectedRespons = LocalDate.of(fom.year,fom.monthValue.toInt(),1).plusYears(1)
        val forstedagForYtelse = ForstedagforYtelseFinder().finnFørsteDagForYtelse(poppRequest)
        Assertions.assertEquals(expectedRespons,forstedagForYtelse,"førstedag for ytelse skal være siste dag perioden dersom periode er 12 mnd")

    //Scenario 2a: Perioden for barnetrygd er kortere enn 12 måneder
    }


    @Test
    fun `Scenario 1 innutflytting mindre en 7 mnd, tidlig paa aaret`() {
        val fnr = "12345678901"
        val fom = YearMonth.of(2023,Month.JANUARY)
        val tom = YearMonth.of(2023,Month.JULY)
        val ref = UUID.randomUUID().toString()
        val landstilknytning = "Norge"
        val periode1 = TrygdePeriode(
            fraOgMed = fom,
            tilOgMed = tom,
            omsorgsmottaker = fnr,
            landstilknytning = landstilknytning)
        val poppRequest = PoppRequest(omsorgsyter = fnr, perioder = listOf(periode1),
            referanse = ref)
        val expectedRespons = tom.atDay(31)
        val forstedagForYtelse = ForstedagforYtelseFinder().finnFørsteDagForYtelseVedInnUtflytting(poppRequest)
        Assertions.assertEquals(expectedRespons,forstedagForYtelse,"førstedag for ytelse skal være siste dag perioden dersom periode er 12 mnd")

        //Scenario 2a: Perioden for barnetrygd er kortere enn 12 måneder
    }
    @Test
    fun `Scenario 2 innutflytting mindre en 7 mnd, sent paa aaret`() {
        val fnr = "12345678901"
        val fom = YearMonth.of(2023,Month.JUNE)
        val tom = YearMonth.of(2023,Month.DECEMBER)
        val ref = UUID.randomUUID().toString()
        val landstilknytning = "Norge"
        val periode1 = TrygdePeriode(
            fraOgMed = fom,
            tilOgMed = tom,
            omsorgsmottaker = fnr,
            landstilknytning = landstilknytning)
        val poppRequest = PoppRequest(omsorgsyter = fnr, perioder = listOf(periode1),
            referanse = ref)
        val expectedRespons = fom.atDay(1).plusYears(1)
        val forstedagForYtelse = ForstedagforYtelseFinder().finnFørsteDagForYtelseVedInnUtflytting(poppRequest)
        Assertions.assertEquals(expectedRespons,forstedagForYtelse,"førstedag for ytelse skal være siste dag perioden dersom periode er 12 mnd")

    }

}