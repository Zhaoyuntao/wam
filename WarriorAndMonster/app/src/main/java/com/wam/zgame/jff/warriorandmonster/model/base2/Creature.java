package com.wam.zgame.jff.warriorandmonster.model.base2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wam.zgame.jff.warriorandmonster.model.base.Element;
import com.wam.zgame.jff.warriorandmonster.model.base.Pose;
import com.wam.zgame.jff.warriorandmonster.model.base.Room;
import com.wam.zgame.jff.warriorandmonster.model.base.World;
import com.wam.zgame.jff.warriorandmonster.tools.S;

import java.util.List;
import java.util.Random;

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
    protected float speed_x = 200;
    protected float speed_y = 100;


    protected boolean move_x;
    protected boolean move_y;


    //x轴方向基础速度,需要根据基础速度计算
    protected float speed_percent_x = 1;
    //y轴方向基础速度,需要根据基础速度计算
    protected float speed_percent_y = 1;

    protected World world;

    protected boolean isFriend = false;

    //当前动作
    protected Pose pose;
    private boolean touchWall_x;
    private boolean touchWall_y;

    private boolean lockControl;

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void onChangeSize(float w, float h) {

    }

    public void setWorld(World world) {
        this.world = world;
    }

    public synchronized void lockControl() {
        lockControl = true;
    }

    public synchronized void releaseControl() {
        lockControl = false;
    }

    private int test_time_random_run = 0;

    /**
     * 本地测试时, 自动随机产生一个机器人位置,以模拟机器人移动
     */
    public void randomMove() {
        if ((!lockControl) && (test_time_random_run-- <= 0 || (touchWall_x && touchWall_y))) {
            touchWall_y = false;
            touchWall_x = false;
            test_time_random_run = new Random().nextInt(200);
            int x_direction = new Random().nextInt(3) - 1;
            int y_direction = new Random().nextInt(3) - 1;
            boolean move_x = new Random().nextInt(2) == 1;
            boolean move_y = new Random().nextInt(2) == 1;
            this.x_direction = x_direction;
            this.y_direction = y_direction;
            this.move_x = move_x;
            this.move_y = move_y;
        }
    }

    @Override
    public void roll() {
        super.roll();
        //随机移动
        randomMove();
        if (world != null) {
            Room room = world.getRoom();

            if (room != null) {
                float left_floor = room.getX_floor();
                float top_floor = room.getY_floor();
                float w_floor = room.getW_floor();
                float h_floor = room.getH_floor();
                float right_room = room.getX_floor() + w_floor;
                float bottom_room = room.getY_floor() + h_floor;
                float x_tmp = 0;
                float y_tmp = 0;
                float time_during_second = (float) ((double) time_during / 1000);
                if (move_x) {
                    x_tmp = x + speed_percent_x * speed_x * time_during_second * x_direction;
                    if (x_tmp < left_floor) {
                        x_tmp = left_floor;
                        touchWall_x = true;
                    } else if (x_tmp > right_room) {
                        x_tmp = right_room;
                        touchWall_x = true;
                    }
                    x = x_tmp;
                }
                if (move_y) {
                    y_tmp = y + speed_percent_y * speed_y * time_during_second * y_direction;
                    if (y_tmp < top_floor) {
                        y_tmp = top_floor;
                        touchWall_y = true;
                    } else if (y_tmp > bottom_room) {
                        y_tmp = bottom_room;
                        touchWall_y = true;
                    }
                    y = y_tmp;
                }


            }
        }
        super.rollEnd();
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

    public boolean isMove_x() {
        return move_x;
    }

    public void setMove_x(boolean move_x) {
        this.move_x = move_x;
    }

    public boolean isMove_y() {
        return move_y;
    }

    public void setMove_y(boolean move_y) {
        this.move_y = move_y;
    }
}
