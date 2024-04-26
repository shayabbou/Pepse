/**
 * The pepse.world package contains classes representing elements of the game world.
 */
package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Block class represents a solid block element in the game world.
 * It extends the GameObject class and represents a basic building block for
 * constructing the game environment.
 */
public class Block extends GameObject {
    /**
     * Block size.
     */
    public static final int SIZE = 30;
    private static final String BLOCK_TAG = "block";
    /**
     * Constructs a Block object with the specified top-left corner position and renderable.
     * @param topLeftCorner The top-left corner position of the block.
     * @param renderable The renderable representing the visual appearance of the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(BLOCK_TAG);
    }
}
