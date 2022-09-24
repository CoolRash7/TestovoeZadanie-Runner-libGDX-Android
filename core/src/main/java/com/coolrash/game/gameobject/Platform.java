package com.coolrash.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coolrash.game.Additions;
import com.coolrash.game.Test;

public class Platform {

    final float y =-213;          //platform position y
    public float velX = -10;       //runner's speed

    Texture texture1, texture2;
    float x1 = 0;
    float x2= Additions.WIDTH;

    public Platform(String pathImage) {
        texture1 = new Texture(pathImage);
        texture2 = new Texture(pathImage);
    }

    public void render(SpriteBatch batch) {
        //endless platform animation
        x1 += velX;
        x2 += velX;

        float screenWidth = Gdx.graphics.getWidth();

        if (x1 <= -Additions.WIDTH) x1 = Additions.WIDTH;
        if (x2 <= -Additions.WIDTH) x2 = Additions.WIDTH;

        batch.draw(texture1, x1, y);
        batch.draw(texture2, x2, y);
    }

    public void dispose() {
        texture1.dispose();
        texture2.dispose();
    }

}
