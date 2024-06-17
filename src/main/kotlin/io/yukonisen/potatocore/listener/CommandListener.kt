package io.yukonisen.potatocore.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.command.CommandExecuteEvent
import com.velocitypowered.api.proxy.Player
import io.yukonisen.potatocore.PotatoCore

class CommandListener(plugin: PotatoCore) {

    @Subscribe
    fun onCommand(event: CommandExecuteEvent) {
    }
}