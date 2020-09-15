package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;


public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    // the constructor will take the world, Tiled map and the rectangle
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // as mentioned before, the rectangle will be taken as a parameter in our constructor as bounds.
        /* next we define our body def. In Box2d there are 3 types of bodydefs we can have as follows
         * DynamicBody are bodies that are usually used for players. they are usually affected by forces like gravity and tend to move around a lot (hence the name)
         * StaticBody unlike the Dynamic body dont move at all. therefore they are not affected by forces
         * KinematicBody are bodies arent necesesarily affeced by forces like gravity but can be affected by velocities. therefore these can move in the map but cannot be moved like moving platforms or pendulums.*/
        bdef.type = BodyDef.BodyType.StaticBody; // next we define our body def
        bdef.position.set((float) (bounds.getX() + bounds.getWidth()/2) / MyGdxGame.PPM, (float) (bounds.getY() + bounds.getHeight()/2) / MyGdxGame.PPM );// defining the midpoint
        body = world.createBody(bdef); // now we create the body
        //creating the fixtures
        shape.setAsBox((float) (bounds.getWidth()/2) / MyGdxGame.PPM , (float)(bounds.getHeight()/2)/ MyGdxGame.PPM) ; //getting the midpoint of our rectangle
        fdef.shape = shape;  //setting our fixturedef to the midpoint of the shape.
        body.createFixture(fdef); // setting the fixture of our body
    }

}
