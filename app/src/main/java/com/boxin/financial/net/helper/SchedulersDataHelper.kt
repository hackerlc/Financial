package com.boxin.financial.net.helper

import com.boxin.financial.entity.ResponseJson
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function

/**
 * 网络数据拦截处理
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/11/8
 */
class SchedulersDataHelper {
    companion object {
        /**
         * 错误信息
         */
        private var errorMsg = ""

        /**
         * 判断数据是否返回成功，成功则返回数据元，不成功则返回错误提示信息
         * 关于@label的方式我不是特别的明白，直接看文档好了(http://kotlinlang.org/docs/reference/returns.html)
         */
        fun <T> handleResult(): FlowableTransformer<ResponseJson<T>, T> {
            return FlowableTransformer {
                it.flatMap(Function {
                    when (it.code) {
                        "0" -> {
                            if (it.result == null){
                                return@Function Flowable.error(ThrowableSuccess4DataNull(it.message))
                            } else {
                                return@Function createData(it.result)
                            }
                        }
                        "USER_VALID_003" -> {
                            return@Function Flowable.error(ThrowableErrorUserPassword(it.message))
                        }
                        "USER_VALID_005" -> {
                            return@Function Flowable.error(ThrowableErrorCode(it.message))
                        }
                        "USER_VALID_010","USER_VALID_002" -> {
                            return@Function Flowable.error(ThrowableErrorUserValid(it.message))
                        }
                        "USER_VALID_012" -> {
                            return@Function Flowable.error(ThrowableErrorUserValid(it.message))
                        }
                        else -> {
                            return@Function Flowable.error(ThrowableOther(it.message))
                        }
                    }
                })
            }
        }

        /**
         * 请求成功后创建并返回数据
         */
        private fun <T> createData(data: T): Flowable<T> {
            return Flowable.create({
                it.onNext(data)
                it.onComplete()
            },BackpressureStrategy.BUFFER)
        }
    }
}