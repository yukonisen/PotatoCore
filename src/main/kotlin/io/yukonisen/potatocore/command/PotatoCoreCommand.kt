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
                        "B=" + Config.PTBConfigBot + " G=" + Config.PTBConfigGroups + "\n" +
                        "AL=" + Config.PTBConfigAdmins + "\n" +
                        "OS=" + System.getProperty("os.name") + "" + System.getProperty("os.version") + "\n" +
                        "§6Functions:" + "§6\n" +
                        "Broadcast=" + Config.PTBFuncBroadcastEnabled + "\n" +
                        "Forward=" + Config.PTBFuncForwardEnabled + "\n" +
                        "GroupCmd=" + Config.PTBFuncGroupCmdEnabled + "\n"
                )
                val groupList: List<*>? = Config.PTBConfigGroups
                groupList?.forEach { group ->
                    sender.sendMessage("group: $group")
                }
                val adminList: List<*>? = Config.PTBConfigAdmins
                adminList?.forEach { admin ->
                    sender.sendMessage("admin: $admin")
                }
            }
            else -> {
                sender.sendMessage("§6PotatoCore §f> Unknown command")
            }
        }
        return false
    }
}