/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Cas <https://github.com/casvandongen>
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

import java.awt.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import net.runelite.api.Player;
import net.runelite.client.ui.overlay.*;
import net.runelite.api.Client;

@Singleton
class PlayerStateSceneOverlay extends Overlay
{

	private final Client client;
	private final PlayerStateConfig config;
	private final PlayerStatePlugin plugin;

	@Inject
	private PlayerStateSceneOverlay(final Client client, final PlayerStateConfig config, final PlayerStatePlugin plugin)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.LOW);
		setLayer(OverlayLayer.ABOVE_SCENE);
		this.client = client;
		this.config = config;
		this.plugin = plugin;
	}

	boolean isPlayerIdle = false;
	Timer idleTimer = new Timer();
	Timer interactingTimer = new Timer();
	Timer walkingTimer = new Timer();
	TimerTask task;
	Player player;

	@Override
	public Dimension render(Graphics2D graphics)
	{
		player = client.getLocalPlayer();
		if (player == null)
		{
			return null;
		}

		if (plugin.lowHP && config.displayLowHP())
		{
			renderG(graphics, config.hpColor(), config.hpLocation().split(Pattern.quote(".")));
		}

		if (plugin.lowPrayer && config.displayLowPrayer())
		{
			renderG(graphics, config.prayerColor(), config.prayerLocation().split(Pattern.quote(".")));
		}

		if (plugin.lowEnergy && config.displayLowEnergy())
		{
			renderG(graphics, config.energyColor(), config.energyLocation().split(Pattern.quote(".")));
		}

		if (plugin.lowSpecial && config.displayLowSpecial())
		{
			renderG(graphics, config.specialColor(), config.specialLocation().split(Pattern.quote(".")));
		}

		if (plugin.lowAttack && config.displayLowAttack())
		{
			renderG(graphics, config.attackColor(), config.attackLocation().split(Pattern.quote(".")));
		}

		if (plugin.lowStrength && config.displayLowStrength())
		{
			renderG(graphics, config.strengthColor(), config.strengthLocation().split(Pattern.quote(".")));
		}

		if (plugin.lowDefence && config.displayLowDefence())
		{
			renderG(graphics, config.defenceColor(), config.defenceLocation().split(Pattern.quote(".")));
		}

		if (plugin.lowMagic && config.displayLowMagic())
		{
			renderG(graphics, config.magicColor(), config.magicLocation().split(Pattern.quote(".")));
		}

		if (plugin.lowRanging && config.displayLowRanging())
		{
			renderG(graphics, config.rangingColor(), config.rangingLocation().split(Pattern.quote(".")));
		}

		if (isPlayerIdle() && config.displayIdle())
		{
			if (task != null)
				renderG(graphics, config.idleColor(), config.idleLocation().split(Pattern.quote(".")));
		}

		/*
		if (plugin.fullInventory && config.display())
		{
			renderG(graphics, config.fullInventoryColor(), config.fullInventoryLocation().split(Pattern.quote(".")));
		}*/

		return null;
	}

	private void renderG(Graphics2D graphics, Color color, String[] s)
	{
		graphics.setColor(color);
		graphics.fillRect(getParsedInt(s,0),
				getParsedInt(s,1),
				getParsedInt(s,2),
				getParsedInt(s,3));
	}

	private int getParsedInt(String[] string, int number)
	{
		return Integer.parseInt(string[number]);
	}

	private boolean isPlayerIdle()
	{
		if (config.displayIdle())
		{
			if (player.getSpotAnimation() == -1
					&& player.getInteracting() == null
					&& player.getAnimation() == -1
					&& client.getLocalDestinationLocation() == null)
			{
				startTimer(idleTimer);
			}
			else
			{
				isPlayerIdle = false;
				task = null;
			}

		}
		return isPlayerIdle;
	}

	private void startTimer(Timer timer)
	{
		if (task == null) {
			if (timer == idleTimer) {
				task = new TimerTask() {
					public void run() {
						isPlayerIdle = true;
					}
				};
				idleTimer.schedule(task, config.idleTime());
			}
		}
	}
}