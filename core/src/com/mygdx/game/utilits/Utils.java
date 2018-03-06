package com.mygdx.game.utilits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Asus123 on 16.12.2017.
 */

public class Utils {

    public static float timeElapsed(long startTime) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
    }

    public static Vector2 randomVector(float bounds){
        return Utils.randomVector(-bounds, bounds);
    }

    public static Vector2 randomVector(float leftBound, float rightBound){
        Vector2 vector = new Vector2(MathUtils.random(leftBound, rightBound),
                MathUtils.random(leftBound, rightBound));
        return vector;
    }

    public static float pow2(float num){
        return pow(num, 2);
    }

    public static float pow(float num, int power){
        float number = num;
        for (int i = 0; i < power - 1; i++) {
            number *= num;
        }
        return number;
    }

    public static Color randomColor(){
        switch (MathUtils.random(0, 5)){
            case 0:
                return Color.FIREBRICK;
            case 1:
                return Color.DARK_GRAY;
            case 2:
                return Color.FOREST;
            case 3:
                return Color.TEAL;
            case 4:
                return Color.ROYAL;
            case 5:
                return Color.BROWN;
        }
        return null;
    }
}
