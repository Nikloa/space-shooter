package com.mygdx.shooter.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.shooter.SpaceShooter;
import com.mygdx.shooter.controller.GameManager;
import com.mygdx.shooter.controller.ListenerClass;

public class GameScreen implements Screen {

    final SpaceShooter game;
    final float width;
    final float height;
    World world;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    GameManager manager;

    OrthographicCamera camera;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    SpriteBatch batch;
    BitmapFont font;
    GlyphLayout layout;

    public GameScreen(final SpaceShooter shooter) {
        this.game = shooter;
        batch = game.getBatch();
        font = game.getFont();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.update();

        tiledMap = new TmxMapLoader().load("background.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Math.max(width, height) / 1024);

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new ListenerClass());
        debugRenderer = new Box2DDebugRenderer();

        manager = new GameManager(world);

        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        if (manager.isRun()) {
            ScreenUtils.clear(0, 0, 0, 1);

            world.step(1/60f, 6, 2);

            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();



            batch.setProjectionMatrix(camera.combined);

            debugMatrix = batch.getProjectionMatrix().cpy().scale(manager.getPTM(), manager.getPTM(), 0);

            batch.begin();

            layout.setText(font, "Score: " + manager.getScore(), Color.WHITE, width, Align.topLeft, true);
            font.draw(batch, layout, width / 64, height - (height/64));
            layout.setText(font, "Lives: " + manager.getLives(), Color.WHITE, width, Align.topRight, true);
            font.draw(batch, layout, 0 - width / 64, height - (height/64));

            manager.draw(batch);
            batch.end();

            manager.calculate();

            if (manager.isDebug())
                debugRenderer.render(world, debugMatrix);
        } else {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        world.dispose();
        manager.dispose();
    }

}
