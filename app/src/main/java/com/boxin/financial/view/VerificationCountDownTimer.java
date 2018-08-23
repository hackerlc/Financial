package com.boxin.financial.view;

import android.os.CountDownTimer;

/**
 * class info
 *
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/18
 */
public class VerificationCountDownTimer extends CountDownTimer {
    /**
     * LoginFastValue
     */
    public static long curMillis =0;
    public static boolean FLAG_FIRST_IN =true;
    /**
     * register value
     */
    public static long curMillisRegister =0;
    public static boolean FLAG_FIRST_IN_Register =true;
    /**
     * password value
     */
    public static long curMillisPassword =0;
    public static boolean FLAG_FIRST_IN_Password =true;
    /**
     * changeOldPhone
     */
    public static long curMillisChangeOldPhone =0;
    public static boolean FLAG_FIRST_IN_ChangeOldPhone =true;
    /**
     * newPhone
     */
    public static long curMillisNewPhone =0;
    public static boolean FLAG_FIRST_IN_NewPhone =true;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public VerificationCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {

    }

    /**
     * 设置时间
     * @param onClick
     * @param type 0 LoginFast 1 Register 2 Password 3 changeOldPhone 4 newPhone
     */
    public void timerStart(boolean onClick,int type){
        switch (type){
            case 0: {
                if(onClick) {
                    VerificationCountDownTimer.curMillis= System.currentTimeMillis();
                }
                VerificationCountDownTimer.FLAG_FIRST_IN=false;
                break;
            }
            case 1: {
                if(onClick) {
                    VerificationCountDownTimer.curMillisRegister= System.currentTimeMillis();
                }
                VerificationCountDownTimer.FLAG_FIRST_IN_Register=false;
                break;
            }
            case 2: {
                if(onClick) {
                    VerificationCountDownTimer.curMillisPassword= System.currentTimeMillis();
                }
                VerificationCountDownTimer.FLAG_FIRST_IN_Password=false;
                break;
            }
            case 3: {
                if(onClick) {
                    VerificationCountDownTimer.curMillisChangeOldPhone= System.currentTimeMillis();
                }
                VerificationCountDownTimer.FLAG_FIRST_IN_ChangeOldPhone=false;
                break;
            }
            case 4: {
                if(onClick) {
                    VerificationCountDownTimer.curMillisNewPhone= System.currentTimeMillis();
                }
                VerificationCountDownTimer.FLAG_FIRST_IN_NewPhone=false;
                break;
            }
            default:{
                if(onClick) {
                    VerificationCountDownTimer.curMillis= System.currentTimeMillis();
                }
                VerificationCountDownTimer.FLAG_FIRST_IN=false;
                break;
            }
        }
        this.start();
    }
}
