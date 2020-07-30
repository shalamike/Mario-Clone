package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.mygdx.game.scenes.Hud;

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

    private World world;
    private Box2DDebugRenderer b2dr; // a debug renderer gives a graphical representation of fixtures and bodies

    public PlayScreen(MyGdxGame game){
        this.game = game;
        //texture = new Texture("badlogic.jpg");
        gameCam = new OrthographicCamera();//initiallising a new camera for that will follow us along the world called gameCam
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_height, gameCam); //creating a fit viewport that would maintain the aspect ratio of the world regardless of screen size
        // alternate viewports
        /*
        * StretchViewport would stretch out or compress the image to fit the different size screens
        * Screenviewports will allow the user to see more or less of the game world depending on the size
        * */
        hud = new Hud(game.batch); // initialising the hud taking the game.batch as the argument

        // initiallising tiled variables
        mapLoader = new TmxMapLoader();// initiallising the map loader
        map = mapLoader.load("level1.tmx"); // initialising the map using the map loader to load our tiled level
        renderer = new OrthogonalTiledMapRenderer(map); //rendering the map after it has been loaded
        gameCam.position.set(gamePort.getWorldWidth()/2 , gamePort.getWorldHeight()/2, 0);

        //initialising box2d variables
        world = new World(new Vector2(0,0), true); //box2d wont calculate physics for objects that are in rest, therefore improving performance
        b2dr = new Box2DDebugRenderer();

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
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2 );// defining the midpoint
            body = world.createBody(bdef); // now we create the body
            //creating the fixtures
            shape.setAsBox(rect.getWidth()/2 , rect.getHeight()/2); //getting the midpoint of our rectangle
            fdef.shape = shape;  //setting our fixturedef to the midpoint of the shape.
            body.createFixture(fdef); // setting the fixture of our body
        }
        // getting the pipes layer
        for(MapObject object :map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); // first get the rectangle object itself. in this line we have to typecast the object to a rectangle
            /* next we define our body def. In Box2d there are 3 types of bodydefs we can have as follows
             * DynamicBody are bodies that are usually used for players. they are usually affected by forces like gravity and tend to move around a lot (hence the name)
             * StaticBody unlike the Dynamic body dont move at all. therefore they are not affected by forces
             * KinematicBody are bodies aren't necesesarily affeced by forces like gravity but can be affected by velocities. therefore these can move in the map but cannot be moved like moving platforms or pendulums.*/
            bdef.type = BodyDef.BodyType.StaticBody; // next we define our body def
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2 );// defining the midpoint
            body = world.createBody(bdef); // now we create the body
            //creating the fixtures
            shape.setAsBox(rect.getWidth()/2 , rect.getHeight()/2); //getting the midpoint of our rectangle
            fdef.shape = shape;
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
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2 );// defining the midpoint
            body = world.createBody(bdef); // now we create the body
            //creating the fixtures
            shape.setAsBox(rect.getWidth()/2 , rect.getHeight()/2); //getting the midpoint of our rectangle
            fdef.shape = shape;
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) { // this method will visualise what is happening in our game
        update(delta);
        Gdx.gl.glClearColor(1,0,0,1); //this line clears the screen to a basic red colour whenever clear is glClear is called
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // this line clears the screan to the colur abouve;

        renderer.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);// this line declares what we are allowed to see in this case its what the camera sees.
        hud.stage.draw();//this line will display the given parameters defined above

        //renderer using our Box2dDebugLines
        b2dr.render(world, gameCam.combined);
    }

    public void handleInput(float dt){ // this method will handle any of the users input
        if(Gdx.input.isTouched()) // this will just check if the screen has been touched for now
            gameCam.position.x += 100 * dt; // this wil just change the game cam somewhere to see if this works
    }
    public void update(float dt){ // this method will update the current scenario of the game based on the user input
        handleInput(dt); // this line will handle any user inputs
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

    }
}
