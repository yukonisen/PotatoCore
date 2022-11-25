package io.yukonisen.potatocore.command

import io.yukonisen.potatocore.PotatoCore
import io.yukonisen.potatocore.util.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class PotatoCoreCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        when (args!![0]){
            // TODO: reload is not working
            "reload" -> {
                PotatoCore.instance.reloadConfig()
                sender.sendMessage("§6PotatoCore §f> Reloaded")
                return true;
            }
            "debug" -> {
                sender.sendMessage("§6PotatoCore §bINFO\n" +
                        "B=" + Config.PTBConfigBot + " G=" + Config.PTBConfigGroup + "\n" +
                        "AL=" + Config.PTBConfigAdmins + "\n" +
                        "OS=" + System.getProperty("os.name") + "" + System.getProperty("os.version") + "\n" +
                        "§bFunctions:" + "\n" +
                        "Broadcast=" + Config.PTBFuncBroadcastEnabled + "\n" +
                        "Forward=" + Config.PTBFuncForwardEnabled + "\n" +
                        "GroupCmd=" + Config.PTBFuncGroupCmdEnabled + "\n"
                )
            }
            else -> {
                sender.sendMessage("§6PotatoCore §f> Unknown command")
            }
        }
        return false
    }
}