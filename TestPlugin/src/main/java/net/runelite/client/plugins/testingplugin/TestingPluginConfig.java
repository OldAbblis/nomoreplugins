package net.runelite.client.plugins.testingplugin;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("testingplugin")
public interface TestingPluginConfig extends Config
{
    @ConfigItem(
            keyName = "location",
            name = "Location",
            description = "",
            position = 1
    )
    default String location()
    {
        return "100:100:10:10";
    }
}
