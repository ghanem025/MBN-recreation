package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import org.omg.CORBA.Current;

import java.util.concurrent.ConcurrentMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {


        if (contact.getFixtureA().getUserData() == "Player" && contact.getFixtureB().getUserData() == "Enter") {

//                System.out.println("exit contact");
               // Main.CurrentMap = Main.map1;
//              Main.ChangeMap(Main.map1);
//
                //Main.city = true;
               // Main.transition = true;
                Main.Enter = true;

        }

        if (contact.getFixtureA().getUserData() == "Player" && contact.getFixtureB().getUserData() == "Exit") {

//                System.out.println("exit contact");
//                Main.CurrentMap = Main.map1;
//                // Main.ChangeMap(Main.map1);
//
//                Main.city = true;
            Main.Exit = true;

        }

        Gdx.app.log("Begin Contact", "");
    }


    @Override
    public void endContact(Contact contact) {

        Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}


