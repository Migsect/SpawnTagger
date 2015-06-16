package net.samongi.SpawnTagger.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.samongi.SamongiLib.CommandHandling.ArgumentType;
import net.samongi.SamongiLib.CommandHandling.BaseCommand;
import net.samongi.SamongiLib.CommandHandling.SenderType;
import net.samongi.SpawnTagger.Info.InfoTracker;
import net.samongi.SpawnTagger.Info.PlayerInfo;

public class CommandDump extends BaseCommand
{
  private InfoTracker tracker;
  
  public CommandDump(String command_path, InfoTracker tracker)
  {
    super(command_path);
    this.tracker = tracker;

    this.permission = "channels.dump";
    this.allowed_senders.add(SenderType.PLAYER);
    this.allowed_arguments.add(new ArgumentType[0]);
  }

  @Override
  public boolean run(CommandSender sender, String[] args)
  {
    Player player = (Player) sender;
    PlayerInfo info = tracker.getPlayerInfo(player);
    
    List<String> to_send = info.getSpawnsDump();
    player.sendMessage(ChatColor.AQUA + "Dumping " + to_send.size() + " locations:");
    for(String s : to_send) player.sendMessage(ChatColor.YELLOW + s);
    
    return true;
  }

}
