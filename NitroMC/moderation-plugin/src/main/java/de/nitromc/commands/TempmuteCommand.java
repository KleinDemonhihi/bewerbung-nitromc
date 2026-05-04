package de.nitromc.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public class TempmuteCommand implements SimpleCommand {

    private final ProxyServer server;

    // UUID -> Unmute-Zeitpunkt (Millis)
    public static final Map<String, Long> tempMutedPlayers = new HashMap<>();

    public TempmuteCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length < 2) {
            source.sendMessage(Component.text("§cBenutzung: /tempmute <Spieler> <Zeit>"));
            source.sendMessage(Component.text("§7Beispiele: 10m, 2h, 1d"));
            return;
        }

        String targetName = args[0];
        String timeString = args[1];

        long duration = parseTime(timeString);
        if (duration <= 0) {
            source.sendMessage(Component.text("§cUngültiges Zeitformat. Beispiele: 10m, 2h, 1d"));
            return;
        }

        server.getPlayer(targetName).ifPresentOrElse(player -> {
            String uuid = player.getUniqueId().toString();
            long unmuteAt = System.currentTimeMillis() + duration;

            tempMutedPlayers.put(uuid, unmuteAt);
            MuteCommand.mutedPlayers.add(uuid);

            source.sendMessage(Component.text("§aSpieler temporär gemutet: §f" + targetName + " §7für " + timeString));
            player.sendMessage(Component.text("§cDu wurdest temporär gemutet für §f" + timeString));

        }, () -> {
            source.sendMessage(Component.text("§cSpieler nicht gefunden."));
        });
    }

    private long parseTime(String input) {
        try {
            long value = Long.parseLong(input.substring(0, input.length() - 1));
            char unit = input.charAt(input.length() - 1);

            return switch (unit) {
                case 's' -> value * 1000;
                case 'm' -> value * 60 * 1000;
                case 'h' -> value * 60 * 60 * 1000;
                case 'd' -> value * 24 * 60 * 60 * 1000;
                default -> -1;
            };
        } catch (Exception e) {
            return -1;
        }
    }
}
