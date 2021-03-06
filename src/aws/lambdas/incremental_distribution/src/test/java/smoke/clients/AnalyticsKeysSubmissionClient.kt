package smoke.clients

import org.http4k.client.JavaHttpClient
import org.http4k.core.*
import org.slf4j.LoggerFactory
import smoke.env.EnvConfig

class AnalyticsKeysSubmissionClient(private val client: JavaHttpClient, private val config: EnvConfig) {


    companion object {
        private val logger = LoggerFactory.getLogger(AnalyticsKeysSubmissionClient::class.java)

        fun baseUrlFrom(config: EnvConfig) = config.analyticsSubmissionEndpoint
    }

    fun invokeMobileAnalytics() {
        AwsLambda.invokeFunction(config.analytics_processing_function)
            .requireStatusCode(Status.OK)
            .requireBodyText("\"success\"")
    }

    fun upload(json: String): Response {
        logger.info("uploadResult")

        val uri = baseUrlFrom(config)

        val request = Request(Method.POST, uri)
            .header("Authorization", config.authHeaders.mobile)
            .header("Content-Type", ContentType("text/json").value)
            .body(json)

        return client(request)
    }

}