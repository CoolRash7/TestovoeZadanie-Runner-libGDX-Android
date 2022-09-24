package com.coolrash.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.coolrash.game.Additions;
import com.coolrash.game.Test;

public class LoseScreen implements Screen {

    final Test game;
    Skin    mySkin;
    Stage   stage;
    int record;

    public LoseScreen(final Test game) {
        this.game = game;

        game.additions.queueAddSkin();
        game.additions.manager.finishLoading();
        mySkin = game.additions.manager.get(game.additions.skin);

        stage = new Stage(game.screenPort);
        Gdx.input.setInputProcessor(stage);

        Label labelTitle = new Label("GAME OVER", mySkin, "default");
        labelTitle.setAlignment(Align.center);
        labelTitle.setFontScale(3);
        labelTitle.setPosition(Additions.WIDTH / 2-labelTitle.getWidth()/2,  Additions.HEIGHT - 200);

        record = Additions.getRecord();

        Label labelRecord = new Label("SCORE: " +record, mySkin, "default");
        labelRecord.setAlignment(Align.center);
        labelRecord.setFontScale(2);
        labelRecord.setPosition(Additions.WIDTH / 2-labelRecord.getWidth()/2,  Additions.HEIGHT - 400);

        Button buttonStart = new TextButton("MAIN MENU", mySkin, "default");
        buttonStart.setSize(500, 100);
        buttonStart.setPosition(Additions.WIDTH/2 - buttonStart.getWidth()/2, 20);
        buttonStart.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.gotoMenuScreen();

                return true;
            }
        });





        stage.addActor(labelTitle);
        stage.addActor(labelRecord);
        stage.addActor(buttonStart);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }



    @Override
    public void resize(int width, int height) {
        game.screenPort.update(width, height, true);
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
        mySkin.dispose();
        stage.dispose();
    }
}
