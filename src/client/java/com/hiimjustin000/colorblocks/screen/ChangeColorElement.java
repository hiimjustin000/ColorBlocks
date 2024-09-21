package com.hiimjustin000.colorblocks.screen;

import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.handler.ColorBlockScreenHandler;
import com.hiimjustin000.colorblocks.init.ComponentTypeInit;
import com.hiimjustin000.colorblocks.init.ItemInit;
import com.hiimjustin000.colorblocks.item.ColorBlockItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ChangeColorElement extends AbstractParentElement implements Drawable, Selectable
{
    public static final Identifier TEXTURE = ColorBlocks.id("textures/gui/change_color.png");

    private final Inventory inventory = new SimpleInventory(1);
    private final List<Element> children = new ArrayList<>();
    private TextFieldWidget rEdit;
    private TextFieldWidget gEdit;
    private TextFieldWidget bEdit;
    private TextFieldWidget hEdit;
    private ButtonWidget saveButton;
    private ButtonWidget removeButton;
    private ButtonWidget cancelButton;
    private Slot slot;
    private MinecraftClient client;
    private ColorBlockScreenHandler handler;
    private int index = -1;
    private boolean visible = false;
    private int x;
    private int y;
    private boolean mainColor = false;

    @Override
    public List<? extends Element> children()
    {
        return children;
    }

    public void init(int width, int height, MinecraftClient client, ColorBlockScreenHandler handler)
    {
        this.client = client;
        this.handler = handler;
        this.slot = new Slot(inventory, 0, 38, 14);
        this.x = (width - 92) / 2 + (width < 288 ? 0 : 98);
        this.y = (height - 222) / 2;
        if (visible)
            initVisuals();
    }

    protected void initVisuals()
    {
        children.clear();

        int color = slot.getStack().getOrDefault(ComponentTypeInit.COLOR, 16777215);
        rEdit = initializeRGBTextField(44, (color >> 16) & 0xff, ColorBlockScreen.getKey("red"));
        gEdit = initializeRGBTextField(66, (color >> 8) & 0xff, ColorBlockScreen.getKey("green"));
        bEdit = initializeRGBTextField(88, color & 0xff, ColorBlockScreen.getKey("blue"));
        hEdit = new TextFieldWidget(client.textRenderer, x + 26, y + 110, 46, 9, ColorBlockScreen.getKey("hex"));
        hEdit.setMaxLength(6);
        hEdit.setDrawsBackground(false);
        hEdit.setText(String.format("%06X", color));
        hEdit.setEditableColor(16777215);

        saveButton = ButtonWidget.builder(ColorBlockScreen.getKey("save"), b -> changeColor())
                .position(x + 16, y + 130).width(60).build();
        removeButton = ButtonWidget.builder(ColorBlockScreen.getKey("remove"), b -> removeColor())
                .position(x + 16, y + 160).width(60).build();
        cancelButton = ButtonWidget.builder(ColorBlockScreen.getKey("cancel"), b -> close())
                .position(x + 16, y + 190).width(60).build();

        children.add(rEdit);
        children.add(gEdit);
        children.add(bEdit);
        children.add(hEdit);
        children.add(saveButton);
        children.add(removeButton);
        children.add(cancelButton);
    }

    protected TextFieldWidget initializeRGBTextField(int y, int value, Text message)
    {
        TextFieldWidget textField = new TextFieldWidget(client.textRenderer, x + 26, this.y + y, 46, 9, message);
        textField.setMaxLength(3);
        textField.setDrawsBackground(false);
        textField.setText(Integer.toString(value));
        textField.setEditableColor(16777215);
        return textField;
    }

    public void toggleVisibility()
    {
        setVisible(!visible);
    }

    protected void setVisible(boolean visible)
    {
        if (visible)
            initVisuals();

        this.visible = visible;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setMainColor(boolean mainColor)
    {
        this.mainColor = mainColor;
    }

    public int updatePosition(int width, int backgroundWidth)
    {
        return (width - backgroundWidth) / 2 - (visible && width >= 288 ? 47 : 0);
    }

    protected int getIndex()
    {
        return index + handler.getPage() * 100;
    }

    public void setIndex(int index)
    {
        this.index = index;
        if (this.client.currentScreen instanceof ColorBlockScreen screen)
            screen.setIndex(index);
        if (index >= 0)
            updateSlot(this.handler.getSlot(mainColor ? 100 : index));
    }

    protected void close()
    {
        setVisible(false);
        if (client.currentScreen instanceof ColorBlockScreen screen)
            screen.updatePosition();
    }

    protected void changeColor()
    {
        int color = Integer.parseInt(hEdit.getText(), 16);
        if (mainColor)
            handler.setColor(color);
        else
            handler.changeColor(getIndex(), color);
        close();
    }

    protected void removeColor()
    {
        int adjIndex = getIndex();
        if (!mainColor && adjIndex < handler.getColors().size())
        {
            handler.removeColor(adjIndex);
            close();
        }
    }

    protected void updateSlot(Slot slot)
    {
        this.slot.setStack(slot.getStack().copy());
        if (slot.getStack().isEmpty())
            this.slot.setStack(ItemInit.COLOR_BLOCK.getDefaultStack());
        if (visible)
        {
            int color = this.slot.getStack().getOrDefault(ComponentTypeInit.COLOR, 16777215);
            this.rEdit.setText(Integer.toString(color >> 16 & 255));
            this.gEdit.setText(Integer.toString(color >> 8 & 255));
            this.bEdit.setText(Integer.toString(color & 255));
            this.hEdit.setText(String.format("%06X", color));
        }
    }

    protected void updateHex()
    {
        hEdit.setText(String.format("%06X", Integer.parseInt(rEdit.getText()) << 16 |
                Integer.parseInt(gEdit.getText()) << 8 | Integer.parseInt(bEdit.getText())));
        updateItemDisplay();
    }

    protected void updateItemDisplay()
    {
        slot.setStack(ColorBlockItem.withColor(Integer.parseInt(hEdit.getText(), 16)));
    }

    protected void updateRGB()
    {
        int color = Integer.parseInt(hEdit.getText(), 16);
        rEdit.setText(Integer.toString(color >> 16 & 255));
        gEdit.setText(Integer.toString(color >> 8 & 255));
        bEdit.setText(Integer.toString(color & 255));
        updateItemDisplay();
    }

    protected boolean charIsInvalid(char character, boolean hex)
    {
        switch (character)
        {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
            {
                return false;
            }
        }

        if (hex)
        {
            switch (Character.toLowerCase(character))
            {
                case 'a', 'b', 'c', 'd', 'e', 'f' ->
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers)
    {
        if (rEdit.isFocused())
        {
            Object result = rgbCharTyped(rEdit, codePoint, modifiers);
            if (result != null)
                return (boolean)result;
        }
        if (gEdit.isFocused())
        {
            Object result = rgbCharTyped(gEdit, codePoint, modifiers);
            if (result != null)
                return (boolean)result;
        }
        if (bEdit.isFocused())
        {
            Object result = rgbCharTyped(bEdit, codePoint, modifiers);
            if (result != null)
                return (boolean)result;
        }
        if (hEdit.isFocused())
        {
            String previous = hEdit.getText();

            if (charIsInvalid(codePoint, true))
                return false;

            if (hEdit.charTyped(codePoint, modifiers) && previous.startsWith("0"))
            {
                hEdit.setText(previous.substring(1, 6) + Character.toUpperCase(codePoint));
                updateRGB();
                return true;
            }
        }

        return super.charTyped(codePoint, modifiers);
    }

    protected Object rgbCharTyped(TextFieldWidget textField, char codePoint, int modifiers)
    {
        String previous = textField.getText();

        if (charIsInvalid(codePoint, false))
            return false;

        if (previous.equals("0"))
        {
            textField.setText(Character.toString(codePoint));
            updateHex();
            return true;
        }

        if (Integer.parseInt(previous + codePoint) > 255)
        {
            textField.setText("255");
            updateHex();
            return true;
        }

        if (textField.charTyped(codePoint, modifiers))
        {
            updateHex();
            return true;
        }

        return null;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (rEdit.isFocused() && keyCode != 256 && keyCode != 257)
            return rgbKeyPressed(rEdit, keyCode, scanCode, modifiers);
        if (gEdit.isFocused() && keyCode != 256 && keyCode != 257)
            return rgbKeyPressed(gEdit, keyCode, scanCode, modifiers);
        if (bEdit.isFocused() && keyCode != 256 && keyCode != 257)
            return rgbKeyPressed(bEdit, keyCode, scanCode, modifiers);
        if (hEdit.isFocused() && keyCode != 256 && keyCode != 257)
        {
            String previous = hEdit.getText();

            if (keyCode == 259)
            {
                hEdit.setText("0" + previous.substring(0, 5));
                updateRGB();
                return true;
            }

            return hEdit.keyPressed(keyCode, scanCode, modifiers);
        }

        if (keyCode == 257)
        {
            changeColor();
            return true;
        }

        if (keyCode == 261)
        {
            removeColor();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    protected boolean rgbKeyPressed(TextFieldWidget textField, int keyCode, int scanCode, int modifiers)
    {
        String previous = textField.getText();

        if (keyCode == 259)
        {
            textField.setText(previous.length() > 1 ? previous.substring(0, previous.length() - 1) : "0");
            updateHex();
            return true;
        }

        return textField.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mX, int mY, float delta)
    {
        if (visible)
        {
            context.getMatrices().push();
            context.getMatrices().translate(0f, 0f, 100f);
            context.drawTexture(TEXTURE, x, y, 0, 0, 92, 222);
            rEdit.render(context, mX, mY, delta);
            gEdit.render(context, mX, mY, delta);
            bEdit.render(context, mX, mY, delta);
            hEdit.render(context, mX, mY, delta);
            saveButton.render(context, mX, mY, delta);
            removeButton.render(context, mX, mY, delta);
            cancelButton.render(context, mX, mY, delta);
            renderSlot(context);
            renderLabels(context);
            context.getMatrices().pop();
        }
    }

    public void renderLabels(DrawContext context)
    {
        if (visible)
        {
            context.drawText(client.textRenderer, Text.literal("R"), x + 18, y + 44, 4210752, false);
            context.drawText(client.textRenderer, Text.literal("G"), x + 18, y + 66, 4210752, false);
            context.drawText(client.textRenderer, Text.literal("B"), x + 18, y + 88, 4210752, false);
            context.drawText(client.textRenderer, Text.literal("#"), x + 18, y + 110, 4210752, false);
        }
    }

    public void renderSlot(DrawContext context)
    {
        if (visible)
        {
            context.getMatrices().push();
            context.getMatrices().translate(0f, 0f, 100f);
            context.drawItem(slot.getStack(), x + slot.x, y + slot.y);
            context.drawItemInSlot(client.textRenderer, slot.getStack(), x + slot.x, y + slot.y,
                    mainColor ? "0" : Integer.toString(Math.min(getIndex(), handler.getColors().size()) + 1));
            context.getMatrices().pop();
        }
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder)
    {
        List<Selectable> list = new ArrayList<>();
        list.add(rEdit);
        list.add(gEdit);
        list.add(bEdit);
        list.add(hEdit);
        list.add(saveButton);
        list.add(removeButton);
        list.add(cancelButton);

        Screen.SelectedElementNarrationData result = Screen.findSelectedElementData(list, null);
        if (result != null)
            result.selectable.appendNarrations(builder);
    }

    @Override
    public SelectionType getType()
    {
        return visible ? SelectionType.HOVERED : SelectionType.NONE;
    }
}
