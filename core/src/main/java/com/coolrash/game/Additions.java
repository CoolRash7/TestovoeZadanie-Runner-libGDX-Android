package com.coolrash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Additions {

    public static final int WIDTH = 1334;
    public static final int HEIGHT = 750;
    public static boolean gameOver = false;
    public static int score = 0;
    public final AssetManager manager = new AssetManager();

    public static String skin = "skin/green.json";
    public static String skinAtlas = "skin/green.atlas";
    public static Preferences prefs;
    public static int record;

    public static int getRecord() {
        prefs = Gdx.app.getPreferences("prefs");
        return prefs.getInteger("record", 0);
    }


    public static void setRecord(int record) {
        prefs = Gdx.app.getPreferences("prefs");
        prefs.putInteger("record", record);
    }

    public void queueAddSkin() {
        SkinLoader.SkinParameter parameter = new SkinLoader.SkinParameter(skinAtlas);
        manager.load(skin, Skin.class, parameter);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static float lerp(float start, float end, float time) {
        return start * (1 - time) + end * time;
    }
}
