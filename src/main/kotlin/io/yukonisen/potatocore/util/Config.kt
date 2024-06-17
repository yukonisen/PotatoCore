package io.yukonisen.potatocore.util

import com.charleskorn.kaml.Yaml
import io.yukonisen.potatocore.PotatoCore.Companion.config
import io.yukonisen.potatocore.PotatoCore.Companion.plugin
import kotlinx.serialization.Serializable

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
    try {
        config = Yaml.default.decodeFromString(
            Config.serializer(),
            plugin.dataDirectory.resolve("config.yml").toFile().readText()
        )
    } catch (e: Exception) {
        plugin.logger.info("Error while loading config: ${e.message}")
    }
}
