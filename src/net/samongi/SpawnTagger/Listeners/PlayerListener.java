package net.samongi.SpawnTagger.Listeners;

import net.md_5.bungee.api.ChatColor;
import net.samongi.SpawnTagger.SpawnTagger;
import net.samongi.SpawnTagger.Info.InfoTracker;
import net.samongi.SpawnTagger.Info.PlayerInfo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener implements Listener
{
  private final InfoTracker tracker;
  @SuppressWarnings("unused")
  private final JavaPlugin plugin;
  
  public PlayerListener(JavaPlugin plugin, InfoTracker tracker)
  {
    this.tracker = tracker;
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    tracker.createPlayerInfo(event.getPlayer());
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    tracker.destroyPlayerInfo(event.getPlayer());
  }
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    PlayerInfo info = tracker.getPlayerInfo(player);
    
    Material wand_type = info.getWandType();
    if(wand_type == null) return;
    if(event.getItem().getType() != wand_type) return;
    
    Location loc = player.getLocation();

    Location new_loc = new Location(loc.getWorld(), loc.getBlockX() + .5, loc.getBlockY() + .5, loc.getBlockZ() + .5);
    new_loc.setDirection(loc.getDirection());
    player.sendMessage(ChatColor.YELLOW + "Recorded: [" + PlayerInfo.getSaveFormat(new_loc) + "]");
    SpawnTagger.debugLog("Added Location [" + PlayerInfo.getSaveFormat(new_loc) + "] to '" + player.getName() + "'s List");
    
    info.addSpawn(new_loc);
  }
}
