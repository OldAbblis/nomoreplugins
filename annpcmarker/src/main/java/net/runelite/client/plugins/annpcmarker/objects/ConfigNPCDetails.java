package net.runelite.client.plugins.annpcmarker.objects;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.awt.*;

@Value
@AllArgsConstructor
public class ConfigNPCDetails {
    String configNPCName;
    Color configNPCColor;
}
