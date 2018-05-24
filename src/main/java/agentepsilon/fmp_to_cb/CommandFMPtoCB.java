package agentepsilon.fmp_to_cb;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by AgentEpsilon on 5/24/18.
 */
public class CommandFMPtoCB implements ICommand {

    @Override
    public String getName() {
        return "fmptocb";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/fmptocb";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("fmptocb");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentString("Converting..."));
        Converter.convert(server, sender);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return Arrays.stream(server.getPlayerList().getOppedPlayerNames()).anyMatch((s) -> s.equals(sender.getName()));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return this.getName().compareTo(o.getName());
    }
}
