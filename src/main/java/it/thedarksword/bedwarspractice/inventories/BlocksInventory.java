package it.thedarksword.bedwarspractice.inventories;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.manager.ConstantObjects;
import org.bukkit.entity.Player;

public class BlocksInventory extends BaseInventory {

    public BlocksInventory(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "Blocchi", 54);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void init() {
        for(int i = 0; i < ConstantObjects.PlaceableBlock.values().length; i++) {
            ConstantObjects.PlaceableBlock placeableBlock = ConstantObjects.PlaceableBlock.values()[i];
            inventory.setItem(i, createItem(i, placeableBlock.get().getType(), 1, placeableBlock.get().getData().getData(), null, false, event -> {
                Player player = (Player) event.getWhoClicked();
                bedwarsPractice.getManager().session(player).ifPresent(session -> session.setPlaceableBlock(placeableBlock, player));
                player.closeInventory();
            }));
        }
    }
}
