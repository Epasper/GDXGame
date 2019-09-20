package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Collectible extends Sprite {

    MyGame game;
    Body collectibleBody;
    ShapeFactory shapeFactory;
    float positionX;
    float positionY;
    Texture collectibleTexture;

    public Collectible(MyGame game, float positionX, float positionY, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        this.game = game;
        this.positionX = positionX;
        this.positionY = positionY;
        collectibleTexture = new Texture(texturePath);
        shapeFactory = new ShapeFactory(game);
        collectibleBody = shapeFactory.createCircle(BodyDef.BodyType.KinematicBody, positionX, positionY, 1, 0);
        this.setPosition(positionX, positionY);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(positionX, positionY);
        //collectibleTexture.dispose();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shapeFactory.circle;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.isSensor = true;
        Filter filter = new Filter();
        filter.categoryBits = CollisionCategories.CATEGORY_COLLECTIBLES;
        filter.maskBits = CollisionCategories.MASK_COLLECTIBLE;
        collectibleBody.getFixtureList().get(0).setUserData("coin");
        collectibleBody.getFixtureList().get(0).setFilterData(filter);
        collectibleBody.setUserData("coin");

        fixtureDef.filter.categoryBits = CollisionCategories.CATEGORY_COLLECTIBLES;
        fixtureDef.filter.maskBits = CollisionCategories.MASK_COLLECTIBLE;


    }

    public void destroyBody () {
        game.world.destroyBody(this.collectibleBody);
    }

}
