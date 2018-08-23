package com.boxin.financial.ui.my.capital.flow.repayment.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boxin.financial.R
import com.boxin.financial.entity.RepaymentGroup
import com.boxin.financial.utils.CommonUtils
import com.githang.groundrecycleradapter.GroupRecyclerAdapter
import kotlinx.android.synthetic.main.head_home_banner.view.*
import kotlinx.android.synthetic.main.item_repayment_list.view.*

/**
 * 资金流水 adapter
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class RepaymentGroupAdapter(mData: MutableList<RepaymentGroup>?) :
        GroupRecyclerAdapter<RepaymentGroup, RepaymentGroupAdapter.TeamViewHolder,
                RepaymentGroupAdapter.MemberViewHolder>(mData) {
    override fun getChildCount(group: RepaymentGroup): Int {
        return group.repaymentItem.size
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repayment_list_group,parent,false))
    }

    override fun onCreateChildViewHolder(parent: ViewGroup): MemberViewHolder {
        return MemberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repayment_list,parent,false))
    }

    override fun onBindChildViewHolder(holder: MemberViewHolder, groupPosition: Int, childPosition: Int) {
        holder.view.tv_name.text = getGroup(groupPosition).repaymentItem[childPosition].name
        holder.view.tv_time.text = getGroup(groupPosition).repaymentItem[childPosition].time
        holder.view.tv_money.text = CommonUtils.onFormatTwo(getGroup(groupPosition).repaymentItem[childPosition].money)
        holder.view.tv_type.text = getGroup(groupPosition).repaymentItem[childPosition].type?.text
        holder.view.tv_info.text = "第${getGroup(groupPosition).repaymentItem[childPosition].info}/${getGroup(groupPosition).repaymentItem[childPosition].totalIssue}期回款"
    }

    override fun onBindGroupViewHolder(holder: TeamViewHolder, groupPosition: Int) {
        holder.view.tv_date.text = getGroup(groupPosition).date
    }

    class TeamViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    class MemberViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}