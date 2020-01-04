package de.xxschrandxx.npg.api;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.xxschrandxx.npg.NetherPortalGate;

public class BungeeCord
  implements PluginMessageListener
{
  public static boolean connectToBungeeServer(Player player, String server) {
    try{
      Messenger messenger = Bukkit.getMessenger();
      if (!messenger.isOutgoingChannelRegistered(NetherPortalGate.getInstance(), "BungeeCord")) {
        messenger.registerOutgoingPluginChannel(NetherPortalGate.getInstance(), "BungeeCord");
      }
      if (server.length() == 0) {
        player.sendMessage("&cThe server name was empty!");
        return false;
      }
      ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(byteArray);
      
      out.writeUTF("Connect");
      out.writeUTF(server);
      
      player.sendPluginMessage(NetherPortalGate.getInstance(), "BungeeCord", byteArray.toByteArray());
    }
    catch (Exception ex) {
      ex.printStackTrace();
      NetherPortalGate.getLogHandler().log(Level.WARNING, "Could not handle BungeeCord command from " + player.getName() + ": tried to connect to \"" + server + "\".");
      
      return false;
    }
    return true;
  }
  
  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    if (!channel.equals("BungeeCord")) {
      return;
    }
  }
}
