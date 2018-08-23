package com.boxin.financial.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.boxin.financial.R;
import com.boxin.financial.base.App;

/**
 * 
 * 功能描述：加载数据对话框
 * 
 */
public class LoadingDialog extends Dialog implements OnDismissListener {

	private static final int MSG_WHAT = 0xaa;
	private Context mContext;
	private CallbackInterface callbackInterface;
	private TextView tv_loading;
	private ImageView loadImage;
//	private LottieAnimationView lav;
	/**
	 * 	默认超时时间
	 */
	private long DEFAULT_TIMEOUT = 55 * 1000;
	private String toast;

	public LoadingDialog(Context context) {
		super(context, R.style.dialog_notitlebar);
		this.mContext = context;
	}

	/**
	 * 显示加载框
	 */
	public void showDialog() {// 默认超时为12s
		showDialog(mContext.getString(R.string.loading),
				null, "数据加载失败");
	}

	/**
	 * 显示加载框
	 * @param text 文本
	 * @param callbackInterface 回调
	 */
	public void showDialog(String text, CallbackInterface callbackInterface) {
		this.callbackInterface = callbackInterface;
		showDialog(text,null, null);
	}

	public void showDialog(String text, Long timeOut, String toast) {
		setContentView(R.layout.loading_dialog_layout);
//		iv_loading = (ImageView) findViewById(R.id.iv_loading);
		tv_loading = (TextView) findViewById(R.id.tv_loading);
//		lav = findViewById(R.id.animation_view);
		loadImage = findViewById(R.id.iv_loading);
		Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.img_animation);
		LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
		animation.setInterpolator(lin);
		loadImage.startAnimation(animation);
		setOnDismissListener(this);
		sendMsg(text, timeOut, toast);
		show();
	}

	public void refreshText(String text) {
		tv_loading.setText(text);
	}

	private void sendMsg(String text, Long time, String toast) {
		this.toast = toast;
		if (TextUtils.isEmpty(text)) {
			tv_loading.setText(mContext.getString(R.string.loading));
		} else {
			tv_loading.setText(text);
		}
		if (time == null || time <= 0) {
			time = DEFAULT_TIMEOUT;
		}
		handler.sendEmptyMessageDelayed(MSG_WHAT, time);
	}

	@Override
	public void dismiss() {
		super.dismiss();
//		if (lav != null && lav.isAnimating()) {
//			super.dismiss();
//		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (isShowing()) {
				dismiss();
//				Utils.showToast(mContext, toast);
			}
			super.handleMessage(msg);
		}

	};

	@Override
	public void onDismiss(DialogInterface dialog) {// 当对话框关闭时，取消http请求
		handler.removeMessages(MSG_WHAT);// 清除消息
	}

	interface CallbackInterface {

		void operation(boolean result);
	}
}
