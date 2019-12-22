import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;


    @Override
    public void onLoad() {
        getLogger().info("Survival Plugin Loading");
    }

    @Override
    public void onEnable() {
        Main.instance = this;
        Config.init();
        registerCommands();
        registerEvents();
        initializeManagers();
    }

    @Override
    public void onDisable() {
    }

    private void initializeManagers(){

    }

    private void registerEvents(){

    }

    private void registerCommands(){

    }


    public static Main getInstance() {
        return instance;
    }
}
