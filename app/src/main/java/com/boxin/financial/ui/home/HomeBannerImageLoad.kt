package com.boxin.financial.ui.home

import android.content.Context
import android.widget.ImageView
import com.boxin.financial.R
import com.boxin.financial.base.glide.AGlide
import com.boxin.financial.base.glide.GlideRoundTransform
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.youth.banner.loader.ImageLoader

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/29
 */
class HomeBannerImageLoad: ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        AGlide.with(context)
                .asBitmap()
                .load(path)
                .fitCenter()
                .transform(GlideRoundTransform(context))
                .into(imageView)

    }

//    override fun createImageView(context: Context?): ImageView {
//        val iv = ImageView(context)
//        iv.scaleType = ImageView.ScaleType.FIT_XY
//        return iv
//    }
}