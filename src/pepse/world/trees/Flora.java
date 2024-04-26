/**
 * The pepse.world.trees package contains classes related to trees and vegetation in the game world.
 */
package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Terrain;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Flora class represents the vegetation in the game world.
 * It generates trees, leaves, and fruits on the terrain.
 */
public class Flora {
    private static final int MIN_TRUNK_HEIGHT = 100 ;
    private final Terrain terrain;
    private final Vector2 windowDimensions;
    private ArrayList<GameObject> floraComponents;
    private static final int MAX_TRUNK_HEIGHT = 200;
    private static final int LEAF_SIZE = 20;
    private static final int TREE_TOP_SIZE = LEAF_SIZE*10;
    private static final int FRUIT_SIZE = 20;
    private static final Vector2 FRUIT_DIMENSIONS = new Vector2(FRUIT_SIZE,FRUIT_SIZE);
    private static Random rand = new Random();
    private Avatar avatar;

    /**
     * Constructs a Flora object with the specified terrain, window dimensions, and avatar.
     * @param terrain The terrain on which the flora is generated.
     * @param windowDimensions The dimensions of the game window.
     * @param avatar The avatar object for observing flora interactions.
     */
    public Flora(Terrain terrain, Vector2 windowDimensions, Avatar avatar){
        this.floraComponents = new ArrayList<>();
        this.terrain = terrain;
        this.windowDimensions = windowDimensions;
        this.avatar = avatar;
        createTree();
    }

    /**
     * Gets the list of flora components.
     * @return The list of flora components.
     */
    public ArrayList<GameObject> getFloraComponents(){
        return floraComponents;
    }

    private void createTree(){
        for(int i=0; i<windowDimensions.x();i+= Block.SIZE) {
            float result = rand.nextFloat();
            if(result<0.1) {
                int height = rand.nextInt(MAX_TRUNK_HEIGHT) + MIN_TRUNK_HEIGHT;
                float heightAtX = terrain.groundHeightAt(i);
                Trunk trunk = new Trunk(new Vector2(i, heightAtX - height), height);
                floraComponents.add(trunk);
                avatar.addObserver(trunk);
                createLeaves(trunk.getTopLeftCorner().subtract(new Vector2(
                        (float)0.5*TREE_TOP_SIZE,(float)0.5*TREE_TOP_SIZE)));
            }
        }
    }
    private void createLeaves(Vector2 trunkTopLeft){
        float currentY = trunkTopLeft.y();
        for(int row=0;row<TREE_TOP_SIZE ; row+=LEAF_SIZE ){
            float currentX = trunkTopLeft.x();
            for(int col=0;col<TREE_TOP_SIZE ; col+=LEAF_SIZE ){
                Vector2 currentLocation  = new Vector2(currentX, currentY);
                if(rand.nextFloat()<0.5){
                    Leaf leaf = new Leaf(currentLocation,LEAF_SIZE);
                    floraComponents.add(leaf);
                    avatar.addObserver(leaf);
                }
                createFruits(currentLocation);
                currentX += LEAF_SIZE;
            }
            currentY += LEAF_SIZE;
        }
    }
    private void createFruits(Vector2 topLeftCorner){
        Renderable renderable = new OvalRenderable(Color.RED);
        float num = rand.nextFloat();
        if(num < 0.2){
            Fruit fruit = new Fruit(topLeftCorner,FRUIT_DIMENSIONS,renderable);
            floraComponents.add(fruit);
            avatar.addObserver(fruit);
        }
    }
}
