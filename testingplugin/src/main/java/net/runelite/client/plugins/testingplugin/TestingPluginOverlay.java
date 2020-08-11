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
    public Dimension render(Graphics2D g) {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return null;
        }
        player = client.getLocalPlayer();
        if (player == null)
        {
            return null;
        }

        Rectangle playerBounds = client.getLocalPlayer().getConvexHull().getBounds();

        // 360 no scope
        g.setColor(Color.GREEN);
        g.drawRect(client.getMouseCanvasPosition().getX(), 0, 0, client.getCanvasHeight());
        g.drawRect(0, client.getMouseCanvasPosition().getY(), client.getCanvasWidth(), 0);
        if (client.getLocalDestinationLocation() != null)
        {
            LocalPoint tilePlayerWalkingTo = client.getLocalDestinationLocation();
            Polygon tile = Perspective.getCanvasTilePoly(client, tilePlayerWalkingTo);
            g.drawLine((int) playerBounds.getCenterX()
                    , (int) playerBounds.getCenterY()
                    , (int) tile.getBounds().getCenterX()
                    , (int) tile.getBounds().getCenterY());
        }

        return null;
    }
}
