package net.kunkun2121.deathcounter.deathcounter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathCounter extends JavaPlugin implements Listener {

    @Override   //開始処理
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override   //終了処理
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler //ログイン処理
    public void onJoin(PlayerJoinEvent e){
        Player player =e.getPlayer();
        String mcid = player.getName().toLowerCase();
        if(!this.getConfig().isSet("Players." + mcid)){
            this.getConfig().set("Players." + mcid, 0);
            getServer().broadcastMessage("追加しました。");
            this.saveConfig();
        }else {
            getServer().broadcastMessage("追加しませんでした。");
            this.saveConfig();
        }
    }

    @EventHandler   //死亡イベント
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String mcid = player.getName().toLowerCase();
        if(this.getConfig().isSet("Players." + mcid)) {
            reloadConfig();
            int dc = this.getConfig().getInt("Players." + mcid,-1);
            if(dc != -1) {
                dc=dc+1;
                this.getConfig().set("Players." + mcid, dc);
                System.out.println(dc);

                getServer().broadcastMessage(player.getName()+"さんの死亡回数は" + dc + "回目です。");
                this.saveConfig();
            }else{
                System.out.println("error");
            }
        }
    }

    @Override   //コマンド処理
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("DeathCounter")) {
            if (args.length == 0) {
                // 引数nasi
                sender.sendMessage("使い方");
                sender.sendMessage("&4---------------------------------");
                sender.sendMessage("/DeathCounter set name count");
                sender.sendMessage("/DeathCounter view name");
                sender.sendMessage("/DeathCounter ranking");
                sender.sendMessage("&4---------------------------------");
                return true;
            }
            if(args[0].equalsIgnoreCase("set")) { //set入力
                String mcid = args[1].toLowerCase( ); //set mcid
//                if(this.getConfig().isSet("Players." + name) { //mcidあるか
                    int dc = Integer.parseInt(args[2]);             //deathcount
                    sender.sendMessage(" "+mcid+" "+ dc);                    //debag
                    this.getConfig().set("Players." + mcid, dc);//設定
                    int test = this.getConfig().getInt("Players."+mcid);
                    sender.sendMessage(""+test);
                    this.saveConfig();
                return true;
                }
            }


            if(args[0].equalsIgnoreCase("view")) {
                sender.sendMessage("view");
                return true;
            }
            if(args[0].equalsIgnoreCase("ranking")) {
                sender.sendMessage("ranking");
                return true;
            }
        return false;
    }

}

