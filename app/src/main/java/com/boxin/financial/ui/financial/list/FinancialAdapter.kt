package com.boxin.financial.ui.financial.list

import android.view.View
import com.boxin.financial.R
import com.boxin.financial.entity.Financial
import com.boxin.financial.utils.CommonUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_common_financial.view.*

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class FinancialAdapter(mData: MutableList<Financial>) :
        BaseQuickAdapter<Financial, FinancialAdapter.Holder>(R.layout.item_common_financial, mData) {

    override fun convert(helper: Holder?, item: Financial) {
        if (helper != null) {
            helper.view.tv_financial_name.text = item.name
            helper.view.tv_financial_profit.text = "${CommonUtils.onFormat(item.yearProfit)}%"
            helper.view.tv_financial_profit_add.text = "${CommonUtils.onFormat(item.moneyRateBo.toString())}%+${CommonUtils.onFormat(item.platformRaiseRate.toString())}%"
            //日息或者月息
            val strShowTime = item.interestTypeBoStr
            helper.view.tv_financial_time.text = "${item.time}$strShowTime"
            helper.view.tv_lave.text = "剩余${CommonUtils.addComma(item.lave.toString())}元"
            helper.view.tv_type.text = item.tips?.text
            helper.view.pb_data.progress = item.getPro()
            helper.view.tv_type_text.text = item.typeStr
            helper.view.tv_progress.text = "${item.getPro()}%"
        }
    }

    fun upData(data: MutableList<Financial>){
        mData = data
        notifyDataSetChanged()
    }

    class Holder(var view: View): BaseViewHolder(view)
}