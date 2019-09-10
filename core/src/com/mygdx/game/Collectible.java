package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Collectible {

    MyGame game;
    Body collectibleBody;
    ShapeFactory shapeFactory;
    float positionX;
    float positionY;
    Texture collectibleTexture;
    Sprite collectibleSprite;

    public Collectible(MyGame game, float positionX, float positionY, String texturePath) {
        this.game = game;
        this.positionX = positionX;
        this.positionY = positionY;
        collectibleTexture = new Texture(texturePath);
        shapeFactory = new ShapeFactory(game);
        collectibleBody = shapeFactory.createCircle(BodyDef.BodyType.KinematicBody, positionX, positionY, 1, 0);
        collectibleSprite = new Sprite(collectibleTexture);
        collectibleSprite.setPosition(positionX, positionY);
        //collectibleTexture.dispose();
    }
}
