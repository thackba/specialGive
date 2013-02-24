package net.thackbarth.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SpecialGive extends JavaPlugin {

    private final Logger log = Logger.getLogger("Minecraft");

    private ResourceBundle items;

    private String errorMessage;

    public void onDisable() {
        log.info("SpecialGive for 1.3.1 is disabled!");
    }

    public void onEnable() {
        items = ResourceBundle.getBundle("specialGive");
        StringBuilder builder = new StringBuilder();
        builder.append("Not an item! Use one of the following item: ");
        List<String> listOfItems = new ArrayList<String>(items.keySet());
        Collections.sort(listOfItems);
        for (String element : listOfItems) {
            builder.append(element);
            builder.append(" ");
        }
        errorMessage = builder.toString().trim();

        SpecialGivePlayerListener playerListener = new SpecialGivePlayerListener();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        log.info("SpecialGive for 1.3.1 is enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean processed = false;

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                String itemStr = args[0].toLowerCase().trim();

                if (items.containsKey(itemStr)) {
                    processed = true;
                    String valueStr = items.getString(itemStr);
                    String[] split = valueStr.split(",");
                    int count = 1;
                    if (split.length > 1) {
                        count = Integer.valueOf(split[1].trim());
                    }
                    addItems(player, Integer.valueOf(split[0].trim()), count);
                }
            }

            if (!processed) {
                processed = true;
                player.sendMessage(errorMessage);
            }
        }

        return processed;
    }

    private void addItems(Player player, int item, int amount) {
        player.getInventory().addItem(new ItemStack(item, amount));
    }

}
