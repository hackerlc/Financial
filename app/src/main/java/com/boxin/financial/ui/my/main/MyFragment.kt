package com.boxin.financial.ui.my.main

import android.Manifest
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.config.APIConfig.Companion.HTML_URL_ABOUT
import com.boxin.financial.config.APIConfig.Companion.HTML_URL_FAQ
import com.boxin.financial.config.APIConfig.Companion.HTML_URL_REVIEW
import com.boxin.financial.config.APIConfig.Companion.fetchHtmlUrl
import com.boxin.financial.config.AppConfig
import com.boxin.financial.config.AppConfig.Companion.I_ACCOUNT
import com.boxin.financial.config.AppConfig.Companion.I_USER
import com.boxin.financial.entity.UserAccount
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onDefaultSmartRefreshLayout
import com.boxin.financial.ui.common.dialog.CommonDialog
import com.boxin.financial.ui.investment.records.InvestmentRecordsActivity
import com.boxin.financial.ui.login.LoginActivity
import com.boxin.financial.ui.my.auth.AuthenticationActivity
import com.boxin.financial.ui.my.capital.flow.info.CapitalFlowInfoActivity
import com.boxin.financial.ui.my.capital.flow.repayment.RepaymentActivity
import com.boxin.financial.ui.my.hosting.HostingActivity
import com.boxin.financial.ui.my.recharge.RechargeActivity
import com.boxin.financial.ui.my.setting.SettingActivity
import com.boxin.financial.ui.my.withdraw.WithdrawActivity
import com.boxin.financial.ui.web.WebActivity
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_my.*
import android.content.Intent
import android.net.Uri
import com.boxin.financial.config.AppConfig.Companion.APP_PHONE
import com.tbruyelle.rxpermissions2.RxPermissions


/**
 * 我的
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class MyFragment :
        BasePFragment<MyContract.Presenter<UserAccount>>(R.layout.fragment_my),
        MyContract.View, CommonDialog.onDialogDoubleButtonClickListener {

    /**
     * 开户dialog
     */
    lateinit var dialog: CommonDialog
    /**
     * 提现dialog
     */
    lateinit var dialogWithdraw: CommonDialog
    /**
     * 客服dialog
     */
    lateinit var dialogPhone: CommonDialog

    override fun attachPresenter() {
        mPresenter = MyPresenter(this)
    }

    override fun initUI() {
//        onLoginStatus()
        sr_layout.onDefaultSmartRefreshLayout()
        sr_layout.isEnableLoadmore = false
        sr_layout.setOnRefreshListener {
            mPresenter.refreshData()
        }
        onClickBind(this, ll_item_1, ll_item_2, ll_item_3, iv_avatar,
                tv_name, iv_setting, ll_balance, ll_recharge, ll_withdraw,
                tv_login, div5, div6, div7, div8)
    }

    override fun initData() {
        dialog = CommonDialog(context!!, "", "为了保障您的资金安全\n在您投资前需要开通 汇付天下托管账户"
                , 0, "稍后开通", "立即开通", this)
        dialogWithdraw = CommonDialog(context!!, "", "您当前未绑定银行卡，请先进行充值绑卡操作。"
                , 0, "稍后绑卡", "充值绑卡", object : CommonDialog.onDialogDoubleButtonClickListener {
            override fun onLeftClick() {
                dialogWithdraw.dismiss()
            }

            override fun onRightClick() {
                dialogWithdraw.dismiss()
                RechargeActivity.startAct(context!!)
            }

        })
        dialogPhone = CommonDialog(context!!, "", "客服电话：0571-28120388\n服务时间：09：00-18：00\n是否确定拨打客服电话？"
                , 0, "取消", "确定", object : CommonDialog.onDialogDoubleButtonClickListener {
            override fun onLeftClick() {
                dialogPhone.dismiss()
            }

            override fun onRightClick() {
                val rxPermissions = RxPermissions(activity!!)
                rxPermissions.request(Manifest.permission.CALL_PHONE)
                        .subscribe { it ->
                            if (it) {
                                dialogPhone.dismiss()
                                //打电话
                                val intent = Intent(Intent.ACTION_DIAL)
                                val data = Uri.parse("tel:${APP_PHONE}")
                                intent.data = data
                                startActivity(intent)
                            } else {
                                showToast("需要获取授权才能拨打客服电话")
                                dialogPhone.dismiss()
                            }
                        }
            }

        })
    }

    override fun onClick(v: View) {
        //统一判断是否登录
        if (AppConfig.IS_LOGIN) {
            when (v.id) {
                R.id.tv_login -> {
                    LoginActivity.startAct(context!!, 0)
                }
                R.id.iv_setting -> {
                    SettingActivity.startAct(context!!)
                }
                R.id.tv_name, R.id.iv_avatar -> {
                    if (!I_ACCOUNT!!.open) {
                        dialog.show()
                        return
                    }
                    AuthenticationActivity.startAct(context!!)

                }
//            R.id.ll_balance -> {
//                BalanceActivity.startAct(context!!)
//            }
                R.id.ll_recharge -> {
                    if (!I_ACCOUNT!!.open) {
                        dialog.show()
                        return
                    }
                    RechargeActivity.startAct(context!!)
                }
                R.id.ll_withdraw -> {
                    if (!I_ACCOUNT!!.open) {
                        dialog.show()
                        return
                    }
                    if (AppConfig.I_ACCOUNT?.bankInfo() != null) {
                        WithdrawActivity.startAct(context!!)
                    } else {
                        dialogWithdraw.show()
                    }
                }
                R.id.ll_item_1 -> {
                    if (!I_ACCOUNT!!.open) {
                        dialog.show()
                        return
                    }
                    InvestmentRecordsActivity.startAct(context!!)
                }
                R.id.ll_item_2 -> {
                    if (!I_ACCOUNT!!.open) {
                        dialog.show()
                        return
                    }
                    CapitalFlowInfoActivity.startAct(context!!)
                }
                R.id.ll_item_3 -> {
                    if (!I_ACCOUNT!!.open) {
                        dialog.show()
                        return
                    }
                    RepaymentActivity.startAct(context!!)
                }
                R.id.div5 -> {
                    if (!I_ACCOUNT!!.open) {
                        dialog.show()
                        return
                    }
                    WebActivity.startAct(context!!, fetchHtmlUrl(HTML_URL_REVIEW, 0), "")
                }
                R.id.div6 -> WebActivity.startAct(context!!, fetchHtmlUrl(HTML_URL_FAQ, 0), "")
                R.id.div7 -> {
                    dialogPhone.show()
                }
                R.id.div8 -> WebActivity.startAct(context!!, fetchHtmlUrl(HTML_URL_ABOUT, 0), "")
            }
        } else {
            LoginActivity.startAct(context!!, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        if (AppConfig.IS_LOGIN) {
            mPresenter.fetch()
            mPresenter.fetchBankCard()
        }
        onLoginStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    private fun onLoginStatus() {
        //判断登录状态
        if (AppConfig.IS_LOGIN) {
            rl_login.visibility = View.VISIBLE
            ll_no_login.visibility = View.GONE

            tv_name.text = CommonUtils.changeStrTrip(I_USER?.phone)
            tv_price.text = CommonUtils.addComma(CommonUtils.onFormatTwo(I_ACCOUNT?.totalMoney))
            tv_earnings.text = CommonUtils.addComma(CommonUtils.onFormatTwo(I_ACCOUNT?.earningsMoney))
            tv_balance.text = CommonUtils.addComma(I_ACCOUNT?.moneyShow())
        } else {
            rl_login.visibility = View.GONE
            ll_no_login.visibility = View.VISIBLE
        }
    }

    override fun updateUI() {
        if (sr_layout.isRefreshing) {
            sr_layout.finishRefresh(500)
        }
        //更新登录状态
        onLoginStatus()
        if (I_ACCOUNT != null && I_ACCOUNT?.open != null) {
            if (!I_ACCOUNT!!.open) {
                if (!dialog.isShowing) {
                    dialog.show()
                }
            }
        }
    }

    override fun onLeftClick() {
        dialog.dismiss()
    }

    override fun onRightClick() {
        dialog.dismiss()
        HostingActivity.startAct(context!!)
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(context, str)
    }

    override fun onError(error: Throwable) {
        if (sr_layout.isRefreshing) {
            sr_layout.finishRefresh(500)
        }
    }

    override fun onDialog(show: Boolean) {
        if (show) {
            ProgressDialogUtil.getInstance().build(context!!).show()
        } else {
            ProgressDialogUtil.getInstance().dismiss()
        }
    }

    override fun <R> getLifecycle2(): LifecycleTransformer<R> {
        return bindToLifecycle()
    }

    override fun <R> getLifecycleDestroy(): LifecycleTransformer<R> {
        return bindToLifecycleDestroy()
    }
}