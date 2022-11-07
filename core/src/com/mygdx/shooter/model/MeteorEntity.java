package com.mygdx.shooter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.shooter.controller.BodyEditorLoader;

import java.util.Random;

public class MeteorEntity extends MainEntity {

    float massRadius;

    public MeteorEntity(BodyEditorLoader loader, World world, float PTM) {

        Random random = new Random();

        Vector2 init = new Vector2(random.nextInt((int) (Gdx.graphics.getWidth() / PTM)), random.nextInt((int) (Gdx.graphics.getHeight() / PTM)));

        switch (random.nextInt(10)) {
            case 0:
                init(loader, world, PTM, init, "meteorBrown_big1.png", "big1"); break;
            case 1:
                init(loader, world, PTM, init, "meteorGrey_big2.png", "big2"); break;
            case 2:
                init(loader, world, PTM, init, "meteorBrown_big3.png", "big3"); break;
            case 3:
                init(loader, world, PTM, init, "meteorGrey_big4.png", "big4"); break;
            case 4:
                init(loader, world, PTM, init, "meteorBrown_med1.png", "med1"); break;
            case 5:
                init(loader, world, PTM, init, "meteorGrey_med2.png", "med2"); break;
            case 6:
                init(loader, world, PTM, init, "meteorBrown_small1.png", "small1"); break;
            case 7:
                init(loader, world, PTM, init, "meteorGrey_small2.png", "small2"); break;
            case 8:
                init(loader, world, PTM, init, "meteorBrown_tiny1.png", "tiny1"); break;
            case 9:
                init(loader, world, PTM, init, "meteorGrey_tiny2.png", "tiny2"); break;
        }
        sprite.setOrigin(0,0);

        massRadius = (float) Math.sqrt(((body.getLocalCenter().x) * (body.getLocalCenter().x)) +
                ((body.getLocalCenter().y) * (body.getLocalCenter().y)));

        body.setLinearVelocity(random.nextInt(10) * 0.05f, random.nextInt(10) * 0.05f);
        body.setAngularVelocity(random.nextInt(10) * 1f);
    }

    public void render(SpriteBatch batch) {
        if (center.x < 0 - radius) {
            center.x = screen.x + radius;
            outOfBorder();
        } else if (center.y < 0 - radius) {
            center.y = screen.y + radius;
            outOfBorder();
        } else if (center.x > screen.x + radius) {
            center.x = 0 - radius;
            outOfBorder();
        } else if (center.y > screen.y + radius) {
            center.y = 0 - radius;
            outOfBorder();
        }

        sprite.setPosition(body.getPosition().x * PTM, body.getPosition().y * PTM);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.draw(batch);

        center.x = body.getPosition().x + (float) (massRadius * Math.cos(body.getAngle() + Math.toRadians(45)));
        center.y = body.getPosition().y + (float) (massRadius * Math.sin(body.getAngle() + Math.toRadians(45)));

    }

    private void outOfBorder() {
        body.setTransform(
                center.x + (float) (massRadius * Math.cos(body.getAngle() - Math.toRadians(135))),
                center.y + (float) (massRadius * Math.sin(body.getAngle() - Math.toRadians(135))),
                body.getAngle()
        );
    }
}
