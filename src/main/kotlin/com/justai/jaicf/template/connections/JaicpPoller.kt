package com.justai.jaicf.template.connections

import com.justai.jaicf.channel.jaicp.JaicpPollingConnector
import com.justai.jaicf.channel.aimybox.AimyboxChannel
import com.justai.jaicf.template.accessToken
import com.justai.jaicf.template.templateBot

fun main() {
    JaicpPollingConnector(
        templateBot,
        accessToken,
        channels = listOf(
            AimyboxChannel
        )
    ).runBlocking()
}