package de.nitromc.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

public class UnmuteCommand implements SimpleCommand {

    private final ProxyServer server;

    public UnmuteCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length < 1) {
            source.sendMessage(Component.text("§cBenutzung: /unmute <Spieler>"));
            return;
        }

        String targetName = args[0];

        server.getPlayer(targetName).ifPresentOrElse(player -> {
            String uuid = player.getUniqueId().toString();

            if (MuteCommand.mutedPlayers.remove(uuid)) {
                source.sendMessage(Component.text("§aSpieler entmutet: §f" + targetName));
                player.sendMessage(Component.text("§aDu wurdest entmutet."));
            } else {
                source.sendMessage(Component.text("§cDieser Spieler ist nicht gemutet."));
            }

        }, () -> {
            source.sendMessage(Component.text("§cSpieler nicht gefunden."));
        });
    }
}
