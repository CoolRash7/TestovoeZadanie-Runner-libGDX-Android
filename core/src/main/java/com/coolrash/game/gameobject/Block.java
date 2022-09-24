package com.coolrash.game.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.coolrash.game.GameObject;

public class Block extends GameObject {

    public Block(Vector2 pos, String pathImage, int width, int height) {
        super(pos, pathImage, width, height);
        vel.x = -10;
    }

    @Override
    public void update(float dt) {
        bounds.setPosition(pos.x, pos.y);
        pos.x += vel.x;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, bounds.width, bounds.height);
    }
}
