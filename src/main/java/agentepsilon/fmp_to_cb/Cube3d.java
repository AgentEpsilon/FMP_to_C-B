package agentepsilon.fmp_to_cb;

import net.minecraft.util.math.Vec3i;

/**
 * Created by AgentEpsilon on 5/24/18.
 */
public class Cube3d {
    int[][][] cube = new int[16][16][16];
    public Cube3d() {
        setAll(0, 0, 0, 15, 15, 15, -1);
    }
    void set(int x, int y, int z, int val) {
        cube[x][y][z] = val;
    }
    int get(int x, int y, int z) {
        return cube[x][y][z];
    }
    void setAll(int fromX, int fromY, int fromZ, int toX, int toY, int toZ, int val) {
        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                for (int z = fromZ; z <= toZ; z++) {
                    cube[x][y][z] = val;
                }
            }
        }
    }
}