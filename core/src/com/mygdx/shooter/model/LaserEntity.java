package com.mygdx.shooter.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LaserEntity extends MainEntity {

    public LaserEntity(World world, float PTM, Vector2 center) {

        init(null, world, PTM, center, "laserBlue03.png", null);

        sprite.setPosition(body.getPosition().x - size.x/2, body.getPosition().y - size.y/2);
    }

    public void render(SpriteBatch batch) {
        if (body.getPosition().x < 0 - radius) {
            super.setAlive(false);
        } else if (body.getPosition().y < 0 - radius) {
            super.setAlive(false);
        } else if (body.getPosition().x > screen.x + radius) {
            super.setAlive(false);
        } else if (body.getPosition().y > screen.y + radius) {
            super.setAlive(false);
        }

        sprite.setPosition((body.getPosition().x - size.x/2) * PTM, (body.getPosition().y - size.y/2) * PTM);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.draw(batch);
    }

}
