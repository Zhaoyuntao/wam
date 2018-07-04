package com.wam.zgame.jff.warriorandmonster.model.base2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wam.zgame.jff.warriorandmonster.model.base.Element;
import com.wam.zgame.jff.warriorandmonster.model.base.Pose;
import com.wam.zgame.jff.warriorandmonster.model.base.Room;
import com.wam.zgame.jff.warriorandmonster.model.base.World;
import com.wam.zgame.jff.warriorandmonster.tools.S;

/**
 * Created by zhaoyuntao on 2018/6/6.
 */

public class Creature extends Element {
    //名称
    protected String name;
    //等級
    protected int level = 1;
    //力量
    protected int value_power;
    //智力
    protected int value_intelligence;
    //体力
    protected int value_body;
    //精神
    protected int value_spirit;

    //基础hp
    protected float hp_basic = 1;

    //仇恨
    protected int id_hatred;

    //基础速度
    protected float speed_x = 5;
    protected float speed_y = 5;

    //方向
    protected int x_direction = 0;
    protected int y_direction = 0;

    //x轴方向基础速度,需要根据基础速度计算
    protected float speed_percent_x = 1;
    //y轴方向基础速度,需要根据基础速度计算
    protected float speed_percent_y = 1;

    protected World world;

    protected boolean isFriend = false;

    //当前动作
    protected Pose pose;

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void setWorld(World world) {
        this.world=world;
    }

    @Override
    public void roll() {
        if(world!=null){
            Room room = world.getRoom();
            float w_room = room.getW_room();
            float h_room = room.getH_room();
            float x_tmp = 0;
            float y_tmp = 0;
            direction = x_direction;
            x_tmp = x + speed_percent_x * speed_x * x_direction;
            y_tmp = y + speed_percent_y * speed_y * y_direction;
            if (x_tmp < 0) {
                x_tmp = 0;
            } else if (x_tmp > w_room) {
                x_tmp = w_room;
            }
            if (y_tmp < 0) {
                y_tmp = 0;
            } else if (y_tmp > h_room) {
                y_tmp = h_room;
            }
            x = x_tmp;
            y = y_tmp;
        }
    }


    //hp相关
    protected float hp_rest_percent = 1;

    /**
     * 获取余量
     *
     * @return
     */
    public float getHp_rest() {
        float hp_all = getHp_all();
        return hp_all * hp_rest_percent;
    }

    /**
     * 获取总量
     *
     * @return
     */
    public float getHp_all() {
        float hp_all = hp_basic * level * 100 + value_body * 10;
        return hp_all;
    }

    /**
     * 给定hp矢量值,增加或者减少
     *
     * @param value_hp
     */
    public void hp(float value_hp) {
        //获取总量
        float hp_all = getHp_all();
        //计算当前余量
        float hp_rest = getHp_rest();
        //计算剩余hp的值
        float tmp_hp_rest = hp_rest + value_hp;
        //如果hp超出容量,则置为100%
        if (tmp_hp_rest > hp_all) {
            hp_rest_percent = 1;
        } else if (tmp_hp_rest < 0) {
            //如果hp降到0以下
            hp_rest_percent = 0;
        } else {
            //如果hp低于容量,则正常计算
            hp_rest_percent = tmp_hp_rest / hp_all;
        }
    }

    /**
     * 依照百分比给定hp矢量值
     *
     * @param percent
     */
    public void hp_percent(float percent) {
        //获取总量
        float hp_all = getHp_all();
        hp(hp_all * percent);
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public float getSpeed_x() {
        return speed_x;
    }

    public void setSpeed_x(float speed_x) {
        this.speed_x = speed_x;
    }

    public float getSpeed_y() {
        return speed_y;
    }

    public void setSpeed_y(float speed_y) {
        this.speed_y = speed_y;
    }

    public int getX_direction() {
        return x_direction;
    }

    public void setX_direction(int x_direction) {
        this.x_direction = x_direction;
    }

    public int getY_direction() {
        return y_direction;
    }

    public void setY_direction(int y_direction) {
        this.y_direction = y_direction;
    }

    public float getSpeed_percent_x() {
        return speed_percent_x;
    }

    public void setSpeed_percent_x(float speed_percent_x) {
        this.speed_percent_x = speed_percent_x;
    }

    public float getSpeed_percent_y() {
        return speed_percent_y;
    }

    public void setSpeed_percent_y(float speed_percent_y) {
        this.speed_percent_y = speed_percent_y;
    }
}
