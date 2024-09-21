package com.hiimjustin000.colorblocks;

import com.hiimjustin000.colorblocks.init.*;
import com.hiimjustin000.colorblocks.network.UpdateColorPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class ColorBlocks implements ModInitializer
{
    public static final String MOD_ID = "colorblocks";

    public static final Logger LOGGER = LoggerFactory.getLogger("ColorBlocks");

    public static final Map<String, Integer> CSS = new LinkedHashMap<>();
    public static final Map<String, Integer> DYES = new LinkedHashMap<>();

    @Override
    public void onInitialize()
    {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer ->
                LOGGER.info(
                        "Loading Color Blocks {} on Minecraft {}...",
                        modContainer.getMetadata().getVersion().getFriendlyString(),
                        SharedConstants.getGameVersion().getName()
                ));
        ItemInit.init();
        BlockInit.init();
        ItemGroupInit.init();
        BlockEntityTypeInit.init();
        ComponentTypeInit.init();
        ScreenHandlerTypeInit.init();
        PayloadTypeRegistry.playC2S().register(UpdateColorPayload.ID, UpdateColorPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(UpdateColorPayload.ID, ((payload, context) -> {
            ServerPlayerEntity player = context.player();
            player.server.execute(() -> payload.data().saveToEntity(player.getWorld()));
        }));
    }

    public static Identifier id(String path)
    {
        return Identifier.of(MOD_ID, path);
    }

    static
    {
        CSS.put("alice_blue", 0xF0F8FF);
        CSS.put("antique_white", 0xFAEBD7);
        CSS.put("aquamarine", 0x7FFFD4);
        CSS.put("azure", 0xF0FFFF);
        CSS.put("beige", 0xF5F5DC);
        CSS.put("bisque", 0xFFE4C4);
        CSS.put("black", 0x000000);
        CSS.put("blanched_almond", 0xFFEBCD);
        CSS.put("blue", 0x0000FF);
        CSS.put("blue_violet", 0x8A2BE2);
        CSS.put("brown", 0xA52A2A);
        CSS.put("burlywood", 0xDEB887);
        CSS.put("cadet_blue", 0x5F9EA0);
        CSS.put("chartreuse", 0x7FFF00);
        CSS.put("chocolate", 0xD2691E);
        CSS.put("coral", 0xFF7F50);
        CSS.put("cornflower_blue", 0x6495ED);
        CSS.put("cornsilk", 0xFFF8DC);
        CSS.put("crimson", 0xDC143C);
        CSS.put("cyan", 0x00FFFF);
        CSS.put("dark_blue", 0x00008B);
        CSS.put("dark_cyan", 0x008B8B);
        CSS.put("dark_goldenrod", 0xB8860B);
        CSS.put("dark_gray", 0xA9A9A9);
        CSS.put("dark_green", 0x006400);
        CSS.put("dark_khaki", 0xBDB76B);
        CSS.put("dark_magenta", 0x8B008B);
        CSS.put("dark_olive_green", 0x556B2F);
        CSS.put("dark_orange", 0xFF8C00);
        CSS.put("dark_orchid", 0x9932CC);
        CSS.put("dark_red", 0x8B0000);
        CSS.put("dark_salmon", 0xE9967A);
        CSS.put("dark_sea_green", 0x8FBC8F);
        CSS.put("dark_slate_blue", 0x483D8B);
        CSS.put("dark_slate_gray", 0x2F4F4F);
        CSS.put("dark_turquoise", 0x00CED1);
        CSS.put("dark_violet", 0x9400D3);
        CSS.put("deep_pink", 0xFF1493);
        CSS.put("deep_sky_blue", 0x00BFFF);
        CSS.put("dim_gray", 0x696969);
        CSS.put("dodger_blue", 0x1E90FF);
        CSS.put("firebrick", 0xB22222);
        CSS.put("floral_white", 0xFFFAF0);
        CSS.put("forest_green", 0x228B22);
        CSS.put("gainsboro", 0xDCDCDC);
        CSS.put("ghost_white", 0xF8F8FF);
        CSS.put("gold", 0xFFD700);
        CSS.put("goldenrod", 0xDAA520);
        CSS.put("gray", 0x808080);
        CSS.put("green", 0x008000);
        CSS.put("green_yellow", 0xADFF2F);
        CSS.put("honeydew", 0xF0FFF0);
        CSS.put("hot_pink", 0xFF69B4);
        CSS.put("indian_red", 0xCD5C5C);
        CSS.put("indigo", 0x4B0082);
        CSS.put("ivory", 0xFFFFF0);
        CSS.put("khaki", 0xF0E68C);
        CSS.put("lavender", 0xE6E6FA);
        CSS.put("lavender_blush", 0xFFF0F5);
        CSS.put("lawn_green", 0x7CFC00);
        CSS.put("lemon_chiffon", 0xFFFACD);
        CSS.put("light_blue", 0xADD8E6);
        CSS.put("light_coral", 0xF08080);
        CSS.put("light_cyan", 0xE0FFFF);
        CSS.put("light_goldenrod_yellow", 0xFAFAD2);
        CSS.put("light_gray", 0xD3D3D3);
        CSS.put("light_green", 0x90EE90);
        CSS.put("light_pink", 0xFFB6C1);
        CSS.put("light_salmon", 0xFFA07A);
        CSS.put("light_sea_green", 0x20B2AA);
        CSS.put("light_sky_blue", 0x87CEFA);
        CSS.put("light_slate_gray", 0x778899);
        CSS.put("light_steel_blue", 0xB0C4DE);
        CSS.put("light_yellow", 0xFFFFE0);
        CSS.put("lime", 0x00FF00);
        CSS.put("lime_green", 0x32CD32);
        CSS.put("linen", 0xFAF0E6);
        CSS.put("magenta", 0xFF00FF);
        CSS.put("maroon", 0x800000);
        CSS.put("medium_aquamarine", 0x66CDAA);
        CSS.put("medium_blue", 0x0000CD);
        CSS.put("medium_orchid", 0xBA55D3);
        CSS.put("medium_purple", 0x9370DB);
        CSS.put("medium_sea_green", 0x3CB371);
        CSS.put("medium_slate_blue", 0x7B68EE);
        CSS.put("medium_spring_green", 0x00FA9A);
        CSS.put("medium_turquoise", 0x48D1CC);
        CSS.put("medium_violet_red", 0xC71585);
        CSS.put("midnight_blue", 0x191970);
        CSS.put("mint_cream", 0xF5FFFA);
        CSS.put("misty_rose", 0xFFE4E1);
        CSS.put("moccasin", 0xFFE4B5);
        CSS.put("navajo_white", 0xFFDEAD);
        CSS.put("navy", 0x000080);
        CSS.put("old_lace", 0xFDF5E6);
        CSS.put("olive", 0x808000);
        CSS.put("olive_drab", 0x6B8E23);
        CSS.put("orange", 0xFFA500);
        CSS.put("orange_red", 0xFF4500);
        CSS.put("orchid", 0xDA70D6);
        CSS.put("pale_goldenrod", 0xEEE8AA);
        CSS.put("pale_green", 0x98FB98);
        CSS.put("pale_turquoise", 0xAFEEEE);
        CSS.put("pale_violet_red", 0xDB7093);
        CSS.put("papaya_whip", 0xFFEFD5);
        CSS.put("peach_puff", 0xFFDAB9);
        CSS.put("peru", 0xCD853F);
        CSS.put("pink", 0xFFC0CB);
        CSS.put("plum", 0xDDA0DD);
        CSS.put("powder_blue", 0xB0E0E6);
        CSS.put("purple", 0x800080);
        CSS.put("rebecca_purple", 0x663399);
        CSS.put("red", 0xFF0000);
        CSS.put("rosy_brown", 0xBC8F8F);
        CSS.put("royal_blue", 0x4169E1);
        CSS.put("saddle_brown", 0x8B4513);
        CSS.put("salmon", 0xFA8072);
        CSS.put("sandy_brown", 0xF4A460);
        CSS.put("sea_green", 0x2E8B57);
        CSS.put("seashell", 0xFFF5EE);
        CSS.put("sienna", 0xA0522D);
        CSS.put("silver", 0xC0C0C0);
        CSS.put("sky_blue", 0x87CEEB);
        CSS.put("slate_blue", 0x6A5ACD);
        CSS.put("slate_gray", 0x708090);
        CSS.put("snow", 0xFFFAFA);
        CSS.put("spring_green", 0x00FF7F);
        CSS.put("steel_blue", 0x4682B4);
        CSS.put("tan", 0xD2B48C);
        CSS.put("teal", 0x008080);
        CSS.put("thistle", 0xD8BFD8);
        CSS.put("tomato", 0xFF6347);
        CSS.put("turquoise", 0x40E0D0);
        CSS.put("violet", 0xEE82EE);
        CSS.put("wheat", 0xF5DEB3);
        CSS.put("white", 0xFFFFFF);
        CSS.put("white_smoke", 0xF5F5F5);
        CSS.put("yellow", 0xFFFF00);
        CSS.put("yellow_green", 0x9ACD32);

        DYES.put("white", 0xF9FFFE);
        DYES.put("light_gray", 0x9D9D97);
        DYES.put("gray", 0x474F52);
        DYES.put("black", 0x1D1D21);
        DYES.put("brown", 0x835432);
        DYES.put("red", 0xB02E26);
        DYES.put("orange", 0xF9801D);
        DYES.put("yellow", 0xFED83D);
        DYES.put("lime", 0x80C71F);
        DYES.put("green", 0x5E7C16);
        DYES.put("cyan", 0x169C9C);
        DYES.put("light_blue", 0x3AB3DA);
        DYES.put("blue", 0x3C44AA);
        DYES.put("purple", 0x8932B8);
        DYES.put("magenta", 0xC74EBD);
        DYES.put("pink", 0xF38BAA);
    }
}
