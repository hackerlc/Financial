package com.boxin.financial.net.api

import com.boxin.financial.entity.*
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * 提供接口调用方法
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/11/8
 */
interface CommonApi {
    /**
     * 首页数据
     */
    @FormUrlEncoded
    @POST("index")
    fun fetchHome(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<HomeData>>
    /**
     * app更新
     */
    @FormUrlEncoded
    @POST("updateapp")
    fun updateApp(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<UpApp>>
    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("login/login")
    fun onLogin(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<User>>
    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("login/userRegister")
    fun onRegister(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<User>>
    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("users/changePwd")
    fun onChangePassword(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 忘记密码
     */
    @FormUrlEncoded
    @POST("login/foundPwd")
    fun onFoundPassword(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 验证手机号
     */
    @FormUrlEncoded
    @POST("login/changePhoneCheck")
    fun onCheckPhone(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 修改手机号
     */
    @FormUrlEncoded
    @POST("login/changePhone")
    fun onChangePhone(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("sms/sendMessage")
    fun fetchCode(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 标的
     */
    @FormUrlEncoded
    @POST("asset/info")
    fun getFinancialDetail(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<Financial>>
    /**
     * 标的列表
     */
    @FormUrlEncoded
    @POST("asset/list")
    fun fetchFinancialList(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<MutableList<Financial>>>
    /**
     * 产品授权出借
     */
    @FormUrlEncoded
    @POST("lend/determineLend")
    fun fetchAddLend(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 确认支付
     */
    @FormUrlEncoded
    @POST("asset/buy")
    fun payFinancialMoney(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>

    /**
     * 投资列表
     */
    @FormUrlEncoded
    @POST("asset/user/list")
    fun getInvestmentRecordsList(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<BaseList<InvestmentRecordInfo>>>

    /**
     * 投资详情
     */
    @FormUrlEncoded
    @POST("asset/user/info")
    fun getInvestmentRecords(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<InvestmentRecordInfo>>
    /**
     * 回款计划
     */
    @FormUrlEncoded
    @POST("")
    fun getInvestmentPanList(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<MutableList<InvestmentPanInfo>>>
    /**
     * 产品详情
     */
    @FormUrlEncoded
    @POST("")
    fun getProductInfo(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<ProductInfo>>
    /**
     * 回款信息
     */
    @FormUrlEncoded
    @POST("asset/repayment/list")
    fun getRepaymentInfo(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<BaseList<RepaymentGroup>>>
    /**
     * 回款详情
     */
    @FormUrlEncoded
    @POST("/asset/repayment/info")
    fun getRepaymentDetail(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<RepaymentDetail>>
    /**
     * 资金流水
     */
    @FormUrlEncoded
    @POST("userAccountFlow/qureyAccountFlow")
    fun getCapitalFlow(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<BaseList<CapitalFlowGroup>>>
    /**
     * 账户信息
     */
    @FormUrlEncoded
    @POST("userAccount/getByUserId")
    fun fetchUserAccount(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<UserAccount>>
    /**
     * 充值、提现
     */
    @FormUrlEncoded
    @POST("accountOrder/addAccountOrder")
    fun putAddAccountOrder(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 充值、提现
     */
    @FormUrlEncoded
    @POST("users/openAccout")
    fun putUserInfoHosting(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 绑定银行卡
     */
    @FormUrlEncoded
    @POST("users/bindCard")
    fun bindingBankCard(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 解绑银行卡
     */
    @FormUrlEncoded
    @POST("users/delCard")
    fun unBindingBankCard(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<String>>
    /**
     * 解绑银行卡
     */
    @FormUrlEncoded
    @POST("users/queryBankCardInfo")
    fun fetchBankCard(@FieldMap map: MutableMap<String, Any>): Flowable<ResponseJson<MutableList<UserBank>>>
}