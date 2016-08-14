package com.frogout.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class FrogOut extends ApplicationAdapter {
	BitmapFont font;
	SpriteBatch batch;
	ShapeRenderer renderer;
	List<GameObject> gameObjectList = new ArrayList<GameObject>();
	Texture img;
	Paddle paddle;
	Ball ball;
	boolean reallyWin = false;
	boolean drawShape = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		font = new BitmapFont();
		gameObjectList.add(new Paddle(new Controller()));
		gameObjectList.add(new Ball());
		gameObjectList.add(new GameWorld(new Vector2(0,0), 50, 600));
		gameObjectList.add(new GameWorld(new Vector2(800-50,0), 50, 600));
		gameObjectList.add(new GameWorld(new Vector2(0, 600-50), 800, 50));
		addBricks(gameObjectList);
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (GameObject go : gameObjectList) {
			go.drawSprite(batch);
		}
		if (reallyWin) font.draw(batch, "CONGRATULATIONS!", 300, 200);
		batch.end();
		if (drawShape) {
			renderer.begin(ShapeRenderer.ShapeType.Filled);
			renderer.setColor(Color.RED);
			for (GameObject go : gameObjectList) {
				go.drawShape(renderer);
			}
			renderer.end();
		}
	}

	public void update() {
		if (!reallyWin) {
			boolean win = true;
			for (GameObject go : gameObjectList) {
				go.update();
				if (go.tag == "brick") {
					Brick b = (Brick) go;
					if (!b.broken) {
						win = false;
					}
				}
			}
			if (win) reallyWin = true;

			checkCollisions();
		} else {

		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	void checkCollisions() {
		for (GameObject go : gameObjectList) {
			for (GameObject go2 : gameObjectList) {
				if (go != go2) {
					if (overlap2D(go, go2)) {
						go.collidedWith(go2);
						go2.collidedWith(go);
					}
				}
			}
		}
	}

	boolean overlap2D (GameObject o1, GameObject o2) {
		Rectangle r1, r2;
		r1 = o1.bound;
		r2 = o2.bound;
		if (overlap1D(r1.x, r1.width, r2.x, r2.width)) {
			if (overlap1D(r1.y, r1.height, r2.y, r2.height)) return true;
		}
		return false;
	}

	boolean overlap1D (float pos1, float wid1, float pos2, float wid2) {
		return (((pos1+wid1) >= pos2) && ((pos2+wid2) >= pos1));
	}

	boolean isPointInRect(Rectangle r, float x, float y) {
		boolean xMatch = ((r.x <= x) && (r.x + r.width >= x));
		boolean yMatch = ((r.y <= y) && (r.y + r.height >= y));
		return xMatch && yMatch;
	}

	void addBricks(List<GameObject> listGameObjects) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				int xPos = 130-(50/2) + (((50 + 70) * i) / 2);
				int yPos = 300-(30/2) + ((30 + 30) * j);
				listGameObjects.add(new Brick(new Vector2(xPos, yPos), 50, 30));
			}
		}
	}
}
