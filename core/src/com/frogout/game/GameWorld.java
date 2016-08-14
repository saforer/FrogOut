package com.frogout.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Forer on 8/11/2016.
 */
public class GameWorld extends GameObject{
    public GameWorld(Vector2 pos, int width, int height) {
        tag = "world";
        this.pos = pos;
        bound = new Rectangle(pos.x, pos.y, width, height);
        if (image == null) image = new Texture("1x1.png");
    }
}
