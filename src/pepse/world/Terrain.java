/**
 * The pepse.world package contains classes representing elements of the game world.
 */
package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * The Terrain class represents the terrain of the game world.
 * It generates and manages the terrain based on Perlin noise.
 */
public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123,74);
    private static final String TERRAIN_TAG = "terrain";
    private float groundHeightAtX0;
    private NoiseGenerator noiseGenerator;
    private ArrayList<Double> groundHeights;
    private Vector2 windowDimensions;

    /**
     * Constructs a Terrain object.
     * @param windowDimensions The dimensions of the game window.
     * @param seed The seed for the noise generator.
     */
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = ((float) 2/3)*windowDimensions.y();
        noiseGenerator = new NoiseGenerator(seed,(int)groundHeightAtX0);
        this.windowDimensions = windowDimensions;
        int num_of_blocks = (int)Math.ceil(windowDimensions.x()/Block.SIZE);
        groundHeights = new ArrayList<>();
        for(int x = 0; x<num_of_blocks ; x++){
            double heigthatX = (noiseGenerator.noise(x*Block.SIZE,Block.SIZE*7)+groundHeightAtX0);
            groundHeights.add(heigthatX);
        }
    }

    /**
     * Returns the height of the terrain at the specified x-coordinate.
     * @param x The x-coordinate.
     * @return The height of the terrain at the specified x-coordinate.
     */
    public float groundHeightAt(float x) {
        int index = (int)(x/30);
        return (float)(double)groundHeights.get(index);

    }
    /**
     * Creates a list of terrain blocks within the specified x-coordinate range.
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return A list of terrain blocks within the specified x-coordinate range.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> groundBlocks = new ArrayList<>();
        maxX = (int)(Math.ceil((double) maxX / Block.SIZE)) *Block.SIZE;
        minX = (int)(Math.floor((double) minX / Block.SIZE)) *Block.SIZE;
        RectangleRenderable blockRenderable ;
        for (int i = minX ; i < maxX; i += Block.SIZE){
            int height = (int)Math.floor(groundHeightAt(i) / Block.SIZE) * Block.SIZE;

            for (int j = height; j < windowDimensions.y(); j+=Block.SIZE) {
                blockRenderable = new RectangleRenderable(ColorSupplier.approximateColor(
                        BASE_GROUND_COLOR));
                Block block = new Block(new Vector2(i, height),blockRenderable);
                height += Block.SIZE;
                block.setTag(TERRAIN_TAG);
                groundBlocks.add(block);
            }
        }
        return groundBlocks;
    }
}