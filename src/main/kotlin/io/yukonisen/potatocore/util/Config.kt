package io.yukonisen.potatocore.util

import io.yukonisen.potatocore.PotatoCore
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.util.ArrayList

val config
    get() = Config

object Config {
    // PotatoCore Options
    // config.yml -> [options]
    val plugin: PotatoCore = PotatoCore.instance
    val pluginConfig: FileConfiguration = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "config.yml"))
    val ConfigVersion: String? = pluginConfig.getString("options.config-version")

    // PotatoBot Config
    val CHECK_UPDATE: Boolean = pluginConfig.getBoolean("check-update")
    val PTBEnabled: Boolean = pluginConfig.getBoolean("potatobot.enabled")
    val PTBConfigBot: Long = pluginConfig.getLong("potatobot.config.bot")
    val PTBConfigGroup: Long = pluginConfig.getLong("potatobot.config.group")
    val PTBConfigAdmins: List<*>? = pluginConfig.getList("potatobot.config.admins")
    val PTBFuncBroadcastEnabled: Boolean = pluginConfig.getBoolean("potatobot.functions.broadcast.enabled")
    val PTBFuncBroadcastJoinMsg: String? = pluginConfig.getString("potatobot.functions.broadcast.join_msg")
    val PTBFuncBroadcastQuitMsg: String? = pluginConfig.getString("potatobot.functions.broadcast.quit_msg")
    val PTBFuncForwardEnabled: Boolean = pluginConfig.getBoolean("potatobot.functions.forward.enabled")
    val PTBFuncForwardGamePrefix: String? = pluginConfig.getString("potatobot.functions.forward.game-prefix")
    val PTBFuncForwardGroupPrefix: String? = pluginConfig.getString("potatobot.functions.forward.group-prefix")
    val PTBFuncForwardFmtToGame: String? = pluginConfig.getString("potatobot.functions.forward.format.game")
    val PTBFuncForwardFmtHover: String? = pluginConfig.getString("potatobot.functions.forward.format.hover")
    val PTBFuncForwardFmtToGroup: String? = pluginConfig.getString("potatobot.functions.forward.format.group")
    val PTBFuncGroupCmdEnabled: Boolean = pluginConfig.getBoolean("potatobot.functions.group-command.enabled")
    val PTBFuncGroupCmdPrefix: String? = pluginConfig.getString("potatobot.functions.group-command.command-prefix")


}