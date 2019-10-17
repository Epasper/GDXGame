package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class PlayerProjectile extends Sprite {

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
        body.setUserData("projectile");
        this.getBody().applyForceToCenter(force, 0, true);
        this.setTexture(texture);
    }
}
