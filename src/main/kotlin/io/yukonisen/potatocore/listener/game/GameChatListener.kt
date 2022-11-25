
package io.yukonisen.potatocore.listener.game


import io.yukonisen.potatocore.PotatoCore
import io.yukonisen.potatocore.util.Config
import me.dreamvoid.miraimc.api.MiraiBot
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class GameChatListener : Listener {

    @EventHandler
    fun onAsyncGameChat(event: AsyncPlayerChatEvent) {
        val eventMessage = event.message
        val group = MiraiBot.getBot(Config.PTBConfigBot).getGroup(Config.PTBConfigGroup)
        val prefix = Config.PTBFuncForwardGamePrefix.toString()
        val eventPlayerName = event.player.displayName
        if (eventMessage.startsWith(prefix)) {
            val forwardMsg: String = eventMessage.replaceFirst(prefix, "")
            val messageText = Config.PTBFuncForwardFmtToGroup.toString()
                .replace("%player%", eventPlayerName)
                .replace("%message%", forwardMsg)
                .replace("&", "§")
            group.sendMessageMirai(messageText)
        }
    }
}
