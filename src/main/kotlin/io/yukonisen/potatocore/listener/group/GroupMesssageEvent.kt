package io.yukonisen.potatocore.listener.group

import io.yukonisen.potatocore.util.Config
import io.yukonisen.potatocore.util.Config.PTBConfigBot
import io.yukonisen.potatocore.util.Config.PTBConfigGroups
import io.yukonisen.potatocore.util.Config.plugin
import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.text.SimpleDateFormat


class GroupMesssageEvent : Listener {

    @EventHandler
    fun onGroupMessage(event: MiraiGroupMessageEvent) {
        val message = event.message
        val srcGroup = MiraiBot.getBot(PTBConfigBot).getGroup(event.groupID)
        val inGroupList = PTBConfigGroups.toString().contains(event.groupID.toString())

        if (inGroupList && Config.PTBFuncForwardEnabled) {
            val prefix = Config.PTBFuncForwardGamePrefix.toString()
            if (message.startsWith(prefix)) {
                val eventMessage = event.message.toString().replaceFirst(prefix, "")
                val eventSenderNameCard = event.senderNameCard.toString()
                val eventSenderName = event.senderName
                val eventTime = event.time
                val eventSenderID = event.senderID
                val eventGroupName = event.groupName
                val sender = eventSenderNameCard.ifEmpty { eventSenderName }

                val messageText = Config.PTBFuncForwardFmtToGame.toString()
                    .replace("%message%", eventMessage)
                    .replace("%sender%", sender)
                    .replace("%groupName%", eventGroupName)
                    .replace("&", "§")

                val hoverText = Config.PTBFuncForwardFmtHover.toString()
                    .replace("%sender%", sender)
                    .replace("%groupName%", eventGroupName)
                    .replace("%senderID%","$eventSenderID")
                    .replace("%sendTime%","$eventTime")
                    .replace("&", "§")

                val toGameMessage = TextComponent(messageText)
                toGameMessage.hoverEvent =
                    HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder(hoverText).create())
                getServer().spigot().broadcast(toGameMessage)
            }
        }

        if (message == "!#ping") {
            val serverVersion = Bukkit.getVersion()
            val osName = System.getProperty("os.name")
            val osVer = System.getProperty("os.version")
            val authors = plugin.description.authors
            val pluginVersion = plugin.description.version
            val apiVersion = plugin.description.apiVersion
            srcGroup.sendMessageMirai(
                "PotatoCore of the UnitedPotato\n" +
                        "by: $authors\n" +
                        "ver: $pluginVersion (api: $apiVersion)\n" +
                        "OS: $osName $osVer\n" +
                        "Server: $serverVersion"

            )
        }

        if (message == "冒泡") {
            srcGroup.sendMessageMirai("冒泡")
        }
    }
}