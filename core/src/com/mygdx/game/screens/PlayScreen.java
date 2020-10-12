package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Mario;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.tools.B2WorldCreator;

public class PlayScreen implements Screen {
    private MyGdxGame game;
    //Texture texture;
    //camera variables
    private OrthographicCamera gameCam;
    private Viewport gamePort; // creating a viewport to help adapt to multiple screen sizes
    private Hud hud; // heads up display for the user
    // tiled variables
    private TmxMapLoader mapLoader; //this is what actually loads in our game map
    private TiledMap map; // reference to the map itself
    private OrthogonalTiledMapRenderer renderer; // this is used to render the map onto our camera
   // box2d variables
    private World world;// making a box2d world
    private Box2DDebugRenderer b2dr; // a debug renderer gives a graphical representation of fixtures and bodies
    // Mario/player variables
    private Mario player;
    // animation variables
    private TextureAtlas atlas;

    public PlayScreen(MyGdxGame game){
        this.game = game;
        //texture = new Texture("badlogic.jpg");
        gameCam = new OrthographicCamera();//initiallising a new camera for that will follow us along the world called gameCam
        gamePort = new FitViewport(MyGdxGame.V_WIDTH/MyGdxGame.PPM, MyGdxGame.V_height/MyGdxGame.PPM, gameCam); //creating a fit viewport that would maintain the aspect ratio of the world regardless of screen size
        // alternate viewports
        /*
        * StretchViewport would stretch out or compress the image to fit the different size screens
        * Screenviewports will allow the user to see more or less of the game world depending on the size
        * */
        hud = new Hud(game.batch); // initialising the hud taking the game.batch as the argument


        // initiallising tiled variables
        mapLoader = new TmxMapLoader();// initiallising the map loader
        map = mapLoader.load("level1.tmx"); // initialising the map using the map loader to load our tiled level
        renderer = new OrthogonalTiledMapRenderer(map, 1/MyGdxGame.PPM); //rendering the map after it has been loaded
        gameCam.position.set(gamePort.getWorldWidth()/2 , gamePort.getWorldHeight()/2, 0);

        //initialising the texture atlas for our animations
        /*
         * the pack file in the used by the texture atlas is simply a text file that describes where each
         * animation image is in the Mario_and_Enemies.png image*/
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        //initialising box2d variables
        /*
        * the wold class takes 2 parameters,
        * a vector 2 for the direction of natural forces in the game world i.e gravity
        * a doSleep boolean where if it is true, box2d wont calculate physics for objects that are in rest. Therefore improving performance*/
        world = new World(new Vector2(0,-10), true);// this line creates a world with some default values for the worlds gravity
        b2dr = new Box2DDebugRenderer(); // the box2dDebugRenderer will show the outlines of all shapes in the game

        //initialising the mario/ player
        player = new Mario(world, this);


        new B2WorldCreator(world, map);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) { // this method will visualise what is happening in our game
        update(delta);
        Gdx.gl.glClearColor(1,0,0,1); //this line clears the screen to a basic red colour whenever clear is glClear is called
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // this line clears the screan to the colur abouve;

        renderer.render();
        //rendering mario
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);// this line declares what we are allowed to see in this case its what the camera sees.
        hud.stage.draw();//this line will display the given parameters defined above

        //renderer using our Box2dDebugLines
        b2dr.render(world, gameCam.combined);
    }

    public void handleInput(float dt){ // this method will handle any of the users input
        // if the user jumped
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2dbody.applyLinearImpulse(new Vector2(0,4f), player.b2dbody.getWorldCenter(), true);
            // with applyLinearImpulse, the first parameter is the direction of the force, the second parameter is the location in the body the force will be applied to (in this case the center), the third parameter will wake the body up if its asleep
        }
        // if the user moved to the right
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2dbody.getLinearVelocity().x <= 1.5){
            player.b2dbody.applyLinearImpulse(new Vector2(0.1f, 0), player.b2dbody.getWorldCenter(), true);
        }
        // if the user moved to the left
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2dbody.getLinearVelocity().x >= -1.5){
            player.b2dbody.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2dbody.getWorldCenter(), true);
        }

    }
    public void update(float dt){ // this method will update the current scenario of the game based on the user input
        handleInput(dt); // this line will handle any user inputs

        player.update(dt);

        /* updating marios motion using the world.step method which takes the following argument sin the following order
        * timeStep - the amount of time to simulate, this should not vary.
        * velocityIterations - for the velocity constraint solver.
        * positionIterations - for the position constraint solver.*/
        world.step(1/30f, 8, 2);// this line takes a timestep for calculating marios motions

        gameCam.position.x = player.b2dbody.getPosition().x;

        gameCam.update(); // this line will update
        renderer.setView(gameCam); // this line will render only what the camera can see.
    }


    @Override
    public void resize(int width, int height) { //resize method will resize the image depending on the size of the screen
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
        renderer.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
