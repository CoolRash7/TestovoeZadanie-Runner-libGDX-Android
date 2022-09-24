package com.coolrash.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

public class MenuScreen implements Screen {

    final Test game;
    Skin    mySkin;
    Stage   stage;
    Texture texLogo;
    Texture texBg;
    Image   imgLogo;
    Image   imgBg;

    float alphaBegin = 0;
    float ySin = 0;
    float yLogo = -200;

    Music music;

    public MenuScreen(final Test game) {

        this.game = game;

        texLogo = new Texture("logo.png");
        texBg = new Texture("titlebg.png");

        game.additions.queueAddSkin();
        game.additions.manager.finishLoading();
        mySkin = game.additions.manager.get(game.additions.skin);

        stage = new Stage(game.screenPort);
        Gdx.input.setInputProcessor(stage);

        Label labelTitle = new Label("(C) 2022 RANDOM DEVELOPER Inc. All right reserved.", mySkin, "default");
        labelTitle.setAlignment(Align.center);
        labelTitle.setFontScale(1);
        labelTitle.setPosition(Additions.WIDTH / 2-labelTitle.getWidth()/ 2, 10);


        Button buttonStart = new TextButton("START GAME", mySkin, "default");
        buttonStart.setSize(500, 100);
        buttonStart.setPosition(Additions.WIDTH/2 - buttonStart.getWidth()/2, 200);
        buttonStart.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Additions.gameOver = false;
                Additions.score = 0;
                game.gotoGameScreen();

                return true;
            }
        });

        Button buttonExit = new TextButton("EXIT", mySkin, "default");
        buttonExit.setSize(400, 50);
        buttonExit.setPosition(Additions.WIDTH/2 - buttonExit.getWidth()/2, 85);
        buttonExit.addListener( new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });



        imgLogo = new Image(texLogo);
        Vector2 posImg = new Vector2(Additions.WIDTH/2 - imgLogo.getWidth()/2, yLogo);
        imgLogo.setPosition(posImg.x, posImg.y);

        imgBg = new Image(texBg);
        imgBg.setPosition(0, 0);
        imgBg.setSize(Additions.WIDTH, Additions.HEIGHT);


        stage.addActor(imgBg);
        stage.addActor(labelTitle);
        stage.addActor(imgLogo);
        stage.addActor(buttonStart);
        stage.addActor(buttonExit);

        music = Gdx.audio.newMusic(Gdx.files.internal("snd/mus_menu.mp3"));
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    private void update(float delta) {
        //alpha color
        alphaBegin = Additions.lerp(alphaBegin, 1, 1 * delta);

        Color colorAlpha = new Color(1, 1, 1, alphaBegin);

        imgBg.setColor(colorAlpha);
        imgLogo.setColor(colorAlpha);

        //y animation (lerp, sin)
        ySin += 5 * delta;
        if (ySin >= 360) ySin = 0;       //from 0 to 360 endless

        yLogo = Additions.lerp(yLogo, Additions.HEIGHT - 300, 3 * delta);
        float animSin = (float)Math.sin(ySin)*25;

        Vector2 posImg = new Vector2(imgLogo.getX(),yLogo + animSin);
        imgLogo.setPosition(posImg.x, posImg.y);
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
        texBg.dispose();
        texLogo.dispose();
        stage.dispose();
    }
}
