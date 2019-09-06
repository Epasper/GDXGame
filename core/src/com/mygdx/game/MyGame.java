package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MyGame extends Game {

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    Body mainCharacterBody;

    SpriteBatch gameBatch;
    SpriteBatch screensBatch;
    BitmapFont font;
    Texture backgroundTexture;
    Texture groundTileTexture;
    World world;
    Box2DDebugRenderer debugRenderer;

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
        // ground
        createEdge(BodyDef.BodyType.StaticBody, -2, 5f, 300, 5f, 0);

        mainCharacterBody = createCircle(BodyDef.BodyType.DynamicBody, 0, 100, 1, 5);

    }

    private Body createBox(BodyDef.BodyType type, float x, float y, float width, float height, float density) {
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, height);

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = world.createBody(def);
        body.createFixture(poly, density);
        body.setTransform(x, y, 0);
        poly.dispose();

        return body;
    }

    private Body createEdge(BodyDef.BodyType type, float x1, float y1, float x2, float y2, float density) {
        EdgeShape poly = new EdgeShape();
        poly.set(new Vector2(0, 0), new Vector2(x2 - x1, y2 - y1));

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = world.createBody(def);
        body.createFixture(poly, density);
        body.setTransform(x1, y1, 0);
        poly.dispose();

        return body;
    }

    private Body createCircle(BodyDef.BodyType type, float x, float y, float radius, float density) {
        CircleShape poly = new CircleShape();
        poly.setRadius(radius);

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = world.createBody(def);
        body.createFixture(poly, density);
        body.setTransform(x, y, 0);
        poly.dispose();

        return body;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        gameBatch.dispose();
        screensBatch.dispose();
        backgroundTexture.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}