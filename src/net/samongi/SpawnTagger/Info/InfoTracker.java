package net.samongi.SpawnTagger.Info;

import java.util.HashMap;
import java.util.Map;

import net.samongi.SpawnTagger.SpawnTagger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InfoTracker
{
  private final Map<String, PlayerInfo> player_info = new HashMap<>();
  private final JavaPlugin plugin;
  
  public InfoTracker(JavaPlugin plugin)
  {
    this.plugin = plugin;
  }
  
  public void createPlayerInfo(Player player){player_info.put(player.getName(), new PlayerInfo(player, plugin));}
  public void destroyPlayerInfo(Player player){player_info.remove(player.getName());}
  public void createAllPlayerInfo()
  {
    SpawnTagger.group.performAction((Player p) ->{
      this.createPlayerInfo(p);
    });
  }
  public void destroyAllPlayerInfo(){player_info.clear();}
  
  public PlayerInfo getPlayerInfo(Player player){return player_info.get(player.getName());}
}
