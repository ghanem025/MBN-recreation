/*
 * This is the main class for the game
 * 2019 - Ghanem & Usman
 * Megaman Battle Network 6
 */

package com.mygdx.game;
import com.badlogic.gdx.physics.box2d.*;
import java.util.ArrayList;
public class WorldContactListener implements ContactListener {
    public ArrayList<Fixture> obj; // an fixture array that adds each object within contact to distingush between them
    public WorldContactListener(){
        obj = new ArrayList<Fixture>(); // make a new one for each map
    }
    public static boolean coll = false;

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() == "Player"){ // if the first fixture is player
            if(contact.getFixtureB().getUserData().getClass() == Door.class){ // check if the second fixture  is any door from door class
                obj.add(contact.getFixtureB()); // add the object for only that door
                Fade_Animation.alpha = 0; //set
                Levels.fade_out = true; // call the fade out animation
                new Fadeout_timer(1); // delay for a second
            }
            if(contact.getFixtureB().getUserData().getClass() == NPC.class){
                //obj.add(contact.getFixtureB());;
                coll = true;
            }
        }
        Main.objs = obj; // update the obj arraylist in the main for changing the map
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().getUserData().equals("Player") && contact.getFixtureB().getUserData().getClass() == Door.class){
            obj.remove(contact.getFixtureB()); // after collision is done remove it so that map doesnt get confused when given multiple objects
        }
        if(contact.getFixtureA().getUserData().equals("Player") && contact.getFixtureB().getUserData().getClass() == NPC.class){
            coll =false;
        }
        Main.objs = obj; // update the main one
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}