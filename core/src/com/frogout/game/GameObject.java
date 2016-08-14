package com.frogout.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Forer on 8/12/2016.
 */
public class GameObject {
    public String tag;
    public Rectangle bound;
    int width;
    int height;
    Vector2 pos;
    static Texture image;

    public void update() {}

    public void drawSprite(SpriteBatch sb) {
        sb.draw(image, bound.x, bound.y, bound.width, bound.height);
    }

    public void drawShape(ShapeRenderer sr) {
        sr.rect(bound.x, bound.y, bound.width, bound.height);
    }

    public void collidedWith(GameObject go) {}


    void updateRect() {
        bound = new Rectangle(pos.x, pos.y, width, height);
    }
}
