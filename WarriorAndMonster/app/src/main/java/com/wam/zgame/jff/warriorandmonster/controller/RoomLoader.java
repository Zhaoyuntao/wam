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

    private static int w = 1800;
    private static int h = 1600;
    private static float h_floor = 1400;
    private static float h_wall = 200;

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
        room.setW_room(w);
        room.setH_room(h);


        //地板
        room.setX_floor(0);
        room.setY_floor(h_wall);
        room.setW_floor(w);
        room.setH_floor(h_floor);
        room.setZbitmap_floor(createBitmap_floor(Color.parseColor("#000000")));
        //墙壁
        room.setX_wall(0);
        room.setY_wall(0);
        room.setW_wall(w);
        room.setH_wall(h_wall);
        room.setZbitmap_wall(createBitmap_wall(Color.parseColor("#111111")));
        //加载门
//        Door door=new Door();
//        door.setOpen(false);
//        door.setX(room.getW_room());
//        door.setY(room.getH_floor()/2f);
        return room;
    }

    /**
     * 模拟创建一张完整的图
     *
     * @return
     */
    public static ZBitmap createBitmap_floor(int color) {
        ZBitmap zbitmap_floor = new ZBitmap();
        //构建整个地图
        int w_room = w;
        int h_room = h;
        //构建bitmap
        Bitmap bitmap = Bitmap.createBitmap(w_room, h_room, Bitmap.Config.ARGB_8888);

        //绘制前准备
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //绘制背景色:黑色
        canvas.drawColor(color);
        //计算网格宽度
        int w_index = 200;
        //设置画笔颜色
        paint.setColor(Color.WHITE);
        //设置线宽
        paint.setStrokeWidth(3);
        //设置抗锯齿
        paint.setAntiAlias(true);
        paint.setTextSize(30);

        //绘制网格线
        for (int x = 0; x <= w_room; x += w_index) {
            for (int y = 0; y <= h_room; y += w_index) {
                //绘制竖线
                canvas.drawLine(x, 0, x, h_room, paint);
                //绘制横线
                canvas.drawLine(0, y, w_room, y, paint);
                String text = "(" + x + "," + y + ")";
                float textsize = 20;
                float[] size = TextMeasure.measure(text, textsize);
                //绘制坐标
                canvas.drawText(text, x, (y - size[1]), paint);
            }
        }
        zbitmap_floor.setBitmap(bitmap);
        return zbitmap_floor;
    }
    /**
     * 模拟创建一张完整的图
     *
     * @return
     */
    public static ZBitmap createBitmap_wall(int color) {
        ZBitmap zbitmap_floor = new ZBitmap();
        //构建整个图
        int w_room = w;
        int h_room = h;
        //构建bitmap
        Bitmap bitmap = Bitmap.createBitmap(w_room, h_room, Bitmap.Config.ARGB_8888);

        //绘制前准备
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //绘制背景色:黑色
        canvas.drawColor(color);
        //计算网格宽度
        int w_index = 200;
        //设置画笔颜色
        paint.setColor(Color.WHITE);
        //设置线宽
        paint.setStrokeWidth(3);
        //设置抗锯齿
        paint.setAntiAlias(true);
        paint.setTextSize(30);

        //绘制网格线
        for (int x = 0; x <= w_room; x += w_index) {
            for (int y = 0; y <= h_room; y += w_index) {
                //绘制竖线
                canvas.drawLine(x, 0, x, h_room, paint);
//                String text = "(" + x + "," + y + ")";
//                float textsize = 20;
//                float[] size = TextMeasure.measure(text, textsize);
//                //绘制坐标
//                canvas.drawText(text, x, (y - size[1]), paint);
            }
        }
        zbitmap_floor.setBitmap(bitmap);
        return zbitmap_floor;
    }
}
