package com.boxin.financial.ui.home

import android.view.View
import com.boxin.financial.R
import com.boxin.financial.config.APIConfig.Companion.HTML_URL_ABOUT
import com.boxin.financial.config.APIConfig.Companion.HTML_URL_INFO_SHOW_A
import com.boxin.financial.config.APIConfig.Companion.HTML_URL_NEWS_DETAIL
import com.boxin.financial.config.APIConfig.Companion.HTML_URL_SAFETY
import com.boxin.financial.config.APIConfig.Companion.fetchHtmlUrl
import com.boxin.financial.entity.Banner
import com.boxin.financial.entity.HomeData
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.financial.detail.FinancialDetailActivity
import com.boxin.financial.ui.web.WebActivity
import com.boxin.financial.utils.CommonUtils
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.head_home_banner.view.*

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class HomeHeadViewHolder(val view: View,d: MutableList<Banner>): View.OnClickListener {

    private var datas: MutableList<Banner> = d
    private val images: MutableList<String> = ArrayList()

    init {
        view.br_view.setDelayTime(5000)
        view.br_view.setIndicatorGravity(BannerConfig.CENTER)
        view.br_view.isAutoPlay(true)
//        view.br_view.setBannerAnimation(Transformer.ScaleInOut)
        view.br_view.setOnBannerListener({
            val banner = datas[it]
            //跳转
            if (banner.url.isNullOrEmpty()) {
                WebActivity.startAct(view.context, banner.url!!, null)
            }
        })
        view.tv_message.setTextStillTime(3000)//设置停留时长间隔
        view.tv_message.setAnimTime(500)//设置进入和退出的时间间隔
        view.tv_message.startAutoScroll()

        onClickBind(this,view.ll_item_1,view.ll_item_2,view.ll_item_3,view.ll_item_4)
    }

    fun loadBanner(d: MutableList<Banner>){
        datas = d
        images.clear()
        for (data in datas) {
            images.add(data.img!!)
        }
        view.br_view.setImages(images)
                .setImageLoader(HomeBannerImageLoad())
                .start()
    }

    fun upHeadView(data: HomeData, strList: ArrayList<String>){
        view.tv_message.setTextList(strList)
        view.tv_message.setOnItemClickListener {
            WebActivity.startAct(view.context,fetchHtmlUrl(HTML_URL_NEWS_DETAIL,0),"")
        }
//        view.tv_title.text = data.financial?.name
        //日息或者月息
        val strShowTime = data.financial?.interestTypeBoStr
        view.tv_date.text = "${data.financial?.time}$strShowTime"
        view.tv_profit.text = "${CommonUtils.onFormat(data.financial?.yearProfit)}%"
        view.btn_ok.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                FinancialDetailActivity.startAct(view.context, data.financial?.id!!)
            }
        })

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.ll_item_1 -> WebActivity.startAct(v.context,fetchHtmlUrl(HTML_URL_INFO_SHOW_A,0),"")
            R.id.ll_item_2 -> WebActivity.startAct(v.context,fetchHtmlUrl(HTML_URL_SAFETY,0),"")
            R.id.ll_item_3 -> WebActivity.startAct(v.context,fetchHtmlUrl(HTML_URL_ABOUT,0),"")
            R.id.ll_item_4 -> WebActivity.startAct(v.context,fetchHtmlUrl(HTML_URL_ABOUT,0),"")
        }
    }
}