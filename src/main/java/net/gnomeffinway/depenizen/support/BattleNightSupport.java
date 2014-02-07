package net.gnomeffinway.depenizen.support;

import net.aufdemrand.denizen.objects.dPlayer;
import net.aufdemrand.denizen.utilities.DenizenAPI;
import net.gnomeffinway.depenizen.Depenizen;
import net.gnomeffinway.depenizen.commands.BattleNightCommands;
import net.gnomeffinway.depenizen.tags.BattleNightTags;
import net.gnomeffinway.depenizen.tags.battlenights.BNPlayerTags;

public class BattleNightSupport {

    public Depenizen depenizen;

    public BattleNightSupport(Depenizen depenizen) {
        this.depenizen = depenizen;
    }

    public void register() {
        new BattleNightTags(depenizen);
        new BattleNightCommands().activate().as("BN").withOptions("see documentation", 1);
        DenizenAPI.getCurrentInstance().getPropertyParser().registerProperty(BNPlayerTags.class, dPlayer.class);
    }
}
