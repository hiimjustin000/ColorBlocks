package com.hiimjustin000.colorblocks.screen;

import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.handler.ColorBlockScreenHandler;
import com.hiimjustin000.colorblocks.network.UpdateColorPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ColorBlockScreen extends HandledScreen<ColorBlockScreenHandler>
{
    public static final Identifier TEXTURE = ColorBlocks.id("textures/gui/select_color.png");

    private TextFieldWidget speedEdit;
    private ButtonWidget saveButton;
    private PageButtonWidget prevButton;
    private PageButtonWidget nextButton;
    private int index = -1;
    private boolean mainColor = false;
    private final ChangeColorElement changeColorElement = new ChangeColorElement();

    public ColorBlockScreen(ColorBlockScreenHandler handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, title);
        backgroundWidth = 194;
        backgroundHeight = 222;
    }

    public static Text getKey(String key)
    {
        return Text.translatable("container." + ColorBlocks.MOD_ID + ".color_block." + key);
    }

    @Override
    protected void init()
    {
        super.init();

        if (changeColorElement.isVisible() && index != -1)
            changeColorElement.setIndex(index);
        changeColorElement.init(width, height, client, handler);
        x = changeColorElement.updatePosition(width, backgroundWidth);
        speedEdit = new TextFieldWidget(textRenderer, x + 9, y + 204, 32, 9, Text.literal("#"));
        speedEdit.setDrawsBackground(false);
        speedEdit.setMaxLength(2);
        speedEdit.setText(Integer.toString(handler.getSpeed()));
        speedEdit.setEditableColor(16777215);
        saveButton = ButtonWidget.builder(getKey("save"), btn -> updateEntity())
                .position(x + 127, y + 198).width(60).build();
        prevButton = new PageButtonWidget(x + 165, y + 4, PageButtonWidget.Type.PREVIOUS, this);
        nextButton = new PageButtonWidget(x + 176, y + 4, PageButtonWidget.Type.NEXT, this);

        addDrawableChild(changeColorElement);
        addDrawableChild(speedEdit);
        addDrawableChild(saveButton);
        addDrawableChild(prevButton);
        addDrawableChild(nextButton);
    }

    public void updatePosition()
    {
        x = changeColorElement.updatePosition(width, backgroundWidth);
        speedEdit.setPosition(x + 9, y + 204);
        saveButton.setPosition(x + 127, y + 198);
        prevButton.setPosition(x + 165, y + 4);
        nextButton.setPosition(x + 176, y + 4);
    }

    protected boolean charIsInvalid(char character)
    {
        return switch (character)
        {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> false;
            default -> true;
        };
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType)
    {
        if (slot != null)
        {
            boolean isTheSame = false;
            if (slot.getIndex() == index)
                isTheSame = slot.inventory == handler.getColorInventory() && mainColor ||
                        slot.inventory == handler.getColorsInventory() && !mainColor;

            if (isTheSame)
                changeColorElement.toggleVisibility();
            else
            {
                mainColor = slot.inventory == handler.getColorInventory();
                changeColorElement.setMainColor(mainColor);
                updateChangeColorElement(slot.getIndex());
            }

            updatePosition();
        }
        else
            super.onMouseClick(null, slotId, button, actionType);
    }

    public void updateChangeColorElement(int idx)
    {
        changeColorElement.setIndex(idx);
        if (!changeColorElement.isVisible())
            changeColorElement.toggleVisibility();
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers)
    {
        if (speedEdit.isFocused())
        {
            String previous = speedEdit.getText();
            if (charIsInvalid(codePoint))
                return false;

            if (Integer.parseInt(previous + codePoint) > 20)
            {
                speedEdit.setText("20");
                updateSpeed();
                return true;
            }

            if (speedEdit.charTyped(codePoint, modifiers))
            {
                updateSpeed();
                return true;
            }
        }

        return super.charTyped(codePoint, modifiers);
    }

    protected void updateSpeed()
    {
        String text = speedEdit.getText();
        handler.setSpeed(text.isEmpty() ? 0 : Integer.parseInt(text));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (speedEdit.isFocused() && keyCode != 256 && keyCode != 257)
            return speedEdit.keyPressed(keyCode, scanCode, modifiers);

        if (keyCode == 257)
        {
            if (changeColorElement.isVisible())
                changeColorElement.keyPressed(keyCode, scanCode, modifiers);
            else
                updateEntity();
            return true;
        }

        if (client != null && client.options.inventoryKey.matchesKey(keyCode, scanCode)) // y u bully me mojang
        {
            if (changeColorElement.isVisible())
                changeColorElement.keyPressed(keyCode, scanCode, modifiers);
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    protected void updateEntity()
    {
        handler.toData().saveToEntity(handler.getEntity());
        ClientPlayNetworking.send(new UpdateColorPayload(handler.toData()));
        close();
    }

    public void onPrevPage()
    {
        handler.onPrevPage();
        if (changeColorElement.isVisible())
            updateChangeColorElement(index);
    }

    public void onNextPage()
    {
        handler.onNextPage();
        if (changeColorElement.isVisible())
            updateChangeColorElement(index);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY)
    {
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY)
    {
        context.drawText(textRenderer, title, titleX, titleY, 4210752, false);
        context.drawText(textRenderer, getKey("speed"), 44, 204, 4210752, false);
    }
}
