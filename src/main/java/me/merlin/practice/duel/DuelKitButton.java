package me.merlin.practice.duel;


import lombok.AllArgsConstructor;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.menu.Button;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import me.merlin.practice.util.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@AllArgsConstructor
public class DuelKitButton extends Button {

    private Kit kit;
    private UUID targetUUID;

    @Override
    public ItemStack getButtonItem(Player player) {
//        ItemStack r = new ItemStack(Practice.getInstance().getConfig().getInt("kits." + kit.getName() + ".item"));
//        ItemMeta m = r.getItemMeta();
//        m.setDisplayName(Practice.getInstance().getConfig().getString("kits." + kit.getName() + ".name"));
//        r.setItemMeta(m);
//
//        return r;
        return new ItemStack(Practice.getInstance().getConfig().getInt("kits." + kit.getName() + ".item"));
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();

        Player target = Bukkit.getPlayer(targetUUID);

        if (target == null) {
            player.closeInventory();
            player.sendMessage("§cThat player is not online!");
            return;
        }

        PlayerProfile playerProfile = profileHandler.getProfile(player);
        PlayerProfile targetProfile = profileHandler.getProfile(target);

        if(targetProfile.getPlayerState() != PlayerProfile.PlayerState.LOBBY) {

            player.sendMessage("§cThat player is not in the lobby!");
            return;
        }

        player.closeInventory();

        targetProfile.getDuelRequests().add(new DuelRequest(player.getUniqueId(), kit));
        player.sendMessage("§eYou have sent a §a" + kit.getName() + " §eduel request to §b" + target.getName() + "§e!");




        FancyMessage message = new FancyMessage( "§b" + player.getName() + " §ehas requested to duel you with §d" + kit.getName() + "§e!" + "§6 [Accept]")
        .tooltip("Click to accept").command("/accept " + player.getName());

        message.send(target);
    }
}
