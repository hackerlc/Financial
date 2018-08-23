package com.boxin.financial.config

/**
 * 提供API接口地址，以及其他接口配置参数、方法
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/11/7
 */
class APIConfig {
    companion object {

//        const val BASE_URL = "http://192.168.1.249:8086/"//大侠
//        const val BASE_URL = "http://192.168.1.147:8080/"//生产
//        const val BASE_URL = "http://192.168.1.148:8080/"//测试
        const val BASE_URL = "https://finance.zhecaijinfu.com/"//正式

        const val BASE_RETURN_URL = "http://124.160.43.66:18087/hf/callback"
        /**
         * 验证码类型
         */
        const val VERIFY_CODE_REGISTER = "register"
        const val VERIFY_CODE_LOGIN = "login"
        const val VERIFY_CODE_CHANGE_PASSWORD = "changeL"
        const val VERIFY_CODE_CHANGE_PAY_PASSWORD = "changeP"
        const val VERIFY_CODE_CHANGE_PASSWORD_FORGET = "forget"
        const val VERIFY_CODE_CHANGE_PHONE_OLD = "changePhone"
        const val VERIFY_CODE_CHANGE_PHONE_CHECK = "checkidentity"


        //网页链接
//        const val HTML_URL = "http://192.168.1.82:8082/"
//        const val HTML_URL = "http://192.168.1.233:8081/"
//        const val HTML_URL = "http://124.160.43.66:18187/"
//        const val HTML_URL = "http://192.168.1.148/"
        const val HTML_URL = "https://app.zhecaijinfu.com/"
        const val HTML_URL_BASE = "https://apph5.zhecaijinfu. com/"
        const val HTML_URL_BASE_ARTICLE = "article/"
        const val HTML_URL_BASE_ABOUT = "about/"
//        const val H5 = "${HTML_URL}app/#/?head=false"
        const val H5 = "${HTML_URL}#/?head=false"

        /**
         *  信息披露-备案信息
         */
        const val HTML_URL_INFO_SHOW_A = "infoShowA.html"
        /**
         * 组织信息
         */
        const val HTML_URL_INFO_SHOW_B = "infoShowB.html"
        /**
         * 审核信息
         */
        const val HTML_URL_INFO_SHOW_C = "infoShowC.html"
        /**
         * 经营信息
         */
        const val HTML_URL_INFO_SHOW_D = "infoShowD.html"
        /**
         * 法律法规
         */
        const val HTML_URL_INFO_SHOW_E = "infoShowE.html"
        /**
         * 承诺函
         */
        const val HTML_URL_INFO_SHOW_F = "infoShowF.html"
        /**
         * 安全保障
         */
        const val HTML_URL_SAFETY = "safety.html"

        /**
         * 关于我们
         */
        const val HTML_URL_ABOUT = "aboutUs.html"
        const val HTML_URL_PRODUCT_SAFETY = "product_safety.html"
        const val HTML_URL_PRODUCT_RECORD = "product_invest_record.html"
        /**
         * 产品详情-投资信息
         */
        const val HTML_URL_PRODUCT_DETAIL = "productDetail.html"

        /**
         * 风险评测 - 待评测
         */
        const val HTML_URL_REVIEW = "review.html"
        /**
         * 评测详情
         */
        const val HTML_URL_REVIEW_DETAIL = "review_detail.html"
        /**
         * 风向评测-已评测
         */
        const val HTML_URL_REVIEW_RESULT = "review_result.html"
        /**
         * 常见问题
         */
        const val HTML_URL_FAQ = "FAQ.html"
        /**
         * 平台快讯详情
         */
        const val HTML_URL_NEWS_DETAIL = "news_detail.html"

        /**
         * 法律法规-关于做好P2P网络借贷风险专项整治整改验收工作的通知
         */
        const val HTML_URL_ARTICLE1 = "article001.html"
        /**
         * 法律法规-网络借贷信息中介机构备案管理登记指引
         */
        const val HTML_URL_ARTICLE2 = "article002.html"
        /**
         * 法律法规-网络借贷信息中介机构业务活动管理暂行办法
         */
        const val HTML_URL_ARTICLE3 = "article003.html"
        /**
         * 法律法规-网络借贷信息中介机构业务活动信息披露指引
         */
        const val HTML_URL_ARTICLE4 = "article004.html"
        /**
         * 法律法规-网络借贷信息中介机构业务活动信息披露指引
         */
        const val HTML_URL_ARTICLE5 = "article005.html"

        /**
         * 公司简介
         */
        const val HTML_URL_COMPANY_INTRO = "companyIntro.html"
        /**
         * 产品介绍
         */
        const val HTML_URL_PRODUCT_INTRO = "productIntro.html"
        /**
         * 产品特点
         */
        const val HTML_URL_PRODUCT_ADVANTAGE = "productAdvantage.html"
        /**
         * 注册协议
         */
        const val HTML_URL_PRODUCT_REGISTER_AGREE = "registerAgree.html"

        /**
         * 组装并返回HTML URL
         * @param htmlUrl 页面URL
         * @param urlType url类型 0无 1article 2about
         */
        fun fetchHtmlUrl(htmlUrl: String,urlType: Int,code: String = ""): String{
            var url = HTML_URL_BASE
            when(urlType){
                1 -> url += HTML_URL_BASE_ARTICLE
                2 -> url += HTML_URL_BASE_ABOUT
            }
            url += htmlUrl
            if (!code.isEmpty()){
                url += "?code=$code&baseUrl=$BASE_URL"
            }
            return url
        }
    }
}