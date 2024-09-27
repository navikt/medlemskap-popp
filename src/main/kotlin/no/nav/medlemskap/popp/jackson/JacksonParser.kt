package no.nav.medlemskap.popp.jackson

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.kotlin.treeToValue
import mu.KotlinLogging
import no.nav.medlemskap.popp.domain.Medlemskap




class JacksonParser {
    private val log = KotlinLogging.logger { }



    fun parseMedlemskap(jsonString: String): Medlemskap {
        try {
            val mapper: ObjectMapper = ObjectMapper()
                .registerKotlinModule()
                .findAndRegisterModules()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            return mapper.readValue(jsonString)
        } catch (t: Throwable) {
            log.error("Unable to parse json. Dropping message. Cause : ${t.message}")
            throw t;
        }
    }


    fun parse(obj: Any): String {
        try {
            val mapper: ObjectMapper = ObjectMapper()
                .registerKotlinModule()
                .findAndRegisterModules()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            return mapper.writeValueAsString(obj)
        } catch (t: Throwable) {
            log.error("Unable to parse json. Dropping message. Cause : ${t.message}")
            throw t;
        }
    }
    fun ToJson(obj: Any): JsonNode {
        val mapper: ObjectMapper = ObjectMapper()
            .registerKotlinModule()
            .findAndRegisterModules()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        return  mapper.valueToTree(obj);

    }
    fun ToJson(string: String): JsonNode {
        val mapper: ObjectMapper = ObjectMapper()
            .registerKotlinModule()
            .findAndRegisterModules()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        return  mapper.readTree(string);

    }

    inline fun <reified T> toDomainObject(jsonNode: JsonNode): T {
        val mapper: ObjectMapper = ObjectMapper()
            .registerKotlinModule()
            .findAndRegisterModules()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        return mapper.treeToValue(jsonNode)

    }
    inline fun <reified T> toDomainObject(jsonString: String): T {
        val mapper: ObjectMapper = ObjectMapper()
            .registerKotlinModule()
            .findAndRegisterModules()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        return mapper.readValue<T>(jsonString)

    }
}