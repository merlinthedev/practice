package me.merlin.practice.duel;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.kit.KitHandler;
import me.merlin.practice.menu.Button;
import me.merlin.practice.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class DuelMenu extends Menu {

    private UUID targetUUID;


    @Override
    public String getTitle(Player player) {
        return ChatColor.DARK_RED + Practice.getInstance().getConfig().getString("menus.duel.title");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        KitHandler kitHandler = Practice.getInstance().getKitHandler();

        for(int i = 0; i < kitHandler.getKits().size(); i++) {
            Kit kit = kitHandler.getKitList().get(i);

            buttons.put(i, new DuelKitButton(kit, targetUUID));
        }


        return buttons;
    }
}
