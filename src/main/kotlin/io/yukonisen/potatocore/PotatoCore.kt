package io.yukonisen.potatocore;

import io.yukonisen.potatocore.listener.game.GameChatListener;
import io.yukonisen.potatocore.listener.game.PlayerJoinQuitEvent;
import io.yukonisen.potatocore.listener.group.GroupMesssageEvent;
import io.yukonisen.potatocore.util.Config;
import io.yukonisen.potatocore.util.Lang;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Level;

public final class PotatoCore extends JavaPlugin {

    private static PotatoCore instance;

    public void onLoad() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        saveResource("language/zh_CN.yml", false);
        getLogger().log(Level.INFO,"[PotatoCore] Loading");
    }

    public void onEnable() {
        instance = this;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new GroupMesssageEvent(), this);
        pm.registerEvents(new GameChatListener(), this);
        pm.registerEvents(new PlayerJoinQuitEvent(), this);
        getLogger().log(Level.INFO, Lang.INSTANCE.getReady());
        getLogger().log(Level.INFO,"PotatoCore ready.");
        getLogger().log(Level.INFO,"Current QQ bot: " + Config.INSTANCE.getPTBConfigBot() + ", group: " + Config.INSTANCE.getPTBConfigGroup());
    }

    @Override
    public void onDisable() {
        System.out.println("PotatoCore Disabled");
    }

    public static PotatoCore getInstance() {
        return instance;
    }

    public static MiraiBot getBot() {
        try {
            MiraiBot.getBot(Config.INSTANCE.getPTBConfigBot());
        } catch (NoSuchElementException elementException) {
            System.out.println("[PotatoCore] BOT not logged in.");
            return null;
        }
        return MiraiBot.getBot(Config.INSTANCE.getPTBConfigBot());
    }

}
