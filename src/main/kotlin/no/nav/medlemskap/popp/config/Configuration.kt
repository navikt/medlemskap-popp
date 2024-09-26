package no.nav.medlemskap.popp.config

import com.natpryce.konfig.*
import mu.KotlinLogging
import java.io.File
import java.io.FileNotFoundException

private val logger = KotlinLogging.logger { }

private val defaultProperties = ConfigurationMap(
    mapOf(
        "AZURE_APP_WELL_KNOWN_URL" to "https://login.microsoftonline.com/966ac572-f5b7-4bbe-aa88-c76419c0f851/v2.0/.well-known/openid-configuration",
        "AZURE_OPENID_CONFIG_TOKEN_ENDPOINT" to "https://login.microsoftonline.com/966ac572-f5b7-4bbe-aa88-c76419c0f851/oauth2/v2.0/token",
        "AZURE_TENANT" to "966ac572-f5b7-4bbe-aa88-c76419c0f851",
        "AZURE_AUTHORITY_ENDPOINT" to "https://login.microsoftonline.com",
        "SERVICE_USER_USERNAME" to "test",
        "AZURE_APP_CLIENT_SECRET" to "",
        "SECURITY_TOKEN_SERVICE_URL" to "",
        "SECURITY_TOKEN_SERVICE_REST_URL" to "",
        "SECURITY_TOKEN_SERVICE_API_KEY" to "",
        "SERVICE_USER_PASSWORD" to "",
        "MEDL_OPPSLAG_API_KEY" to "",
        "MEDL_OPPSLAG_BASE_URL" to "https://medlemskap-oppslag.dev.intern.nav.no",
        "MEDL_OPPSLAG_CLIENT_ID" to "2719da58-489e-4185-9ee6-74b7e93763d2",
        "NAIS_APP_NAME" to "",
        "NAIS_CLUSTER_NAME" to "dev-gcp",
        "NAIS_APP_IMAGE" to "",
        "AZURE_APP_CLIENT_ID" to "1305007d-29b8-441d-b87c-3139be10c1dc",
        "AZURE_APP_TENANT_ID" to "966ac572-f5b7-4bbe-aa88-c76419c0f851",
    )
)

private val config = ConfigurationProperties.systemProperties() overriding
    EnvironmentVariables overriding
    defaultProperties

private fun String.configProperty(): String = config[Key(this, stringType)]

private fun String.readFile() =
    try {
        logger.info { "Leser fra azure-fil $this" }
        File(this).readText(Charsets.UTF_8)
    } catch (err: FileNotFoundException) {
        logger.warn { "Azure fil ikke funnet" }
        null
    }

private fun hentCommitSha(image: String): String {
    val parts = image.split(":")
    if (parts.size == 1) return image
    return parts[1].substring(0, 7)
}

data class Configuration(
    val register: Register = Register(),
    val azureAd: AzureAd = AzureAd(),
    val cluster: String = "NAIS_CLUSTER_NAME".configProperty(),
    val commitSha: String = hentCommitSha("NAIS_APP_IMAGE".configProperty())
) {
    data class AzureAd(
        val clientId: String = "AZURE_APP_CLIENT_ID".configProperty(),
        val clientSecret: String = "AZURE_APP_CLIENT_SECRET".configProperty(),
        val jwtAudience: String = "AZURE_APP_CLIENT_ID".configProperty(),
        val tokenEndpoint: String = "AZURE_OPENID_CONFIG_TOKEN_ENDPOINT".configProperty().removeSuffix("/"),
        val azureAppWellKnownUrl: String = "AZURE_APP_WELL_KNOWN_URL".configProperty().removeSuffix("/"),
        val authorityEndpoint: String = "AZURE_AUTHORITY_ENDPOINT".configProperty().removeSuffix("/"),
        val tenant: String = "AZURE_APP_TENANT_ID".configProperty()
    )
    data class Register(
        val medlemskapOppslagBaseUrl: String = "MEDL_OPPSLAG_BASE_URL".configProperty(),
        val medlemskapOppslagClientID: String = "MEDL_OPPSLAG_CLIENT_ID".configProperty(),

    )

}


