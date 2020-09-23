package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.PlayScreen;

import sun.java2d.opengl.WGLSurfaceData;

public class Mario extends Sprite {
    public World world; // this will be the world that mario will live inside
    public Body b2dbody; // box2d body
    private TextureRegion marioStand;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world; //
        defineMario();
        marioStand = new TextureRegion(getTexture(), 0,0,16, 16);
        setBounds(0, 0, 16/ MyGdxGame.PPM, 16/MyGdxGame.PPM);
        setRegion(marioStand);
    }

    /*
    * the following code will define the mario body*/
    public void defineMario(){
        BodyDef bdef = new BodyDef(); // creating a new body definition for mario
        bdef.position.set(32 / MyGdxGame.PPM, 60 / MyGdxGame.PPM); // temporarily setting marios position to 32,32
        bdef.type = BodyDef.BodyType.DynamicBody; // setting marios body to dynamic body
        b2dbody = world.createBody(bdef);//now we have the box2d body defined, we can create the body in our game world
        //defining the fixtures
        FixtureDef fdef = new FixtureDef(); // creating a new fixture def
        CircleShape shape = new CircleShape(); // creating a circle for our fixture def for now
        shape.setRadius(7 / MyGdxGame.PPM); // setting the circles radius to 5
        fdef.shape = shape; // setting our shapes radius to the fixure def
        b2dbody.createFixture(fdef); // setting the fixture def to our body.
    }

    public void update(float dt){
        setPosition(b2dbody.getPosition().x - getWidth() / 2, b2dbody.getPosition().y - getHeight() / 2);
    }
}
