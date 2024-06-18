package io.yukonisen.potatocore.listener

import com.velocitypowered.api.event.Subscribe
import io.yukonisen.potatocore.PotatoCore.Companion.config
import io.yukonisen.potatocore.PotatoCore.Companion.plugin
import io.yukonisen.potatocore.util.*
import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.velocity.event.message.passive.MiraiFriendMessageEvent
import me.dreamvoid.miraimc.velocity.event.message.passive.MiraiGroupMessageEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

class BotGroupListener() {
    val sender = SendMsg()

    /*@Subscribe*/
    fun onFriendMessageReceive(event: MiraiFriendMessageEvent) {
        val src = MiraiBot.getBot(event.botID).getFriend(event.friend.id)
        val message = event.message
    }

    @Subscribe
    fun onGroupMessage(event: MiraiGroupMessageEvent) {
        plugin.logger.info("Triggered: onGroupMessage")
        val groupList: Set<Long> = config.bot.groups
        if (event.groupID !in groupList) return
        val message = event.message

        if (message.startsWith(config.function.forwardToGame.prefix)) {
            /* MATCH FORWARD TO GAME CONDITION */
            /* §a$sender§7: §f$message  §7§o@${event.groupName} */
            plugin.proxy.sendMessage(
                Component.text(event.senderName).color(NamedTextColor.GREEN)
                .append(Component.text(": ").color(NamedTextColor.GRAY))
                .append(Component.text(message).color(NamedTextColor.WHITE))
                .append(Component.text("  @${event.groupName}").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC))
            )
        }

        if (!message.startsWith("#")) return
        val src = MiraiBot.getBot(event.botID).getGroup(event.groupID)

        /* BIND PART */
        if (message.startsWith("#bind")) {
            val id = message.substringAfter("#bind", "").trim()
            println("player id is $id")
            if (id.isEmpty() || id.isBlank()) {
                src.sendMessageMirai("无效 ID，格式：#bind <你的ID>")
                return
            }
            /* We set senderID to NEGATIVE as ↓ unverified!! */
            updatePlayerData(id, "", -event.senderID, true)
            src.sendMessageMirai("已成功绑定！\n" +
                    "$id <-> ${event.senderName}(${event.senderID})\n" +
                    "注意：使用此 ID 登录游戏进行验证前功能受限。")
            return
        }

        val senderPlayerName = getPlayerName(-event.senderID) ?: getPlayerName(event.senderID)
        plugin.logger.info("${event.senderID} is $senderPlayerName")
        if (senderPlayerName == null)
            src.sendMessageMirai("请先发送 #bind <你的玩家id> 进行绑定后再操作")

        /* FUNCTION PART*/
        val data = senderPlayerName?.let { getPlayerData(it) } ?: return
        if (message.startsWith("#subscribe")) {
            src.sendMessageMirai("$senderPlayerName 已订阅消息转发")
            updatePlayerData(senderPlayerName, data.uuid, data.playerUin, true)
        } else if (message.startsWith("#unsubscribe")) {
            src.sendMessageMirai("$senderPlayerName 已取消订阅消息转发")
            updatePlayerData(senderPlayerName, data.uuid, data.playerUin, false)
        }

        if (message.startsWith("#whoami")) {
            val unverified = if (data.playerUin <= 0) "\n未登录验证" else "已验证"
            src.sendMessageMirai("你已绑定 $senderPlayerName, uin is ${data.playerUin}")
        }
    }



}
