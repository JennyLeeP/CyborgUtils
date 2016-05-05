package com.cyborgJenn.cyborgUtils.module.motd;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.cyborgJenn.cyborgUtils.core.utils.Reference;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


public class Motd {

	private final Path file;
    private List<String> lines = new ArrayList<>();

    public Motd(final Path file) throws IOException {
        this.file = file;
        if (!Files.exists(file.getParent())) {
            Files.createDirectories(file.getParent());
        }
        load();
        save();
    }

    private void load() throws IOException {
        if (!Files.exists(file)) {
            genDefaultMotd();
        } else {
            lines = Files.readAllLines(file, Reference.CHARSET);
        }
    }

    private void save() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file, Reference.CHARSET)) {
            for (String line : lines) {
                writer.write(line + "\r\n");
            }
        }
    }

    private void genDefaultMotd() {
        final List<String> list = new ArrayList<>();
        list.add("This is the default CyborgUtils MOTD.");
        list.add("To change it, edit the motd.txt in the ");
        list.add("CyborgUtils configuration directory");
        this.lines = list;
    }

    @SubscribeEvent
    public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        serveMotd(event.player);
    }

    public void serveMotd(final ICommandSender sender) {
        for (String line : lines) {
            sender.addChatMessage(new TextComponentString(line));
        }
    }
}
