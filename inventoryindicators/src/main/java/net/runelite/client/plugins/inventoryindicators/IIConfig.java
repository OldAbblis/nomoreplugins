package net.runelite.client.plugins.inventoryindicators;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("amiscplugin")
public interface IIConfig extends Config {

    @ConfigTitleSection(
            keyName = "firstTitle",
            name = "Inventory Indicators",
            description = "",
            position = 1
    )
    default Title clickLogTitle() { return new Title(); }

    @ConfigItem(
            keyName = "inventoryEnum",
            name = "Display options for",
            description = "Drop down menu to display configuration options.",
            position = 2,
            titleSection = "firstTitle"
    )
    default InventoryEnum inventoryEnum() { return InventoryEnum.FULL; }

    @ConfigItem(
            keyName = "displayFull",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 3,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default boolean displayFull() { return false; }

    @ConfigItem(
            keyName = "fullLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 10.10.20.20",
            position = 4,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default String fullLocation() { return "100.0.5.5"; }

    @ConfigItem(
            keyName = "fullColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 5,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default Color fullColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayContain",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 6,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default boolean displayContain() { return false; }

    @ConfigItem(
            keyName = "containNames",
            name = "Inventory contains:",
            description = "An indicator will display if the inventory contains the following, format to use: item name.item name e.g Bones.Logs",
            position = 7,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default String containName() { return null; }

    @ConfigItem(
            keyName = "containLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 10.10.20.20",
            position = 8,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default String containLocation() { return "105.0.5.5"; }

    @ConfigItem(
            keyName = "containColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 9,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "firstTitle"
    )
    default Color containColor() { return Color.RED; }

}
