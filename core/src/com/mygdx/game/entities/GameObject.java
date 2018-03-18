package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.particles.Particle;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 15.12.2017.
 */

public abstract class GameObject {

    float mass;
    protected long startTime;
    public Vector2 position;
    public Vector2 velocity;
    public Level level;

    public float radius;

    public GameObject(Vector2 position, Vector2 velocity, Level level) {
        this.position = position;
        this.velocity = velocity;
        this.level = level;
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta * 60 * delta);

//        //Bounds collision
//        if (position.x < Constants.WORLD_BOUNDS.x + radius) {
//            this.reflect_left();
//        } else if (position.x > Constants.WORLD_BOUNDS.x + Constants.WORLD_BOUNDS.width - radius) {
//            this.reflect_right();
//        } else if (position.y < Constants.WORLD_BOUNDS.y + radius) {
//            this.reflect_down();
//        } else if (position.y > Constants.WORLD_BOUNDS.y + Constants.WORLD_BOUNDS.height - radius) {
//            this.reflect_up();
//        }
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer){
        //TODO: Don't forget to change ShapeRenderer to SpriteBatch
        renderer.setColor(Color.GRAY);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, radius, 20);
    }

    public boolean collideWithWalls(){
        return Constants.WORLD_BOUNDS.contains(new Circle(position, radius));
    }


    public void collideWithObject(GameObject object){
        // check if overlapping
        if (position.dst2(object.position) >= Utils.pow2(radius + object.radius)
                || this.equals(object)) {return;}

        Vector2 position1 = position.cpy();
        Vector2 position2 = object.position.cpy();
        Vector2 axisDirection = position2.cpy().mulAdd(position1, -1).nor(); // from this to enemy

        float vel1 = velocity.dot(axisDirection);
        float vel2 = object.velocity.dot(axisDirection);
        float mass1 = mass;
        float mass2 = object.mass;

        // overlapped area length
        float deltaL =  radius + object.radius - position1.dst(position2);
        object.position.mulAdd(axisDirection, deltaL * 2);
        position.mulAdd(axisDirection, deltaL * -2);

        // annul axisDirection velocity projection
        velocity.mulAdd(axisDirection, 1 * vel1);
        object.velocity.mulAdd(axisDirection, -1 * vel2);

        // TODO: 14.02.2018 (success) solve the mystery of this magic crutch!
        if (velocity.len2() > 50) velocity = new Vector2();

        // conservation of pulse
        velocity.mulAdd(axisDirection,
                ((mass1 - mass2) * vel1 + 2 * mass2 * vel2) / (mass1 + mass2));

        object.velocity.mulAdd(axisDirection,
                ((mass2 - mass1) * vel2 + 2 * mass1 * vel1) / (mass1 + mass2));
    }


    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    protected void sparkleTrace(Particle[] particles){
        for (Particle particle : particles) {
            level.particleManager.particles.add(particle);
        }
    }

    // TODO: change reflections. They are glitchy
    public void reflect_up(){
        velocity.y -= 2 * Math.abs(velocity.y);
    }
    public void reflect_down(){
        velocity.y += 2 * Math.abs(velocity.y);
    }
    public void reflect_left(){velocity.x += 2 * Math.abs(velocity.x);}
    public void reflect_right(){
        velocity.x -= 2 * Math.abs(velocity.x);
    }
}
