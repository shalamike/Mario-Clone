package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import sun.java2d.opengl.WGLSurfaceData;

public class Mario extends Sprite {
    public World world; // this will be the world that mario will live inside
    public Body b2dbody; // box2d body

    public Mario(World world){
        this.world = world;

    }

    /*
    * the following code will define the mario body*/
    public void defineMario(){
        BodyDef bdef = new BodyDef(); // creating a new body definition for mario
        bdef.position.set(32, 32); // temporarily setting marios position to 32,32
        bdef.type = BodyDef.BodyType.DynamicBody; // setting marios body to dynamic body
        b2dbody = world.createBody(bdef);//now we have the box2d body defined, we can create the body in our game world
        //defining the fixtures
        FixtureDef fdef = new FixtureDef(); // creating a new fixture def
        CircleShape shape = new CircleShape(); // creating a circle for our fixture def for now
        shape.setRadius(5); // setting the circles radius to 5
        fdef.shape = shape; // setting our shapes radius to the fixure def
        b2dbody.createFixture(fdef); // setting the fixture def to our body. 
    }
}
