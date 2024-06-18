package io.yukonisen.potatocore.util

import com.charleskorn.kaml.Yaml
import io.yukonisen.potatocore.PotatoCore.Companion.plugin
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File


@Serializable
data class Data(
    val uuid: String,
    val playerUin: Long,
    val subscribedGroupChat: Boolean
)

val dataFile: File = plugin.dataDirectory.resolve("player_data.yml").toFile()
val playerDataMap = mutableMapOf<String, Data>()
val uinMap = mutableMapOf<Long, String>()
val yaml = Yaml.default

fun readDataFileContent(): Map<String, Data> {
    var fileContent = dataFile.readText()
    if (fileContent.isEmpty()) {
        plugin.logger.warn("Data yml is empty, initializing default placeholder data.")
        /* Create default placeholder data to avoid empty file content */
        val defaultData = mapOf("@DONT_REMOVE_PLACEHOLDER@" to Data("00000000-0000-0000-0000-000000000000", 0, false))
        dataFile.writeText(yaml.encodeToString(defaultData))
        fileContent = yaml.encodeToString(defaultData)
    }
    return yaml.decodeFromString(fileContent)
}

fun updatePlayerData(playerName: String, uuid: String, playerUin: Long, subscribedGroupChat: Boolean) {
    val content = readDataFileContent().toMutableMap()
    val uinMapTemp = mutableMapOf<Long, String>()

    for ((name, data) in content) uinMapTemp[data.playerUin] = name
    content[playerName] = Data(uuid, playerUin, subscribedGroupChat)
    playerDataMap.remove(playerName)
    dataFile.writeText(yaml.encodeToString(content))
    plugin.logger.info("Successfully updated playerData for $playerName")
    uinMap.clear()
    uinMap.putAll(uinMapTemp)
}

fun getPlayerData(playerName: String): Data? {
    val content = readDataFileContent()
    return content[playerName]
}

fun getPlayerName(uin: Long): String? {
    val content = readDataFileContent()
    val uinMapTemp = mutableMapOf<Long, String>()
    for ((name, data) in content) uinMapTemp[data.playerUin] = name
    return uinMapTemp[uin]
}
