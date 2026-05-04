package de.nitromc.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.util.HashSet;
import java.util.Set;

public class MuteCommand implements SimpleCommand {

    private final ProxyServer server;


    public static final Set<String> mutedPlayers = new HashSet<>();

    public MuteCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        // Ablauf von Tempmutes prüfen
        TempmuteCommand.tempMutedPlayers.entrySet().removeIf(entry -> {
            if (System.currentTimeMillis() >= entry.getValue()) {
                mutedPlayers.remove(entry.getKey());
                return true;
            }
            return false;
        });

        if (args.length < 1) {
            source.sendMessage(Component.text("§cBenutzung: /mute <Spieler>"));
            return;
        }

        String targetName = args[0];

        server.getPlayer(targetName).ifPresentOrElse(player -> {
            String uuid = player.getUniqueId().toString();

            mutedPlayers.add(uuid);

            source.sendMessage(Component.text("§aSpieler gemutet: §f" + targetName));
            player.sendMessage(Component.text("§cDu wurdest gemutet."));
        }, () -> {
            source.sendMessage(Component.text("§cSpieler nicht gefunden."));
        });
    }
}
