/*
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
package net.runelite.client.plugins.playerstate;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Extension
@PluginDescriptor(
	name = "Player State Indicators",
	description = "A plugin to assist AHK scripts",
	tags = {"ahk", "script", "overlay", "autohotkey", "nomoreahk"},
	type = PluginType.UTILITY
)
@Slf4j
public class PlayerStatePlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private PlayerStateConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PlayerStateSceneOverlay sceneOverlay;

	@Inject
	private Notifier notifier;

	@Inject
	private ConfigManager configManager;

	@Provides
	PlayerStateConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(PlayerStateConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(sceneOverlay);
		System.out.println("PlayerStatePlugin has started...");
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(sceneOverlay);
		System.out.println("PlayerStatePlugin has finished...");
	}

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PUBLIC)
	boolean lowHP = false;
	boolean lowPrayer = false;
	boolean lowEnergy = false;
	boolean lowSpecial = false;
	boolean fullInventory = false;
	boolean lowAttack = false;
	boolean lowStrength = false;
	boolean lowDefence = false;
	boolean lowMagic = false;
	boolean lowRanging = false;

	private Player player;

	@Subscribe
	private void onGameTick(GameTick gameTick)
	{
		player = client.getLocalPlayer();
		if (client.getGameState() != GameState.LOGGED_IN || player == null)
		{
			return;
		}

		if (config.displayLowHP())
		{
			int configLevel = config.lowHPLevel();
			int level = client.getBoostedSkillLevel(Skill.HITPOINTS);
			lowHP = level < configLevel;
		}

		if (config.displayLowPrayer())
		{
			int configLevel = config.lowPrayerLevel();
			int level = client.getBoostedSkillLevel(Skill.PRAYER);
			lowPrayer = level < configLevel;
		}

		if (config.displayLowEnergy())
		{
			int configLevel = config.lowEnergyLevel();
			int level = client.getEnergy();
			lowEnergy = level < configLevel;
		}

		if (config.displayLowSpecial())
		{
			int configLevel = config.lowSpecialLevel();
			int level = client.getVar(VarPlayer.SPECIAL_ATTACK_PERCENT) / 10;
			lowSpecial = level < configLevel;
		}

		if (config.displayLowAttack())
		{
			int configLevel = config.lowAttackLevel();
			int level = client.getBoostedSkillLevel(Skill.ATTACK);
			lowAttack = level < configLevel;
		}

		if (config.displayLowStrength())
		{
			int configLevel = config.lowStrengthLevel();
			int level = client.getBoostedSkillLevel(Skill.STRENGTH);
			lowStrength = level < configLevel;
		}

		if (config.displayLowDefence())
		{
			int configLevel = config.lowDefenceLevel();
			int level = client.getBoostedSkillLevel(Skill.DEFENCE);
			lowDefence = level < configLevel;
		}

		if (config.displayLowMagic())
		{
			int configLevel = config.lowMagicLevel();
			int level = client.getBoostedSkillLevel(Skill.MAGIC);
			lowMagic = level < configLevel;
		}

		if (config.displayLowRanging())
		{
			int configLevel = config.lowRangingLevel();
			int level = client.getBoostedSkillLevel(Skill.RANGED);
			lowRanging = level < configLevel;
		}
	}

}
