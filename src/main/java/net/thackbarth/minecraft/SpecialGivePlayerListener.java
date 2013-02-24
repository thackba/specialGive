package net.thackbarth.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SpecialGivePlayerListener implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

        String[] split = event.getMessage().split(" ");
        if (split.length >= 1) {

            // Trim and remove "/"
            String cmd = split[0].trim().substring(1).toLowerCase();

            if ("give".equals(cmd)) {
                event.setCancelled(true);
                event.setMessage("'give' command is not active! Use '/g' instead.");
            }
        }
    }
}
