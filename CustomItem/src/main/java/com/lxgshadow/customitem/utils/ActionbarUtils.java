package com.lxgshadow.customitem.utils;

import com.lxgshadow.customitem.Main;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_14_R1.ChatComponentText;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;


public class ActionbarUtils {
    static HashMap<UUID,BukkitRunnable> messages = new HashMap<>();
    /**
     * Sends a raw message (JSON format) to the player's action bar. Note: while the action bar accepts raw messages
     * it is currently only capable of displaying text.
     * The message will appear above the player's hot bar for 2 seconds and then fade away over 1 second.
     */
    public static void sendRaw(Player player,String text){
        ChatComponentText chatComponentText = new ChatComponentText(text);
        ChatMessageType chatMessageType = ChatMessageType.GAME_INFO;
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatComponentText,chatMessageType);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

    public static void send(Player player,String text){
        sendRaw(player,"{\"text\": \"" + text + "\"}");

    }

    public static void startStay(Player player,String text,int time){
        cancelStay(player);
        BukkitRunnable r = new BukkitRunnable() {
            int t = 0;
            @Override
            public void run() {
                send(player,text);
                t++;
                if (t>(time-3)){
                    cancelStay(player);
                }
            }
        };
        r.runTaskTimer(Main.getInstance(),0,20);
        messages.put(player.getUniqueId(), r);
    }
    public static void cancelStay(Player player){
        if (messages.get(player.getUniqueId()) != null){
            messages.get(player.getUniqueId()).cancel();
        }
        messages.remove(player.getUniqueId());
    }

}
