package de.bydopeman.dopelib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private JavaPlugin plugin;
    private int resourceID;

    public UpdateChecker(JavaPlugin plugin, int resourceID){
        this.plugin = plugin;
        this.resourceID = resourceID;
    }

    public void getVersion(final Consumer<String> consumer){
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceID).openStream();
                Scanner scanner = new Scanner(inputStream);
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException e){
                this.plugin.getLogger().info("Cannot look for updates: " + e.getMessage());
            }
        });
    }
}
