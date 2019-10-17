package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;

public class PlayerProjectile {

    boolean isAffectedByGravity;
    boolean splashDamage;
    int damage;
    private float radius = 0.1f;
    private float density = 0.02f;
    private float forceMagnitude = 20f;
    private Body body;

    Texture texture = new Texture("fireball.png");

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    //todo implement the bullets logic from com.badlogic.gdx.physics.bullet
    PlayerProjectile(MyGame game, PlayerCharacter playerCharacter) {
        ShapeFactory shapeFactory = new ShapeFactory(game);
        float force;
        if (playerCharacter.isFacingLeft) force = -forceMagnitude;
        else force = forceMagnitude;

        body = shapeFactory.createCircle(BodyDef.BodyType.DynamicBody,
                playerCharacter.body.getPosition().x,
                playerCharacter.body.getPosition().y,
                radius, density);
        body.setBullet(true);
        body.getFixtureList().get(0).setUserData("projectile");
        this.getBody().applyLinearImpulse(force, 0, body.getPosition().x, body.getPosition().y, true);
        //body.setTexture(texture);

        Filter filter = new Filter();
        filter.categoryBits = CollisionCategories.CATEGORY_PROJECTILE;
        filter.maskBits = CollisionCategories.MASK_PROJECTILE;
        body.getFixtureList().get(0).setFilterData(filter);
        game.projectileArray.add(this);

    }

}
