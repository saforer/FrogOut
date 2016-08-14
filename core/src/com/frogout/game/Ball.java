package com.frogout.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Forer on 8/11/2016.
 */
public class Ball extends GameObject{
    Vector2 vel;
    float speedLimit;
    public Ball() {
        if (image == null) image = new Texture("1x1.png");
        tag = "ball";
        width = 10;
        height = 10;
        speedLimit = 20;
        resetBall();
        updateRect();
    }

    void resetBall() {
        pos = new Vector2(400 - (width / 2), 150 - (height / 2));
        vel = new Vector2(0, -1f);
    }

    public void update() {
        vel.y +=-.2f;

        if (vel.x > speedLimit) vel.x = speedLimit;
        if (vel.y > speedLimit) vel.y = speedLimit;
        if (vel.x < -speedLimit) vel.x = -speedLimit;
        if (vel.y < -speedLimit) vel.y = -speedLimit;

        pos.x+=vel.x;
        pos.y+=vel.y;


        if (pos.y < -5) resetBall();

        updateRect();
    }



    void updateRect() {
        bound = new Rectangle(pos.x, pos.y, width, height);
    }

    public void collidedWith(GameObject object) {
        Rectangle r = object.bound;
        Directions colDirection = Directions.up;

        //Which corners collided?
        Vector2 bottomLeft;
        Vector2 bottomRight;
        Vector2 topLeft;
        Vector2 topRight;
        //Break this shape out to individual points
        bottomLeft = new Vector2(pos.x, pos.y);
        bottomRight = new Vector2(pos.x + width, pos.y);
        topLeft = new Vector2(pos.x, pos.y+height);
        topRight = new Vector2(pos.x+width, pos.y+height);
        //Which points actually collided
        boolean bLCol = isPointInRect(r, bottomLeft);
        boolean bRCol = isPointInRect(r, bottomRight);
        boolean tLCol = isPointInRect(r, topLeft);
        boolean tRCol = isPointInRect(r, topRight);

        if (bLCol&&bRCol) {
            colDirection = Directions.down;
        } else if (bLCol&&tLCol) {
            colDirection = Directions.left;
        } else if (tLCol&&tRCol) {
            colDirection = Directions.up;
        } else if (tRCol&&bRCol) {
            colDirection = Directions.right;
        }

        float friction = .9f;
        switch (colDirection) {
            case down:
                if (vel.y < 0) {
                    vel.y = vel.y * -friction;
                    pos.y = r.y+r.height;
                    if (object.tag == "paddle") {
                        Paddle p = (Paddle) object;
                        vel.x += p.getVelX() * .3f;
                    }
                }
                break;
            case left:
                if (vel.x < 0) {
                    vel.x *= -.9f;
                    pos.x = r.x + r.width;
                }
                break;
            case up:
                if (vel.y > 0) {
                    vel.y = vel.y * -friction;
                    pos.y = r.y-height;
                }
                break;
            case right:
                if (vel.x > 0) {
                    vel.x *= -.9f;
                    pos.x = r.x - width;
                }
                break;
        }

    }

    public void striked(float strikePower, Paddle paddle) {
        float strikeMult = 2;
        vel.y += (strikePower * strikeMult);
        pos.y = paddle.pos.y;
    }

    boolean isPointInRect(Rectangle r, Vector2 p) {
        boolean xMatch = ((r.x <= p.x) && (r.x + r.width >= p.x));
        boolean yMatch = ((r.y <= p.y) && (r.y + r.height >= p.y));
        return xMatch && yMatch;
    }
}
