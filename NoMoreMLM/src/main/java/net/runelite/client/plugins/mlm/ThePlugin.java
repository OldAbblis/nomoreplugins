/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.mlm;

import com.google.inject.Provides;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.WorldLocation;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.http.api.worlds.World;
import org.pf4j.Extension;

import net.runelite.client.plugins.nmutils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Extension
@PluginDescriptor(
	name = "NoMore MLM",
	description = "Motherload Mine",
	tags = {"tag1", "tag2", "tag3"},
	type = PluginType.UTILITY
)
@Slf4j
@PluginDependency(Utils.class)
public class ThePlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private TheConfig config;

	@Inject
	private TheOverlay overlay;

	@Inject
	private Utils utils;

	@Provides
    TheConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TheConfig.class);
	}

	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> lowerObjects = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> upperObjects = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> upperNorthWestVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> upperNorthEastVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> upperSouthVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashSet<GameObject> closestRockfall = new HashSet<>();

	Set<Integer> OBJECT_IDS = Stream.of(ObjectsList.ORE_VEINS, ObjectsList.BANK_CHEST,  ObjectsList.EMPTY_SACK, ObjectsList.FIX_WATER_WHEEL, ObjectsList.LADDERS, ObjectsList.SHORTCUTS, ObjectsList.ROCK_FALL).flatMap(Set::stream).collect(Collectors.toSet());

	@Getter(AccessLevel.PACKAGE)
	int numberOfBrokenWaterWheels = 0;

	@Override
	protected void startUp() {
		numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() {
		upperObjects.clear();
		lowerObjects.clear();
		upperNorthEastVeins.clear();
		upperNorthWestVeins.clear();
		upperSouthVeins.clear();
		overlayManager.remove(overlay);
	}

	private int getAmountOfBrokenWaterWheels()
	{
		if (client.getGameState() == GameState.LOGGED_IN) {
			return utils.getGameObjects(26670).size();
		}
		return 0;
	}

	@Subscribe
	private void on(GameTick event)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return;
		}
		if (player.getWorldLocation().getRegionID() != 14936)
		{
			return;
		}
		@Nullable
		GameObject rockfall = utils.findNearestGameObject(26679, 26680);
		if (rockfall != null)
		{
			closestRockfall.clear();
			closestRockfall.add(rockfall);
		}
	}

	@Subscribe
	private void on(ConfigChanged event)
	{
		if (!event.getGroup().equals("NoMoreMLM"))
		{
			return;
		}
	}

	@Subscribe
	private void on(GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN)
		{
			upperObjects.clear();
			lowerObjects.clear();
			upperNorthEastVeins.clear();
			upperNorthWestVeins.clear();
			upperSouthVeins.clear();
		}
		numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
	}

	@Subscribe
	private void on(GameObjectSpawned event)
	{
		getTileObject(event.getTile(), event.getGameObject(), null);
		if (ObjectsList.FIX_WATER_WHEEL.contains(event.getGameObject().getId()))
		{
			numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
		}
	}

	@Subscribe
	private void on(GameObjectChanged event) {
		getTileObject(event.getTile(), event.getGameObject(), event.getPrevious());
		if (ObjectsList.FIX_WATER_WHEEL.contains(event.getGameObject().getId()))
		{
			numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
		}
	}

	@Subscribe
	private void on(GameObjectDespawned event)
	{
		getTileObject(event.getTile(),  null, event.getGameObject());
		if (ObjectsList.FIX_WATER_WHEEL.contains(event.getGameObject().getId()))
		{
			numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
		}
	}

	@Subscribe
	private void on(DecorativeObjectSpawned event)
	{
		getTileObject(event.getTile(), event.getDecorativeObject(), null);
	}

	@Subscribe
	private void on(DecorativeObjectChanged event) { getTileObject(event.getTile(), event.getDecorativeObject(), event.getPrevious()); }

	@Subscribe
	private void on(DecorativeObjectDespawned event)
	{
		getTileObject(event.getTile(), null, event.getDecorativeObject());
	}

	@Subscribe
	private void on(GroundObjectSpawned event)
	{
		getTileObject(event.getTile(), event.getGroundObject(), null);
	}

	@Subscribe
	private void on(GroundObjectChanged event) { getTileObject(event.getTile(), event.getGroundObject(), event.getPrevious()); }

	@Subscribe
	private void on(GroundObjectDespawned event)
	{
		getTileObject(event.getTile(), null, event.getGroundObject());
	}

	@Subscribe
	private void on(WallObjectSpawned event)
	{
		getTileObject(event.getTile(), event.getWallObject(), null);
	}

	@Subscribe
	private void on(WallObjectChanged event) { getTileObject(event.getTile(), event.getWallObject(), event.getPrevious()); }

	@Subscribe
	private void on(WallObjectDespawned event)
	{
		getTileObject(event.getTile(), null, event.getWallObject());
	}

	private void getTileObject(Tile tile, TileObject newObject, TileObject oldObject)
	{
		upperObjects.remove(tile);
		lowerObjects.remove(tile);
		upperNorthEastVeins.remove(tile);
		upperNorthWestVeins.remove(tile);
		upperSouthVeins.remove(tile);
		if (newObject == null)
		{
			return;
		}
		if (OBJECT_IDS.contains(newObject.getId()))
		{
			LocalPoint lp = newObject.getLocalLocation();
			if (lp == null)
			{
				return;
			}
			if (isUpstairs(lp))
			{
				if (ObjectsList.ORE_VEINS.contains(newObject.getId()))
				{
					checkTileObjectLocation(tile, newObject);
				}
				upperObjects.putIfAbsent(tile, newObject);
			}
			else
			{
				lowerObjects.putIfAbsent(tile, newObject);
			}
		}
	}

	private void checkTileObjectLocation(Tile tile ,TileObject tileObject)
	{
		WorldPoint wp = tileObject.getWorldLocation();
		if (wp == null)
		{
			return;
		}
		if (utils.isTileObjectWithinArea(tileObject, 3747, 5684, 3754, 5676, 0))
		{
			upperNorthWestVeins.put(tile, tileObject);
		}
		if (utils.isTileObjectWithinArea(tileObject, 3755, 5685, 3765, 5675, 0))
		{
			upperNorthEastVeins.put(tile, tileObject);
		}
		if (utils.isTileObjectWithinArea(tileObject, 3756, 5674, 3762, 5668, 0))
		{
			upperSouthVeins.put(tile, tileObject);
		}
		return;
	}

	boolean isPlayerUpstairs()
	{
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return false;
		}
		LocalPoint lp = player.getLocalLocation();
		if (lp == null)
		{
			return false;
		}
		return isUpstairs(lp);
	}

	boolean isUpstairs(LocalPoint localPoint)
	{
		return Perspective.getTileHeight(client, localPoint, 0) < -490;
	}
}