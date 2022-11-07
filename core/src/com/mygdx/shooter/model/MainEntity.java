package com.mygdx.shooter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.shooter.controller.BodyEditorLoader;

public class MainEntity {

    Texture texture;
    Sprite sprite;
    Vector2 screen;
    Vector2 size;
    Vector2 center;
    float radius;
    Body body;
    float PTM;
    private boolean isAlive = true;

    public void init(BodyEditorLoader loader, World world, float PTM, Vector2 center, String textureName, String fixtureName) {
        this.PTM = PTM;
        screen = new Vector2(Gdx.graphics.getWidth() / PTM, Gdx.graphics.getHeight() / PTM);
        this.center = center;

        texture = new Texture(Gdx.files.internal(textureName));

        sprite = new Sprite(texture);
        size = new Vector2(sprite.getWidth() / PTM, sprite.getHeight() / PTM);
        radius = (float) Math.sqrt(((size.x/2) * (size.x/2)) + ((size.y/2) * (size.y/2)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(center.x - (size.x/2), center.y - (size.y/2));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x/2, size.y/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;

        if (loader != null)
        loader.attachFixture(body, fixtureName, fixtureDef, size.x);

        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();

        sprite.setPosition(body.getPosition().x * PTM, body.getPosition().y * PTM);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
    }

    public void damage() {
        isAlive = false;
    }

    public void dispose() {
        texture.dispose();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
