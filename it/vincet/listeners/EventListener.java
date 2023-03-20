package it.vincet.listeners;

import it.vincet.Main;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class EventListener implements Listener {
    private Main plugin = Main.getInstance();
    String proxy = String.valueOf(ProxyServer.getInstance().getConsole());

    public EventListener() {
    }

    @EventHandler
    public void onJoin(LoginEvent e) throws IOException {
        if (this.plugin.getConfig().getBoolean("enabled")) {
            if (!e.isCancelled()) {
                List<String> whitelist = this.plugin.getConfig().getStringList("whitelist");
                String kickMessage = this.plugin.getConfig().getString("kick_message");
                kickMessage = ChatColor.translateAlternateColorCodes('&', kickMessage);
                if (!whitelist.contains(e.getConnection().getName())) {
                    e.setCancelled(true);
                    e.setCancelReason(kickMessage);
                    Logger var10000 = Main.getInstance().getLogger();
                    ChatColor var10001 = ChatColor.GRAY;
                    var10000.info("" + var10001 + "[" + ChatColor.GREEN + "VTWhitelist" + ChatColor.GRAY + "]" + ChatColor.GREEN + ChatColor.BOLD + e.getConnection().getName() + ChatColor.GREEN + " ha provato ad entrare!");
                    Iterator var4 = ProxyServer.getInstance().getPlayers().iterator();

                    while(var4.hasNext()) {
                        ProxiedPlayer player = (ProxiedPlayer)var4.next();
                        if (player.hasPermission("vtwhitelistbungee.admin")) {
                            player.sendMessage(this.getProvajoin("&8[&2&lVTW&r&8] &f[Whitelist] &c&l" + Main.getInstance().getConfig().getString("tryed-to-join").replace("%player%", e.getConnection().getName())));
                        }
                    }
                }

            }
        }
    }

    private TextComponent getProvajoin(String label) {
        label = ChatColor.translateAlternateColorCodes('&', label);
        return new TextComponent(label);
    }
}