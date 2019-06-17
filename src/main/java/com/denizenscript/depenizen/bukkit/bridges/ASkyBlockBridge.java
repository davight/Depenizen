package com.denizenscript.depenizen.bukkit.bridges;

import com.denizenscript.depenizen.bukkit.events.askyblock.PlayerExitsSkyBlockScriptEvent;
import com.denizenscript.depenizen.bukkit.events.askyblock.PlayerCompletesSkyBlockChallengeScriptEvent;
import com.denizenscript.depenizen.bukkit.events.askyblock.PlayerEntersSkyBlockScriptEvent;
import com.denizenscript.depenizen.bukkit.events.askyblock.SkyBlockCreatedScriptEvent;
import com.denizenscript.depenizen.bukkit.events.askyblock.SkyBlockResetScriptEvent;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import net.aufdemrand.denizen.objects.dLocation;
import net.aufdemrand.denizen.objects.dPlayer;
import net.aufdemrand.denizen.objects.dWorld;
import net.aufdemrand.denizencore.events.ScriptEvent;
import net.aufdemrand.denizencore.objects.TagRunnable;
import net.aufdemrand.denizencore.objects.properties.PropertyParser;
import net.aufdemrand.denizencore.tags.ReplaceableTagEvent;
import net.aufdemrand.denizencore.objects.Element;
import net.aufdemrand.denizencore.tags.Attribute;
import com.denizenscript.depenizen.bukkit.extensions.askyblock.ASkyBlockLocationExtension;
import com.denizenscript.depenizen.bukkit.extensions.askyblock.ASkyBlockPlayerExtension;
import com.denizenscript.depenizen.bukkit.extensions.askyblock.ASkyBlockWorldExtension;
import com.denizenscript.depenizen.bukkit.Bridge;
import net.aufdemrand.denizencore.tags.TagManager;

public class ASkyBlockBridge extends Bridge {

    @Override
    public void init() {
        api = ASkyBlockAPI.getInstance();
        PropertyParser.registerProperty(ASkyBlockPlayerExtension.class, dPlayer.class);
        PropertyParser.registerProperty(ASkyBlockLocationExtension.class, dLocation.class);
        PropertyParser.registerProperty(ASkyBlockWorldExtension.class, dWorld.class);
        ScriptEvent.registerScriptEvent(new SkyBlockCreatedScriptEvent());
        ScriptEvent.registerScriptEvent(new SkyBlockResetScriptEvent());
        ScriptEvent.registerScriptEvent(new PlayerEntersSkyBlockScriptEvent());
        ScriptEvent.registerScriptEvent(new PlayerExitsSkyBlockScriptEvent());
        ScriptEvent.registerScriptEvent(new PlayerCompletesSkyBlockChallengeScriptEvent());
        TagManager.registerTagHandler(new TagRunnable.RootForm() {
            @Override
            public void run(ReplaceableTagEvent event) {
                tagEvent(event);
            }
        }, "skyblock");
        // TODO: Skyblock Command
    }

    public ASkyBlockAPI api;

    public void tagEvent(ReplaceableTagEvent event) {
        Attribute attribute = event.getAttributes().fulfill(1);

        // <--[tag]
        // @attribute <skyblock.island_world>
        // @returns dWorld
        // @description
        // Returns the world that A Skyblock uses for islands.
        // @Plugin DepenizenBukkit, A SkyBlock
        // -->
        if (attribute.startsWith("island_world")) {
            event.setReplacedObject(new dWorld(api.getIslandWorld()).getObjectAttribute(attribute.fulfill(1)));
        }

        // <--[tag]
        // @attribute <skyblock.nether_world>
        // @returns dWorld
        // @description
        // Returns the world that A Skyblock uses for the nether.
        // @Plugin DepenizenBukkit, A SkyBlock
        // -->
        else if (attribute.startsWith("nether_world")) {
            event.setReplacedObject(new dWorld(api.getNetherWorld()).getObjectAttribute(attribute.fulfill(1)));
        }

        // <--[tag]
        // @attribute <skyblock.island_count>
        // @returns Element(Number)
        // @description
        // Returns the number of Skyblock Islands that exist.
        // @Plugin DepenizenBukkit, A SkyBlock
        // -->
        else if (attribute.startsWith("island_count")) {
            event.setReplacedObject(new Element(api.getIslandCount()).getObjectAttribute(attribute.fulfill(1)));
        }
    }
}