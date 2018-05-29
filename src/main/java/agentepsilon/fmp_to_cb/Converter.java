package agentepsilon.fmp_to_cb;

import codechicken.microblock.IMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import codechicken.microblock.Microblock;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import mod.chiselsandbits.api.APIExceptions;
import mod.chiselsandbits.api.IBitAccess;
import mod.chiselsandbits.core.ChiselsAndBits;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by AgentEpsilon on 5/24/18.
 */
public class Converter {


    public static Cube3d multipartToCube(TileMultipart tile, ICommandSender sender) {
        Cube3d model = new Cube3d();
        for (TMultiPart part: tile.jPartList()) {
            if (part instanceof Microblock) {
                Microblock mb = (Microblock) part;
                FMPtoCB.tellSender(sender, "Slot "+mb.getShapeSlot()+", Size "+mb.getSize()+", Name "+mb.getType());
                Slots.run(model, mb);
            }
        }
        return model;
    }

    public static void convert(MinecraftServer server, ICommandSender sender) {
        World world = sender.getEntityWorld();
        Chunk chunk = world.getChunkFromBlockCoords(sender.getPosition());
        Queue<Tuple<BlockPos, Cube3d>> queue = new ConcurrentLinkedQueue<>();
        chunk.getTileEntityMap().values().parallelStream()
                .filter(te -> te instanceof TileMultipart).map(te -> (TileMultipart)te)
                .forEachOrdered(tile -> {
                    FMPtoCB.tellSender(sender, tile.getPos().toString());
                    Cube3d blob = multipartToCube(tile, sender);
                    queue.add(new Tuple<>(tile.getPos(), blob));
                });
        queue.forEach(t -> {
            world.setBlockToAir(t.getFirst());
            try {
                IBitAccess access = ChiselsAndBits.getApi().getBitAccess(world, t.getFirst());
                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 16; y++) {
                        for (int z = 0; z < 16; z++) {
                            if (t.getSecond().get(x, y, z) != -1) {
                                IMicroMaterial mat = MicroMaterialRegistry.getMaterial(t.getSecond().get(x, y, z));
                                access.setBitAt(x, y, z, ChiselsAndBits.getApi().createBitItem(mat.getItem()).getBitAt(0, 0, 0));
                            }
                        }
                    }
                }
                access.commitChanges(true);
            } catch (APIExceptions.CannotBeChiseled e) {
                FMPtoCB.tellSender(sender, "Block cannot be chiseled");
            } catch (NullPointerException e) {
                FMPtoCB.tellSender(sender, "Invalid Bit Item");
            } catch (APIExceptions.SpaceOccupied e) {
                FMPtoCB.tellSender(sender, "Bit space occupied");
            }
        });
    }
}
