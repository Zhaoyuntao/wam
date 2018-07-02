package com.wam.zgame.jff.warriorandmonster.model.base2;

import android.graphics.Canvas;

import com.wam.zgame.jff.warriorandmonster.model.base.Element;
import com.wam.zgame.jff.warriorandmonster.model.base.Pose;

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
    protected final float speed = 0;

    //x轴方向基础速度,需要根据基础速度计算
    protected float speed_percent_x;
    //y轴方向基础速度,需要根据基础速度计算
    protected float speed_percent_y;



    protected boolean isFriend=false;

    //当前动作
    protected Pose pose;


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Pose pose = this.pose;
        if (pose != null) {
            int index_pic=pose.getPicIndex();

        }
    }

    @Override
    public void roll() {

        Pose pose = this.pose;
        if (pose != null) {
            pose.roll();
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
}
