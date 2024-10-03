package no.nav.medlemskap.popp.domain

import com.fasterxml.jackson.datatype.jsr310.deser.key.YearMonthKeyDeserializer
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.temporal.ChronoUnit

class ForstedagforYtelseFinder {

    fun finnFørsteDagForYtelse(request: PoppRequest) : LocalDate{
        val forsteFraOgMed = request.perioder.sortedBy { it.fraOgMed }.first().fraOgMed
        return forsteFraOgMed.plusYears(1).atDay(1)

    }
    fun finnFørsteDagForYtelseVedInnUtflytting(request: PoppRequest) : LocalDate {
        val forsteFraOgMed = request.perioder.sortedBy { it.fraOgMed }.first().fraOgMed
        val sisteTilOgMed = request.perioder.sortedBy { it.fraOgMed }.last().tilOgMed
        val antallmnd = ChronoUnit.MONTHS.between(forsteFraOgMed, sisteTilOgMed)

        when {
            sisteTilOgMed.isBefore(YearMonth.of(sisteTilOgMed.year, Month.AUGUST)) -> return sisteTilOgMed.atDay(31)
            forsteFraOgMed.isAfter(YearMonth.of(forsteFraOgMed.year, Month.MAY)) -> return forsteFraOgMed.atDay(1).plusYears(1)
            else ->  return forsteFraOgMed.plusYears(1).atDay(1)
        }
    }
}