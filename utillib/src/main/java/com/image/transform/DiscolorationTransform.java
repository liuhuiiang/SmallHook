package com.image.transform;

import android.graphics.Bitmap;

import com.image.ImageUtil;


/**
 * 2018/7/4 10:40
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class DiscolorationTransform implements ITransform {
    @Override
    public Bitmap transform(Bitmap bitmap) {
        return ImageUtil.quse(bitmap);
    }
}
