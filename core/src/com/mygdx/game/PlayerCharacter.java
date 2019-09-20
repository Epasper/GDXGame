package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;

public class PlayerCharacter {

    Body body;

    private MyGame game;

    int hitPoints;

    ShapeFactory shapeFactory;

    Sprite playerSprite = new Sprite(new Texture("Player.png"));

    PlayerCharacter(MyGame game) {
        hitPoints = 50;
        this.game = game;
        shapeFactory = new ShapeFactory(game);
        body = shapeFactory.createBox(BodyDef.BodyType.DynamicBody,
                0,
                20,
                2,
                2,
                0.005f);
        Filter filter = new Filter();
        filter.categoryBits = CollisionCategories.CATEGORY_PLAYER;
        filter.maskBits = CollisionCategories.MASK_PLAYER;
        body.getFixtureList().get(0).setFilterData(filter);
        body.setUserData("player");
        body.setFixedRotation(true);
    }


}
