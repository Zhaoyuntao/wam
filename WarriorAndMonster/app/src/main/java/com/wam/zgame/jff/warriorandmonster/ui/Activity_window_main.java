package com.wam.zgame.jff.warriorandmonster.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wam.zgame.jff.warriorandmonster.R;
import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.controller.RoomLoader;
import com.wam.zgame.jff.warriorandmonster.model.base.Camera;
import com.wam.zgame.jff.warriorandmonster.model.base.Pose;
import com.wam.zgame.jff.warriorandmonster.model.base.World;
import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;
import com.wam.zgame.jff.warriorandmonster.model.expand.Player;
import com.wam.zgame.jff.warriorandmonster.tools.B;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.model.base.Skill;

import java.util.ArrayList;
import java.util.List;

import com.wam.zgame.jff.warriorandmonster.component.FingerControlImpl;
import com.wam.zgame.jff.warriorandmonster.model.base.Room;
import com.wam.zgame.jff.warriorandmonster.component.Window_main;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class Activity_window_main extends Activity_base {
    FingerControlImpl fingerControl;
    Window_main window_main;

    public Player player;
    public World world;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.activity_num = 2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_main);
        initView();
        initCallBack();
        //初始化world
        world = new World();
        //初始化room
        Room room = RoomLoader.downloadRoom(-1);
//        room.setZbitmap_floor(new ZBitmap(new ZBitmap(B.getBitmapById(Activity_window_main.this,R.drawable.town)));
        world.addRoom(room);
        //初始化player
        player = new Player();
        player.setX(200);
        player.setY(300);
        float w = 40f;

        test_init_pic(Activity_window_main.this);
        player.setRange_x_back_pic(w);
        player.setRange_x_forward_pic(w);
        player.setRange_y_top_pic(w);
        player.setRange_y_bottom_pic(w);
        player.setRange_x_back_real(w);
        player.setRange_x_forward_real(w);
        player.setRange_y_top_real(w);
        player.setRange_y_bottom_real(w);
        player.setPictures(test_pictures);
        player.addPose(new Pose(new int[]{0, 1, 2, 3, 4, 5, 6, 7,8}, 1,"walk") {
            @Override
            protected float getPosibility() {
                return 1;
            }

            @Override
            protected long getDuring_action() {
                return 50;
            }

            @Override
            public void onChangeSize(float w, float h) {

            }
        });
        player.addPose(new Pose(new int[]{16, 17, 18, 19, 20, 21, 22, 23},  1,"run") {
            @Override
            protected float getPosibility() {
                if(player!=null&&player.isLockControl()){
                    return -1;
                }
                return 10;
            }

            @Override
            protected long getDuring_action() {
                return 50;
            }

            @Override
            public void onChangeSize(float w, float h) {

            }
        });
        world.addPlayer(player);
        //初始化camera
        float w_camera = 500;
        float h_camera = 400;
        Camera camera = new Camera();
        camera.setSize(w_camera, h_camera);
        camera.lookAt(player);
        //添加相机
        world.addCamera(camera);
        world.setCallBack(new World.CallBack() {
            @Override
            public void onDraw(Bitmap bitmap) {
                window_main.flush(bitmap);
                GameParams.fps_draw = "FPS " + world.getFps_draw();
                GameParams.fps_cal = "FPS " + world.getFps_cal();
            }
        });
        world.start();
    }

    private void initView() {
        fingerControl = findViewById(R.id.fingercontrol);
        window_main = findViewById(R.id.window_main);
    }

    private void initCallBack() {

        fingerControl.setCallBack(new FingerControlImpl.CallBack() {
            @Override
            public void whenPress() {
                if (player != null) {
                    player.lockControl();
                }
            }

            @Override
            public void send(int x, int y) {
                if (player != null) {
                    player.setMove_x(x != 0);
                    player.setMove_y(y != 0);
                    if (x > 0) {
                        player.setX_direction(1);
                    } else if (x < 0) {
                        player.setX_direction(-1);
                    }
                    if (y > 0) {
                        player.setY_direction(1);
                    } else if (y < 0) {
                        player.setY_direction(-1);
                    }
                }
            }

            @Override
            public void whenUp() {
                if (player != null) {
                    player.releaseControl();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (world != null) {
            world.pauseDraw();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (world != null) {
            world.resumeDraw();
            world.onChangeSize(window_main.getWidth(), window_main.getHeight());
        }
    }

    @Override
    protected void onDestroy() {
        if (world != null) {
            world.destroy();
        }
        super.onDestroy();
    }


    ZBitmap[] test_pictures;
    ZBitmap[] test_pictures_goblin;

    private void test_init_pic(Context context) {
        test_pictures = new ZBitmap[24];

        test_pictures[0] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_0));
        test_pictures[1] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_1));
        test_pictures[2] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_2));
        test_pictures[3] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_3));
        test_pictures[4] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_4));
        test_pictures[5] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_5));
        test_pictures[6] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_6));
        test_pictures[7] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_7));
        test_pictures[8] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_8));
        test_pictures[9] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_9));
        test_pictures[10] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_10));
        test_pictures[11] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_11));
        test_pictures[12] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_12));
        test_pictures[13] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_13));
        test_pictures[14] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_14));
        test_pictures[15] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_15));
        test_pictures[16] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_16));
        test_pictures[17] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_17));
        test_pictures[18] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_18));
        test_pictures[19] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_19));
        test_pictures[20] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_20));
        test_pictures[21] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_21));
        test_pictures[22] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_22));
        test_pictures[23] = new ZBitmap(B.getBitmapById(context, R.drawable.swordman_23));
//
        test_pictures_goblin = new ZBitmap[17];
        test_pictures_goblin[0] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_0));
        test_pictures_goblin[1] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_1));
        test_pictures_goblin[2] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_2));
        test_pictures_goblin[3] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_3));
        test_pictures_goblin[4] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_4));
        test_pictures_goblin[5] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_5));
        test_pictures_goblin[6] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_6));
        test_pictures_goblin[7] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_7));
        test_pictures_goblin[8] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_8));
        test_pictures_goblin[9] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_9));
        test_pictures_goblin[10] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_10));
        test_pictures_goblin[11] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_11));
        test_pictures_goblin[12] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_12));
        test_pictures_goblin[13] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_13));
        test_pictures_goblin[14] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_14));
        test_pictures_goblin[15] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_15));
        test_pictures_goblin[16] = new ZBitmap(B.getBitmapById(context, R.drawable.goblin_16));

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


    private void test_init_skill() {

//        Bitmap zbitmap=Bitmap.createBitmap()

        Skill test_skill_attack = new Skill(0);
//        test_skill_attack.name = "攻击";
//        test_skill_attack.id = 0;
//        test_skill_attack.player = player;
//        test_skill_attack.bitmap_ok = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_yuangujiyi_ok);
//        test_skill_attack.bitmap_no = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_yuangujiyi_no);

        Skill test_skill_jumpback = new Skill(0);
//        test_skill_jumpback.name = "后跳";
//        test_skill_jumpback.id = 1;
//        test_skill_jumpback.player = player;
//        test_skill_jumpback.bitmap_ok = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_jumpback_ok);
//        test_skill_jumpback.bitmap_no = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_jumpback_no);

        Skill test_skill_big = new Skill(10);
//        test_skill_big.name = "变大";
//        test_skill_big.id = 2;
//        test_skill_big.player = player;
//        test_skill_big.bitmap_ok = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_buquyizhi_ok);
//        test_skill_big.bitmap_no = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_buquyizhi_no);

        Skill test_skill_small = new Skill(10);
//        test_skill_small.name = "变小";
//        test_skill_small.id = 3;
//        test_skill_small.player = player;
//        test_skill_small.bitmap_ok = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_buquyizhi_ok);
//        test_skill_small.bitmap_no = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_buquyizhi_no);

        Skill test_skill_addhp = new Skill(25000);
//        test_skill_addhp.name = "恢复hp";
//        test_skill_addhp.id = 4;
//        test_skill_addhp.player = player;
//        test_skill_addhp.bitmap_ok = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_hp_ok);
//        test_skill_addhp.bitmap_no = new ZBitmap(B.getBitmapById(Activity_window_main.this, R.drawable.skill_hp_no);
        List<Skill> list = new ArrayList<>();

        list.add(test_skill_attack);
        list.add(test_skill_jumpback);
        list.add(test_skill_big);
        list.add(test_skill_small);
        list.add(test_skill_addhp);

    }


}
