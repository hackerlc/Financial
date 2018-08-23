package com.boxin.financial.net.helper

/**
 * 调用验证码接口时用户已注册
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/12/8
 */
class ThrowableErrorCode(message: String?, cause: Throwable?): Throwable(message,cause){
    constructor(message: String?) : this(message, null)

    constructor(cause: Throwable?) : this(cause?.toString(), cause)

    constructor() : this(null, null)
}