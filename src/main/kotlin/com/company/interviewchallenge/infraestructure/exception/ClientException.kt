package com.company.interviewchallenge.infraestructure.exception

class ClientException(
    val status: Int,
    val reason: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(reason, cause)