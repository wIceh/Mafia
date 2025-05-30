package me.wiceh.mafia.server.commands;

import me.wiceh.mafia.MafiaPlugin;
import me.wiceh.mafia.api.permissions.MafiaPermission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;

public class MafiaCommand implements CommandExecutor, TabCompleter {
    private final MafiaPlugin plugin;

    public MafiaCommand(MafiaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return List.of();

        if (args.length == 1) {
            List<String> subcommands = new ArrayList<>();

            if (player.hasPermission(MafiaPermission.CREATE.getPermission()))
                subcommands.add("crea");

            return subcommands;
        }

        return List.of();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            sendHelpMessage(player);
            return true;
        }

        String action = args[0].toLowerCase();
        switch (action) {
            case "crea" -> {
                if (!player.hasPermission(MafiaPermission.CREATE.getPermission())) {
                    player.sendMessage(text("\n §cᴇʀʀᴏʀᴇ \n §7Non hai il permesso per eseguire questo comando.\n"));
                    break;
                }

                String name = args[1];
                String color = args[2];
                String leaderName = args[3];

                if (plugin.getMafiaManager().getMafia(name).isPresent()) {
                    player.sendMessage(text("\n §cᴇʀʀᴏʀᴇ \n §7Mafia §f" + name + " §7già esistente.\n"));
                    break;
                }


            }
        }

        return true;
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage(empty());
        player.sendMessage(text("§4§lMAFIA"));
        player.sendMessage(text(" §8| §7/mafia crea <nome> <colore> <leader>"));
        player.sendMessage(empty());
    }
}
