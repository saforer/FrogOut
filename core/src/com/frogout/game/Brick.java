package com.frogout.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Forer on 8/12/2016.
 */
public class Brick extends GameObject {
    boolean broken = false;
    public Brick(Vector2 pos, int width, int height) {
        tag = "brick";
        this.pos = pos;
        bound = new Rectangle(pos.x, pos.y, width, height);
        if (image == null) image = new Texture("1x1.png");
    }

    public void collidedWith(GameObject obj) {
        if (obj.tag == "ball") {
            broken = true;
            bound = new Rectangle(0,0,0,0);
        }
    }
}