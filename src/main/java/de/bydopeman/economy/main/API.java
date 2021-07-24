package de.bydopeman.economy.main;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class API {


    public boolean playerExists(String uuid){
        try {
            ResultSet rs = MySQL.query("SELECT * FROM eco WHERE UUID= '" + uuid + "'");
            if(rs.next()){
                return (rs.getString("UUID") != null);
            }
            return false;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void createPlayer(String uuid){
        MySQL.update("INSERT INTO eco(UUID, COINS) VALUES ('" + uuid + "', '0');");
    }

    public void addEco(String uuid, int amount){
        if(playerExists(uuid)){
            int eco = getEco(uuid) + amount;
            MySQL.update("UPDATE eco SET COINS= '" + eco + "' WHERE UUID= '" + uuid + "';");
        }
    }

    public void setEco(String uuid, int amount){
        if(playerExists(uuid)){
            MySQL.update("UPDATE eco SET COINS= '" + amount + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setEco(uuid, amount);
        }
    }

    public int getEco(String uuid){
        if(playerExists(uuid)){
            try {
                PreparedStatement ps = MySQL.con.prepareStatement("SELECT COINS FROM eco WHERE UUID= '" + uuid + "';");
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    return rs.getInt("COINS");
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            getEco(uuid);
        }
        return 0;
    }
}
