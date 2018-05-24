package agentepsilon.fmp_to_cb;

import codechicken.microblock.*;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * Created by AgentEpsilon on 5/24/18.
 */
public class Slots {
    public static Slots SLOTS = new Slots();
    public HashMap<Integer, TriConsumer<Cube3d, Integer, Integer>> slots = new HashMap<>();

    public static void run(Cube3d cube, Microblock mb) {
        int s = mb.getShapeSlot();
        if (mb instanceof PostMicroblock) {
            s += 27; // hacks, because FMP doesnt work like it says it does
        } else if (mb instanceof CornerMicroblock) {
            s += 7;
        } else if (mb instanceof EdgeMicroblock) {
            s += 15;
        }

        SLOTS.slots.get(s).accept(cube, mb.getSize()*2 - 1, mb.getMaterial());

        if (mb instanceof HollowMicroblock) {
            makeHollow(cube, s);
        }
    }

    public static void makeHollow(Cube3d cube, int slot) {
        switch(slot) {
            case 0:
            case 1:
                cube.setAll(4, 0, 4, 11, 15, 11, -1);
                break;
            case 2:
            case 3:
                cube.setAll(4, 4, 0, 11, 11, 15, -1);
                break;
            case 4:
            case 5:
                cube.setAll(0, 4, 4, 15, 11, 11, -1);
                break;
        }
    }

    private Slots() {
        slots.put(0, (cube, size, mat) -> {
            cube.setAll(0, 0, 0, 15, size, 15, mat);
        }); // BOTTOM; -y
        slots.put(1, (cube, size, mat) -> {
            cube.setAll(0, 15 - size, 0, 15, 15, 15, mat);
        }); // TOP; +y
        slots.put(2, (cube, size, mat) -> {
            cube.setAll(0, 0, 0, 15, 15, size, mat);
        }); // NORTH; -z
        slots.put(3, (cube, size, mat) -> {
            cube.setAll(0, 0, 15 - size, 15, 15, 15, mat);
        }); // SOUTH; +z
        slots.put(4, (cube, size, mat) -> {
            cube.setAll(0, 0, 0, size, 15, 15, mat);
        }); // WEST; -x
        slots.put(5, (cube, size, mat) -> {
            cube.setAll(15 - size, 0, 0, 15, 15, 15, mat);
        }); // EAST; +x
        slots.put(7, (cube, size, mat) -> {
            cube.setAll(0, 0, 0, size, size, size, mat);
        }); // CORNER_NNN;
        slots.put(8, (cube, size, mat) -> {
            cube.setAll(0, 15 - size, 0, size, 15, size, mat);
        }); // CORNER_NPN;
        slots.put(9, (cube, size, mat) -> {
            cube.setAll(0, 0, 15 - size, size, size, 15, mat);
        }); // CORNER_NNP;
        slots.put(10, (cube, size, mat) -> {
            cube.setAll(0, 15 - size, 15 - size, size, 15, 15, mat);
        }); // CORNER_NPP;
        slots.put(11, (cube, size, mat) -> {
            cube.setAll(15 - size, 0, 0, 15, size, size, mat);
        }); // CORNER_PNN;
        slots.put(12, (cube, size, mat) -> {
            cube.setAll(15 - size, 15 - size, 0, 15, 15, size, mat);
        }); // CORNER_PPN;
        slots.put(13, (cube, size, mat) -> {
            cube.setAll(15 - size, 0, 15 - size, 15, size, 15, mat);
        }); // CORNER_PNP;
        slots.put(14, (cube, size, mat) -> {
            cube.setAll(15 - size, 15 - size, 15 - size, 15, 15, 15, mat);
        }); // CORNER_PPP;
        slots.put(15, (cube, size, mat) -> {
            cube.setAll(0, 0, 0, size, 15, size, mat);
        }); // EDGE_NYN;
        slots.put(16, (cube, size, mat) -> {
            cube.setAll(0, 0, 15 - size, size, 15, 15, mat);
        }); // EDGE_NYP;
        slots.put(17, (cube, size, mat) -> {
            cube.setAll(15 - size, 0, 0, 15, 15, size, mat);
        }); // EDGE_PYN;
        slots.put(18, (cube, size, mat) -> {
            cube.setAll(15 - size, 0, 15 - size, 15, 15, 15, mat);
        }); // EDGE_PYP;
        slots.put(19, (cube, size, mat) -> {
            cube.setAll(0, 0, 0, size, size, 15, mat);
        }); // EDGE_NNZ;
        slots.put(20, (cube, size, mat) -> {
            cube.setAll(15 - size, 0, 0, 15, size, 15, mat);
        }); // EDGE_PNZ;
        slots.put(21, (cube, size, mat) -> {
            cube.setAll(0, 15 - size, 0, size, 15, 15, mat);
        }); // EDGE_NPZ;
        slots.put(22, (cube, size, mat) -> {
            cube.setAll(15 - size, 15 - size, 0, 15, 15, 15, mat);
        }); // EDGE_PPZ;
        slots.put(23, (cube, size, mat) -> {
            cube.setAll(0, 0, 0, 15, size, size, mat);
        }); // EDGE_XNN;
        slots.put(24, (cube, size, mat) -> {
            cube.setAll(0, 15 - size, 0, 15, 15, size, mat);
        }); // EDGE_XPN;
        slots.put(25, (cube, size, mat) -> {
            cube.setAll(0, 0, 15 - size, 15, size, 15, mat);
        }); // EDGE_XNP;
        slots.put(26, (cube, size, mat) -> {
            cube.setAll(0, 15 - size, 15 - size, 15, 15, 15, mat);
        }); // EDGE_XPP;

        slots.put(27, (cube, size, mat) -> {
            cube.setAll(8 - (size+1)/2, 0, 8 - (size+1)/2, 7 + (size+1)/2, 15, 7 + (size+1)/2, mat);
        }); // CENTER y-axis
        slots.put(28, (cube, size, mat) -> {
            cube.setAll(8 - (size+1)/2, 8 - (size+1)/2, 0, 7 + (size+1)/2, 7 + (size+1)/2, 15, mat);
        }); // CENTER z-axis
        slots.put(29, (cube, size, mat) -> {
            cube.setAll(0, 8 - (size+1)/2, 8 - (size+1)/2, 15, 7 + (size+1)/2, 7 + (size+1)/2, mat);
        }); // CENTER x-axis
    }
}
