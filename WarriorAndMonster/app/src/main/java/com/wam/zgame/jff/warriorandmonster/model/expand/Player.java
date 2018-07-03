package com.wam.zgame.jff.warriorandmonster.model.expand;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.wam.zgame.jff.warriorandmonster.controller.GameColor;
import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;
import com.wam.zgame.jff.warriorandmonster.tools.B;
import com.wam.zgame.jff.warriorandmonster.tools.GapSizeManager;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.SizeManager;
import com.wam.zgame.jff.warriorandmonster.tools.TextSizeManager;

/**
 * Created by zhaoyuntao on 2018/5/17.
 */

public class Player extends Creature {

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        S.s("player draw --------");
        Paint p=new Paint();
        //绘制名称------------------------
        String content = "Lv." + level + " " + name;
        float textsize = TextSizeManager.getTextSize_name();
        //计算名称总长度和宽度
        float[] wh = B.getTextWH(content, textsize);

        //名称宽度
        float w_text = 0;
        //名称高度
        float h_text = 0;
        float w_gap = GapSizeManager.getGapSize_name_body();
        if (wh != null && wh.length >= 2) {
            w_text = wh[0];
            h_text = wh[1];
        }

        //绘制名称(带描边的文字)
        //字体为白色
        p.setColor(Color.WHITE);
        //计算绘制x坐标=实体左边界
        float x_text_draw = left;
        //y坐标=顶部绘制高度-字体高度-间隙宽度
        float y_text_draw = top;
        p.setTextSize(textsize);
        p.setStyle(Paint.Style.FILL);
        canvas.drawText(content, x_text_draw, y_text_draw, p);
        p.setColor(Color.BLACK);
        p.setTextSize(textsize);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1);
        canvas.drawText(content, x_text_draw, y_text_draw, p);

        //绘制hp------------------------
        //hp总长度
        float w_hp = SizeManager.getW_hp();
        //hp高度
        float h_hp = SizeManager.getH_hp();
        //计算圆角半径
        float radius = h_hp / 2;
        //hp绘制坐标x=body实体左边界
        float x_hp = left;
        //hp绘制坐标y=名称绘制高度-名称高度-hp高度
        float y_hp = y_text_draw - h_text - h_hp;

        //hp槽绘制范围
        RectF rect_hp_all_draw = new RectF();
        //hp剩余量绘制范围
        RectF rect_hp_rest_draw = new RectF();
        //hp剩余量的绘制长度=剩余量/总量*hp绘制长度.
        float length_hp_rest_draw = (float) getHp_rest() / getHp_all() * w_hp;
        //设置hp槽绘制范围
        rect_hp_all_draw.set(x_hp, y_hp, x_hp + w_hp, y_hp + h_hp);
        //hp剩余量绘制范围左边界=hp槽左边界
        float left_hp_rest_draw = x_hp;
        //hp剩余量绘制范围右边界=hp槽左边界+剩余量长度
        float right_hp_rest_draw = left_hp_rest_draw + length_hp_rest_draw;
        //hp剩余量绘制范围上边界=hp绘制y坐标
        float top_hp_rest_draw = y_hp;
        //hp剩余量绘制范围下边界=hp上边界+hp高度
        float bottom_hp_rest_draw = top_hp_rest_draw + h_hp;
        //设置hp剩余量绘制范围
        rect_hp_rest_draw.set(left_hp_rest_draw, top_hp_rest_draw, right_hp_rest_draw, bottom_hp_rest_draw);
        /**
         * 参数说明:
         * 前面四个参数分别为渐变开始点的xy坐标和渐变结束点的xy坐标
         * 数组为渐变颜色
         * 渐变颜色奇点位置，如果为空，颜色均匀分布
         * 模式，这里为循环渐变
         */
        Shader shader_rest = new LinearGradient(x_hp, y_hp, x_hp, y_hp + h_hp, new int[]{Color.BLACK, GameColor.color_hp_rest, Color.BLACK}, null, Shader.TileMode.REPEAT);
        Shader shader_all = new LinearGradient(x_hp, y_hp, x_hp, y_hp + h_hp, new int[]{Color.BLACK, GameColor.color_hp_all, Color.BLACK}, null, Shader.TileMode.REPEAT);
        //绘制hp槽
        p.setStyle(Paint.Style.FILL);
        p.setColor(GameColor.color_hp_all);
        p.setShader(shader_all);
        canvas.drawRoundRect(rect_hp_all_draw, radius, radius, p);
        //绘制hp剩余
        p.setColor(GameColor.color_hp_rest);
        p.setShader(shader_rest);
        canvas.drawRoundRect(rect_hp_rest_draw, radius, radius, p);
        //绘制hp边框-------------------
        //hp边框宽度
        p.setShader(null);
        float w_border_hp = SizeManager.getW_border_hp();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(w_border_hp);
        canvas.drawRoundRect(rect_hp_all_draw, radius, radius, p);
    }

    @Override
    public void roll() {
        super.roll();

    }
}
