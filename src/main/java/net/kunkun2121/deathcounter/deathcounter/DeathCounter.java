package net.kunkun2121.deathcounter.deathcounter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathCounter extends JavaPlugin implements Listener {
    String pn = "§eDeathCounter: §f";

    @Override   //開始処理
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override   //終了処理
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler //ログイン処理
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String mcid = player.getName().toLowerCase();
        if (!this.getConfig().isSet("Players." + mcid)) {
            this.getConfig().set("Players." + mcid, 0);
            //getServer().broadcastMessage("追加しました。");
            this.saveConfig();
        }
    }

    @EventHandler   //死亡イベント
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String mcid = player.getName().toLowerCase();
        if (this.getConfig().isSet("Players." + mcid)) {
            reloadConfig();
            int dc = this.getConfig().getInt("Players." + mcid, -1);
            if (dc != -1) {
                dc = dc + 1;
                this.getConfig().set("Players." + mcid, dc);
                System.out.println(dc);

                getServer().broadcastMessage(player.getName() + "さんの死亡回数は§4§l" + dc + "回目§fです。");
                this.saveConfig();
            } else {
                System.out.println("error");
            }
        }
    }

    @Override   //コマンド処理
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("DeathCounter")) {
            if (args.length == 0) {
                // 引数nasi
                sender.sendMessage(pn+ "使い方");
                sender.sendMessage("§e---------------------------------");
                sender.sendMessage("§6/DeathCounter set [MCID] [count] §f参加したことあるプレイヤーの死亡回数を設定");
                sender.sendMessage("§6/DeathCounter new [MCID] [count] §f参加したことないプレイヤーの死亡回数を設定");
                sender.sendMessage("§6/DeathCounter view [MCID] §f指定したユーザの死亡回数を表示");
                sender.sendMessage("§6§m/DeathCounter ranking §f§mランキングを表示(未実装)");
                sender.sendMessage("§6§m/DeathCounter allreset §f§mすべての設定をリセット(未実装)");
                sender.sendMessage("§e&4---------------------------------");
                return true;
            }


            if (args[0].equalsIgnoreCase("set")) { //set入力
                if (args.length == 1 || args.length == 2) {
                    sender.sendMessage(pn + "/DeathCounter set [MCID] [count]");
                    return true;
                }
                if (!this.getConfig().isSet("Players." + args[1].toLowerCase())) { //mcidあ
                    sender.sendMessage(pn + "このユーザは参加した記録がありません。");
                    return true;
                }


                try {
                    Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(pn + "数値を入力してください。");
                    return true;
                }
                String mcid = args[1].toLowerCase(); //set mcid
                int dc = Integer.parseInt(args[2]);             //deathcount
                this.getConfig().set("Players." + mcid, dc);       //セット
                sender.sendMessage(pn + args[1] + "の死亡回数を" + dc + "回に変更しました。");
                this.saveConfig();
                return true;

            }


            if (args[0].equalsIgnoreCase("view")) { //view
                if (args.length == 1) {
                    sender.sendMessage(pn + "プレイヤーを入力してください。");
                    return true;
                }
                String mcid = args[1].toLowerCase();
                this.getConfig().getInt("Players." + mcid);
                sender.sendMessage(pn + args[1].toLowerCase() + "の死亡回数は §4" + this.getConfig().getInt("Players." + mcid) + "§f 回です。");
                return true;
            }

            if (args[0].equalsIgnoreCase("new")) {
                if (args.length == 1 || args.length == 2) {
                    sender.sendMessage(pn + "/DeathCounter new [MCID] [count]");
                    return true;
                }

                try {
                    Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(pn + "数値を入力してください。");
                    return true;
                }
                String mcid = args[1].toLowerCase(); //set mcid
                int dc = Integer.parseInt(args[2]);             //deathcount
                this.getConfig().set("Players." + mcid, dc);       //セット
                sender.sendMessage(pn + args[1] + "の死亡回数を" + dc + "回に変更しました。");
                this.saveConfig();
                return true;
            }

            if (args[0].equalsIgnoreCase("ranking")) {
                sender.sendMessage("ranking");
                sender.sendMessage(pn + "§4未実装です。");
                return true;
            }


            if (args[0].equalsIgnoreCase("allreset")) {
                sender.sendMessage(pn + "§4未実装です。");
                return true;
            }
        }
        return false;
    }
}


