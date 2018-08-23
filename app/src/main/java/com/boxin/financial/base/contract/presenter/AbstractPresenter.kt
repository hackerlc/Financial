package com.boxin.financial.base.contract.presenter

import com.boxin.financial.base.contract.BaseContract
import java.lang.ref.WeakReference

/**
 * 初始化mView
 * @author joker
 * Email:812405389@qq.com
 * mView修改为弱引用防止内存泄漏
 * @version 2017/12/19
 * @version 2017/11/8
 */
abstract class AbstractPresenter<V : BaseContract.BaseView>(v : V) {
    val mView : WeakReference<V> = WeakReference(v)
}