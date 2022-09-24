package com.coolrash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

    public Vector2 pos;
    public Vector2 vel;
    public Texture texture;
    public float frame = 0;
    public Rectangle bounds;

    public GameObject(Vector2 pos, String pathImage, int width, int height) {
        this.pos = pos;
        this.vel = new Vector2(0,0);
        this.bounds = new Rectangle();
        this.bounds.width = width;
        this.bounds.height = height;
        this.texture = new Texture(Gdx.files.internal( pathImage ));

    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);

    public void dispose() {
        texture.dispose();
    }
}