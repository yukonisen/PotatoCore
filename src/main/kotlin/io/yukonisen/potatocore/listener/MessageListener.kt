package io.yukonisen.potatocore.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import io.yukonisen.potatocore.PotatoCore.Companion.plugin

class MessageListener() {

    @Subscribe
    fun onChat(event: PlayerChatEvent) {
        plugin.logger.info("Triggered: onChat")
        if (event.message.startsWith("!")) {
            plugin.logger.info("ok, msg is: ${event.message}")
        }
    }
}