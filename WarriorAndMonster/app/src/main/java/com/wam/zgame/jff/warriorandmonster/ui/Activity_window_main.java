package com.wam.zgame.jff.warriorandmonster.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.FrameLayout;


import com.wam.zgame.jff.warriorandmonster.R;
import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.controller.RoomLoader;
import com.wam.zgame.jff.warriorandmonster.model.base.Camera;
import com.wam.zgame.jff.warriorandmonster.model.base.World;
import com.wam.zgame.jff.warriorandmonster.model.expand.Player;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.T;
import com.wam.zgame.jff.warriorandmonster.model.base.Skill;

import java.util.ArrayList;
import java.util.List;

import com.wam.zgame.jff.warriorandmonster.component.FingerControl;
import com.wam.zgame.jff.warriorandmonster.component.FingerControlImpl_control;
import com.wam.zgame.jff.warriorandmonster.controller.GameCalculator;
import com.wam.zgame.jff.warriorandmonster.model.base.Room;
import com.wam.zgame.jff.warriorandmonster.component.Window_main;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class Activity_window_main extends Activity_base {
    FingerControlImpl_control fingerControl;
    Window_main window_main;
//    Window_skill window_skill;
    public GameCalculator gameCalculator;
    public Player player;
    public Room room;
    public World world;
    private T t;
    private List<Skill> list = new ArrayList<>();
    private Camera camera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.activity_num = 2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_main);
        initView();
        //初始化world
        world=new World();
        window_main.addWorld(world);
        //初始化room
        room=RoomLoader.downloadRoom(-1);
        room.setW_room(3000);
        room.setH_room(2000);
        world.addRoom(room);
        //初始化player
        player = new Player();
        player.setX(0);
        player.setY(0);
        world.addPlayer(player);
        //初始化camera
        camera=new Camera(600,400,3000,2000);
        camera.lookAt(player);
        world.addCamera(camera);

        initCalCulator();
        initCallBack();
    }

    ZBitmap[] test_pictures;
    ZBitmap[] test_pictures_goblin;

    private void test_init_pic() {
        test_pictures = new ZBitmap[24];

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(50, 50, 50, paint);

        ZBitmap zBitmap = new ZBitmap(bitmap);
        zBitmap.setW(50);
        zBitmap.setH(50);
        test_pictures[0] = zBitmap;
//        test_pictures[1] = B.getDrawableById_Percent(context, R.drawable.swordman_1, 1);
//        test_pictures[2] = B.getDrawableById_Percent(context, R.drawable.swordman_2, 1);
//        test_pictures[3] = B.getDrawableById_Percent(context, R.drawable.swordman_3, 1);
//        test_pictures[4] = B.getDrawableById_Percent(context, R.drawable.swordman_4, 1);
//        test_pictures[5] = B.getDrawableById_Percent(context, R.drawable.swordman_5, 1);
//        test_pictures[6] = B.getDrawableById_Percent(context, R.drawable.swordman_6, 1);
//        test_pictures[7] = B.getDrawableById_Percent(context, R.drawable.swordman_7, 1);
//        test_pictures[8] = B.getDrawableById_Percent(context, R.drawable.swordman_8, 1);
//        test_pictures[9] = B.getDrawableById_Percent(context, R.drawable.swordman_9, 1);
//        test_pictures[10] = B.getDrawableById_Percent(context, R.drawable.swordman_10, 1);
//        test_pictures[11] = B.getDrawableById_Percent(context, R.drawable.swordman_11, 1);
//        test_pictures[12] = B.getDrawableById_Percent(context, R.drawable.swordman_12, 1);
//        test_pictures[13] = B.getDrawableById_Percent(context, R.drawable.swordman_13, 1);
//        test_pictures[14] = B.getDrawableById_Percent(context, R.drawable.swordman_14, 1);
//        test_pictures[15] = B.getDrawableById_Percent(context, R.drawable.swordman_15, 1);
//        test_pictures[16] = B.getDrawableById_Percent(context, R.drawable.swordman_16, 1);
//        test_pictures[17] = B.getDrawableById_Percent(context, R.drawable.swordman_17, 1);
//        test_pictures[18] = B.getDrawableById_Percent(context, R.drawable.swordman_18, 1);
//        test_pictures[19] = B.getDrawableById_Percent(context, R.drawable.swordman_19, 1);
//        test_pictures[20] = B.getDrawableById_Percent(context, R.drawable.swordman_20, 1);
//        test_pictures[21] = B.getDrawableById_Percent(context, R.drawable.swordman_21, 1);
//        test_pictures[22] = B.getDrawableById_Percent(context, R.drawable.swordman_22, 1);
//        test_pictures[23] = B.getDrawableById_Percent(context, R.drawable.swordman_23, 1);
//
        test_pictures_goblin = new ZBitmap[17];
//        test_pictures_goblin[0] = B.getDrawableById_Percent(context, R.drawable.goblin_0, 1);
//        test_pictures_goblin[1] = B.getDrawableById_Percent(context, R.drawable.goblin_1, 1);
//        test_pictures_goblin[2] = B.getDrawableById_Percent(context, R.drawable.goblin_2, 1);
//        test_pictures_goblin[3] = B.getDrawableById_Percent(context, R.drawable.goblin_3, 1);
//        test_pictures_goblin[4] = B.getDrawableById_Percent(context, R.drawable.goblin_4, 1);
//        test_pictures_goblin[5] = B.getDrawableById_Percent(context, R.drawable.goblin_5, 1);
//        test_pictures_goblin[6] = B.getDrawableById_Percent(context, R.drawable.goblin_6, 1);
//        test_pictures_goblin[7] = B.getDrawableById_Percent(context, R.drawable.goblin_7, 1);
//        test_pictures_goblin[8] = B.getDrawableById_Percent(context, R.drawable.goblin_8, 1);
//        test_pictures_goblin[9] = B.getDrawableById_Percent(context, R.drawable.goblin_9, 1);
//        test_pictures_goblin[10] = B.getDrawableById_Percent(context, R.drawable.goblin_10, 1);
//        test_pictures_goblin[11] = B.getDrawableById_Percent(context, R.drawable.goblin_11, 1);
//        test_pictures_goblin[12] = B.getDrawableById_Percent(context, R.drawable.goblin_12, 1);
//        test_pictures_goblin[13] = B.getDrawableById_Percent(context, R.drawable.goblin_13, 1);
//        test_pictures_goblin[14] = B.getDrawableById_Percent(context, R.drawable.goblin_14, 1);
//        test_pictures_goblin[15] = B.getDrawableById_Percent(context, R.drawable.goblin_15, 1);
//        test_pictures_goblin[16] = B.getDrawableById_Percent(context, R.drawable.goblin_16, 1);

    }

    private void test_init_creature() {

        int[] stay_goblin = {0};
        int[] walk_goblin = {11, 12, 13, 14, 15, 16};
        int[] run_goblin = walk_goblin;
        int[] attack_goblin = {1, 2, 3, 4};
//        float x = room.w_map_bitmap / 4;
//        float y = room.h_map_bitmap / 5;
        float range_x_goblin = 140;
        float range_y_goblin = 200;
//        int i = 1;
//        for (; i > 0; i--) {
//            Creature monster1 = new Creature(Creature.identity_enemy, x, y, range_x_goblin, range_y_goblin, Creature.state_stay);
//            monster1.name = "哥布林";
//            monster1.setPose(test_pictures_goblin, stay_goblin, walk_goblin, run_goblin, attack_goblin, null);
//            monster1.setHp_all(2000);
//            monster1.speed_percent_x = 0.6f;
//            monster1.setPercent_pic(0.5f);
//            room.list.add(monster1);
//        }
//        int[] stay_swordman = {0, 1, 2, 3, 4, 5, 6, 7};
//        int[] walk_swordman = {8, 9, 10, 11, 12, 13, 14, 15};
//        int[] run_swordman = {16, 17, 18, 19, 20, 21, 22, 23};
        int[] stay_swordman = {0};
        int[] walk_swordman = {0};
        int[] run_swordman = {0};
        float range_x = 70;
        float range_y = 120;
    }
    boolean flag=true;

    private void test_init_skill() {

//        Bitmap zbitmap=Bitmap.createBitmap()

        Skill test_skill_attack = new Skill(0);
//        test_skill_attack.name = "攻击";
//        test_skill_attack.id = 0;
//        test_skill_attack.player = player;
//        test_skill_attack.bitmap_ok = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_yuangujiyi_ok, 1);
//        test_skill_attack.bitmap_no = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_yuangujiyi_no, 1);

        Skill test_skill_jumpback = new Skill(0);
//        test_skill_jumpback.name = "后跳";
//        test_skill_jumpback.id = 1;
//        test_skill_jumpback.player = player;
//        test_skill_jumpback.bitmap_ok = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_jumpback_ok, 1);
//        test_skill_jumpback.bitmap_no = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_jumpback_no, 1);

        Skill test_skill_big = new Skill(10);
//        test_skill_big.name = "变大";
//        test_skill_big.id = 2;
//        test_skill_big.player = player;
//        test_skill_big.bitmap_ok = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_buquyizhi_ok, 1);
//        test_skill_big.bitmap_no = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_buquyizhi_no, 1);

        Skill test_skill_small = new Skill(10);
//        test_skill_small.name = "变小";
//        test_skill_small.id = 3;
//        test_skill_small.player = player;
//        test_skill_small.bitmap_ok = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_buquyizhi_ok, 1);
//        test_skill_small.bitmap_no = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_buquyizhi_no, 1);

        Skill test_skill_addhp = new Skill(25000);
//        test_skill_addhp.name = "恢复hp";
//        test_skill_addhp.id = 4;
//        test_skill_addhp.player = player;
//        test_skill_addhp.bitmap_ok = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_hp_ok, 1);
//        test_skill_addhp.bitmap_no = B.getDrawableById_Percent(Activity_window_main.this, R.drawable.skill_hp_no, 1);


        list.add(test_skill_attack);
        list.add(test_skill_jumpback);
        list.add(test_skill_big);
        list.add(test_skill_small);
        list.add(test_skill_addhp);


    }

    private Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    private void initCalCulator() {
        this.gameCalculator = new GameCalculator();
    }

    private void initCallBack() {
//        gameCalculator.addObject(player);
//        gameCalculator.addObject(room);
//        for (Skill skill : list) {
//            gameCalculator.addObject(skill);
//        }
//        gameCalculator.addObject(camera);
        gameCalculator.addObject(world);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    player.setX(player.getX()+1);
                    player.setY(player.getY()+1);
                    if(player.getX()<0){
                        player.setX(0);
                    }else if(player.getX()>room.getW_room()){
                        player.setX(0);
                    }
                    if(player.getY()<0){
                        player.setY(0);
                    }else if(player.getY()>room.getH_room()){
                        player.setY(0);
                    }
//                    S.s("x:"+ player.getX()+" y:"+ player.getY());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        fingerControl.setCallBack(new FingerControl.CallBack() {
            @Override
            public void send(int x, int y) {
                S.s(" x : " + x + " y : " + y);
                if (player != null) {
//                    player.speed_percent_x = (float) x / 100 / 2;
//                    player.speed_percent_y = (float) y / 100 / 2;
                }
            }

            @Override
            public void whenTouch() {

            }
        });
//        window_skill.setCallBack(new Window_skill.CallBack() {
//            @Override
//            public void whenPress(int number) {
//                if (number >= 0 && number < list.size()) {
//                    Skill skill = list.get(number);
////                    if (skill.isOk()) {
////                        T.t(getApplication(), skill.name);
////                    }
////                    skill.go();
//                }
//
//            }
//
//            @Override
//            public Bitmap getBitmap_skill(int index) {
////                if (index >= 0 && index < list.size()) {
////                    Skill skill = list.get(index);
////                    return skill.time_rest > 0 ? skill.bitmap_no : skill.bitmap_ok;
////                }
//                return null;
//            }
//
//            @Override
//            public Skill getSkill(int index) {
//                if (index >= 0 && index < list.size()) {
//                    Skill skill = list.get(index);
//                    return skill;
//                }
//                return null;
//            }
//
//        });
    }

    private void initView() {
        fingerControl = findViewById(R.id.fingercontrol);
        window_main = findViewById(R.id.window_main);
//        window_skill = findViewById(R.id.window_skill);

        int h_window_skill = (int) (GameParams.h_visual / 2);
        int w_window_skill = h_window_skill / 3 * 2;
        FrameLayout.LayoutParams fl_window_skill = new FrameLayout.LayoutParams(w_window_skill, h_window_skill);
        fl_window_skill.gravity = Gravity.RIGHT | Gravity.BOTTOM;
//        window_skill.setLayoutParams(fl_window_skill);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag=false;
        fingerControl.close();
        window_main.close();
    }
}
