package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    // creating a viewport to help adapt to multiple screen sizes
    private Viewport gamePort;
    private Hud hud;


    public PlayScreen(MyGdxGame game){
        this.game = game;
        //texture = new Texture("badlogic.jpg");
        //initiallising a new camera for that will follow us along the world called gameCam
        gameCam = new OrthographicCamera();
        //creating a fit viewport that would maintain the aspect ratio of the world regardless of screen size
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_height, gameCam);
        // alternate viewports
        /*
        * StretchViewport would stretch out or compress the image to fit the different size screens
        * screen viewports will allow the user to see more or less of the game world depending on the size*/
        hud = new Hud(game.batch);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);// this line declares what we are allowed to see in this case its what the camera sees.
        hud.stage.draw();
    }

    //resize method will resize the image depending on the size of the screen
    @Override
    public void resize(int width, int height) {
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
