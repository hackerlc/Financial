package com.boxin.financial.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boxin.financial.ui.web.WebH5Activity

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/6/26
 */
class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this,WebH5Activity::class.java))
        finish()
    }
}