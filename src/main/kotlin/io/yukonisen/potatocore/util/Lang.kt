package io.yukonisen.potatocore.util

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object Lang {
    private val pluginLanguageFile: String? = Config.pluginConfig.getString("options.languageFile")
    private val langConfig: FileConfiguration = YamlConfiguration.loadConfiguration(File(Config.plugin.dataFolder, "language/$pluginLanguageFile}"))
    //
    val ReadyWithPTBDisabled: String? = langConfig.getString("general.readynoptb")
    val Ready: String? = langConfig.getString("general.ready")
    val Reloaded: String? = langConfig.getString("general.reloaded")
}