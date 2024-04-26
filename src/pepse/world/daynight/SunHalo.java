/**
 * The package pepse.world.daynight contains classes related to managing day and
 * night cycles in a game world.
 */
package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Component;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The SunHalo class represents a halo effect around the sun in the game world.
 * It creates a semi-transparent circular halo around the sun to enhance its visual appearance.
 */
public class SunHalo {
    /**
     * The SunHalo class represents a halo effect around the sun in the game world.
     * It creates a semi-transparent circular halo around the sun to enhance \
     * its visual appearance.
     */
    private static final float SUN_HALO_DIAMETER = Sun.SUN_DIAMETER + 40;
    private static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);
    private static final String SUN_HALO_TAG = "sunHalo";
    /**
     * Constructs a SunHalo object.
     */
    public SunHalo(){};

    /**
     * Creates a new GameObject representing the halo around the sun.
     * @param sun The sun GameObject around which the halo will be created.
     * @return A GameObject representing the halo around the sun.
     */
    public static GameObject create(GameObject sun){
        Renderable sunHaloRender = new OvalRenderable(SUN_HALO_COLOR);
        Vector2 center = new Vector2(sun.getTopLeftCorner());
        GameObject sunHalo = new GameObject(center, new Vector2(SUN_HALO_DIAMETER,
                SUN_HALO_DIAMETER), sunHaloRender);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(SUN_HALO_TAG);
        Component update = (deltaTime) -> {sunHalo.setCenter(sun.getCenter());} ;
        sunHalo.addComponent(update);
        return sunHalo;
    }
}
