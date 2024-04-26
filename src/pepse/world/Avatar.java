/**
 * The pepse.world package contains classes representing elements of the game world.
 */
package pepse.world;

import danogl.GameObject;
import danogl.components.RendererComponent;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.*;
import danogl.util.Vector2;
import pepse.world.trees.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * The Avatar class represents the player's avatar in the game world.
 * It extends the GameObject class and provides functionality for controlling the avatar's movement,
 * energy management, and interaction with other game elements.
 */
public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final int AVATAR_SIZE = 50;
    private static final Vector2 AVATAR_DIMENSIONS =  new Vector2(AVATAR_SIZE,AVATAR_SIZE);
    private static final String RELATIVE_PATH = "assets/assets/";
    private static final String[] IDLE_PHOTOS = {"idle_0.png", "idle_1.png", "idle_2.png", "idle_3.png"};
    private static final String[] JUMP_PHOTOS = {"jump_0.png", "jump_1.png", "jump_2.png", "jump_3.png"};
    private static final String[] RUN_PHOTOS = {"run_0.png", "run_1.png", "run_2.png",
            "run_3.png", "run_4.png", "run_5.png"};
    private static final float MAX_ENERGY = 100f;
    private static final float JUMP_RAISE_ENERGY = 10f;
    private static final float HORIZON_MOVE_ENERGY = 0.5f;
    private static final String AVATAR_TAG = "avatar";
    private static final double TIME_BETWEEN_CLIPS = 15;
    private RendererComponent rendererComponent;
    private UserInputListener inputListener;
    private float energy;
    private Renderable[] idleRender;
    private Renderable[] runRender;
    private Renderable[] jumpRender;
    private Consumer<Float> changeEnergyDisplay;
    private ArrayList<AvatarObserver> observers ;


    /**
     * Constructs an Avatar object with the specified position, input listener, and image reader.
     * @param pos The position of the avatar.
     * @param inputListener The input listener for receiving user input.
     * @param imageReader The image reader for reading avatar images.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos.subtract(AVATAR_DIMENSIONS), Vector2.ONES.mult(AVATAR_SIZE),
                imageReader.readImage(RELATIVE_PATH +IDLE_PHOTOS[0], true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.energy = MAX_ENERGY;
        observers = new ArrayList<>();
        idleRender = initRenders(IDLE_PHOTOS, imageReader);
        runRender = initRenders(RUN_PHOTOS, imageReader);
        jumpRender = initRenders(JUMP_PHOTOS, imageReader);
        rendererComponent = super.renderer();
        setTag(AVATAR_TAG);
    }

    /**
     * Updates the avatar's state and behavior based on the elapsed time.
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        calculateVelocity(0);
        calculateEnergy();
        changeEnergyDisplay.accept(energy);
        setRendererByMove();
    }
    /**
     * Adds an observer to monitor the avatar's actions.
     * @param observer The observer to be added.
     */
    public void addObserver(AvatarObserver observer){
        observers.add(observer);
    }
    /**
     * Sets the consumer for updating energy display.
     * @param changeEnergyDisplay The consumer for updating energy display.
     */
    public void setEnergyRender(Consumer<Float> changeEnergyDisplay) {
        this.changeEnergyDisplay = changeEnergyDisplay;
    }
    /**
     * Sets the energy level of the avatar.
     * @param addEnergy The amount of energy to be added to the current energy level.
     */
    public void setEnergy(float addEnergy){
        energy += addEnergy;
        if (energy > MAX_ENERGY){
            energy = MAX_ENERGY;
        }
    }

    private void calculateVelocity(float xVel){
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy >= HORIZON_MOVE_ENERGY)
            xVel -= VELOCITY_X;
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy >= HORIZON_MOVE_ENERGY)
            xVel += VELOCITY_X;
        transform().setVelocityX(xVel);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 &&
                energy >= JUMP_RAISE_ENERGY) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= JUMP_RAISE_ENERGY;
            notifyObservers();
        }
    }

    private void calculateEnergy(){
        if(getVelocity().x() != 0 && energy >= HORIZON_MOVE_ENERGY){
            energy -= HORIZON_MOVE_ENERGY;
        } if (getVelocity().y() == 0 && getVelocity().x() == 0) {
            energy += 1;
            if(energy > MAX_ENERGY){
                energy = MAX_ENERGY;
            }
        }
    }

    private void notifyObservers(){
        if(observers != null) {
            for (AvatarObserver observer : observers) {
                observer.jumpReaction();
            }
        }
    }
    private Renderable[] initRenders(String[] paths, ImageReader imageReader){
        Renderable[] renderables = new Renderable[paths.length];
        int i = 0;
        for(String imagePath: paths){
            renderables[i] = imageReader.readImage(RELATIVE_PATH +imagePath,
                    true);
            i++;
        }
        return renderables;
    }

    private void setRendererByMove() {
        AnimationRenderable render;
        if (getVelocity().x() == 0 && getVelocity().y() == 0) {
            render = new AnimationRenderable(idleRender, TIME_BETWEEN_CLIPS);
        } else if (getVelocity().x() == 0 && getVelocity().y() != 0) {
            render = new AnimationRenderable(jumpRender, TIME_BETWEEN_CLIPS);
        } else {
            render = new AnimationRenderable(runRender, TIME_BETWEEN_CLIPS);
        }
        rendererComponent.setRenderable(render);
        if (getVelocity().x() < 0) {
            rendererComponent.setIsFlippedHorizontally(true);
        }
        else {
            rendererComponent.setIsFlippedHorizontally(false);
        }
    }
}
