package com.mygdx.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;



public class Hud implements Disposable {
    public Stage stage;
    // making a new camera and new viewport specifically for the hud
    // this is to ensure that the hud stays in place while the game moves
    public Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_height, new OrthographicCamera());
        // a stage is kind of like an empty box
        stage = new Stage(viewport, sb);
        // therefore what we need is a table to organise everything inside our empty box
        Table table = new Table();
        //by efault the table will be in the centre of our stage
        table.top();
        // now the table is on top of the stage
        table.setFillParent(true);
        // now the table is the size of the stage

        //time to create the labels
        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.CORAL));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.CORAL));
        timeLabel = new Label("Time ", new Label.LabelStyle(new BitmapFont(), Color.CORAL));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.CORAL));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.CORAL));
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.CORAL));

        // now time to add the labels to our table
        // expandX expands the label the entire width of our screen. if multiple items use expandX, then the screen will share those equally by default
        // pad Top will set the spacing between the top of the screen and our label by 10px in this case
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

        stage.addActor(table);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
