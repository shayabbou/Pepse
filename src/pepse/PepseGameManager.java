package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import java.util.List;

/**
 * The `PepseGameManager` class manages the initialization of the game environment and objects.
 * It sets up the game world, including the sky, terrain, avatar, flora, sun, halo, and energy display.
 */
public class PepseGameManager extends GameManager {
    private static final int CYCLE_LENGTH = 30;
    private static final int AVATAR_START = 100;
    private static final int TEXT_SIZE = 20;
    private static final int SEED = 5;

    /**
     * Initializes the game environment and objects.
     * @param imageReader The image reader for loading images.
     * @param soundReader The sound reader for loading sounds.
     * @param inputListener The user input listener for handling input events.
     * @param windowController The window controller for managing the game window.
     */
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader,soundReader,inputListener,windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        createSky(windowDimensions);
        Terrain terrain = createTerrain(windowDimensions);
        createSunAndHalo(windowDimensions);
        Vector2 pos = new Vector2(AVATAR_START, (terrain.groundHeightAt(AVATAR_START)));
        Avatar avatar = new Avatar(pos, inputListener, imageReader);
        Flora flora = new Flora(terrain,windowDimensions, avatar);
        gameObjects().addGameObject(avatar);
        createEnergyDisplay(avatar);
        addFloraGameObjects(flora);
        createNight(windowDimensions);
    }
    /**
     * The main method to start the game.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
    private void createNight(Vector2 windowDimensions){
        GameObject night = Night.create(windowDimensions, CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);
    }
    private void createSky(Vector2 windowDimensions){
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    private Terrain createTerrain(Vector2 windowDimensions){
        Terrain terrain = new Terrain(windowDimensions,SEED);
        List<Block> groundBlocks = terrain.createInRange(0, (int)windowDimensions.x());
        for(Block groundBlock : groundBlocks){
            gameObjects().addGameObject(groundBlock, Layer.STATIC_OBJECTS);
        }
        return terrain;
    }

    private void createSunAndHalo(Vector2 windowDimensions){
        GameObject sun = Sun.create(windowDimensions, CYCLE_LENGTH*2);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
    }

    private void createEnergyDisplay(Avatar avatar){
        TextRenderable textRenderable = new TextRenderable(""); // Initialize the text renderable
        UiEnergy text = new UiEnergy(Vector2.ZERO, new Vector2(TEXT_SIZE, TEXT_SIZE), textRenderable);
        gameObjects().addGameObject(text, Layer.UI);
        avatar.setEnergyRender(text::changeEnergyDisplay);
    }
    private void addFloraGameObjects(Flora flora){
        for(GameObject observer : flora.getFloraComponents()){
            if(observer.getTag().equals("trunk")){
                gameObjects().addGameObject(observer, Layer.STATIC_OBJECTS);
            }
            else if(observer.getTag().equals("leaf")){
                gameObjects().addGameObject(observer, Layer.STATIC_OBJECTS+1);
            }
            else if(observer.getTag().equals("fruit")){
                gameObjects().addGameObject(observer, Layer.DEFAULT);
            }
        }
    }
}
