package io.yukonisen.potatocore.util

import com.charleskorn.kaml.Yaml
import io.yukonisen.potatocore.PotatoCore.Companion.config
import io.yukonisen.potatocore.PotatoCore.Companion.plugin
import kotlinx.serialization.Serializable
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

@Serializable
data class Config(
    val bot: Bot,
    val function: Function,
) {
    @Serializable
    data class Bot(
        val uin: Long,
        val groups: Set<Long>,
        val superusers: Set<Long>
    )

    @Serializable
    data class Function(
        val forwardToGame: ForwardToGame,
        val forwardToGroup: ForwardToGroup,
        val destinationControl: DestinationControl
    ) {
        @Serializable
        data class ForwardToGame(
            val enabled: Boolean,
            val prefix: String,
            val forced: Boolean
        )

        @Serializable
        data class ForwardToGroup(
            val enabled: Boolean,
            val prefix: String,
            val forced: Boolean
        )

        @Serializable
        data class DestinationControl(
            val enabled: Boolean,
            val bindingRequired: Boolean
        ) }
}

fun reloadConfig() {
    val dir = plugin.dataDirectory
    val configFile = dir.resolve("config.yml").toFile()
    val dataFile = dir.resolve("player_data.yml").toFile()
    try {
        if (!dir.exists()) dir.createDirectory()
        if (!dataFile.exists()) dataFile.createNewFile()
        if (!configFile.exists()) {
            Config::class.java.getResourceAsStream("/" + configFile.name)?.use { default ->
                Files.copy(default, configFile.toPath())
            } ?: throw IOException("Unable to save default config.yml! Validate the integrity of your plugin jar.")
        }
        config = Yaml.default.decodeFromString(
            Config.serializer(),
            configFile.readText()
        )
    } catch (e: Exception) {
        plugin.logger.error("Error while loading config: ${e.message}")
        plugin.proxy.shutdown()
    }
}
