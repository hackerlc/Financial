package com.boxin.financial.ui.home

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.entity.Banner
import com.boxin.financial.entity.HomeData
import com.boxin.financial.entity.HomeDetail
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.CommonNM
import com.boxin.financial.net.model.FinancialNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 首页
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class HomePresenter(v: HomeContract.View) :
        PresenterDataWrapper<HomeData, HomeContract.View>(v),
        HomeContract.Presenter<HomeData> {

    private val mNM = CommonNM()
    init {
        mData = HomeData()
    }
    override fun fetch() {
        mNM.fetchHome()
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                            { errorData(it) })

    }

    override fun processData(d: HomeData) {
        super.processData(d)
        mData.banners.clear()
        mData.banners.addAll(d.banners)
        mData.financial = d.financial
        mData.news.clear()
        mData.news.addAll(d.news)
        mData.financials.clear()
        mData.financials.addAll(d.financials)
        mView.get()?.updateUI()
    }

    override fun refreshData() {
        fetch()
    }

    override fun getData(): HomeData {
        return mData
    }

    override fun getBanner(): MutableList<Banner> {
        return mData.banners
    }

    override fun getNews(): ArrayList<String> {
        var newsList = ArrayList<String>()
        for (news in mData.news){
            newsList.add(news.title!!)
        }
        return newsList
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        mView.get()?.onError(error)
    }

    override fun close() {
        mView.clear()
    }
}