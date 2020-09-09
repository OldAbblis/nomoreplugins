package net.runelite.client.plugins.mouselogging;

import net.runelite.client.config.*;

@ConfigGroup("amiscplugin")
public interface AMiscConfig extends Config {

    @ConfigTitleSection(
            keyName = "clickLogTitle",
            name = "Mouse Clicks",
            description = "Logs the button pressed, x, y, time button held and time between clicks.",
            position = 0
    )
    default Title clickLogTitle() { return new Title(); }

    @ConfigItem(
            position = 1,
            keyName = "clickLog",
            name = "Log mouse clicks",
            description = "Folder saved to my documents.",
            titleSection = "clickLogTitle"
    )
    default boolean clickLog() {
        return false;
    }

    @ConfigItem(
            position = 2,
            keyName = "invertYClickLog",
            name = "invert Y axis",
            description = "Inverts the Y axis to make displaying the clicks on graphs easier.",
            titleSection = "clickLogTitle"
    )
    default boolean invertYClickLog() {
        return false;
    }

    @ConfigItem(
            position = 3,
            keyName = "separateInventoryLogging",
            name = "Separate inventory log.",
            description = "Logs the inventory clicks in a separate file.",
            titleSection = "clickLogTitle"
    )
    default boolean separateInventoryLog() {
        return false;
    }

    @ConfigItem(
            position = 4,
            keyName = "displayMarker",
            name = "Display marker.",
            description = "Display's a marker over the inventory.",
            titleSection = "clickLogTitle",
            hidden = true,
            unhide = "separateInventoryLogging",
            unhideValue = "true"
    )
    default boolean displayMarker() {
        return false;
    }

    @Range(
            min = 0,
            max = 255
    )
    @ConfigItem(
            position = 5,
            keyName = "markerTransparency",
            name = "Marker transparency.",
            description = "Set's the transparency of the marker.",
            titleSection = "clickLogTitle",
            hidden = true,
            unhide = "separateInventoryLogging",
            unhideValue = "true"
    )
    default int markerTransparency() { return 50; }

    @ConfigTitleSection(
            keyName = "mousePositionTitle",
            name = "Mouse Position",
            description = "Logs the x and y of the mouse constantly.",
            position = 10
    )
    default Title mousePositionTitle() {
        return new Title();
    }

    @ConfigItem(
            position = 11,
            keyName = "positionLog",
            name = "Log mouse position",
            description = "Folder saved to my documents.",
            titleSection = "positionLog"
    )
    default boolean positionLog() {
        return false;
    }

    @ConfigItem(
            position = 12,
            keyName = "invertYPositionLog",
            name = "invert Y axis",
            description = "Inverts the Y axis to make displaying the clicks on graphs easier.",
            titleSection = "positionLog"
    )
    default boolean invertYPositionLog() {
        return false;
    }

}
