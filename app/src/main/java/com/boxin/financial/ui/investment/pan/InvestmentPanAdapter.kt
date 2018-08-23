package com.boxin.financial.ui.investment.pan

import android.view.View
import com.boxin.financial.R
import com.boxin.financial.entity.InvestmentPanInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_investment_pan.view.*

/**
 * 回款计划adapter
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class InvestmentPanAdapter(mData: MutableList<InvestmentPanInfo>) :
        BaseQuickAdapter<InvestmentPanInfo, InvestmentPanAdapter.Holder>(R.layout.item_investment_pan, mData) {

    override fun convert(helper: Holder?, item: InvestmentPanInfo) {
        if (helper != null) {
            helper.view.tv_date.text = item.date
            helper.view.tv_all_money.text = item.allMoney
            helper.view.tv_money.text = item.money
            helper.view.tv_interest.text = item.interest
        }
    }

    class Holder(var view: View): BaseViewHolder(view)
}