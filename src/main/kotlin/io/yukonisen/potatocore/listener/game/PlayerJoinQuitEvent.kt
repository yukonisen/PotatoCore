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

        if (Config.PTBFuncBroadcastEnabled) {
            val msg = Config.PTBFuncBroadcastJoinMsg?.replace("%player%", join.player.name)
            val groupList: List<*>? = Config.PTBConfigGroups
            groupList?.forEach { j ->
                val group = MiraiBot.getBot(Config.PTBConfigBot).getGroup(j.toString().toLong())
                group.sendMessageMirai(msg)
            }
        }
    }

    @EventHandler
    fun onPlayerQuit(quit: PlayerQuitEvent) {
        if (Config.PTBFuncBroadcastEnabled) {
            val msg = Config.PTBFuncBroadcastQuitMsg?.replace("%player%", quit.player.name)
            val groupList: List<*>? = Config.PTBConfigGroups
            groupList?.forEach { q ->
                val group = MiraiBot.getBot(Config.PTBConfigBot).getGroup(q.toString().toLong())
                group.sendMessageMirai(msg)
            }
        }
    }
}