package it.thedarksword.bedwarspractice.bridging.sessions;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingConfiguration;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.inventories.BridgingSettingsInventory;
import it.thedarksword.bedwarspractice.manager.ConstantObjects;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class InfiniteBridging extends BridgingSession {

    public InfiniteBridging(BedwarsPractice bedwarsPractice) {
        this(bedwarsPractice, null);
    }

    public InfiniteBridging(BedwarsPractice bedwarsPractice, BridgingSettingsInventory inventory) {
        super(new BridgingConfiguration(BridgingConfiguration.BridgeLength.INFINITE, BridgingConfiguration.BridgeHeight.NONE, BridgingConfiguration.BridgeDirection.FORWARD),
                bedwarsPractice, inventory);
    }

    @Override
    public void sidebarTemplate(List<String> list) {
        list.add(ChatColor.GRAY + Format.now());
        /*if (isRunning()) {
            list.add("§fCronometro: §e" + Format.now());
        }else{
            list.add("§ePiazza un blocco");
            list.add("§eper iniziare!");
        }*/
        if(!isRunning()) {
            list.add("     ");
            list.add("§bPiazza un blocco");
            list.add("§bper iniziare!");
        }
        list.add(" ");
        if(getConfiguration().getLength() == BridgingConfiguration.BridgeLength.INFINITE)
            list.add("Distanza: §bInfinita");
        else
            list.add("Distanza: §b" + (getConfiguration().getLength().getXForward() + getConfiguration().getHeight().getY()));


        //list.add("Piazzati: §b" + getPlaced());
        if(isRunning())
            list.add("Tempo: §b" + Format.decimal1((System.currentTimeMillis() - getSessionStart())/1000f));
        else
            list.add("Tempo: §b0");
        list.add("Velocità: §b" + Format.decimal1(getMovementSpeed()) + " m/s");
        list.add("  ");
        list.add("Modalità: §7" + getType().name());
        list.add("    ");
        list.add(ChatColor.YELLOW + "play.coralmc.it");
    }
}
