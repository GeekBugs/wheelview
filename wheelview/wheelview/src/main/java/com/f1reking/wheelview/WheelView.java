package com.f1reking.wheelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuangYH on 2015/12/28.
 * 滚轮控件
 */
public class WheelView extends ScrollView {

    private Context context;
    private LinearLayout views;
    private int highColor;

    public WheelView(Context context) {
        super(context);
        init(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        highColor = ta.getColor(R.styleable.WheelView_highColor, 0);
        ta.recycle();
        init(context);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        highColor = ta.getColor(R.styleable.WheelView_highColor, 0);
        ta.recycle();
        init(context);
    }


    List<String> items;

    private List<String> getItems() {
        return items;
    }

    public void setItems(List<String> list) {
        if (null == items) {
            items = new ArrayList<String>();
        }
        items.clear();
        items.addAll(list);

        for (int i = 0; i < offset; i++) {
            items.add(0, "");
            items.add("");
        }
        initData();
    }

    public static final int OFF_SET_DEFAULT = 1;
    int offset = OFF_SET_DEFAULT;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    int displayItemCount;
    int selectedIndex = 1;

    private void init(Context context) {
        this.context = context;
        this.setVerticalScrollBarEnabled(false);
        views = new LinearLayout(context);
        views.setOrientation(LinearLayout.VERTICAL);
        this.addView(views);
        scrollerTask = new Runnable() {

            public void run() {
                int newY = getScrollY();
                if (initialY - newY == 0) { // stopped
                    final int remainder = initialY % itemHeight;
                    final int divided = initialY / itemHeight;
                    if (remainder == 0) {
                        selectedIndex = divided + offset;
                        onSeletedCallBack();
                    } else {
                        if (remainder > itemHeight / 2) {
                            WheelView.this.post(new Runnable() {

                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY
                                            - remainder + itemHeight);
                                    selectedIndex = divided + offset + 1;
                                    onSeletedCallBack();
                                }
                            });
                        } else {
                            WheelView.this.post(new Runnable() {

                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY
                                            - remainder);
                                    selectedIndex = divided + offset;
                                    onSeletedCallBack();
                                }
                            });
                        }
                    }
                } else {
                    initialY = getScrollY();
                    WheelView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    int initialY;
    Runnable scrollerTask;
    int newCheck = 50;

    public void startScrollerTask() {
        initialY = getScrollY();
        this.postDelayed(scrollerTask, newCheck);
    }

    private void initData() {
        displayItemCount = offset * 2 + 1;
        for (String item : items) {
            views.addView(createView(item));
        }
        refreshItemView(0);
    }

    int itemHeight = 0;

    private TextView createView(String item) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setSingleLine(true);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics());
        tv.setPadding(padding, padding, padding, padding);
        if (0 == itemHeight) {
            itemHeight = getViewMeasuredHeight(tv);
            views.setLayoutParams(new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, itemHeight
                    * displayItemCount));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this
                    .getLayoutParams();
            this.setLayoutParams(new LinearLayout.LayoutParams(lp.width,
                    itemHeight * displayItemCount));
        }
        return tv;
    }

    public static int getViewMeasuredHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(t);
        if (t > oldt) {
            scrollDirection = SCROLL_DIRECTION_DOWN;
        } else {
            scrollDirection = SCROLL_DIRECTION_UP;
        }
    }

    private void refreshItemView(int y) {
        int position = y / itemHeight + offset;
        int remainder = y % itemHeight;
        int divided = y / itemHeight;
        if (remainder == 0) {
            position = divided + offset;
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1;
            }
        }
        int childSize = views.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) views.getChildAt(i);
            if (null == itemView) {
                return;
            }
            if (position == i) {
                itemView.setTextColor(highColor);
                itemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            } else {
                itemView.setTextColor(Color.parseColor("#969696"));
                itemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
        }
    }

    int[] selectedAreaBorder;

    private int[] obtainSelectedAreaBorder() {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = new int[2];
            selectedAreaBorder[0] = itemHeight * offset;
            selectedAreaBorder[1] = itemHeight * (offset + 1);
        }
        return selectedAreaBorder;
    }

    private int scrollDirection = -1;
    private static final int SCROLL_DIRECTION_UP = 0;
    private static final int SCROLL_DIRECTION_DOWN = 1;
    Paint paint;
    int viewWidth;

    @SuppressWarnings("deprecation")
    @Override
    public void setBackgroundDrawable(Drawable background) {
        if (null == paint) {
            paint = new Paint();
            paint.setColor(highColor);
            paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        }
        background = new Drawable() {

            @Override
            public void draw(Canvas canvas) {
                canvas.drawLine(viewWidth * 1 / 20,
                        obtainSelectedAreaBorder()[0], viewWidth * 18 / 20,
                        obtainSelectedAreaBorder()[0], paint);
                canvas.drawLine(viewWidth * 1 / 20,
                        obtainSelectedAreaBorder()[1], viewWidth * 18 / 20,
                        obtainSelectedAreaBorder()[1], paint);
            }

            @Override
            public void setAlpha(int alpha) {
            }

            @Override
            public void setColorFilter(ColorFilter cf) {
            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        super.setBackgroundDrawable(background);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        setBackgroundDrawable(null);
    }

    /**
     * 选中回调
     */
    private void onSeletedCallBack() {
        if (null != onWheelViewListener) {
            onWheelViewListener.onSelected(selectedIndex,
                    items.get(selectedIndex));
        }
    }

    public void setSeletion(int position) {
        final int p = position;
        selectedIndex = p + offset;
        this.post(new Runnable() {

            @Override
            public void run() {
                WheelView.this.smoothScrollTo(0, p * itemHeight);
            }
        });
    }

    public String getSeletedItem() {
        return items.get(selectedIndex);
    }

    public int getSeletedIndex() {
        return selectedIndex - offset;
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    private OnWheelViewListener onWheelViewListener;

    public OnWheelViewListener getOnWheelViewListener() {
        return onWheelViewListener;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener) {
        this.onWheelViewListener = onWheelViewListener;
    }

    public static class OnWheelViewListener {

        public void onSelected(int selectedIndex, String item) {
        }
    }
}
