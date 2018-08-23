package com.boxin.financial.ui.home

import android.view.View
import com.boxin.financial.R
import com.boxin.financial.entity.Financial
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_common_financial.view.*

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
@Deprecated("暂时用FinancialAdapter")
class HomeAdapter(mData: MutableList<Financial>) :
        BaseQuickAdapter<Financial, HomeAdapter.Holder>(R.layout.item_common_financial, mData) {

    override fun convert(helper: Holder?, item: Financial) {
        if (helper != null) {
            helper.view.tv_financial_name.text = item.name
            helper.view.tv_financial_profit.text = item.yearProfit
            helper.view.tv_financial_time.text = item.time
            helper.view.tv_lave.text = "${item.lave}"
            helper.view.tv_type.text = item.tips?.text
            helper.view.pb_data.progress = item.getPro()
            helper.view.tv_type_text.text = item.typeStr
            helper.view.tv_progress.text = "${item.getPro()}%"
        }
    }

    class Holder(var view: View): BaseViewHolder(view)
}