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

fun updatePlayerData(playerName: String, uuid: String, playerUin: Long, subscribedGroupChat: Boolean) {
    val content = mutableMapOf<String, Data>()
    val uinMapTemp = mutableMapOf<Long, String>()
    var fileContent = dataFile.readText()
    if (fileContent.isEmpty()) {
        plugin.logger.warn("Data yml is empty, initializing default placeholder data.")
        /* Create default placeholder data to avoid empty file content */
        content["@DONT_REMOVE_PLACEHOLDER@"] = Data("00000000-0000-0000-0000-000000000000", 0, false)
        playerDataMap.remove(playerName)
        dataFile.writeText(yaml.encodeToString(content))
        /* After initialized, read content again */
        fileContent = dataFile.readText()
    }

    val result = yaml.decodeFromString<Map<String, Data>>(fileContent)
    content.putAll(result)
    for ((name, data) in result) uinMapTemp[data.playerUin] = name

    content[playerName] = Data(uuid, playerUin, subscribedGroupChat)
    playerDataMap.remove(playerName)
    dataFile.writeText(yaml.encodeToString(content))
    plugin.logger.info("Successfully updated playerData for $playerName")

    uinMap.clear()
    uinMap.putAll(uinMapTemp)
}

fun getPlayerData (playerName: String): Data? {
    if (playerDataMap.containsKey(playerName)) {
        return playerDataMap[playerName]
    }

    if (dataFile.readText().isEmpty()) return null

    val fileContent = dataFile.readText()
    if (fileContent.isEmpty()) return null

    val result = yaml.decodeFromString<Map<String, Data>>(fileContent)
    val data = result[playerName]
    if (data != null) playerDataMap[playerName] = data
    return result[playerName]
}

fun getPlayerName (uin: Long): String? {
    return uinMap[uin]
}