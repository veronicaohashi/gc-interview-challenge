package com.company.interviewchallenge

import com.company.interviewchallenge.infraestructure.stub.SimpleDebitStub
import com.github.tomakehurst.wiremock.WireMockServer

class WiremockStubHelper(private val server: WireMockServer) {
    val simpleDebit = SimpleDebitStub(server)
}