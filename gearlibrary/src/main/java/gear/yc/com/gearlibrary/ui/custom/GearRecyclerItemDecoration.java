package gear.yc.com.gearlibrary.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import gear.yc.com.gearlibrary.utils.ConvertPadPlus;

/**
 * GearApplication
 * Created by YichenZ on 2016/3/30 17:24.
 */
public class GearRecyclerItemDecoration extends RecyclerView.ItemDecoration{

    /**
     *     use android divider
     */
    private static final int[] ARRTS =new int[]{
            android.R.attr.listDivider
    };
    /** h or  v line */
    private int mOrientation;

    private Drawable mDivider;

    private Paint paint;

    private int intrinsic;

    public GearRecyclerItemDecoration(Context context,int orientation,int intrinsic){
        final TypedArray a =context.obtainStyledAttributes(ARRTS);
        mDivider=a.getDrawable(0);
        this.intrinsic = ConvertPadPlus.dip2px(context,intrinsic);
        a.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0x00000000);
        setOrientation(orientation);
    }

    private void setOrientation(int mOrientation){
        if(mOrientation!= LinearLayoutManager.HORIZONTAL && mOrientation!=LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = mOrientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(mOrientation == LinearLayoutManager.VERTICAL){
            drawVertical(c,parent);
        }else{
            drawHorizontal(c,parent);
        }
    }



    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);
            float top = view.getTop();
            float left = parent.getPaddingLeft();
            float bottom = view.getTop() - intrinsic;
            float right = parent.getWidth() - parent.getPaddingRight();
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        if (parent.getChildAdapterPosition(view) != 0) {
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, intrinsic);
            } else {
                outRect.set(0, 0, intrinsic, 0);
            }
//        }
    }


}
