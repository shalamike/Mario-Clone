package com.mygdx.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;

public class B2WorldCreator {


    public B2WorldCreator (World world, TiledMap map){
        // initiallising the Box2d Variables
        BodyDef bdef = new BodyDef(); // this variable defines what a body consists of
        PolygonShape shape = new PolygonShape(); // this defines the shape for our fixtures
        FixtureDef fdef = new FixtureDef(); // this variables defines the fixtures;
        Body body;

        /*
         * for  every coin, ground, pipe and other tile created in the tiled map editor,
         * we need to create bodys and fixtures for those objects which is defined in the following code
         * map.getlayers() will get the layers from the tiled map editor
         * get(n) will the specific layer at n + 1 (starting from the bottom at 0) in the case below it will be the third layer
         * getObjects() will get all the objects in that layer
         * getByType() will get the type of object shape as defined by their shape class. in this case its (RectangleMapObject.class)*/
        // getting the ground layer
        for(MapObject object :map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // first get the rectangle object itself. in this line we have to typecast the object to a rectangle
            /* next we define our body def. In Box2d there are 3 types of bodydefs we can have as follows
             * DynamicBody are bodies that are usually used for players. they are usually affected by forces like gravity and tend to move around a lot (hence the name)
             * StaticBody unlike the Dynamic body dont move at all. therefore they are not affected by forces
             * KinematicBody are bodies arent necesesarily affeced by forces like gravity but can be affected by velocities. therefore these can move in the map but cannot be moved like moving platforms or pendulums.*/
            bdef.type = BodyDef.BodyType.StaticBody; // next we define our body def
            bdef.position.set((rect.getX() + rect.getWidth()/2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight()/2) / MyGdxGame.PPM );// defining the midpoint
            body = world.createBody(bdef); // now we create the body
            //creating the fixtures
            shape.setAsBox((rect.getWidth()/2) / MyGdxGame.PPM , (rect.getHeight()/2)/ MyGdxGame.PPM) ; //getting the midpoint of our rectangle
            fdef.shape = shape;  //setting our fixturedef to the midpoint of the shape.
            body.createFixture(fdef); // setting the fixture of our body
        }
        // getting the pipes layer
        for(MapObject object :map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // first get the rectangle object itself. in this line we have to typecast the object to a rectangle
            /* next we define our body def. In Box2d there are 3 types of bodydefs we can have as follows
             * DynamicBody are bodies that are usually used for players. they are usually affected by forces like gravity and tend to move around a lot (hence the name)
             * StaticBody unlike the Dynamic body dont move at all. therefore they are not affected by forces
             * KinematicBody are bodies arent necesesarily affeced by forces like gravity but can be affected by velocities. therefore these can move in the map but cannot be moved like moving platforms or pendulums.*/
            bdef.type = BodyDef.BodyType.StaticBody; // next we define our body def
            bdef.position.set((rect.getX() + rect.getWidth()/2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight()/2) / MyGdxGame.PPM );// defining the midpoint
            body = world.createBody(bdef); // now we create the body
            //creating the fixtures
            shape.setAsBox((rect.getWidth()/2) / MyGdxGame.PPM , (rect.getHeight()/2)/ MyGdxGame.PPM) ; //getting the midpoint of our rectangle
            fdef.shape = shape;  //setting our fixturedef to the midpoint of the shape.
            body.createFixture(fdef); // setting the fixture of our body
        }
        // getting the coins layer
        for(MapObject object :map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // first get the rectangle object itself. in this line we have to typecast the object to a rectangle
            /* next we define our body def. In Box2d there are 3 types of bodydefs we can have as follows
             * DynamicBody are bodies that are usually used for players. they are usually affected by forces like gravity and tend to move around a lot (hence the name)
             * StaticBody unlike the Dynamic body dont move at all. therefore they are not affected by forces
             * KinematicBody are bodies arent necesesarily affeced by forces like gravity but can be affected by velocities. therefore these can move in the map but cannot be moved like moving platforms or pendulums.*/
            bdef.type = BodyDef.BodyType.StaticBody; // next we define our body def
            bdef.position.set((rect.getX() + rect.getWidth()/2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight()/2) / MyGdxGame.PPM );// defining the midpoint
            body = world.createBody(bdef); // now we create the body
            //creating the fixtures
            shape.setAsBox((rect.getWidth()/2) / MyGdxGame.PPM , (rect.getHeight()/2)/ MyGdxGame.PPM) ; //getting the midpoint of our rectangle
            fdef.shape = shape;  //setting our fixturedef to the midpoint of the shape.
            body.createFixture(fdef); // setting the fixture of our body
        }

        //getting the bricks layer
        for(MapObject object :map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // first get the rectangle object itself. in this line we have to typecast the object to a rectangle
            /* next we define our body def. In Box2d there are 3 types of bodydefs we can have as follows
             * DynamicBody are bodies that are usually used for players. they are usually affected by forces like gravity and tend to move around a lot (hence the name)
             * StaticBody unlike the Dynamic body dont move at all. therefore they are not affected by forces
             * KinematicBody are bodies arent necesesarily affeced by forces like gravity but can be affected by velocities. therefore these can move in the map but cannot be moved like moving platforms or pendulums.*/
            bdef.type = BodyDef.BodyType.StaticBody; // next we define our body def
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2 );// defining the midpoint
            body = world.createBody(bdef); // now we create the body
            //creating the fixtures
            shape.setAsBox(rect.getWidth()/2 , rect.getHeight()/2); //getting the midpoint of our rectangle
            fdef.shape = shape;
            body.createFixture(fdef); // setting the fixture of our body
        }
    }
}
