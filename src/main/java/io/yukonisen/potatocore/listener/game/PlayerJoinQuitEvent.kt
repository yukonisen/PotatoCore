package io.yukonisen.potatocore.listener.game

import io.yukonisen.potatocore.util.Config
import me.dreamvoid.miraimc.api.MiraiBot
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerJoinQuitEvent : Listener {

    @EventHandler
    fun onPlayerJoin(join: PlayerJoinEvent) {
        val group = MiraiBot.getBot(Config.PTBConfigBot).getGroup(Config.PTBConfigGroup)
        if (Config.PTBFuncBroadcastEnabled) {
            val msg = Config.PTBFuncBroadcastJoinMsg?.replace("%player%", join.player.name)
            group.sendMessageMirai(msg)
        }
    }

    @EventHandler
    fun onPlayerQuit(quit: PlayerQuitEvent) {
        val group = MiraiBot.getBot(Config.PTBConfigBot).getGroup(Config.PTBConfigGroup)
        if (Config.PTBFuncBroadcastEnabled) {
            val msg = Config.PTBFuncBroadcastQuitMsg?.replace("%player%", quit.player.name)
            group.sendMessageMirai(msg)
        }
    }
}