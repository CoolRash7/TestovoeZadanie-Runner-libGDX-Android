package com.coolrash.game.gameobject;

import static com.coolrash.game.Additions.clamp;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.coolrash.game.Additions;
import com.coolrash.game.GameObject;
import com.coolrash.game.screens.GameScreen;

public class Player extends GameObject {

    private final float gravity = 10f;

    public enum STATE { RUN, JUMP, DEATH, DEATH_LOOP }
    public STATE state = STATE.RUN;

    //spritesheet: width = 36, height = 52
    int spriteWidth = 36;
    int spriteHeight = 52;
    TextureRegion region;
    Sound sndJump;
    Sound sndFall;
    Sound sndDeath;
    float tempWalkSpeed = 0;
    boolean dontSpam1 = false;
    boolean dontSpam2 = false;


    public Player(Vector2 pos, String pathImage, int width, int height) {
        super(pos, pathImage, width, height);
        region = new TextureRegion(texture, 0, 0, 36, 52);

        sndJump =Gdx.audio.newSound(Gdx.files.internal("snd/snd_jump.mp3"));
        sndFall =Gdx.audio.newSound(Gdx.files.internal("snd/snd_fall.mp3"));
        sndDeath =Gdx.audio.newSound(Gdx.files.internal("snd/snd_death.mp3"));


    }

    @Override
    public void update(float delta) {
        //update bounds (rect) position
        bounds.setPosition(pos.x, pos.y);

        //velocity
        pos.y += vel.y * (delta*20);               //velocity
        vel.y -= (gravity * delta * 20);           //gravity
        pos.y = clamp(pos.y, 100, 750);  //clamp

        animation(delta);

        Gdx.input.setInputProcessor( new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //Gdx.app.log("TOUCHED", "PLAYER");
                if (state == STATE.RUN)
                    sndJump.play(0.2f);

                if (pos.y <= 100 && !Additions.gameOver)
                    vel.y = 70;
                return true;
            }
        });
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(region, pos.x, pos.y, bounds.width, bounds.height);

    }

    //animation
    public void animation(float delta) {
        if (!Additions.gameOver)
            state = (pos.y <= 100) ? STATE.RUN : STATE.JUMP;
        switch (state) {
            case RUN:
                frame += delta * speedAnimRun();
                if (frame >= 4) frame = 0;
                region.setRegion(( (int)frame+9 ) * spriteWidth, 0, spriteWidth, spriteHeight);

                //sound fall
                if (!dontSpam1) {
                    sndFall.play();
                    dontSpam1 = true;
                }
                break;

            case JUMP:
                frame = 0;
                region.setRegion(( (int)frame+8 ) * spriteWidth, 0, spriteWidth, spriteHeight);

                dontSpam1 = false;
                break;

            case DEATH:
                frame += delta * 3;
                if (frame >= 6) state = STATE.DEATH_LOOP;
                region.setRegion( ((int)frame+1) * spriteWidth, 0, spriteWidth, spriteHeight  );

                //snd death
                if (!dontSpam2) {
                    sndDeath.play();
                    dontSpam2 = true;
                }
                break;

            case DEATH_LOOP:
                frame = 0;
                region.setRegion(( (int)frame+7 ) * spriteWidth, 0, spriteWidth, spriteHeight);


                break;

        }
    }

    private int speedAnimRun() {

        int result=8;
        if (Additions.score >= 20)
            result = 10;
        if (Additions.score >= 40)
            result = 12;
        if (Additions.score >= 60)
            result = 14;
        if (Additions.score >= 80)
            result = 16;
        if (Additions.score >= 100)
            result = 18;
        return result;
    }

}
