package com.mosadie.servermainmenu.mixin;

import com.mosadie.servermainmenu.client.ServerMainMenuLibClient;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Identifier.class)
public class IdentifierMixin {
    // I'm sorry future self for this mixin. It's a complete hack, and will likely break at some point.
    /**
     * @author MoSadie
     * @reason Trying to get the panorama ID to resolve to a custom one instead, am tied of running into so many rendering issues so time for hacky solutions. Added in v2.0.5 when updating to Minecraft 1.21.5 because I could not get the previous system of overwriting the panorama renderer completely to work.
     */
    @Overwrite
    public static Identifier ofVanilla(String path) {
        // If we are getting the background panorama texture, we want to return the custom panorama texture from the selected theme instead.
        if (path != null && path.equals("textures/gui/title/background/panorama")) {
            return ServerMainMenuLibClient.getTheme().getPanorama();
        }

        return Identifier.of("minecraft", path);
    }
}
