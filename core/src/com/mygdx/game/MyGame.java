package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class MyGame extends Game {

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    ShapeFactory shapeFactory;

    Body mainCharacterBody;
    Body groundBody;

    SpriteBatch gameBatch;
    SpriteBatch screensBatch;
    BitmapFont font;
    Texture backgroundTexture;
    Texture groundTileTexture;
    World world;
    Box2DDebugRenderer debugRenderer;
    PlayerCharacter playerCharacter;
    LevelFactory levelFactory;

    int levelHeight = 36;
    int levelWidth = 48;

    @Override
    public void create() {
        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -200), true);
        gameBatch = new SpriteBatch();
        screensBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new MenuScreen(this));
        camera = new OrthographicCamera(levelWidth, levelHeight);
        camera.position.set(levelWidth / 2f, levelHeight / 2f, 0);
        camera.update();
        backgroundTexture = new Texture(Gdx.files.internal("background_image.jpg"));
        groundTileTexture = new Texture(Gdx.files.internal("GroundTile.png"));
        playerCharacter = new PlayerCharacter(this);
        mainCharacterBody = playerCharacter.body;
        levelFactory = new LevelFactory(this);
        groundBody = levelFactory.createFloor(BodyDef.BodyType.StaticBody,
                -2 * Configuration.resolutionScaling,
                5f * Configuration.resolutionScaling,
                300 * Configuration.resolutionScaling,
                5f * Configuration.resolutionScaling,
                0);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        gameBatch.dispose();
        screensBatch.dispose();
        backgroundTexture.dispose();
        groundTileTexture.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}