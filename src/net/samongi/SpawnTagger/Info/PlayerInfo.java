package net.samongi.SpawnTagger.Info;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerInfo
{
  private final String player_name;
  private List<Location> selected_spawns = new ArrayList<>();
  private final JavaPlugin plugin;
  private Material wand_type;
  
  public PlayerInfo(Player player, JavaPlugin plugin)
  {
    this.player_name = player.getName();
    this.plugin = plugin;
  }
  
  public Player getPlayer(){return Bukkit.getPlayer(player_name);}
  public List<Location> getSpawnsSelected(){return this.selected_spawns;}
  public List<String> getSpawnsDump()
  {
    List<String> ret_list = new ArrayList<>();
    for(Location l : selected_spawns)
    {
      String s = getSaveFormat(l);
      ret_list.add(s);
    }
    return ret_list;
  }
  public void addSpawn(Location loc){this.selected_spawns.add(loc);}
  public void clearSpawns(){this.selected_spawns.clear();}
  
  public void displaySelectedSpawns(){this.displaySelectedSpawns(this.getPlayer(), 200);}
  public void displaySelectedSpawns(Player player, int ticks)
  {
    for(Location l : selected_spawns) 
    {
      ArmorStand entity = (ArmorStand) player.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
      entity.setHelmet(new ItemStack(Material.PUMPKIN));
      ReturnSpawnDisplay task = new ReturnSpawnDisplay(entity);
      task.runTaskLater(this.plugin, ticks);
    }
  }
  class ReturnSpawnDisplay extends BukkitRunnable
  {
    private Entity entity;
    ReturnSpawnDisplay(Entity entity)
    {
      this.entity = entity;
    }
    
    @Override
    public void run()
    {
      if(!entity.isDead()) entity.remove();  
    }
  }
  
  public final boolean saveSpawns(File file)
  {
    try
    {
      file.getParentFile().mkdirs();
      if(!file.exists()) file.createNewFile();
      FileOutputStream f_out = new FileOutputStream(file);
      OutputStreamWriter writer = new OutputStreamWriter(f_out);
      
      for(String s : this.getSpawnsDump()) writer.write(s + System.getProperty("line.separator"));
      
      writer.close();
      f_out.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  public static String getSaveFormat(Location l)
  {
    Vector dir = l.getDirection();
    String dir_x_round = String.format("%.2f", dir.getX());
    String dir_y_round = String.format("%.2f", dir.getY());
    String dir_z_round = String.format("%.2f", dir.getZ());
    return l.getX() + " " + l.getY() + " " + l.getZ() + " " + dir_x_round + " " + dir_y_round + " " + dir_z_round;
  }
  public Material getWandType(){return this.wand_type;}
  public void setWandType(Material type){this.wand_type = type;}
  
  
  
}
