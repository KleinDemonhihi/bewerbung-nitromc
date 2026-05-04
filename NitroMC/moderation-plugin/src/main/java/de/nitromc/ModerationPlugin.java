package de.nitromc;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import de.nitromc.commands.BanCommand;
import de.nitromc.commands.KickCommand;
import de.nitromc.commands.MuteCommand;
import de.nitromc.commands.UnmuteCommand;
import de.nitromc.commands.TempmuteCommand;

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

        commandManager.register(
                commandManager.metaBuilder("kick").build(),
                new KickCommand(server)
        );

        commandManager.register(
                commandManager.metaBuilder("mute").build(),
                new MuteCommand(server)
        );

        commandManager.register(
                commandManager.metaBuilder("unmute").build(),
                new UnmuteCommand(server)
        );

        commandManager.register(
                commandManager.metaBuilder("tempmute").build(),
                new TempmuteCommand(server)
        );
    }
}
