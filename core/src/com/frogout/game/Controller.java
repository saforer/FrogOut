package com.frogout.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Forer on 8/11/2016.
 */
public class Controller {
    public boolean left;
    public boolean right;
    public boolean swing;
    public void update() {
        reset();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) left = true;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) right = true;
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) swing = true;
    }
    void reset() {
        left = false;
        right = false;
        swing = false;
    }
}
