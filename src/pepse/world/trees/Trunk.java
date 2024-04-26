/**
 * The pepse.world.trees package contains classes related to trees and vegetation in the game world.
 */
package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import java.awt.*;


/**
 * The Trunk class represents tree trunks in the game world.
 * Trunks are GameObjects that provide support for the tree structure and can
 * react to avatar jumps by changing their color.
 * The Trunks collide with the Avatar and he can't go through them.
 */
public class Trunk extends GameObject implements AvatarObserver {
    private static final Color TRUNK_COLOR = new Color(100,50,20);
    private static final int TRUNK_WIDTH = 20;
    private static final int COLOR_DELTA = 20;
    private static final String TRUNK_TAG = "trunk";

    /**
     * Constructs a Trunk object with the specified position and size.
     * @param topLeftCorner The position of the trunk, in window coordinates (pixels).
     * @param size The size of the trunk.
     */
    public Trunk(Vector2 topLeftCorner , int size){
        super(topLeftCorner,new Vector2(TRUNK_WIDTH,size),
                new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));;
        setTag(TRUNK_TAG);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * Reacts to the avatar's jump by changing the color of the trunk.
     */
    public void jumpReaction() {
        this.renderer().setRenderable
                (new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR,COLOR_DELTA)));
    }

}
