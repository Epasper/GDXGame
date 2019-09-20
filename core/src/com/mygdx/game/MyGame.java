package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGame extends Game {

    OrthographicCamera camera;

    Body mainCharacterBody;
    Body groundBody;

    SpriteBatch gameBatch;
    SpriteBatch screensBatch;
    BitmapFont font;
    Texture backgroundTexture;
    Texture groundTileTexture;
    Texture groundInnerTexture;
    Texture groundTileDownTexture;
    Texture groundTileUpTexture;
    World world;
    Box2DDebugRenderer debugRenderer;
    PlayerCharacter playerCharacter;
    Monster monster;
    LevelFactory levelFactory;
    HUDElements hud;

    Viewport viewport;

    int levelHeight = 50;
    int levelWidth = 50;

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(mainCharacterBody.getPosition().x,
                mainCharacterBody.getPosition().y, 0);
    }

    @Override
    public void create() {
        System.out.println("GDX Version: " + Gdx.app.getGraphics().getGLVersion().getMajorVersion() + "." + Gdx.app.getGraphics().getGLVersion().getMinorVersion());

        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -200), true);
        gameBatch = new SpriteBatch();
        screensBatch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new MenuScreen(this));
        camera = new OrthographicCamera(levelWidth, levelHeight);
        viewport = new FillViewport(levelWidth, levelHeight, camera);
        viewport.apply();
        playerCharacter = new PlayerCharacter(this);
        monster = new Monster(this, 120f, 80f);
        mainCharacterBody = playerCharacter.body;
        //camera.position.set(mainCharacterBody.getPosition().x,
       //         mainCharacterBody.getPosition().y, 0);
        camera.update();
        backgroundTexture = new Texture(Gdx.files.internal("background_image.jpg"));
        groundTileTexture = new Texture(Gdx.files.internal("GroundTile.png"));
        groundInnerTexture = new Texture(Gdx.files.internal("DeepEarthTile.png"));
        groundTileDownTexture = new Texture(Gdx.files.internal("GroundTileDown.png"));
        groundTileUpTexture = new Texture(Gdx.files.internal("GroundTileUp.png"));
        levelFactory = new LevelFactory(this);
        groundBody = levelFactory.createFloor(BodyDef.BodyType.StaticBody,
                -2,
                5f,
                300,
                5f,
                0);
        groundBody.setUserData("ground");
        Filter filter = new Filter();
        filter.categoryBits = CollisionCategories.CATEGORY_SCENERY;
        filter.maskBits = CollisionCategories.MASK_SCENERY;
        groundBody.getFixtureList().get(0).setFilterData(filter);
        hud = new HUDElements();
        hud.player = playerCharacter;
    }

    @Override
    public void dispose() {
        gameBatch.dispose();
        screensBatch.dispose();
        backgroundTexture.dispose();
        groundTileTexture.dispose();
        groundInnerTexture.dispose();
        groundTileDownTexture.dispose();
        groundTileUpTexture.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}