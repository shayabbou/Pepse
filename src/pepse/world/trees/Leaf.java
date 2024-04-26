/**
 * The pepse.world.trees package contains classes related to trees and vegetation in the game world.
 */
package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;

import java.awt.*;
import java.util.Random;

/**
 * The Leaf class represents individual leaves in the game world.
 * Leaves are GameObjects that can react to avatar jumps by changing their orientation.
 */
public class Leaf extends GameObject implements AvatarObserver{
    private static final float REGULAR_FINAL_ANGLE = 20f;
    private static final float JUMP_FINAL_ANGLE = 90f;
    private static final Color LEAF_COLOR = new Color(50,200,30);
    private static final String LEAF_TAG = "leaf";
    private static final float TRANSITION_TIME = 10 ;
    private static final float SHORT_TRANSITION_TIME = 1f ;
    private static final float FACTOR = 1.2f;
    private static final int BOUND = 4;

    Random rand = new Random();
    /**
     * Constructs a Leaf object with the specified position and size.
     * @param topLeftCorner The position of the leaf, in window coordinates (pixels).
     * @param size The size of the leaf.
     */
    public Leaf(Vector2 topLeftCorner , int size) {
        super(topLeftCorner, new Vector2(size, size),
                new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR)));
        setTag(LEAF_TAG);
        leafTransition(REGULAR_FINAL_ANGLE);
    }

    /**
     * Reacts to the avatar's jump by changing the orientation of the leaf.
     */
    public void jumpReaction(){
        Runnable runnable = ()-> {
            new Transition<Float>(this, (Float angle) -> {
                this.renderer().setRenderableAngle(angle);
            }, 0f, JUMP_FINAL_ANGLE,
                    Transition.LINEAR_INTERPOLATOR_FLOAT, SHORT_TRANSITION_TIME,
                    Transition.TransitionType.TRANSITION_ONCE,
                    null);
        };
        new ScheduledTask( this
                ,0, false, runnable);
    }

    private void leafTransition(float finalValue){
        Runnable runnable = ()->{
            new Transition<Float>(this,(Float angle) -> {
                this.renderer().setRenderableAngle(angle);}, 0f,finalValue,
                    Transition.LINEAR_INTERPOLATOR_FLOAT,TRANSITION_TIME,
                    Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                    null);
            new Transition<Vector2>(this,(Vector2 dimensionsAsVector2) ->
            { this.setDimensions(dimensionsAsVector2);}
                    , this.getDimensions(), this.getDimensions().mult(FACTOR),
                    Transition.LINEAR_INTERPOLATOR_VECTOR,TRANSITION_TIME,
                    Transition.TransitionType.TRANSITION_LOOP,
                    null);
        };
        new ScheduledTask( this
                ,(float) rand.nextInt(BOUND), false, runnable);

    }

}
