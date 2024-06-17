package io.yukonisen.potatocore.util

import io.yukonisen.potatocore.PotatoCore.Companion.config
import me.dreamvoid.miraimc.api.MiraiBot

class SendMsg {
    fun group(msg: String, target: Long) {
        MiraiBot.getBot(config.bot.uin).getGroup(target).sendMessageMirai(msg)
    }

    fun private(msg: String, target: Long) {
        MiraiBot.getBot(config.bot.uin).getFriend(target).sendMessageMirai(msg)
    }
}