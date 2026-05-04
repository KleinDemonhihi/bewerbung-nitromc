package de.nitromc.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.util.Arrays;

public class BanCommand implements SimpleCommand {

    private final ProxyServer server;

    public BanCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length < 2) {
            source.sendMessage(Component.text("§cBenutzung: /ban <Spieler> <Grund>"));
            return;
        }

        String targetName = args[0];
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        server.getPlayer(targetName).ifPresentOrElse(player -> {
            player.disconnect(Component.text("§cDu wurdest gebannt!\n§7Grund: §f" + reason));
            source.sendMessage(Component.text("§aSpieler gebannt: §f" + targetName));
        }, () -> {
            source.sendMessage(Component.text("§cSpieler nicht gefunden."));
        });
    }
}
