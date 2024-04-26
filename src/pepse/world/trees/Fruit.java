/**
 * The pepse.world.trees package contains classes related to trees and vegetation in the game world.
 */
package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import java.awt.*;

/**
 * The Fruit class represents fruits in the game world.
 * Fruits are GameObjects that in collision with avatar they disappear for 30 seconds - cycle time.
 * The fruit observe the avatar and change color when the avatar jump.
 */
public class Fruit extends GameObject implements AvatarObserver{
    private static final String FRUIT_TAG = "fruit";
    private static final String AVATAR_TAG = "avatar";
    private static final int CYCLE_TIME = 30;
    private static final int ENERGY_RAISE = 10;
    private static final int COLOR_DELTA = 100;
    private boolean disappeared;
    /**
     * Constructs a Fruit object with the specified position, dimensions, and renderable.
     * @param topLeftCorner The position of the fruit, in window coordinates (pixels).
     * @param dimensions The dimensions (width and height) of the fruit, in window coordinates.
     * @param renderable The renderable representing the fruit. Can be null.
     */
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        setTag(FRUIT_TAG);
        disappeared = false;
    }
    /**
     * Reacts to the avatar's jump by changing the color of the fruit.
     */
    public void jumpReaction(){
        this.renderer().setRenderable
                (new OvalRenderable(ColorSupplier.approximateColor(Color.RED,COLOR_DELTA)));
    }

    /**
     * Handles the event when the fruit collides with another game object.
     * @param other The other game object involved in the collision.
     * @param collision The collision data.
     */
    @Override
    public void onCollisionEnter(GameObject other , Collision collision){
        super.onCollisionEnter(other,collision);
        if(other.getTag().equals(AVATAR_TAG) && !disappeared){
            ((Avatar)other).setEnergy(ENERGY_RAISE);
            this.renderer().fadeOut(0);
            this.disappeared = true;
            new ScheduledTask(other, CYCLE_TIME, false, () -> {
                this.renderer().fadeIn(0);
                this.disappeared = false;
            });
        }
    }
}
