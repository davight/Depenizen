package com.denizenscript.depenizen.bukkit.objects.pvparena;

import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import net.slipcor.pvparena.arena.Arena;
import net.slipcor.pvparena.arena.ArenaPlayer;
import net.slipcor.pvparena.managers.ArenaManager;

public class PVPArenaArena implements ObjectTag {

    String prefix = "PVPArena";
    Arena arena = null;

    public static boolean matches(String name) {
        return valueOf(name) != null;
    }

    public static PVPArenaArena valueOf(String name) {
        return valueOf(name, null);
    }

    public static PVPArenaArena valueOf(String name, TagContext context) {
        name = name.replace("pvparena@", "");
        Arena a = ArenaManager.getArenaByName(name);
        if (a == null) {
            return null;
        }
        return new PVPArenaArena(a);
    }

    public PVPArenaArena(Arena a) {
        if (a != null) {
            this.arena = a;
        }
        else {
            Debug.echoError("Arena referenced is null");
        }
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public ObjectTag setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public String debug() {
        return prefix + "='<A>" + identify() + "<G>' ";
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getObjectType() {
        return "PVPArena";
    }

    @Override
    public String identify() {
        return "pvparena@" + arena.getName();
    }

    @Override
    public String identifySimple() {
        return identify();
    }

    @Override
    public String toString() {
        return identify();
    }

    @Override
    public String getAttribute(Attribute attribute) {
        if (attribute == null) {
            return null;
        }

        // <--[tag]
        // @attribute <pvparena@pvparena.name>
        // @returns ElementTag
        // @description
        // Returns the name of the arena.
        // @Plugin Depenizen, PVPArena
        // -->
        if (attribute.startsWith("name")) {
            return new ElementTag(arena.getName()).getAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <pvparena@pvparena.fighters>
        // @returns ListTag(PlayerTag)
        // @description
        // Returns a list of all fighters in the arena.
        // @Plugin Depenizen, PvPArena
        // -->
        else if (attribute.startsWith("fighters")) {
            ListTag fighters = new ListTag();
            for (ArenaPlayer p : arena.getFighters()) {
                fighters.add(new PlayerTag(p.get()).identify());
            }
            return fighters.getAttribute(attribute.fulfill(1));
        }

        // NOTE: Deprecated.
        if (attribute.startsWith("playercount") || attribute.startsWith("player_count")) {
            return new ElementTag(arena.getFighters().size()).getAttribute(attribute.fulfill(1));
        }

        // <--[tag]
        // @attribute <pvparena@pvparena.type>
        // @returns ElementTag
        // @description
        // Always returns 'PVPArena' for PVPArena objects. All objects fetchable by the Object Fetcher will return the
        // type of object that is fulfilling this attribute.
        // @Plugin Depenizen, PVPArena
        // -->
        else if (attribute.startsWith("type")) {
            return new ElementTag("PVPArena").getAttribute(attribute.fulfill(1));
        }

        return new ElementTag(identify()).getAttribute(attribute);
    }
}
