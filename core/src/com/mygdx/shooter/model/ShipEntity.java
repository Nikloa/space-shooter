package com.mygdx.shooter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.shooter.controller.BodyEditorLoader;
import com.mygdx.shooter.controller.SoundManager;

public class ShipEntity extends MainEntity {

    SoundManager sound;
    Texture shield;

    private int lives = 2;
    boolean run = true;

    Sprite shieldSprite;
    float shieldRadius;
    long shieldTerm = 0;

    public ShipEntity(BodyEditorLoader loader, World world, float PTM, SoundManager sound) {
        this.sound = sound;
        Vector2 init = new Vector2(Gdx.graphics.getWidth()/2f / PTM, Gdx.graphics.getHeight()/2f / PTM);

        init(loader, world, PTM, init, "playerShip3_blue.png", "ship3");
        sprite.setOrigin(0,0);

        shield = new Texture(Gdx.files.internal("shield3.png"));
        shieldSprite = new Sprite(shield);
        shieldSprite.setOrigin(0,0);
        shieldRadius = (float) Math.sqrt(
                ((shieldSprite.getWidth()/2 / PTM) * (shieldSprite.getWidth()/2 / PTM))
                        + ((shieldSprite.getHeight()/2 / PTM) * (shieldSprite.getHeight()/2 / PTM))
        );

        }

    public void render(SpriteBatch batch) {
        if (center.x < 0 - radius) {
            center.x = screen.x + radius;
        } else if (center.y < 0 - radius) {
            center.y = screen.y + radius;
        } else if (center.x > screen.x + radius) {
            center.x = 0 - radius;
        } else if (center.y > screen.y + radius) {
            center.y = 0 - radius;
        }

        double x1 = center.x, y1 = center.y;
        double x2 = Gdx.input.getX() / PTM, y2 = screen.y - (Gdx.input.getY() / PTM);
        double angle = 90 + (Math.atan2(y1 - y2, x1 - x2) / Math.PI * 180);

        body.setTransform(
                (float) (x1 + (radius * Math.cos(Math.toRadians(angle-135)))),
                (float) (y1 + (radius * Math.sin(Math.toRadians(angle-135)))),
                (float) Math.toRadians(angle)
        );


        center.x = body.getPosition().x + (float) (radius * Math.cos(Math.toRadians(angle+45)) + body.getLinearVelocity().x);
        center.y = body.getPosition().y + (float) (radius * Math.sin(Math.toRadians(angle+45)) + body.getLinearVelocity().y);

        body.setLinearVelocity(body.getLinearVelocity().x * 0.99f, body.getLinearVelocity().y * 0.99f);

        sprite.setPosition(body.getPosition().x * PTM, body.getPosition().y * PTM);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.draw(batch);

        if (System.currentTimeMillis() < shieldTerm) {
            shieldSprite.setPosition(
                    (body.getPosition().x + (float) ((shieldRadius - radius) * Math.cos(body.getAngle() - Math.toRadians(135)))) * PTM,
                    (body.getPosition().y + (float) ((shieldRadius - radius) * Math.sin(body.getAngle() - Math.toRadians(135)))) * PTM
            );
            shieldSprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
            shieldSprite.draw(batch);
        }
    }

    public LaserEntity shoot(World world) {
        Vector2 shot = new Vector2(
                center.x + (float) (radius * Math.cos(body.getAngle() + Math.toRadians(90))),
                center.y + (float) (radius * Math.sin(body.getAngle() + Math.toRadians(90)))
        );

        LaserEntity entity = new LaserEntity(world, PTM, shot);
        entity.body.setTransform(shot.x, shot.y, body.getAngle());
        entity.body.setLinearVelocity(
                (20 * (float) Math.cos(body.getAngle() + Math.toRadians(90))),
                (20 * (float) Math.sin(body.getAngle() + Math.toRadians(90)))
        );
        sound.playSound("laser");
        return entity;
    }

    public void applyForceCenter() {

        body.setLinearVelocity(
                body.getLinearVelocity().x + (0.002f * (float) Math.cos(body.getAngle() + Math.toRadians(90))),
                body.getLinearVelocity().y + (0.002f * (float) Math.sin(body.getAngle() + Math.toRadians(90)))
        );
    }

    public void applyForceRight() {

        body.setLinearVelocity(
                body.getLinearVelocity().x + (0.002f * (float) Math.cos(body.getAngle() + Math.toRadians(0))),
                body.getLinearVelocity().y + (0.002f * (float) Math.sin(body.getAngle() + Math.toRadians(0)))
        );
    }

    public void applyForceLeft() {

        body.setLinearVelocity(
                body.getLinearVelocity().x + (0.002f * (float) Math.cos(body.getAngle() + Math.toRadians(180))),
                body.getLinearVelocity().y + (0.002f * (float) Math.sin(body.getAngle() + Math.toRadians(180)))
        );
    }

    @Override
    public void damage() {
        lives--;
        shieldTerm = System.currentTimeMillis() + 300;
        sound.playSound("block");
        if (lives < 0) {
            run = false;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        shield.dispose();
    }

    public int getLives() {
        return lives;
    }

    public boolean isRun() {
        return run;
    }
}
