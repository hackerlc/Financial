package com.boxin.financial.base.fragment


import com.boxin.financial.base.contract.BaseContract

/**
 * MVP Fragment
 * @author joker
 * Email:lc@shandaichaoren.com or 812405389@qq.com
 * @version 2017/12/6
 */
abstract class BasePFragment<P: BaseContract.BasePresenter<*>>(id: Int): BaseFragment(id) {
    protected lateinit var mPresenter : P
}