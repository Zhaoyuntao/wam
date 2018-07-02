package com.wam.zgame.jff.warriorandmonster.tools;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by zhaoyuntao on 2018/1/18.
 * 用来生成可点击文本
 */

public class ClickableText {
    public interface CallBack{
        public void exec(String clickableText);
    }
    public static SpannableString getText(final String text, int start, int end, final CallBack callBack){
        if (end>text.length()){
            end=text.length();
        }
        if(start<0){
            start=0;
        }else if(start>end){
            start=end;
        }
        SpannableString string_clickable = new SpannableString(text);
        string_clickable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                callBack.exec(text);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string_clickable;
    }
}
