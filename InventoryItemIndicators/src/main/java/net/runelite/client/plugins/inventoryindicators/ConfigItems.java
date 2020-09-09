package net.runelite.client.plugins.inventoryindicators;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.awt.*;

@Value
@AllArgsConstructor
class ConfigItems {
    String name;
    Integer amount;
    Color color;
}
