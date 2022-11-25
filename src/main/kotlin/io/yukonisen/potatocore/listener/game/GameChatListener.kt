
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
        val msg = event.message
        val group = MiraiBot.getBot(Config.PTBConfigBot).getGroup(Config.PTBConfigGroup)
        if (msg.startsWith("#") && PotatoCore.bot != null) {
            val forwardMsg: String = msg.substring(1)
           group.sendMessageMirai(
                event.player.displayName + ": " + forwardMsg
            )
        }
    }
}
