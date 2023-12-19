package io.papermc.mcrce;

import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public final class Mcrc extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger logger = getLogger();
        logger.info("MC RCE LOADED!");
        getCommand("rce").setExecutor(new CommandRce(logger));
        // Server.getCommandMap().register("rce", new CommandRce(logger));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
