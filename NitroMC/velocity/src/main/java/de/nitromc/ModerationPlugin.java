package de.nitromc;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import de.nitromc.commands.BanCommand;

@Plugin(
        id = "moderation",
        name = "ModerationPlugin",
        version = "1.0.0",
        authors = {"Marcel"}
)
public class ModerationPlugin {

    private final ProxyServer server;
    private final CommandManager commandManager;

    @Inject
    public ModerationPlugin(ProxyServer server, CommandManager commandManager) {
        this.server = server;
        this.commandManager = commandManager;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        System.out.println("[ModerationPlugin] Plugin wurde geladen!");

        commandManager.register(
                commandManager.metaBuilder("ban").build(),
                new BanCommand(server)
        );
    }
}

