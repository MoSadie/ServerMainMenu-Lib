package com.mosadie.simplemainmenu.api;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public record SplashText(Component[] lines) {
    public static Builder builder(Component component) {
        return builder().addLine(component);
    }
    public static Builder builder(String text) {
        return builder().addLine(text);
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<Component> lines = new ArrayList<>();

        public Builder addLine(Component component) {
            lines.add(component);
            return this;
        }
        public Builder addLine(String text) {
            lines.add(Component.literal(text).setStyle(Util.SPLASH_TEXT_STYLE));
            return this;
        }

        public SplashText build() {
            return new SplashText(lines.toArray(Component[]::new));
        }
    }
}
