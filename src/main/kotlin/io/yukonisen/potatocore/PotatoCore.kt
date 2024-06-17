package io.yukonisen.potatocore

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import io.yukonisen.potatocore.listener.BotGroupListener
import io.yukonisen.potatocore.listener.ConnectListener
import io.yukonisen.potatocore.listener.MessageListener
import io.yukonisen.potatocore.util.Config
import io.yukonisen.potatocore.util.reloadConfig
import org.slf4j.Logger
import java.nio.file.Path
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Plugin(
    id = "potatocore",
    name = "PotatoCore",
    version = "0.2",
    authors = ["yukonisen"],
    description = "PotatoCore for Velocity",
    url = "https://upt.curiousers.org/docs/PotatoCore",
    dependencies = [Dependency(id = "miraimc")]
)

class PotatoCore @Inject constructor(
    @DataDirectory
    val dataDirectory: Path,
    val logger: Logger,
    val proxy: ProxyServer
) {
    companion object {
        private lateinit var instance: PotatoCore
        val plugin by lazy { instance }
        lateinit var config: Config
    }

    init {
        instance = this
        reloadConfig()
    }

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent?) {
        proxy.eventManager.register(this, BotGroupListener())
        proxy.eventManager.register(this, MessageListener())
        proxy.eventManager.register(this, ConnectListener())
        /* FINISH INITIALIZE PLUGIN */
        logger.info("PotatoCore ready")
        logger.info("Bot uin set to: ${config.bot.uin}")
        /*broadCast()*/
    }

    @Subscribe
    fun onDisable(event: ProxyShutdownEvent) {
        logger.info("PotatoCore Disabled")
    }

    private fun broadCast() {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val formattedTime = LocalTime.now().format(formatter)
        /*SendMsg().private("[$formattedTime]\n" +
                "PotatoCore initialized on Velocity startup.\n" +
                "Currently configured groups: ${config.bot.groups.toList()}", 0) // TODO */
    }

}
