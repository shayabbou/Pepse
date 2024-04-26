/**
 * The pepse.world package contains classes representing elements of the game world.
 */
package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
/**
 * The UiEnergy class represents a UI element for displaying energy information in the game world.
 * It extends the GameObject class and provides functionality for updating the displayed
 * energy value.
 */
public class UiEnergy extends GameObject {
    private TextRenderable textRenderable;
    /**
     * Constructs a UiEnergy object with the specified position, dimensions, and renderable.
     *
     * @param topLeftCorner The position of the UI element, in window coordinates (pixels).
     * @param dimensions The width and height of the UI element in window coordinates.
     * @param renderable The renderable representing the UI element. Can be null if the element
     *                   should not be rendered.
     */
    public UiEnergy(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.textRenderable = renderable;
    }

    /**
     * Updates the displayed energy value on the UI element.
     * @param energy The energy value to be displayed.
     */
    public void changeEnergyDisplay(float energy) {
        textRenderable.setString(String.format("%.1f%%", energy));
    }
}