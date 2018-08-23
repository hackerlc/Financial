package com.boxin.financial.ui.login.lock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import com.boxin.financial.R
import com.boxin.financial.base.App
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.User
import com.boxin.financial.utils.SPUtil
import com.boxin.financial.view.gestureview.LockPatternUtils
import com.boxin.financial.view.gestureview.LockPatternView
import com.gyf.barlibrary.ImmersionBar
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_gesture_create.*
import java.util.*

class GestureCreateActivity : AppCompatActivity(), OnClickListener {

    protected var mChosenPattern: List<LockPatternView.Cell>? = null
    private var mUiStage = Stage.Introduction
    private var mPreviewViews = Array<Array<View?>>(3) { arrayOfNulls(3) }
    /**
     * The patten used during the help screen to show how to draw a pattern.
     */
    private val mAnimatePattern = ArrayList<LockPatternView.Cell>()

    /**
     * 用户账户手机号，用来存储该用户的手势锁
     */
    private var account: String? = null

    private val mClearPatternRunnable = Runnable { gesturepwd_create_lockview.clearPattern() }

    protected var mChooseNewLockPatternListener: LockPatternView.OnPatternListener = object : LockPatternView.OnPatternListener {
        override fun onPatternStart() {
            gesturepwd_create_lockview.removeCallbacks(mClearPatternRunnable)
            patternInProgress()
        }

        override fun onPatternCleared() {
            gesturepwd_create_lockview.removeCallbacks(mClearPatternRunnable)
        }

        override fun onPatternDetected(pattern: List<LockPatternView.Cell>?) {
            if (pattern == null) {
                return
            }
            if (mUiStage == Stage.NeedToConfirm || mUiStage == Stage.ConfirmWrong) {
                if (mChosenPattern == null) {
                    throw IllegalStateException(
                            "null chosen pattern in stage 'need to confirm")
                }
                if (mChosenPattern == pattern) {
                    saveChosenPatternAndFinish()
                } else {
                    updateStage(Stage.ConfirmWrong)
                }
            } else if (mUiStage == Stage.Introduction || mUiStage == Stage.ChoiceTooShort) {
                if (pattern.size < LockPatternUtils.MIN_LOCK_PATTERN_SIZE) {
                    updateStage(Stage.ChoiceTooShort)
                } else {
                    mChosenPattern = ArrayList(pattern)
                    updateStage(Stage.NeedToConfirm)
                    tv_reload.visibility = View.VISIBLE
                }
            } else {
                throw IllegalStateException("Unexpected stage " + mUiStage + " when "
                        + "entering the pattern.")
            }
        }

        override fun onPatternCellAdded(pattern: List<LockPatternView.Cell>) {

        }

        private fun patternInProgress() {

        }
    }

    /**
     * The states of the left footer button.
     */
    internal enum class LeftButtonMode
    /**
     * @param text
     * The displayed text for this mode.
     * @param enabled
     * Whether the button should be enabled.
     */
    private constructor(val text: Int, val enabled: Boolean) {
        Cancel(android.R.string.cancel, true), CancelDisabled(android.R.string.cancel, false),
        Retry(R.string.lockpattern_retry_button_text, true), RetryDisabled(
                R.string.lockpattern_retry_button_text, false),
        Gone(ID_EMPTY_MESSAGE, false)
    }

    /**
     * The states of the right button.
     */
    internal enum class RightButtonMode
    /**
     * @param text
     * The displayed text for this mode.
     * @param enabled
     * Whether the button should be enabled.
     */
    private constructor(val text: Int, val enabled: Boolean) {
        Continue(R.string.lockpattern_continue_button_text, true), ContinueDisabled(
                R.string.lockpattern_continue_button_text, false),
        Confirm(
                R.string.lockpattern_confirm_button_text, true),
        ConfirmDisabled(
                R.string.lockpattern_confirm_button_text, false),
        Ok(android.R.string.ok, true)
    }

    /**
     * Keep track internally of where the user is in choosing a pattern.
     */
    protected enum class Stage
    /**
     * @param headerMessage
     * The message displayed at the top.
     * @param leftMode
     * The mode of the left button.
     * @param rightMode
     * The mode of the right button.
     * @param footerMessage
     * The footer message.
     * @param patternEnabled
     * Whether the pattern widget is enabled.
     */
    private constructor(internal val headerMessage: Int, internal val leftMode: LeftButtonMode, internal val rightMode: RightButtonMode,
                        internal val footerMessage: Int, internal val patternEnabled: Boolean) {

        Introduction(R.string.lockpattern_recording_intro_header, LeftButtonMode.Cancel,
                RightButtonMode.ContinueDisabled, ID_EMPTY_MESSAGE, true),
        HelpScreen(
                R.string.lockpattern_settings_help_how_to_record, LeftButtonMode.Gone,
                RightButtonMode.Ok, ID_EMPTY_MESSAGE, false),
        ChoiceTooShort(
                R.string.lockpattern_recording_incorrect_too_short, LeftButtonMode.Retry,
                RightButtonMode.ContinueDisabled, ID_EMPTY_MESSAGE, true),
        FirstChoiceValid(
                R.string.lockpattern_pattern_entered_header, LeftButtonMode.Retry,
                RightButtonMode.Continue, ID_EMPTY_MESSAGE, false),
        NeedToConfirm(
                R.string.lockpattern_need_to_confirm, LeftButtonMode.Cancel,
                RightButtonMode.ConfirmDisabled, ID_EMPTY_MESSAGE, true),
        ConfirmWrong(
                R.string.lockpattern_need_to_unlock_wrong, LeftButtonMode.Cancel,
                RightButtonMode.ConfirmDisabled, ID_EMPTY_MESSAGE, true),
        ChoiceConfirmed(
                R.string.lockpattern_pattern_confirmed_header, LeftButtonMode.Cancel,
                RightButtonMode.Confirm, ID_EMPTY_MESSAGE, false)
    }


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture_create)


        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init()


        left_image.setOnClickListener(this)
        tv_reload.setOnClickListener(this)

        AppConfig.I_USER = SPUtil.readObj(SPUtil.USER_PSD) as User
        if (AppConfig.I_USER != null) {
            account = AppConfig.I_USER!!.phone
        }

        // 初始化演示动画
        // mAnimatePattern.add(Cell.of(0, 0));
        // mAnimatePattern.add(Cell.of(0, 1));
        // mAnimatePattern.add(Cell.of(1, 1));
        // mAnimatePattern.add(Cell.of(2, 1));
        // mAnimatePattern.add(Cell.of(2, 2));

        gesturepwd_create_lockview.setOnPatternListener(mChooseNewLockPatternListener)
        gesturepwd_create_lockview.isTactileFeedbackEnabled = true

        initPreviewViews()
        if (savedInstanceState == null) {
            updateStage(Stage.Introduction)
            // updateStage(Stage.HelpScreen);
        } else {
            // restore from previous state
            val patternString = savedInstanceState.getString(KEY_PATTERN_CHOICE)
            if (patternString != null) {
                mChosenPattern = LockPatternUtils.stringToPattern(patternString)
            }
            updateStage(Stage.values()[savedInstanceState.getInt(KEY_UI_STAGE)])
        }

    }

    private fun initPreviewViews() {
        mPreviewViews = Array<Array<View?>>(3) { arrayOfNulls(3) }
        mPreviewViews[0][0] = findViewById(R.id.gesturepwd_setting_preview_0)
        mPreviewViews[0][1] = findViewById(R.id.gesturepwd_setting_preview_1)
        mPreviewViews[0][2] = findViewById(R.id.gesturepwd_setting_preview_2)
        mPreviewViews[1][0] = findViewById(R.id.gesturepwd_setting_preview_3)
        mPreviewViews[1][1] = findViewById(R.id.gesturepwd_setting_preview_4)
        mPreviewViews[1][2] = findViewById(R.id.gesturepwd_setting_preview_5)
        mPreviewViews[2][0] = findViewById(R.id.gesturepwd_setting_preview_6)
        mPreviewViews[2][1] = findViewById(R.id.gesturepwd_setting_preview_7)
        mPreviewViews[2][2] = findViewById(R.id.gesturepwd_setting_preview_8)
    }

    private fun updatePreviewViews() {
        if (mChosenPattern == null) {
            for (mPreviewView in mPreviewViews) {
                for (view in mPreviewView) {
                    view!!.setBackgroundResource(R.drawable.grey_point)
                }
            }
            return
        }
        Log.i("way", "result = " + mChosenPattern!!.toString())
        for (cell in mChosenPattern!!) {
            Log.i("way",
                    "cell.getRow() = " + cell.row + ", cell.getColumn() = " + cell.column)
            mPreviewViews[cell.row][cell.column]!!.setBackgroundResource(R.drawable.blue_point)

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_UI_STAGE, mUiStage.ordinal)
        if (mChosenPattern != null) {
            outState.putString(KEY_PATTERN_CHOICE, LockPatternUtils.patternToString(mChosenPattern))
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            //			if (App.getGestureLock().savedPatternExists(account)) {
            //				EventBus.getDefault().post(new GestureEvent(true));
            //			} else {
            //				EventBus.getDefault().post(new GestureEvent(false));
            //			}
            finish()
        }
        return false
    }

    private fun updateStage(stage: Stage) {
        mUiStage = stage
        if (stage == Stage.ChoiceTooShort) {
            gesturepwd_create_text.text = resources.getString(stage.headerMessage,
                    LockPatternUtils.MIN_LOCK_PATTERN_SIZE)
        } else {
            gesturepwd_create_text.setText(stage.headerMessage)
        }

        // same for whether the patten is enabled
        if (stage.patternEnabled) {
            gesturepwd_create_lockview.enableInput()
        } else {
            gesturepwd_create_lockview.disableInput()
        }

        gesturepwd_create_lockview.setDisplayMode(LockPatternView.DisplayMode.Correct)

        when (mUiStage) {
            GestureCreateActivity.Stage.Introduction -> gesturepwd_create_lockview.clearPattern()
            GestureCreateActivity.Stage.HelpScreen -> gesturepwd_create_lockview.setPattern(LockPatternView.DisplayMode.Animate, mAnimatePattern)
            GestureCreateActivity.Stage.ChoiceTooShort -> {
                gesturepwd_create_lockview.setDisplayMode(LockPatternView.DisplayMode.Wrong)
                postClearPatternRunnable()
            }
            GestureCreateActivity.Stage.FirstChoiceValid -> {
            }
            GestureCreateActivity.Stage.NeedToConfirm -> {
                gesturepwd_create_lockview.clearPattern()
                updatePreviewViews()
            }
            GestureCreateActivity.Stage.ConfirmWrong -> {
                gesturepwd_create_lockview.setDisplayMode(LockPatternView.DisplayMode.Wrong)
                postClearPatternRunnable()
            }
            GestureCreateActivity.Stage.ChoiceConfirmed -> {
            }
        }
    }

    // clear the wrong pattern unless they have started a new one
    // already
    private fun postClearPatternRunnable() {
        gesturepwd_create_lockview.removeCallbacks(mClearPatternRunnable)
        gesturepwd_create_lockview.postDelayed(mClearPatternRunnable, 2000)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.left_image -> {
                var num = SPUtil.getLockNum(AppConfig.I_USER?.phone)
                num++
                SPUtil.setLockNum(num, AppConfig.I_USER?.phone)
                AppConfig.I_USER?.signLock = false
                SPUtil.saveObj(AppConfig.I_USER, SPUtil.USER_PSD)
                finish()
            }
            R.id.tv_reload -> {
                tv_reload.visibility = View.GONE
                gesturepwd_create_lockview.clearPattern()
                mChosenPattern = null
                updateStage(Stage.Introduction)
                updatePreviewViews()
            }
        }
    }

    private fun saveChosenPatternAndFinish() {
        App.mLockPatternUtils.saveLockPattern(mChosenPattern, account)
        AppConfig.I_USER?.signLock = true
        SPUtil.saveObj(AppConfig.I_USER, SPUtil.USER_PSD)
        SPUtil.setLockNum(0, AppConfig.I_USER?.phone)
        ToastUtil.getInstance().makeShortToast(this, "手势密码设置成功")
        finish()
    }

    companion object {

        private val ID_EMPTY_MESSAGE = -1
        private val KEY_UI_STAGE = "uiStage"
        private val KEY_PATTERN_CHOICE = "chosenPattern"


        fun startActivity(context: Context) {
            val i = Intent(context, GestureCreateActivity::class.java)
            context.startActivity(i)
        }
    }


}
