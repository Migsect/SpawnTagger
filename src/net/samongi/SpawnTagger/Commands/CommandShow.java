package net.samongi.SpawnTagger.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.samongi.SamongiLib.CommandHandling.ArgumentType;
import net.samongi.SamongiLib.CommandHandling.BaseCommand;
import net.samongi.SamongiLib.CommandHandling.SenderType;
import net.samongi.SpawnTagger.Info.InfoTracker;
import net.samongi.SpawnTagger.Info.PlayerInfo;

public class CommandShow extends BaseCommand
{
  private InfoTracker tracker;

  public CommandShow(String command_path, InfoTracker tracker)
  {
    super(command_path);
    this.tracker = tracker;

    this.permission = "channels.show";
    this.allowed_senders.add(SenderType.PLAYER);
    this.allowed_arguments.add(new ArgumentType[0]);
    
  }

  @Override
  public boolean run(CommandSender sender, String[] args)
  {
    Player player = (Player) sender;
    PlayerInfo info = tracker.getPlayerInfo(player);
    
    info.displaySelectedSpawns();
    
    return true;
  }
  
}
