package io.yukonisen.potatocore.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.ServerPreConnectEvent
import io.yukonisen.potatocore.PotatoCore.Companion.plugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class ConnectListener() {

    companion object {
        private const val SERVER_FORGE = "forge"
        private const val SERVER_POTATO = "potato"
    }

    private val playerVersionMap = mutableMapOf<String, String>()

    @Subscribe
    fun onConnect(event: ServerPreConnectEvent) {
        val clientBrand = event.player.clientBrand ?: "unknown"
        val playerName = event.player.username

        autoAssign(event, playerName)

        when (val playerVersion = playerVersionMap[playerName]) {
            "1.20" -> switchServer(playerName, SERVER_FORGE)
            "1.21" -> switchServer(playerName, SERVER_POTATO)
            else -> {
                event.player.disconnect(Component.text("检测到的客户端版本: $playerVersion\n" +
                        "PotatoTerminal 无法为你分配目标服务器……\n" +
                        "接受的客户端类型：\n\n" +
                        "AnvilCraft: 版本 1.20.1 (1.20), forge\n" +
                        "Fantasy Generation: 版本 1.21 (1.21), vanilla, fabric, geyser").color(NamedTextColor.AQUA))
                event.result = ServerPreConnectEvent.ServerResult.denied()
            }
        }
    }

    @Subscribe
    fun onDisconnect(event: DisconnectEvent) {
        val playerName = event.player.username
        /* Remove cache for this session */
        playerVersionMap.remove(playerName)
    }

    private fun switchServer(playerName: String, serverName: String) {
        plugin.logger.info("switch $playerName to server \"$serverName\"")
        plugin.proxy.getPlayer(playerName).ifPresent { p ->
            plugin.proxy.getServer(serverName).ifPresent { target ->
                p.createConnectionRequest(target).fireAndForget()
            }
        }
        playerVersionMap.remove(playerName)
    }

    private fun autoAssign(event: ServerPreConnectEvent, playerName: String) {
        val protocolVersion = event.player.protocolVersion.toString()
        val protocolState = event.player.protocolState.name
        if (protocolState == "CONFIGURATION" || protocolState == "LOGIN") {
            plugin.logger.info("$playerName TRIGGERED: $protocolState, $protocolVersion")
        } else return

        when (protocolVersion) {
            "1.20" -> playerVersionMap[playerName] = "1.20"
            "1.21" -> playerVersionMap[playerName] = "1.21"
            else -> {
                plugin.logger.warn("Cannot assign for $playerName because unknown protocol version $protocolVersion")
                playerVersionMap[playerName] = "unknown"
            }
        }
    }

}