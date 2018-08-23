package com.boxin.financial.ui.investment.records

import android.view.View
import com.boxin.financial.R
import com.boxin.financial.entity.Financial
import com.boxin.financial.entity.InvestmentRecordInfo
import com.boxin.financial.utils.CommonUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_investment_records.view.*

/**
 * 投资信息
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class InvestmentRecordsAdapter(mData: MutableList<InvestmentRecordInfo>) :
        BaseQuickAdapter<InvestmentRecordInfo, InvestmentRecordsAdapter.Holder>(R.layout.item_investment_records, mData) {

    override fun convert(helper: Holder?, item: InvestmentRecordInfo) {
        if (helper != null) {
            helper.view.tv_title.text = item.title
            helper.view.tv_status.text = item.type?.text
            helper.view.tv_money.text = "${item.money}元"
            helper.view.tv_interest.text = "${item.interest}元"
            helper.view.tv_date.text = item.date
        }
    }

    class Holder(var view: View): BaseViewHolder(view)
}