package com.mygdx.game.entities.particles;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilits.Assets;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 22.12.2017.
 */

public class Light extends Particle {
    public Light(Vector2 position, Vector2 velocity) {
        super(position, velocity);
        radius = Constants.LIGHT_RADIUS;
        lifeTime = Constants.LIGHT_LIFETIME;
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA);
        batch.draw(Assets.instance.lightAssets.lihgt, position.x - radius, position.y - radius, 2*radius, 2*radius);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }
}