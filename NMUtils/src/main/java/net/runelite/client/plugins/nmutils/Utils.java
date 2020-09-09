
package net.runelite.client.plugins.nmutils;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
		name = "NMUtils",
		type = PluginType.UTILITY,
		hidden = true
)
@Slf4j
@SuppressWarnings("unused")
@Singleton
public class Utils extends Plugin
{
	@Inject
	private Client client;

	@Override
	protected void startUp()
	{

	}

	@Override
	protected void shutDown()
	{

	}

	public int[] stringToIntArray(String string)
	{
		return Arrays.stream(string.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
	}

	public String removeTextFromString(String string)
	{
		return string.replaceAll("[\\d]", "");
	}

	public String removeNumbersFromString(String string)
	{
		return string.replaceAll("[0-9]", "");
	}

	public int[] getIndicatorLocation(String string)
	{
		int[] intArray = {0,0,5,5};
		if (string.isEmpty())
		{
			return intArray;
		}
		string = string.toLowerCase().replaceAll(" ", "");
		String[] strings = string.split(":");
		return Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
	}

}
