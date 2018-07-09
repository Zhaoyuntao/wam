package com.wam.zgame.jff.warriorandmonster.model.base;

import com.wam.zgame.jff.warriorandmonster.tools.S;

/**
 * Created by zhaoyuntao on 2018/5/18.
 * <p>
 * 一个动作的帧集合,包含该动作开始到结束的完整过程
 */

public abstract class Pose extends Element {

    //执行上一帧的时间戳
    private long time_last;

    //每一帧所对应的贴图下标集合
    private int[] index_pic;
    //当前为第几帧
    private int index_now;
    //上一帧的下标
    private int index_last;
    //重复次数,如果为-1则为无限重复
    private int repeat = 1;
    //剩余次数
    private int repeat_count_rest = 1;

    private boolean breakDown;

    public Pose(int[] index_pic, int repeat) {
        this.index_pic = index_pic;
        this.repeat = repeat;
        this.repeat_count_rest = repeat;
    }

    public Pose(int[] index_pic, int repeat, String name) {
        this(index_pic, repeat);
        setName(name);
    }

    /**
     * 获取当前帧动作的执行概率, 例如随着距离而改变, 或者随着狂暴值而改变
     *
     * @return
     */
    protected abstract float getPosibility();

    /**
     * 获取帧间隔
     *
     * @return 返回帧间隔
     */
    protected abstract long getDuring_action();

    protected void breakDown() {
        breakDown = true;
    }

    protected void reset() {
        this.breakDown = false;
        this.time_last = 0;
        this.index_now = 0;
        this.index_last = 0;
        this.repeat_count_rest = repeat;
    }

    protected boolean isBroken() {
        return breakDown;
    }

    @Override
    public void roll() {
        super.roll();
        //如果动作帧数目小于等于0,则不执行任何操作------
        S.s("剩余次数:"+repeat_count_rest);
        if (index_pic == null || repeat_count_rest <= 0) {
            return;
        }
        if (breakDown) {
            return;
        }

        int index_all = index_pic.length;
        //获取时间
        long time_now = ct();

        //获取上次循环的时间
        if (this.time_last == 0) {
            this.time_last = time_now;
        }
        long time_last = this.time_last;
        //获得技能帧间隔
        long during_action = getDuring_action();
        //计算这次循环距离上次的时间差
        long time_during = time_now - time_last;
        //判断本次是否满足改变下标的条件 : 如果上次改变动作帧下标的时间已经超过帧间隔
        if (time_during >= during_action) {
            //计算这段时间间隔内走过了多少帧
            int indexs = (int) (time_during / during_action);
            //获取上次的index下标
            int index_last = this.index_last;
            //取余,循环帧数,
            int index_now = (index_last + indexs) % index_all;
            //如果执行到队列尾部, 且无剩余重复次数, 或者强制中断时
            if (repeat != -1 && index_now >= index_pic.length - 1 ) {
                repeat_count_rest--;
                if(repeat_count_rest<=0){
                    breakDown = true;
                }
            } else {
                //记录改变本次帧下标的时间+=帧数*帧间隔
                time_last += (indexs * during_action);
                index_last = index_now;
                this.time_last = time_last;
                this.index_now = index_now;
                this.index_last = index_last;
            }
        }
    }


    /**
     * 获取贴图下标
     *
     * @return
     */
    public int getPicIndex() {
        //如果当前下标小于最大下标
        if (index_pic != null && index_pic.length > index_now) {
            return index_pic[index_now];
        } else {
            return 0;
        }
    }

    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public interface CallBack {
        void broken();
    }
}
