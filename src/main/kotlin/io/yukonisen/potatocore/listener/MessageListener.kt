package io.yukonisen.potatocore.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import io.yukonisen.potatocore.PotatoCore.Companion.plugin

class MessageListener() {

    @Subscribe
    fun onQuit(e: DisconnectEvent) {
    }

    @Subscribe
    fun onChat(event: PlayerChatEvent) {
        plugin.logger.info("Triggered: onChat")
        if (event.message.startsWith("!")) {
            plugin.logger.info("ok, msg is: ${event.message}")
        }

        /*if (event.player.uniqueId in m.lockedAdminChat) {
            m.broadcastAdminChatMessage(event.player, event.message)
            event.result = PlayerChatEvent.ChatResult.denied()
        }

        if (event.message.startsWith("/")) {
            if (m.loggedInPlayers.contains(event.player.uniqueId))
                return

            if (commands.any { event.message.startsWith(it, true) })
                return

            event.result = PlayerChatEvent.ChatResult.denied()*/

    }
}