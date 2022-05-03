package me.merlin.practice.effects;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EffectCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location location = player.getLocation();
            player.playEffect(location, Effect.MOBSPAWNER_FLAMES, 10);
            player.sendMessage("You have played an explosion effect.");
        }
        return false;
    }

}
