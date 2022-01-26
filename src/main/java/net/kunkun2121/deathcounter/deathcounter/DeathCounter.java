package net.kunkun2121.deathcounter.deathcounter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathCounter extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        FileConfiguration config = getConfig();


        getServer().getPluginManager().registerEvents(this,this);

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(this.getConfig().isSet("Players." + player.getName())) {
            reloadConfig();
            int dc = this.getConfig().getInt("Players." + player.getName(),9999);
            if(dc != 9999) {
                this.getConfig().set("Players." + player.getName(), dc + 1);
                System.out.println(dc);
                dc=dc+1;
                getServer().broadcastMessage(player.getName()+"さんの死亡回数は" + dc + "回目です。");
                this.saveConfig();
            }else{
                System.out.println("error");
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player =e.getPlayer();
        if(!this.getConfig().isSet("Players." + player.getName())){
            this.getConfig().set("Players." + player.getName(), 0);
//            getServer().broadcastMessage("追加しました。");
            this.saveConfig();
        }else {
//            getServer().broadcastMessage("追加しませんでした。");
            this.saveConfig();
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
