package net.samongi.SpawnTagger.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.samongi.SamongiLib.CommandHandling.ArgumentType;
import net.samongi.SamongiLib.CommandHandling.BaseCommand;
import net.samongi.SamongiLib.CommandHandling.SenderType;
import net.samongi.SpawnTagger.Info.InfoTracker;
import net.samongi.SpawnTagger.Info.PlayerInfo;

public class CommandWand extends BaseCommand
{
  private InfoTracker tracker;
  
  public CommandWand(String command_path, InfoTracker tracker)
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
    ItemStack item = player.getItemInHand();
    if(item == null) return true;
    
    player.sendMessage("Set wand type material to: " + item.getType().toString());
    PlayerInfo info = tracker.getPlayerInfo(player);
    info.setWandType(item.getType());
    return true;
  }
}
