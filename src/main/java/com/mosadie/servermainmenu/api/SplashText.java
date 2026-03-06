package com.mosadie.servermainmenu.api;

import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public record SplashText(Text[] lines) {
    public static Builder builder(Text text) {
        return builder().addLine(text);
    }
    public static Builder builder(String text) {
        return builder().addLine(text);
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<Text> lines = new ArrayList<>();

        public Builder addLine(Text text) {
            lines.add(text);
            return this;
        }
        public Builder addLine(String text) {
            lines.add(Text.literal(text).setStyle(Util.SPLASH_TEXT_STYLE));
            return this;
        }

        public SplashText build() {
            return new SplashText(lines.toArray(Text[]::new));
        }
    }
}
