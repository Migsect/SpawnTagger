package net.samongi.SpawnTagger.Commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.samongi.SamongiLib.CommandHandling.ArgumentType;
import net.samongi.SamongiLib.CommandHandling.BaseCommand;
import net.samongi.SamongiLib.CommandHandling.SenderType;
import net.samongi.SpawnTagger.SpawnTagger;
import net.samongi.SpawnTagger.Info.InfoTracker;
import net.samongi.SpawnTagger.Info.PlayerInfo;

public class CommandRecord extends BaseCommand
{
  private final InfoTracker tracker;
  public CommandRecord(String command_path, InfoTracker tracker)
  {
    super(command_path);
    this.tracker = tracker;
    
    this.permission = "spawntagger.record";
    this.allowed_senders.add(SenderType.PLAYER);
    this.allowed_arguments.add(new ArgumentType[0]);
    
  }

  @Override
  public boolean run(CommandSender sender, String[] args)
  {
    Player player = (Player) sender;
    PlayerInfo info = tracker.getPlayerInfo(player);
    Location loc = player.getLocation();

    Location new_loc = new Location(loc.getWorld(), loc.getBlockX() + .5, loc.getBlockY() + .5, loc.getBlockZ() + .5);
    new_loc.setDirection(loc.getDirection());
    player.sendMessage(ChatColor.YELLOW + "Recorded: [" + PlayerInfo.getSaveFormat(new_loc) + "]");
    SpawnTagger.debugLog("Added Location [" + PlayerInfo.getSaveFormat(new_loc) + "] to '" + player.getName() + "'s List");
    
    info.addSpawn(new_loc);
    
    return true;
  }

}
