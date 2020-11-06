package com.justai.jaicf.template.connections

import com.justai.jaicf.channel.jaicp.JaicpServer
import com.justai.jaicf.channel.aimybox.AimyboxChannel
import com.justai.jaicf.template.accessToken
import com.justai.jaicf.template.templateBot

fun main() {
    JaicpServer(
        botApi = templateBot,
        accessToken = accessToken,
        channels = listOf(
            AimyboxChannel
        )
    ).start(wait = true)
}