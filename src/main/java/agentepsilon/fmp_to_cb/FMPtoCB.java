package agentepsilon.fmp_to_cb;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

/**
 * Created by AgentEpsilon on 5/24/18.
 */
@Mod(modid = FMPtoCB.MODID, name = FMPtoCB.NAME, version = FMPtoCB.VERSION, acceptableRemoteVersions = "*")
public class FMPtoCB {
    public static final String MODID = "fmp_to_cb";
    public static final String NAME = "FMP to CB";
    public static final String VERSION = "1.0.1";

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
    }

    @Mod.EventHandler
    public void serverinit(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandFMPtoCB());
    }

    public static void tellSender(ICommandSender sender, String message) {
        sender.sendMessage(new TextComponentString(message));
    }
}
