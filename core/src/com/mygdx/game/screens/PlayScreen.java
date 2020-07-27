package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.scenes.Hud;

public class PlayScreen implements Screen {
    private MyGdxGame game;
    //Texture texture;
    private OrthographicCamera gameCam;
    private Viewport gamePort; // creating a viewport to help adapt to multiple screen sizes
    private Hud hud;

    private TmxMapLoader mapLoader; //this is what actually loads in our game map
    private TiledMap map; // reference to the map itself
    private OrthogonalTiledMapRenderer renerer; // this is used to render the map onto our camera


    public PlayScreen(MyGdxGame game){
        this.game = game;
        //texture = new Texture("badlogic.jpg");
        gameCam = new OrthographicCamera();//initiallising a new camera for that will follow us along the world called gameCam
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_height, gameCam); //creating a fit viewport that would maintain the aspect ratio of the world regardless of screen size
        // alternate viewports
        /*
        * StretchViewport would stretch out or compress the image to fit the different size screens
        * screen viewports will allow the user to see more or less of the game world depending on the size
        * */
        hud = new Hud(game.batch); // initialising the hud taking the game.batch as the argument

        mapLoader = new TmxMapLoader();// initiallising the map loader
        map = mapLoader.load("level1.tmx"); // initialising the map using the map loader to load our tiled level
        renerer = new OrthogonalTiledMapRenderer(map); //rendering the map after it has been loaded
        gameCam.position.set(gamePort.getScreenWidth()/2 , gamePort.getScreenHeight()/2, 0);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1); //this line clears the screen to a basic red colour whenever clear is glClear is called
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // this line clears the screan to the colur abouve;

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);// this line declares what we are allowed to see in this case its what the camera sees.
        hud.stage.draw();//this line will display the given parameters defined above
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
