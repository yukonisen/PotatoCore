package io.yukonisen.potatocore.listener.group

import io.yukonisen.potatocore.util.Config
import io.yukonisen.potatocore.util.Config.PTBConfigBot
import io.yukonisen.potatocore.util.Config.PTBConfigGroup
import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class GroupMesssageEvent : Listener {

    @EventHandler
    fun onGroupMessage(event: MiraiGroupMessageEvent) {
        val message = event.message
        val group = MiraiBot.getBot(PTBConfigBot).getGroup(PTBConfigGroup)

        if (event.groupID == PTBConfigGroup) {
            if (message.startsWith("#")) {
                val msg = event.message.toString().replaceFirst("#", "")
                val namecard = event.senderNameCard.toString()
                val name = event.senderName
                val sender = namecard.ifEmpty { name }
                Bukkit.broadcastMessage("$sender > $msg")
            }
            if (message == "!#ping") {
                val version = Bukkit.getVersion()
                val osname = System.getProperty("os.name")
                val osver = System.getProperty("os.version")
                group.sendMessageMirai(
                    "PTB for the UnitedPotato\n" +
                            "OS: $osver $osname\nServer: $version\n"
                )
            }
        }
    }
}