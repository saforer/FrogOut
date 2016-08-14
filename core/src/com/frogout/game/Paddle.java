package com.frogout.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Forer on 8/11/2016.
 */

public class Paddle extends GameObject {
    Controller controller;
    float velX;
    float speed = 1;
    float speedLimit = 20;
    float brake = .9f;
    boolean lastPaddle = false;
    float swingCount = .0f;
    float maxSwing = 30;
    boolean swinging = false;
    public Paddle(Controller controller) {
        width = 100;
        height = 50;
        pos = new Vector2(400 - (width/2), 100 - (height/2));
        tag = "paddle";
        bound = new Rectangle(pos.x, pos.y, width, height);
        this.controller = controller;
        if (image == null) image = new Texture("1x1.png");
        updateRect();
    }

    public void update() {
        controller.update();
        movePaddle();
        swingPaddle();
        updateRect();
    }

    void movePaddle() {
        if (controller.left) velX -= speed;
        if (controller.right) velX += speed;
        if (!(controller.left || controller.right)) velX *= brake;
        if ((velX < .1f)&&(velX > -.1f)) velX = 0; //no .0000001f for me!
        if (velX > speedLimit) velX = speedLimit;
        if (velX < -speedLimit) velX = -speedLimit;
        pos.x += velX;
    }

    void swingPaddle() {
        if (controller.swing) {
            //Holding down button to swing
            lastPaddle = true;
            swingCount += .3f;
            if (swingCount >= height) swingCount = height;
            pos.y = 100 - (height/2) - swingCount;
        } else {
            //not holding button to swing
            if (lastPaddle) {
                //Just stopped holding button to swing
                swinging = true;
                lastPaddle = false;
                pos.y = 100 - (height/2);
            } else {
                swinging = false;
                swingCount = .0f;
            }
        }
    }

    void updateRect() {
        bound = new Rectangle(pos.x, pos.y, width, height);
    }

    public void collidedWith(GameObject object) {
        if (object.tag == "world") {
            Rectangle r = object.bound;
            velX = 0;
            if (whichDirection(r) == Directions.left) {
                pos.x = r.x+r.width;
            } else {
                pos.x = r.x-width;
            }
        }
        if (swinging) {
            if (object.tag == "ball") {
                Ball ball = (Ball)object;
                ball.striked(swingCount, this);
            }
        }
    }

    Directions whichDirection(Rectangle r) {
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
        return colDirection;
    }

    boolean isPointInRect(Rectangle r, Vector2 p) {
        boolean xMatch = ((r.x <= p.x) && (r.x + r.width >= p.x));
        boolean yMatch = ((r.y <= p.y) && (r.y + r.height >= p.y));
        return xMatch && yMatch;
    }

    public float getVelX() {
        return velX;
    }
}
