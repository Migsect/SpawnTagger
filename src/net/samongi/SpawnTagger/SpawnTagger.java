package net.samongi.SpawnTagger;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import net.samongi.SamongiLib.CommandHandling.CommandHandler;
import net.samongi.SamongiLib.Maps.MapData;
import net.samongi.SamongiLib.Player.Group;
import net.samongi.SamongiLib.Player.ServerGroup;
import net.samongi.SpawnTagger.Commands.CommandClear;
import net.samongi.SpawnTagger.Commands.CommandDump;
import net.samongi.SpawnTagger.Commands.CommandHelp;
import net.samongi.SpawnTagger.Commands.CommandRecord;
import net.samongi.SpawnTagger.Commands.CommandSave;
import net.samongi.SpawnTagger.Commands.CommandShow;
import net.samongi.SpawnTagger.Commands.CommandWand;
import net.samongi.SpawnTagger.Info.InfoTracker;
import net.samongi.SpawnTagger.Listeners.PlayerListener;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnTagger extends JavaPlugin
{
  public static Logger logger;
  public static boolean debug = false;
  public static Group group;

  private InfoTracker info_tracker;
  
  public void onEnable()
  {
    logger = this.getLogger();
    group = new ServerGroup(this.getServer());
    
    // config handling.
    File config_file = new File(this.getDataFolder(),"config.yml");
    if(!config_file.exists())
    {
      SpawnTagger.log("Found no config file, copying over defaults...");
      this.getConfig().options().copyDefaults(true);
      this.saveConfig();
    }
    debug = this.getConfig().getBoolean("debug", true);

    // InfoTracker
    info_tracker = new InfoTracker(this);
    info_tracker.createAllPlayerInfo();
    
    // Command handling
    CommandHandler command_handler = new CommandHandler(this);
    command_handler.registerCommand(new CommandHelp("spawntagger", command_handler));
    command_handler.registerCommand(new CommandRecord("spawntagger record", info_tracker));
    command_handler.registerCommand(new CommandDump("spawntagger dump", info_tracker));
    command_handler.registerCommand(new CommandClear("spawntagger clear", info_tracker));
    command_handler.registerCommand(new CommandWand("spawntagger wand", info_tracker));
    command_handler.registerCommand(new CommandShow("spawntagger show", info_tracker));
    command_handler.registerCommand(new CommandSave("spawntagger save", info_tracker, this));
    
    // Event Listener
    PluginManager pm = this.getServer().getPluginManager();
    pm.registerEvents(new PlayerListener(this, info_tracker), this);
  }
  
  static final public void log(String to_log)
  {
    logger.info(to_log);
  }
  static final public void debugLog(String to_log)
  {
    if(debug == true) logger.info(to_log);
  }
  static final public boolean debug(){return debug;}
}
