package agentepsilon.fmp_to_cb;

import com.google.common.collect.ImmutableList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by AgentEpsilon on 5/24/18.
 */
public class CommandFMPtoCB extends CommandBase {

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
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentString("Converting..."));
        Converter.convert(server, sender);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return FMLCommonHandler.instance().getSide() == Side.CLIENT || Arrays.stream(server.getPlayerList().getOppedPlayerNames()).anyMatch((s) -> s.equals(sender.getName()));
    }
}
