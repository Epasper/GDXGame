package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;

public class Monster {

    Body body;

    private MyGame game;

    int hitPoints;

    ShapeFactory shapeFactory;

    float rangeLeft = 40f;
    float rangeRight = 40f;
    float monsterSpeed = 20f;
    float initialXPos;
    float initialYPos;
    boolean isGoingRight;
    boolean isGoingLeft = false;

    Sprite monsterSprite = new Sprite(new Texture("Monster.png"));

    Monster(MyGame game, float initialXPos, float initialYPos) {
        hitPoints = 50;
        this.game = game;
        this.initialXPos = initialXPos;
        this.initialYPos = initialYPos;
        shapeFactory = new ShapeFactory(game);
        body = shapeFactory.createBox(BodyDef.BodyType.DynamicBody,
                initialXPos,
                initialYPos,
                2,
                2,
                5);
        //body.applyForce(0, -200, body.getPosition().x, body.getPosition().y, true);

/*        body.getPosition().x = initialXPos;
        body.getPosition().y = initialYPos;*/

        monsterSprite.setX(initialXPos);
        monsterSprite.setY(initialYPos);

        Filter filter = new Filter();
        filter.categoryBits = CollisionCategories.CATEGORY_MONSTER;
        filter.maskBits = CollisionCategories.MASK_MONSTER;
        body.getFixtureList().get(0).setFilterData(filter);
        body.setUserData("monster");

        body.setFixedRotation(true);

        isGoingRight = true;
    }

    public void setMonsterInMotion() {
        if (body.getPosition().x < initialXPos - rangeLeft) {
            isGoingRight = true;
            isGoingLeft = false;
        } else if (body.getPosition().x > initialXPos + rangeRight) {
            isGoingRight = false;
            isGoingLeft = true;
        }
        if (isGoingLeft) {
            body.setLinearVelocity(-monsterSpeed, body.getLinearVelocity().y);
        } else if (isGoingRight) {
            body.setLinearVelocity(monsterSpeed, body.getLinearVelocity().y);
        }
    }

}
