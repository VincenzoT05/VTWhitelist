package it.vincet;

import it.vincet.listeners.CommandListener;
import it.vincet.listeners.EventListener;
import java.io.IOException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
public class Main extends Plugin {
    private YamlConfig yamlConfig;
    private CommandListener commands;
    private EventListener events;
    private static Main instance;
    private String serverVersion;
    public Main() {
    }
    public void onEnable() {
        long timeAtStart = System.currentTimeMillis();
        instance = this;
        (this.yamlConfig = new YamlConfig("config.yml", instance)).saveDefaultConfig();
        this.commands = new CommandListener();
        this.events = new EventListener();
        this.getProxy().getPluginManager().registerListener(this, this.events);
        this.getProxy().getPluginManager().registerCommand(this, this.commands);
        String projectVersion = getInstance().getDescription().getVersion();
        this.serverVersion = ProxyServer.getInstance().getVersion().substring(24, 33);
        String serverPlatform = ProxyServer.getInstance().getName();
        long timeAtEnd = System.currentTimeMillis();
        long timeTakenInMS = timeAtEnd - timeAtStart;
        CommandSender console = this.getProxy().getConsole();
        console.sendMessage(ChatColor.DARK_GREEN + "--------------------------------------------------------------------------------------------");
        console.sendMessage("");
        console.sendMessage("");
        console.sendMessage(ChatColor.DARK_GREEN + " Enabling VTW v." + projectVersion + " by VincenzoT");
        console.sendMessage(ChatColor.DARK_GREEN + " Starting BungeeCord version for (" + serverPlatform + " - " + this.serverVersion + ")...");
        console.sendMessage(ChatColor.DARK_GREEN + " Took " + timeTakenInMS + "ms to process.");
        console.sendMessage("");
        console.sendMessage("");
        console.sendMessage(ChatColor.DARK_GREEN + "--------------------------------------------------------------------------------------------");
    }
    public void onDisable() {
        try {
            this.yamlConfig.saveConfig();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }

        CommandSender console = this.getProxy().getConsole();
        console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "VTW" + ChatColor.GRAY + "]" + ChatColor.GREEN + " Good Bye!!!!!!!");
        console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "VTW" + ChatColor.GRAY + "]" + ChatColor.GREEN + " Plugin Disattivato");
    }
    public static Main getInstance() {
        return instance;
    }
    public Configuration getConfig() {
        return this.yamlConfig.getConfig();
    }
}
