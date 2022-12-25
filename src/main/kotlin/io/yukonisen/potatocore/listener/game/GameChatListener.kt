
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
        val prefix = Config.PTBFuncForwardGroupPrefix.toString()
        if (eventMessage.startsWith(prefix)) {
            val forwardMsg: String = eventMessage.replaceFirst(prefix, "")
            val eventPlayerName = event.player.displayName
            val messageText = Config.PTBFuncForwardFmtToGroup.toString()
                .replace("%player%", eventPlayerName)
                .replace("%message%", forwardMsg)
                .replace("&", "§")
            val groupList: List<Long>? = Config.PTBConfigGroups
            groupList?.forEach {
                val group = MiraiBot.getBot(Config.PTBConfigBot).getGroup(it.toString().toLong())
                group.sendMessageMirai(messageText)
            }
        }
    }
}
