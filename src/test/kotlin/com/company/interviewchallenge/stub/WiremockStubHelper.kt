package com.company.interviewchallenge.stub

import com.github.tomakehurst.wiremock.WireMockServer

class WiremockStubHelper(private val server: WireMockServer) {
    val simpleDebit = SimpleDebitStub(server)
}