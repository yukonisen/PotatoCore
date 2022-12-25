package io.yukonisen.potatocore

import io.yukonisen.potatocore.command.PotatoCoreCommand
import io.yukonisen.potatocore.listener.game.GameChatListener
import io.yukonisen.potatocore.listener.game.PlayerJoinQuitEvent
import io.yukonisen.potatocore.listener.group.GroupMesssageEvent
import io.yukonisen.potatocore.util.Config.PTBConfigBot
import io.yukonisen.potatocore.util.Config.PTBConfigGroups
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
        // register listeners
        val pm = Bukkit.getPluginManager()
        pm.registerEvents(GroupMesssageEvent(), this)
        pm.registerEvents(GameChatListener(), this)
        pm.registerEvents(PlayerJoinQuitEvent(), this)
        // register commands
        Bukkit.getPluginCommand("potatocore")?.setExecutor(PotatoCoreCommand())

        logger.log(Level.INFO, "PotatoCore ready.")
        logger.log(Level.INFO, "Current QQ bot: $PTBConfigBot, group: $PTBConfigGroups")
    }

    override fun onDisable() {
        println("PotatoCore Disabled")
    }

    companion object {
        lateinit var instance: PotatoCore
    }
}