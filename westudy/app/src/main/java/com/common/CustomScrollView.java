package com.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/**
 * Created by baggajin on 14. 8. 18..
 */
public class CustomScrollView extends ScrollView {


    protected OnEdgeTouchListener onEdgeTouchListener;
    public static enum DIRECTION { TOP, BOTTOM, NONE };


    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public void setOnEdgeTouchListener(OnEdgeTouchListener l){
        this.onEdgeTouchListener = l;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (onEdgeTouchListener != null) {
            if (getScrollY() == 0)
                onEdgeTouchListener.onEdgeTouch(DIRECTION.TOP);
        } else if ((getScrollY() + getHeight()) == computeHorizontalScrollRange()) {
            onEdgeTouchListener.onEdgeTouch(DIRECTION.BOTTOM);
        } else {
            onEdgeTouchListener.onEdgeTouch(DIRECTION.NONE);
        }
    }

    public interface OnEdgeTouchListener {
        void onEdgeTouch(DIRECTION direction);
    }
}
