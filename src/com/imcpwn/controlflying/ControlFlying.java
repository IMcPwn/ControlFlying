/* ControlFlying Bukkit/Spigot plugin by IMcPwn.
 * Copyright 2016 IMcPwn 

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * For the latest code and contact information visit: http://imcpwn.com
 */

package com.imcpwn.controlflying;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ControlFlying extends JavaPlugin implements Listener {
	public final Logger logger = Logger.getLogger("Minecraft");

	@Override
	public void onDisable()
	{
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + "is now disabled. ");
	}

	@Override
	public void onEnable()
	{
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + "is now enabled. ");
		// For EventListener
		getServer().getPluginManager().registerEvents(this, this);
	}

	private boolean isMinable(Player player, GameMode gamemode)
	{
		if (player.hasPermission("controlflying.*"))
		{
			return true;
		}
		if (player.hasPermission("controlflying.build"))
		{
			return true;
		}
		if (player.isFlying() && !gamemode.toString().equals("CREATIVE"))
		{
			return false;
		}
		return true;
	}

	private boolean isPickupAble(Player player, GameMode gamemode)
	{
		if (player.hasPermission("controlflying.*"))
		{
			return true;
		}
		if (player.hasPermission("controlflying.pickup"))
		{
			return true;
		}
		if (player.isFlying() && !gamemode.toString().equals("CREATIVE"))
		{
			return false;
		}
		return true;
	}

	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		final Player player = event.getPlayer();
		final GameMode gamemode = event.getPlayer().getGameMode();
		boolean isMinable = isMinable(player, gamemode);
		if (isMinable)
		{
			return;
		}
		player.sendMessage(ChatColor.RED + "You cannot break blocks while flying.");
		event.setCancelled(true);
	}

	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		final Player player = event.getPlayer();
		final GameMode gamemode = event.getPlayer().getGameMode();
		boolean isPickupAble = isPickupAble(player, gamemode);
		if (isPickupAble)
		{
			return;
		}
		player.sendMessage(ChatColor.RED + "You can not pick up items while flying.");
		event.setCancelled(true);
	}

}
