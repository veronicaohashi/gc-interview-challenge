package com.company.interviewchallenge

import com.company.interviewchallenge.stub.WiremockStubHelper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [InterviewChallengeApplication::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class IntegrationTest {

    lateinit var stub: WiremockStubHelper

    companion object {
        val server = WireMockServer(
            WireMockConfiguration
                .wireMockConfig()
                .port(9981)
                .notifier(ConsoleNotifier(true))
        )
    }

    @BeforeEach
    fun setup() {
        if (server.isRunning) {
            server.resetAll()
        } else {
            server.start()
            stub = WiremockStubHelper(server)
        }
    }

    @AfterEach
    fun teardown() {
        server.stop()
    }

}
