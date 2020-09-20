package net.runelite.client.plugins.nmutils;

import lombok.AllArgsConstructor;
import lombok.Value;
import net.runelite.api.Item;

@Value
@AllArgsConstructor
public class InventoryItem {
    Item item;
    String itemName;
    int itemId;
    int inventorySlot;
}
