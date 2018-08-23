package com.boxin.financial.ui.financial.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.boxin.financial.R
import com.boxin.financial.R.id.num
import com.boxin.financial.entity.Financial
import com.boxin.financial.utils.CommonUtils
import gear.yc.com.gearlibrary.ui.adapter.GearRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_common_financial.view.*

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class FinancialALLAdapter(val context: Context, mData: MutableList<Financial>) :
        GearRecyclerViewAdapter<Financial, RecyclerView.ViewHolder>(context, mData) {
    var viewType = -1

    var onClickMore: onClickMoreListener? = null

    companion object {
        val VIEW = 0
        val VIEW_DIV = 1
        val VIEW_MORE = 2
    }

    override fun getItemViewType(position: Int): Int {
        if (position <= FinancialListPresenter.num - 1) {
            viewType = VIEW
        } else if (position == FinancialListPresenter.num) {
            viewType = VIEW_DIV
        } else {
            viewType = VIEW_MORE
        }
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        //普通
        val view = View.inflate(mContext, R.layout.item_common_financial, null)
        //更多条
        val viewDiv = View.inflate(mContext, R.layout.item_common_financial_more, null)
        //更多
        val viewMore = View.inflate(mContext, R.layout.item_common_financial_more, null)

        var holder: RecyclerView.ViewHolder?
        when (viewType) {
            VIEW -> {
                holder = Holder(view)
            }
            VIEW_DIV -> {
                holder = HolderDiv(viewDiv)
            }
            VIEW_MORE -> {
                holder = HolderMore(viewMore)
            }
            else -> {
                holder = Holder(view)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = mData[position]
        when (holder) {
            is Holder -> {
                holder.rlFinancial.setOnClickListener(this)
                holder.rlFinancial.tag = item
                holder.tvFinancialName.text = item.name
                holder.tvFinancialProfit.text = "${CommonUtils.onFormat(item.yearProfit)}%"
                if (item.platformRaiseRate > 0) {
                    holder.tvFinancialProfitAdd.text = "${CommonUtils.onFormat(item.moneyRateBo.toString())}%+${CommonUtils.onFormat(item.platformRaiseRate.toString())}%"
                } else {
                    holder.tvFinancialProfitAdd.text = ""
                }
                //日息或者月息
                val strShowTime = item.interestTypeBoStr
                holder.tvFinancialTime.text = "${item.time}$strShowTime"
                holder.tvLave.text = "剩余${CommonUtils.addComma(item.lave.toString())}元"
                holder.tvType.text = item.tips?.text
                holder.pbData.progress = item.getPro()
                holder.tvTypeText.text = item.typeStr
                holder.tvProgress.text = "${item.getPro()}%"
            }
            is HolderDiv -> {
                holder.llMore.visibility = View.VISIBLE
                holder.rlDiv.visibility = View.GONE
                holder.llMore.setOnClickListener{
                    if(onClickMore != null){
                        onClickMore!!.onClickMore()
                    }
                }
            }
            is HolderMore -> {
                holder.rlFinancial.setOnClickListener(this)
                holder.rlFinancial.tag = item
                holder.tvFinancialName.text = item.name
                holder.tvFinancialProfit.text = "${CommonUtils.onFormat(item.yearProfit)}%"
                //日息或者月息
                val strShowTime = item.interestTypeBoStr
                holder.tvFinancialTime.text = "${item.time}$strShowTime"
                holder.tvType.text = item.tips?.text
            }
        }
    }

    open interface onClickMoreListener{
        fun onClickMore()
    }

    open class Holder : RecyclerView.ViewHolder {
        var rlFinancial: RelativeLayout
        var tvFinancialName: TextView
        var tvFinancialProfit: TextView
        var tvFinancialProfitAdd: TextView
        var tvFinancialTime: TextView
        var tvLave: TextView
        var tvType: TextView
        var tvTypeText: TextView
        var tvProgress: TextView
        var pbData: ProgressBar

        constructor(view: View) : super(view) {
            rlFinancial = view.findViewById(R.id.rl_financial)
            tvFinancialName = view.findViewById(R.id.tv_financial_name)
            tvFinancialProfit = view.findViewById(R.id.tv_financial_profit)
            tvFinancialProfitAdd = view.findViewById(R.id.tv_financial_profit_add)
            tvFinancialTime = view.findViewById(R.id.tv_financial_time)
            tvLave = view.findViewById(R.id.tv_lave)
            tvType = view.findViewById(R.id.tv_type)
            tvTypeText = view.findViewById(R.id.tv_type_text)
            tvProgress = view.findViewById(R.id.tv_progress)
            pbData = view.findViewById(R.id.pb_data)
        }
    }

    class HolderDiv: RecyclerView.ViewHolder{
        var llMore :LinearLayout
        var rlDiv :RelativeLayout

        constructor(view: View) : super(view) {
            llMore = view.findViewById(R.id.ll_more)
            rlDiv = view.findViewById(R.id.rl_div)
        }
    }
    class HolderMore: RecyclerView.ViewHolder{
        var rlFinancial: RelativeLayout
        var llMore :LinearLayout
        var rlDiv :RelativeLayout
        var tvFinancialName: TextView
        var tvFinancialProfit: TextView
        var tvFinancialProfitAdd: TextView
        var tvFinancialTime: TextView
        var tvType: TextView

        constructor(view: View) : super(view) {
            rlFinancial = view.findViewById(R.id.rl_financial)
            tvFinancialName = view.findViewById(R.id.tv_financial_name)
            tvFinancialProfit = view.findViewById(R.id.tv_financial_profit)
            tvFinancialProfitAdd = view.findViewById(R.id.tv_financial_profit_add)
            tvFinancialTime = view.findViewById(R.id.tv_financial_time)
            tvType = view.findViewById(R.id.tv_type)
            llMore = view.findViewById(R.id.ll_more)
            rlDiv = view.findViewById(R.id.rl_div)
        }
    }
}