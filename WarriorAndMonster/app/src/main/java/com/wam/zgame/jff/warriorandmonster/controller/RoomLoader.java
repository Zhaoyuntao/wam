package com.wam.zgame.jff.warriorandmonster.controller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wam.zgame.jff.warriorandmonster.model.base.Door;
import com.wam.zgame.jff.warriorandmonster.model.base.Room;
import com.wam.zgame.jff.warriorandmonster.tools.TextMeasure;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public class RoomLoader {

    /**
     * 根据一个id去获取一个地图
     *
     * @param id
     * @return
     */
    public static Room downloadRoom(int id) {

        //生成房间
        Room room = new Room(Room.DUNGEONS);
        room.setId(id);
//        room.setH_floor(1500);
//        room.setH_wall(500);
        room.setW_room(3000);
        room.setH_room(2000);
        room.setZbitmap_floor(createBitmap());
//        room.setZbitmap_wall(createBitmap());
        //加载门
        Door door=new Door();
        door.setOpen(false);
        door.setX(room.getW_room());
        door.setY(room.getH_floor()/2f);
        return room;
    }

    /**
     * 模拟创建一张完整的图
     *
     * @return
     */
    public static ZBitmap createBitmap() {
        ZBitmap zbitmap_floor = new ZBitmap();
        //构建整个地图
        int w_room = 3000;
        int h_room = 2000;
        //构建bitmap
        Bitmap bitmap = Bitmap.createBitmap(w_room, h_room, Bitmap.Config.ARGB_8888);
        zbitmap_floor.setBitmap(bitmap);
        //绘制前准备
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //绘制背景色:黑色
        canvas.drawColor(Color.parseColor("#000000"));
        //计算网格宽度
        int w_index =500;
        //设置画笔颜色
        paint.setColor(Color.WHITE);
        //设置线宽
        paint.setStrokeWidth(3);
        //设置抗锯齿
        paint.setAntiAlias(true);
        paint.setTextSize(30);

        //绘制网格线
        for (int i = 0; i <= w_room; i += w_index) {
            for (int j = 0; j <= h_room; j += w_index) {
                //绘制竖线
                canvas.drawLine(i, 0, i, h_room, paint);
                //绘制横线
                canvas.drawLine(0, j, w_room, j, paint);
                String text="(" + i + "," + j + ")";
                float textsize=20;
                float[] size = TextMeasure.measure(text, textsize);
                //绘制坐标
                canvas.drawText(text, i, (j - size[1]), paint);
            }
        }
        return zbitmap_floor;
    }
}
