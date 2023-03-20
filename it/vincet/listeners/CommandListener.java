package it.vincet.listeners;

import it.vincet.Main;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
public class CommandListener extends Command {
    private Main plugin = Main.getInstance();
    String PREFIX = "&8[&2&lVTW&r&8] ";
    Configuration CONFIG = Main.getInstance().getConfig();
    public CommandListener() {
        super("whitelist");
    }

    public void execute(CommandSender sender, String[] args) {
        Configuration whitelistFile = this.plugin.getConfig();
        String kick_message;
        if (args.length != 0 && !args[0].equalsIgnoreCase("version") && !args[0].equalsIgnoreCase("reload")) {
            String var10002;
            if (!sender.hasPermission("vtwhitelistbungee.admin")) {
                var10002 = this.PREFIX;
                sender.sendMessage(this.getString(var10002 + this.CONFIG.getString("permission-denied")));
            } else {
                if (args[0].equalsIgnoreCase("on")) {
                    this.plugin.getConfig().set("enabled", true);
                    var10002 = this.PREFIX;
                    sender.sendMessage(this.getString(var10002 + this.CONFIG.getString("whitelist-enabled")));
                }

                if (args[0].equalsIgnoreCase("off")) {
                    this.plugin.getConfig().set("enabled", false);
                    var10002 = this.PREFIX;
                    sender.sendMessage(this.getString(var10002 + this.CONFIG.getString("whitelist-disabled")));
                }

                List whitelist;
                if (args[0].equalsIgnoreCase("add")) {
                    whitelist = whitelistFile.getStringList("whitelist");
                    if (args.length >= 2) {
                        if (whitelist.contains(args[1])) {
                            var10002 = this.PREFIX;
                            sender.sendMessage(this.getString(var10002 + this.CONFIG.getString("already-whitelisted").replace("%player%", args[1])));
                        } else {
                            whitelist.add(args[1]);
                            this.plugin.getConfig().set("whitelist", whitelist);
                            var10002 = this.PREFIX;
                            sender.sendMessage(this.getString(var10002 + this.CONFIG.getString("added-to-whitelist").replace("%player%", args[1])));
                        }
                    } else {
                        sender.sendMessage(this.getString(this.PREFIX + "&cUsa: /whitelist add <player>"));
                    }
                }

                if (args[0].equalsIgnoreCase("remove")) {
                    whitelist = whitelistFile.getStringList("whitelist");
                    if (args.length >= 2) {
                        if (!whitelist.contains(args[1])) {
                            var10002 = this.PREFIX;
                            sender.sendMessage(this.getString(var10002 + this.CONFIG.getString("already-not-in-whitelist").replace("%player%", args[1])));
                        } else {
                            whitelist.remove(args[1]);
                            this.plugin.getConfig().set("whitelist", whitelist);
                            var10002 = this.PREFIX;
                            sender.sendMessage(this.getString(var10002 + this.CONFIG.getString("removed-from-whitelist").replace("%player%", args[1])));
                        }
                    } else {
                        sender.sendMessage(this.getString(this.PREFIX + "&cUsage: /whitelist remove <player>"));
                    }
                }

                if (args[0].equalsIgnoreCase("list")) {
                    whitelist = whitelistFile.getStringList("whitelist");
                    sender.sendMessage(this.getString(""));
                    var10002 = this.PREFIX;
                    sender.sendMessage(this.getString(var10002 + "&bWhitelist: " + whitelist.size()));
                    sender.sendMessage(this.getString(""));
                    sender.sendMessage(this.getString("&a" + whitelist));
                    sender.sendMessage(this.getString(""));
                }

                if (args[0].equalsIgnoreCase("message")) {
                    if (args.length == 1) {
                        sender.sendMessage(this.getString(this.PREFIX + "&cUsa: /whitelist message <set/get>"));
                    }

                    if (args[1].equalsIgnoreCase("set")) {
                        if (args.length == 2) {
                            sender.sendMessage(this.getString(this.PREFIX + "&cUsa: /whitelist message set <message>"));
                        } else {
                            kick_message = args[2];

                            for(int i = 3; i < args.length; ++i) {
                                kick_message = kick_message + " " + args[i];
                            }

                            this.plugin.getConfig().set("kick_message", kick_message);
                            sender.sendMessage(this.getString(this.PREFIX + "&bKick message has been set!"));
                        }
                    }

                    if (args[1].equalsIgnoreCase("get")) {
                        kick_message = this.plugin.getConfig().getString("kick_message");
                        sender.sendMessage(this.getString(this.PREFIX + "&bKick message: " + kick_message));
                    } else {
                        String label="prova";
                        sender.sendMessage(this.getString(label));
                    }
                }

            }
        } else {
            kick_message = Main.getInstance().getDescription().getVersion();
            sender.sendMessage(this.getString("&fRunning &4&lVTW &b&lv." + kick_message + " &r&fby &cVincenzoT"));
        }
    }

    private TextComponent getString(String label) {
        label = ChatColor.translateAlternateColorCodes('&', label);
        return new TextComponent(label);
    }
}