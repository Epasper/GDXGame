package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGame extends Game {

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    SpriteBatch gameBatch;
    SpriteBatch screensBatch;
    BitmapFont font;
    Texture texture;

    Sprite backgroundSprite;

    int levelHeight = 360;
    int levelWidth = 480;


    @Override
    public void create() {
        gameBatch = new SpriteBatch();
        screensBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new MenuScreen(this));
        camera = new OrthographicCamera(levelWidth, levelHeight);
        camera.position.set(levelWidth/2f, levelHeight/2f, 0);
        camera.update();
        texture = new Texture(Gdx.files.internal("background_image.jpg"));
    }



    @Override
    public void dispose() {
        shapeRenderer.dispose();
        gameBatch.dispose();
        screensBatch.dispose();
        texture.dispose();
    }
}