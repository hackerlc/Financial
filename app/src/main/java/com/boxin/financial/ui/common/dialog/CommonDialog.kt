package com.boxin.financial.ui.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.boxin.financial.R
import kotlinx.android.synthetic.main.dialog_on_and_off.*

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/26
 */
open class CommonDialog: Dialog {
    var mTitle = ""
    var mMessage = ""
    var mType = 0

    var btnLeft = "取消"
    var btnRight = "确认"

    var onClick: onDialogDoubleButtonClickListener

    /**
     * 按钮默认名称 left 确认 right 取消
     */
    constructor(context: Context,message: String,type :Int,onDialogDoubleButtonClickListener: onDialogDoubleButtonClickListener):
            this(context, "", message, type,"取消","确认",onDialogDoubleButtonClickListener)

    /**
     * @param message 内容
     * @param type 类型 0 左+右按钮 1 居中左按钮
     */
    constructor(context: Context,title: String,message: String,type :Int,
                leftName: String,rightName: String,onDialogDoubleButtonClickListener: onDialogDoubleButtonClickListener):
            super(context, R.style.MyDialogStyleTop){
        mTitle = title
        mMessage = message
        mType = type

        btnLeft = leftName
        btnRight = rightName

        onClick = onDialogDoubleButtonClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_on_and_off)
        setCanceledOnTouchOutside(false)
        tv_message.text = mMessage
        tv_left.text = btnLeft
        tv_right.text = btnRight

        if (mType == 1){
            tv_right.visibility = View.GONE
            line1.visibility = View.GONE
        } else {
            tv_right.visibility = View.VISIBLE
            line1.visibility = View.VISIBLE
        }

        tv_left.setOnClickListener{
            onClick.onLeftClick()
        }
        tv_right.setOnClickListener{
            onClick.onRightClick()
        }
    }

    interface onDialogDoubleButtonClickListener{
        fun onLeftClick()
        fun onRightClick()
    }
}