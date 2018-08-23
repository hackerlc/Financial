package com.boxin.financial.ui.guide

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.main.MainActivity
import com.gyf.barlibrary.ImmersionBar
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_guide.*

/**
 * 用户引导
 * 图片命名为guide_bg_01 ... 0x 默认加载三张引导图
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class GuideActivity :
        BasePActivity<GuideContract.Presenter<String>>(R.layout.activity_guide),
        GuideContract.View,ViewPager.OnPageChangeListener {

    private lateinit var guideAdapter: GuideAdapter
    private var views: MutableList<View> = ArrayList()
    private var imageIds: MutableList<Int> = ArrayList()

    companion object {
        fun startAct(context: Context) {
            context.startActivity(Intent(context, GuideActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = GuidePresenter(this)
    }

    override fun initUI() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent_gray_end)
                .init()
        initViews()
        initPoint()

        onClickBind(this, btn_ok)
    }

    /**
     * 初始化小点
     */
    private fun initPoint() {

    }

    /**
     * 初始化view page
     */
    @SuppressLint("NewApi")
    private fun initViews() {
        btn_ok.visibility = View.GONE
        imageIds.add(R.drawable.guide_bg_01)
        imageIds.add(R.drawable.guide_bg_02)
        imageIds.add(R.drawable.guide_bg_03)

        //获取linearLayout 参数设置为填充
        var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)

        for (imageId in imageIds) {
            var imageView = ImageView(this)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.layoutParams = params
            imageView.setBackgroundResource(imageId)
            views.add(imageView)
        }

        guideAdapter = GuideAdapter(views)
        vp_views.adapter = guideAdapter
        vp_views.addOnPageChangeListener(this)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_ok -> {
//                MainActivity.startAct(this,0)
                toEnter(true)
            }
        }
    }

    override fun updateUI() {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position == imageIds.size - 1){
            btn_ok.visibility = View.VISIBLE
        } else {
            btn_ok.visibility = View.GONE
        }
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(this, str)
    }

    override fun onError(error: Throwable) {

    }

    override fun onDialog(show: Boolean) {
        if (show) {
            ProgressDialogUtil.getInstance().build(this).show()
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

    fun GuideActivity.startAct(context: Context) {
        context.startActivity(Intent(context, GuideActivity::class.java))
    }
}