package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.PlayScreen;

import sun.java2d.opengl.WGLSurfaceData;

public class Mario extends Sprite {
    // declaring the different states mario could have
    public enum State{FALLING, JUMPING, STANDING, RUNNING}
    public State currentState;
    public State previousState;

    public World world; // this will be the world that mario will live inside
    public Body b2dbody; // box2d body
    private TextureRegion marioStand;
    // declaring animations for different states
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private float stateTimer;

    private boolean runningRight;


    public Mario(World world, PlayScreen screen){
        //inheriting from the Sprite class to find the little mario region
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        // initialising the possible states that mario could take
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //creating an array of images/frames to animate mario running or jumping
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // creating a for loop to get all the pictures/frames from the texture region of mario running
        for(int i = 0; i<3; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 11, 16,16));
        }
        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        //creating a for loop to get all the pictures/frames from the texture region of mario jumping
        for(int i = 3; i < 5; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 11, 16,16));
        }
        marioJump = new Animation<TextureRegion>(0.1f, frames);

        defineMario();
        marioStand = new TextureRegion(getTexture(), 0,11,16, 16);
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
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        // checking if mario was running to the left
        if ((b2dbody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2dbody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        // checking if mario is jumping or falling by seeing if there is any linear velocity y velocity
        if (b2dbody.getLinearVelocity().y > 0 || (b2dbody.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
            return State.JUMPING;
        }
        else if  (b2dbody.getLinearVelocity().y < 0){
            return State.FALLING;
        }
        //checking if mario is running by seeing if there is linear x velocity
        else if (b2dbody.getLinearVelocity().x != 0){
            return State.RUNNING;
        }
        else {
            return State.STANDING;
        }
    }
}
