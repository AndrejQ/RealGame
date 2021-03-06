package com.mygdx.game.entities.particles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 19.12.2017.
 */

public abstract class Particle extends GameObject {

    protected float lifeTime;

    // Enemy with explosion (particles)
    private GameObject mHostObject;

    public Particle(Particle particle){
        this(particle.position, particle.velocity, particle.level);
    }

    public Particle(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        this.startTime = TimeUtils.nanoTime();
    }

    public Particle(GameObject hostObject,Vector2 position, Vector2 velocity, Level level){
        this(position, velocity, level);
        this.mHostObject = hostObject;
    }

    public boolean isTimeElapsed(){
//        return Utils.timeElapsed(startTime) > lifeTime || Utils.outOfScreen(position, level.gg.position, 0);
        return Utils.timeElapsed(startTime) > lifeTime || Utils.outOfScreen(position, level.getInstantCameraPosition(), 0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // explosions and particles move with host enemy
        if (mHostObject != null) {
            position.mulAdd(mHostObject.velocity, delta);
        }
    }

    public Particle setLifeTime(float lifeTime) {
        this.lifeTime = lifeTime;
        return this;
    }

    public float getLifeTime() {
        return lifeTime;
    }
}
