package com.boxin.financial.net.model

import com.boxin.financial.R.id.phone
import com.boxin.financial.base.net.BaseNM
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.*
import com.boxin.financial.net.api.ApiManager
import com.boxin.financial.net.helper.RxNetWorkHelper
import com.boxin.financial.utils.CommonUtils
import io.reactivex.Flowable
import java.util.HashMap

/**
 * 用户相关
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/4
 */
class UserNM: BaseNM() {

    /**
     * 登录
     * @param phone 手机
     * @param password 密码
     * @param code 验证码
     */
    fun onLogin(phone: String,password: String,code :String): Flowable<ResponseJson<User>> {
        mRP.map.clear()
        mRP.map["userPhone"] = phone
        mRP.map["userPwd"] = password
        mRP.map["code"] = code
        if (AppConfig.IS_LOGIN) {
            mRP.map["key"] = CommonUtils.encode(AppConfig.I_USER!!.key + AppConfig.MD5_KEY)
        } else {
            mRP.map["key"] = ""
        }
        return ApiManager.COMMON_API.onLogin(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
    /**
     * 注册
     * @param phone 手机
     * @param password 密码
     */
    fun onRegister(phone: String,password: String,code :String): Flowable<ResponseJson<User>> {
        mRP.map.clear()
        mRP.map["phone"] = phone
        mRP.map["loginPwd"] = password
        mRP.map["code"] = code
        mRP.map["source"] = "Android"
        return ApiManager.COMMON_API.onRegister(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
    /**
     * 修改密码
     * @param phone 手机
     * @param password 密码
     * @param flag 改登密：changeL，改支密：changeP，忘密：forget
     */
    fun onChangePassword(phone: String,password: String,code :String,idCard :String,flag: String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["phone"] = phone
        mRP.map["pwd"] = password
        mRP.map["code"] = code
        if (AppConfig.IS_LOGIN) {
            mRP.map["boxinAuthToken"] = AppConfig.I_USER?.token!!
        }
        if (flag == APIConfig.VERIFY_CODE_CHANGE_PASSWORD_FORGET){
            return ApiManager.COMMON_API.onFoundPassword(mRP.map)
                    .compose(RxNetWorkHelper.isNetWork())
        } else {
            mRP.map["idCard"] = idCard
            mRP.map["flag"] = flag
            return ApiManager.COMMON_API.onChangePassword(mRP.map)
                    .compose(RxNetWorkHelper.isNetWork())
        }

    }

    /**
     * 验证手机号
     * @param phone 手机
     * @param password 密码
     */
    fun onCheckPhone(phone: String,password: String,code :String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["oldPhone"] = phone
        mRP.map["pwd"] = password
        mRP.map["code"] = code
        return ApiManager.COMMON_API.onCheckPhone(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    /**
     * 修改手机号
     * @param phone 手机
     * @param code 验证码
     */
    fun onChangePhone(phone: String,code :String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["newPhone"] = phone
        mRP.map["code"] = code
        if (AppConfig.IS_LOGIN) {
            mRP.map["boxinAuthToken"] = AppConfig.I_USER?.token!!
        }
        return ApiManager.COMMON_API.onChangePhone(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    /**
     * 获取验证码
     * @param type 类型
     * @param phone 手机
     */
    fun fetchCode(phone: String,type: String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["flag"] = type
        mRP.map["mobile"] = phone
        return ApiManager.COMMON_API.fetchCode(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
    /**
     * 获取用户账户信息
     * 因为参数名问题这里修改
     * @param type 类型
     * @param phone 手机
     */
    fun fetchUserAccount(userId: String): Flowable<ResponseJson<UserAccount>> {
//        mRP.map.clear()
//        mRP.map["userId"] = userId
        val mMap: MutableMap<String, Any> = HashMap()
        mMap["userId"] = userId
        return ApiManager.COMMON_API.fetchUserAccount(mMap)
                .compose(RxNetWorkHelper.isNetWork())
    }
    /**
     * 用户托管申请
     */
    fun putUserInfoHosting(id: String,name: String,idCard: String,retUrl: String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["retUrl"] = retUrl
        mRP.map["id"] = id
        mRP.map["usrName"] = name
        mRP.map["idNo"] = idCard
        return ApiManager.COMMON_API.putUserInfoHosting(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
    /**
     * 充值提现
     * @param type 类型 0充值 1普通提现 2快速提现
     * @param money 金额
     * @param accountId 账户id
     */
    fun putAddAccountOrder(accountId: String,money: String,type: Int): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["accountId"] = accountId
        mRP.map["money"] = money
        mRP.map["flag"] = APIConfig.BASE_RETURN_URL
        when(type){
            0 -> {mRP.map["type"] = "recharge"}
            1 -> {mRP.map["type"] = "normalWithdraw"}
            2 -> {mRP.map["type"] = "fastWithdraw"}
        }
        return ApiManager.COMMON_API.putAddAccountOrder(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
    /**
     * 绑定银行卡
     * @param id 用户id
     */
    fun bindingBankCard(id: String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["id"] = id
        return ApiManager.COMMON_API.bindingBankCard(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
    /**
     * 解绑银行卡
     * @param accountId 账户id
     */
    fun onBindingBankCard(accountId: String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["accountId"] = accountId
        return ApiManager.COMMON_API.unBindingBankCard(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    /**
     * 查看银行卡信息
     * @param id 用户id
     */
    fun fetchBankCard(id: String): Flowable<ResponseJson<MutableList<UserBank>>> {
        mRP.map.clear()
        mRP.map["id"] = id
        return ApiManager.COMMON_API.fetchBankCard(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

}