package com.company.interviewchallenge.infraestructure.stub

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching

class SimpleDebitStub(private val server: WireMockServer) {

    fun stubGetMerchants(responseBody: String) {
        server.stubFor(
            get(urlMatching("/merchants"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)
                )
        )
    }

    fun stubGerMerchantsWithError() {
        server.stubFor(
            get(urlMatching("/merchants"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(400)
                )
        )
    }
}