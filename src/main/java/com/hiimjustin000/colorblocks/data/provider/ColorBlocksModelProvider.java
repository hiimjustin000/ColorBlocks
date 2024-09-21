package com.hiimjustin000.colorblocks.data.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.init.BlockInit;
import com.hiimjustin000.colorblocks.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;

public class ColorBlocksModelProvider extends FabricModelProvider
{
    public ColorBlocksModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
    {
        blockStateModelGenerator.registerBuiltinWithParticle(BlockInit.COLOR_BLOCK, ColorBlocks.id("block/color_block_particle"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator)
    {
        new Model(Optional.of(Identifier.ofVanilla("builtin/entity")), Optional.empty(), TextureKey.PARTICLE).upload(
                ModelIds.getItemModelId(ItemInit.COLOR_BLOCK),
                TextureMap.particle(ColorBlocks.id("block/color_block_particle")),
                itemModelGenerator.writer,
                this::createBuiltinEntity
        );
    }

    public JsonObject createBuiltinEntity(Identifier id, Map<TextureKey, Identifier> textures)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parent", "minecraft:builtin/entity");
        jsonObject.addProperty("gui_light", "side");
        if (!textures.isEmpty())
        {
            JsonObject textureJsonObject = new JsonObject();
            textures.forEach((textureKey, texture) -> textureJsonObject.addProperty(textureKey.getName(), texture.toString()));
            jsonObject.add("textures", textureJsonObject);
        }

        JsonObject displayJsonObject = new JsonObject();
        displayJsonObject.add("gui", createAffineTransform(30f, 225f, 0f, 0f, 0f, 0f, .625f, .625f, .625f));
        displayJsonObject.add("ground", createAffineTransform(0f, 0f, 0f, 0f, 3f, 0f, .25f, .25f, .25f));
        displayJsonObject.add("fixed", createAffineTransform(0f, 0f, 0f, 0f, 0f, 0f, .5f, .5f, .5f));
        displayJsonObject.add("thirdperson_righthand", createAffineTransform(75f, 45f, 0f, 0f, 2.5f, 0f, .375f, .375f, .375f));
        displayJsonObject.add("firstperson_righthand", createAffineTransform(0f, 45f, 0f, 0f, 0f, 0f, .4f, .4f, .4f));
        displayJsonObject.add("firstperson_lefthand", createAffineTransform(0f, 225f, 0f, 0f, 0f, 0f, .4f, .4f, .4f));
        jsonObject.add("display", displayJsonObject);

        return jsonObject;
    }

    public JsonObject createAffineTransform(
            float rotationX, float rotationY, float rotationZ,
            float translationX, float translationY, float translationZ,
            float scaleX, float scaleY, float scaleZ
    )
    {
        JsonObject jsonObject = new JsonObject();
        JsonArray rotation = new JsonArray();
        rotation.add(rotationX);
        rotation.add(rotationY);
        rotation.add(rotationZ);
        jsonObject.add("rotation", rotation);
        JsonArray translation = new JsonArray();
        translation.add(translationX);
        translation.add(translationY);
        translation.add(translationZ);
        jsonObject.add("translation", translation);
        JsonArray scale = new JsonArray();
        scale.add(scaleX);
        scale.add(scaleY);
        scale.add(scaleZ);
        jsonObject.add("scale", scale);
        return jsonObject;
    }
}
