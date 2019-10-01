package com.mygdx.game;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.net.httpserver.Filter;

import static com.mygdx.game.Main.map;

public class WorldCreator {
    static float x_enter, y_enter, x_exit, y_exit , x_spawn , y_spawn;

    static int mapWidth;
    static int mapHeight;
    static int tilePixelWidth;
    static int tilePixelHeight;

    static int mapPixelWidth;
    static int mapPixelHeight;


    public  static void Boundaries (World world, MapObjects objects){
        Body body;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        MapProperties prop = Main.CurrentMap.getProperties();

        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;


        for(MapObject obj : objects){
            Shape shape;
            if(obj instanceof PolylineMapObject){
                shape = createPolyline((PolylineMapObject)obj);
            }
            else if (obj instanceof PolygonMapObject){
                shape = createPolygon((PolygonMapObject)obj);
            }
            else {
                continue;
            }
            bdef.type =  BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(shape,0.1f);
            shape.dispose();
        }

    }

    public static void World(World world, TiledMap map) {
        Body body;
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();



        for (MapObject obj : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            x_spawn = ((RectangleMapObject) obj).getRectangle().getX() * Main.PPM;
            y_spawn = ((RectangleMapObject) obj).getRectangle().getY() * Main.PPM;

            bdef.type = BodyDef.BodyType.StaticBody;

            bdef.position.set(rect.getX() * Main.PPM + rect.getWidth() / 2 * Main.PPM, rect.getY() * Main.PPM + rect.getHeight() / 2 * Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 * Main.PPM, rect.getHeight() / 2 * Main.PPM);

            fdef.shape = shape;
            body.createFixture(fdef).setUserData("Spawn");

        }

        // for buildings
        for (MapObject obj : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            x_exit = ((RectangleMapObject) obj).getRectangle().getX() * Main.PPM;
            y_exit = ((RectangleMapObject) obj).getRectangle().getY() * Main.PPM;

            bdef.type = BodyDef.BodyType.StaticBody;

            bdef.position.set(rect.getX() * Main.PPM + rect.getWidth() / 2 * Main.PPM, rect.getY() * Main.PPM + rect.getHeight() / 2 * Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 * Main.PPM, rect.getHeight() / 2 * Main.PPM);

            fdef.shape = shape;
            body.createFixture(fdef).setUserData("Exit");

        }

        for (MapObject obj : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            x_enter = ((RectangleMapObject) obj).getRectangle().getX() * Main.PPM;
            y_enter = ((RectangleMapObject) obj).getRectangle().getY() * Main.PPM;

            bdef.type = BodyDef.BodyType.StaticBody;

            bdef.position.set(rect.getX() * Main.PPM + rect.getWidth() / 2 * Main.PPM, rect.getY() * Main.PPM + rect.getHeight() / 2 * Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 * Main.PPM, rect.getHeight() / 2 * Main.PPM);

            fdef.shape = shape;
            body.createFixture(fdef).setUserData("Enter");

        }
    }

    private static ChainShape createPolyline(PolylineMapObject polyline){
        float [] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldverticies = new Vector2[vertices.length/2];

        for(int i = 0; i < worldverticies.length; i ++){
            worldverticies[i] = new Vector2(vertices[i * 2] * Main.PPM, vertices[i * 2 + 1] * Main.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldverticies);
        return cs;
    }
    private static ChainShape createPolygon (PolygonMapObject polygon){
        float [] v = polygon.getPolygon().getTransformedVertices();
        Vector2[] wv = new Vector2[v.length/2];

        for ( int i =0 ; i< wv.length ; i++){
            wv[i] = new Vector2(v[i*2] * Main.PPM , v[i*2+1] * Main.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(wv);
        return cs;
    }

}