package com.hiimjustin000.colorblocks.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.function.Predicate;

// It took me thirty minutes to find out it was a Fabric mixin
// This is essentially a copy of the original class
public class PageButtonWidget extends ButtonWidget
{
    public static final Identifier TEXTURE = Identifier.of("fabric", "textures/gui/creative_buttons.png");

    private final ColorBlockScreen screen;
    private final Type type;

    public PageButtonWidget(int x, int y, Type type, ColorBlockScreen screen)
    {
        super(x, y, 11, 12, type.text, btn -> type.consumer.accept(screen), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.screen = screen;
        this.type = type;
    }

    @Override
    protected void renderWidget(DrawContext context, int mX, int mY, float delta)
    {
        active = type.enabled.test(screen);
        visible = screen.getScreenHandler().getColors().size() > 100;

        if (!visible)
            return;

        int u = active && isHovered() ? 22 : 0;
        int v = active ? 0 : 12;
        context.drawTexture(TEXTURE, getX(), getY(), u + (type == Type.NEXT ? 11 : 0), v, 11, 12);

        if (isHovered())
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.translatable("fabric.gui.creativeTabPage",
                    screen.getScreenHandler().getPage() + 1, screen.getScreenHandler().getMaxPage() + 1), mX, mY);
    }

    public enum Type
    {
        NEXT(Text.literal(">"), ColorBlockScreen::onNextPage,
                screen -> screen.getScreenHandler().getPage() < screen.getScreenHandler().getMaxPage()),
        PREVIOUS(Text.literal("<"), ColorBlockScreen::onPrevPage,
                screen -> screen.getScreenHandler().getPage() > 0);

        final Text text;
        final Consumer<ColorBlockScreen> consumer;
        final Predicate<ColorBlockScreen> enabled;

        Type(Text text, Consumer<ColorBlockScreen> consumer, Predicate<ColorBlockScreen> enabled)
        {
            this.text = text;
            this.consumer = consumer;
            this.enabled = enabled;
        }
    }
}
