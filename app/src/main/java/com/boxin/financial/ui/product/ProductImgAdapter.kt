package com.boxin.financial.ui.product

import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.glide.AGlide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_img.view.*

/**
 * 产品详情adapter
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class ProductImgAdapter(mData: MutableList<String>) :
        BaseQuickAdapter<String, ProductImgAdapter.Holder>(R.layout.item_img, mData) {

    override fun convert(helper: Holder?, item: String) {
        if (helper != null) {
            AGlide.with(mContext)
                    .load(item)
                    .into(helper.view.iv_img)

        }
    }

    fun upData(data: MutableList<String>){
        mData = data
        notifyDataSetChanged()
    }

    class Holder(var view: View): BaseViewHolder(view)
}