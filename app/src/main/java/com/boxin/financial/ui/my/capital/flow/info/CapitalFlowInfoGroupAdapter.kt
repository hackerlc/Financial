package com.boxin.financial.ui.my.capital.flow.info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boxin.financial.R
import com.boxin.financial.entity.CapitalFlowGroup
import com.githang.groundrecycleradapter.GroupRecyclerAdapter
import kotlinx.android.synthetic.main.head_home_banner.view.*
import kotlinx.android.synthetic.main.item_capital_flow_info.view.*

/**
 * 资金流水 adapter
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class CapitalFlowInfoGroupAdapter(mData: MutableList<CapitalFlowGroup>?) :
        GroupRecyclerAdapter<CapitalFlowGroup, CapitalFlowInfoGroupAdapter.TeamViewHolder,
                CapitalFlowInfoGroupAdapter.MemberViewHolder>(mData) {
    override fun getChildCount(group: CapitalFlowGroup): Int {
        return group.capitalFlow.size
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_capital_flow_info_group,parent,false))
    }

    override fun onCreateChildViewHolder(parent: ViewGroup): MemberViewHolder {
        return MemberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_capital_flow_info,parent,false))
    }

    override fun onBindChildViewHolder(holder: MemberViewHolder, groupPosition: Int, childPosition: Int) {
        holder.view.tv_name.text = getGroup(groupPosition).capitalFlow[childPosition].nameStr()
        holder.view.tv_money.text = getGroup(groupPosition).capitalFlow[childPosition].moneyStr()
        holder.view.tv_time.text = getGroup(groupPosition).capitalFlow[childPosition].time
    }

    override fun onBindGroupViewHolder(holder: TeamViewHolder, groupPosition: Int) {
        holder.view.tv_date.text = getGroup(groupPosition).date
    }

    class TeamViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    class MemberViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}