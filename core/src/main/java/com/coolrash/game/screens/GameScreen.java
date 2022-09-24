package com.coolrash.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.coolrash.game.Additions;
import com.coolrash.game.Test;
import com.badlogic.gdx.graphics.GL20;
import com.coolrash.game.gameobject.Block;
import com.coolrash.game.gameobject.Platform;
import com.coolrash.game.gameobject.Player;

import java.util.LinkedList;
import java.util.Random;

public class GameScreen implements Screen {

    final Test game;

    Skin mySkin;
    Stage stage;
    SpriteBatch batch;
    Player player;
    LinkedList<Block> blockHandler;
    Platform platform;
    Label labelScore;
    Texture texBg;

    Music music;


    float tempGameSpeed =-10;
    float randomTimerBlock;
    float tempTimerBlock = 0;
    float tempTimeScore = 0;
    float tempTimeGameSpeed = 0;
    Random random;

    Sound sndNext;

    public GameScreen(final Test game) {
        this.game = game;

        game.additions.queueAddSkin();
        game.additions.manager.finishLoading();
        mySkin = game.additions.manager.get(game.additions.skin);

        stage = new Stage(game.screenPort);

        texBg = new Texture("033.png");
        Image imgBg = new Image(texBg);
        imgBg.setPosition(0, 0);
        imgBg.setSize(Additions.WIDTH, Additions.HEIGHT);

        labelScore = new Label("SCORE: " , mySkin, "default");
        labelScore.setFontScale(2);
        labelScore.setPosition(100, Additions.HEIGHT-100);
        labelScore.setColor(Color.WHITE);

        stage.addActor(imgBg);
        stage.addActor(labelScore);

        batch = new SpriteBatch();
        player = new Player(new Vector2(100, 100), "robocop.png", 150, 250);
        platform = new Platform("fall.png");
        blockHandler = new LinkedList<Block>();
        blockHandler.add(new Block(new Vector2(Additions.WIDTH, 100), "block.png", 84,  84));

        random = new Random();

        randomTimerBlock = setRandomTime();

        music = Gdx.audio.newMusic(Gdx.files.internal("snd/mus_game.mp3"));
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();

        sndNext =Gdx.audio.newSound(Gdx.files.internal("snd/snd_next.mp3"));

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


        batch.begin();
        player.render(batch);
        platform.render(batch);

        renderAndUpdateHandlerBlock(delta);


        batch.end();

    }

    public void update(float delta) {

        player.update(delta);
        labelScore.setText("SCORE: " + Additions.score);

        if (!Additions.gameOver) {
            tempTimeScore += delta;
            if (tempTimeScore > 1) {
                Additions.score++;
                tempTimeScore = 0;
            }

            tempTimerBlock += 1 * delta;
            if (tempTimerBlock >= randomTimerBlock)
                randomTimerBlock = setRandomTime();



            //game speed
            tempTimeGameSpeed += delta;
            if (tempTimeGameSpeed >= 20) {
                sndNext.play();
                tempGameSpeed -= 4;
                platform.velX = tempGameSpeed;
                tempTimeGameSpeed = 0;
            }
        }

        //check gameover and end death animation (wait state to DEATH_LOOP)
        if (player.state == Player.STATE.DEATH_LOOP) {
            music.stop();
            if (Additions.score > Additions.getRecord())
                Additions.setRecord(Additions.score);
            game.gotoLoseScreen();
        }

    }

    private void renderAndUpdateHandlerBlock(float delta) {
        try {


            blockHandler.forEach( (block) -> {    //лямбда выражение с анонимной функцией
                block.vel.x = tempGameSpeed;
                block.render(batch);
                block.update(delta);
                if (player.bounds.overlaps(block.bounds) && !Additions.gameOver) {
                    tempGameSpeed = 0;
                    platform.velX = 0;
                    Additions.gameOver = true;
                    player.state = Player.STATE.DEATH;
                    //Gdx.app.exit();
                }

                if (block.pos.x <= -100) {

                    blockHandler.remove(block);
                }

            });

            //Gdx.app.log("TOUCHED", "removed, handler: " + blockHandler.size());

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private float setRandomTime() {

        float screenWidth = Gdx.graphics.getWidth();
        blockHandler.add(new Block(new Vector2(screenWidth, 100), "block.png", 84,  84) );
        tempTimerBlock = 0;
        return random.nextFloat() + random.nextInt(2)+0.8f;
    }

    @Override
    public void resize(int width, int height) {
        game.screenPort.setScreenSize(width, height);
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
        batch.dispose();
        player.dispose();
        texBg.dispose();
        platform.dispose();

        try {
            blockHandler.forEach( (block) -> {
                block.dispose();
            });
            blockHandler.clear();
        } catch(Exception e) {
            e.printStackTrace();
        }


        music.stop();
        music.dispose();
    }
}
