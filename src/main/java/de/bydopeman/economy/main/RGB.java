package de.bydopeman.economy.main;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RGB {

    private final static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String format(String msg){
        if(Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17")){
            Matcher match = pattern.matcher(msg);
            while(match.find()){
                String color = msg.substring(match.start(), match.end());
                msg = msg.replace(color, ChatColor.of(color) + "");
                match = pattern.matcher(msg);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
