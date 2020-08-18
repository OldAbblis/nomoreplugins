package net.runelite.client.plugins.testingplugin;

import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetType;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Random;

public class TestingPluginOverlay extends Overlay {

    private final Client client;
    private final TestingPlugin plugin;
    private final TestingPluginConfig config;

    @Inject
    TestingPluginOverlay(Client client, TestingPlugin plugin, TestingPluginConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    Player player;
    int timer = 2500;
    long currentTime = System.currentTimeMillis();

    @Override
    public Dimension render(Graphics2D g)
    {
        return null;
    }
}
