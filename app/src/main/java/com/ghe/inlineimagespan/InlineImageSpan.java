/**
 * Copyright (C) 2010-2015 Softbank Mobile Corp. All Rights Reserved.
 */

package com.ghe.inlineimagespan;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.ScriptGroup;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class InlineImageSpan extends DynamicDrawableSpan {
    Context mContext;

    protected InlineImageSpan(Context context, int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        int size = super.getSize(paint, text, start, end, fm);
        Log.i(Constants.TAG, "size=" + size);
        return size;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        Log.i(Constants.TAG, "text=" + text);
        Log.i(Constants.TAG, "start=" + start + ",end=" + end + ",x=" + x + ",top=" + top + ",y=" + y + ",bottom=" + bottom);
    }

    @Override
    public Drawable getDrawable() {
        AssetManager assetManager = mContext.getAssets();
        InputStream is = null;
        try {
            is = assetManager.open("original.jpg");
        }catch (IOException e1) {
            e1.printStackTrace();
        }
        Drawable d = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            Resources r = mContext.getResources();
            d = new BitmapDrawable(r, bitmap);
            d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
        }catch (Exception ignored) {
        }finally {
            try {
                is.close();
            }catch(Exception ignored) {
            }
        }

        return d;
    }

    /*
    @Override
    public Drawable getDrawable() {
        InputStream is = null;
        Drawable d = null;
        try {
            is = mContext.getAssets().open("original.jpg");
            d = Drawable.createFromStream(is, null);
            int w = d.getIntrinsicWidth();
            int h = d.getIntrinsicHeight();
            d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
        }catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            try {
                if(is!=null)
                    is.close();
            }catch (Exception ignored) {
            }
        }

        return d;
    }
    */

}