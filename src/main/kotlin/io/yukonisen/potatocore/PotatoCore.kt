package io.yukonisen.potatocore

import io.yukonisen.potatocore.listener.game.GameChatListener
import io.yukonisen.potatocore.listener.game.PlayerJoinQuitEvent
import io.yukonisen.potatocore.listener.group.GroupMesssageEvent
import io.yukonisen.potatocore.util.Config.PTBConfigBot
import io.yukonisen.potatocore.util.Config.PTBConfigGroup
import io.yukonisen.potatocore.util.Lang.Ready
import me.dreamvoid.miraimc.api.MiraiBot
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Level

class PotatoCore : JavaPlugin() {
    override fun onLoad() {
        config.options().copyDefaults()
        saveDefaultConfig()
        saveResource("language/zh_CN.yml", false)
        logger.log(Level.INFO, "[PotatoCore] Loading")
        if (!File(dataFolder, "config.yml").exists()) {
            saveDefaultConfig()
        }
    }

    override fun onEnable() {
        instance = this
        val pm = Bukkit.getPluginManager()
        pm.registerEvents(GroupMesssageEvent(), this)
        pm.registerEvents(GameChatListener(), this)
        pm.registerEvents(PlayerJoinQuitEvent(), this)
        logger.log(Level.INFO, Ready)
        logger.log(Level.INFO, "PotatoCore ready.")
        logger.log(Level.INFO, "Current QQ bot: $PTBConfigBot, group: $PTBConfigGroup")
    }

    override fun onDisable() {
        println("PotatoCore Disabled")
    }

    companion object {
        lateinit var instance: PotatoCore
        val bot: MiraiBot?
            get() {
                try {
                    MiraiBot.getBot(PTBConfigBot)
                } catch (elementException: NoSuchElementException) {
                    println("[PotatoCore] BOT not logged in.")
                    return null
                }
                return MiraiBot.getBot(PTBConfigBot)
            }
    }
}