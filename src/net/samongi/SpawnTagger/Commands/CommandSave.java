package net.samongi.SpawnTagger.Commands;

import java.io.File;
import java.util.Calendar;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.samongi.SamongiLib.CommandHandling.ArgumentType;
import net.samongi.SamongiLib.CommandHandling.BaseCommand;
import net.samongi.SamongiLib.CommandHandling.SenderType;
import net.samongi.SpawnTagger.SpawnTagger;
import net.samongi.SpawnTagger.Info.InfoTracker;
import net.samongi.SpawnTagger.Info.PlayerInfo;

public class CommandSave extends BaseCommand
{
  JavaPlugin plugin;
  InfoTracker tracker;
  
  public CommandSave(String command_path, InfoTracker tracker, JavaPlugin plugin)
  {
    super(command_path);
    
    this.plugin = plugin;
    this.tracker = tracker;
    
    this.permission = "channels.save";
    this.allowed_senders.add(SenderType.PLAYER);
    this.allowed_arguments.add(new ArgumentType[0]);
  }

  @Override
  public boolean run(CommandSender sender, String[] args)
  {
    Player player = (Player) sender;
    PlayerInfo info = tracker.getPlayerInfo(player);
    
    Calendar cal = Calendar.getInstance();
    String time = cal.get(Calendar.YEAR)+ "_" + cal.get(Calendar.MONTH) + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR) + "_" + cal.get(Calendar.MINUTE)+ "_" + cal.get(Calendar.SECOND);
    String player_name = player.getName();
    String plugin_dir = plugin.getDataFolder().getAbsolutePath();

    String save_to = plugin.getConfig().getString("save-location");
    if(save_to == null)
    {
      return true;
    }
    save_to = save_to.replace("%PLUGIN_FOLDER%", plugin_dir);
    save_to = save_to.replace("%PLAYER_NAME%", player_name);
    save_to = save_to.replace("%TIME%", time);
    
    File file_save = new File(save_to);
    
    String access_loc = plugin.getConfig().getString("access-location");
    if(access_loc == null)
    {
      return true;
    }
    access_loc = access_loc.replace("%PLUGIN_FOLDER%", plugin_dir);
    access_loc = access_loc.replace("%PLAYER_NAME%", player_name);
    access_loc = access_loc.replace("%TIME%", time);
    access_loc = access_loc.replace("/", File.separator);

    SpawnTagger.debugLog("Saving file to: " + file_save.toPath());
    boolean success = info.saveSpawns(file_save);
    if(!success)
    {
      player.sendMessage(ChatColor.RED + "File was not saved because of some reason (this is the server-ops fault, not your's)");
      return true;
    }
    
    player.sendMessage(ChatColor.YELLOW + "Spawn Locations saved.");
    player.sendMessage(ChatColor.YELLOW + "You can access the list at: " + ChatColor.WHITE + access_loc);
    
    
    return false;
  }

}
