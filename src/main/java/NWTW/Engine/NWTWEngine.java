package NWTW.Engine;

import NWTW.Engine.BossBar.BossBarManager;
import NWTW.Engine.CustomSkin.SkinManager;
import NWTW.Engine.GeoIP.GeoIP;
import NWTW.Engine.Inventory.FakeInventoryListener;
import NWTW.Engine.Inventory.InventoryManager;
import NWTW.Engine.PlaceHolder.PlaceHolderAPI;
import NWTW.Engine.ScoreBoard.ScoreboardManager;
import cn.nukkit.plugin.PluginBase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NWTWEngine extends PluginBase {
    private static NWTWEngine plugin;
    private ScoreboardManager scoreboardManager;
    private InventoryManager inventoryManager;
    private BossBarManager bossBarManager;
    private SkinManager skinManager;
    private GeoIP ipManager;
    @Override
    public void onLoad() {
        plugin = this;
        super.onLoad();
    }

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        PlaceHolderAPI placeHolderAPI = new PlaceHolderAPI();
        placeHolderAPI.registerDefaultPlaceHolder();
        ipManager =  new GeoIP(getDataFolder().toString());
        scoreboardManager = new ScoreboardManager();
        inventoryManager = new InventoryManager();
        bossBarManager = new BossBarManager();
        skinManager = new SkinManager(getDataFolder().toPath().resolve("Skins"));
        getServer().getScheduler().scheduleRepeatingTask(scoreboardManager.getTask(), 20);
        getServer().getPluginManager().registerEvents(new FakeInventoryListener(),this);
        getServer().getPluginManager().registerEvents(new TestListener(),this);
        getLogger().info(getName()+"已經開啟");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTask(scoreboardManager.getTask().getTaskId());
        getLogger().info(getName()+"已經關閉");
        super.onDisable();
    }

    public static NWTWEngine getPlugin() {
        return plugin;
    }
    public static String getStringDate(String str) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(str);
        return formatter.format(currentTime);
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public GeoIP getIpManager() {
        return ipManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public BossBarManager getBossBarManager() {
        return bossBarManager;
    }

    public SkinManager getSkinManager() {
        return skinManager;
    }
}
