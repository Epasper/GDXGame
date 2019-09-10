package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class PlayerCharacter {

    Body body;

    Body feetSensor;

    private MyGame game;

    ShapeFactory shapeFactory;

    Sprite playerSprite = new Sprite(new Texture("Mario.png"));

    PlayerCharacter(MyGame game) {
        this.game = game;
        shapeFactory = new ShapeFactory(game);
        body = shapeFactory.createBox(BodyDef.BodyType.DynamicBody,
                0,
                20 ,
                2,
                2,
                5);
        /*feetSensor = shapeFactory.createBox(BodyDef.BodyType.DynamicBody,
                0,
                20 * Configuration.resolutionScalingX,
                2 * Configuration.resolutionScalingX,
                0.1f * Configuration.resolutionScalingX,
                5);*/
        body.setFixedRotation(true);
/*
        feetSensor.setFixedRotation(true);
*/

    }


}
