package com.leo.viewpagerscrollindicator.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by littleming on 15/7/28.
 */
public class TabIndicator extends HorizontalScrollView {
    private Context context;
    private IndicatorLayout tabLayout;
    private int count = 0;
    private int visibleCount = 5;
    private int width;
    private ViewPager viewPager;
    private OnItemClickListener onItemClickListener;
    private OnPageChangeListener onPageChangeListener;
    private List<String> titles;
    private static final int COLOR_TEXT_NORMAL = 0xff989898;
    private static final int TEXT_SIZE_NORMAL  = 14;

    private static final int COLOR_TEXT_HIGHLIGHT_COLOR = 0xFF000000;
    private static final int TEXT_SIZE_SELETED = 18;

    public TabIndicator(Context context) {
        super(context);
        this.context = context;
        initTabLayout();
        addView(tabLayout);
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initTabLayout();
        addView(tabLayout);
    }

    public TabIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initTabLayout();
        addView(tabLayout);
    }

    private void initTabLayout(){
        tabLayout = new IndicatorLayout(context);
        tabLayout.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setTabs(List<String> titles){
        count = titles.size();
        this.titles = titles;
        for (String title : titles) {
            tabLayout.addView(generateTextView(title));
        }
        setItemClickListener();
        highLightTextView(0);
    }

    private TextView generateTextView(String text) {
        DrawableCenterTextView tv = new DrawableCenterTextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.width = width / visibleCount;
        lp.setMargins(0, 20, 0, 0);
        tv.setGravity(Gravity.CENTER);
        tv.setCompoundDrawablePadding(dip2px(5));
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_NORMAL);
        tv.setLayoutParams(lp);
        return tv;
    }

    private void setTextSize(int position, float textSize){
        Log.i("TextSize", textSize + "");
        View view = tabLayout.getChildAt(position);
        if (view instanceof TextView) {
            TextView tv = ((TextView) view);

            tv.setTextSize(textSize);
        }
    }

    protected void highLightTextView(int position) {
        View view = tabLayout.getChildAt(position);
        if (view instanceof TextView) {
            TextView tv = ((TextView) view);
            tv.setTextColor(COLOR_TEXT_HIGHLIGHT_COLOR);
            tv.setTextSize(TEXT_SIZE_SELETED);
        }

    }

    private void resetTextViewColor() {
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            View view = tabLayout.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = ((TextView) view);
                tv.setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    private void resetTextViewSize() {
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            View view = tabLayout.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = ((TextView) view);
                tv.setTextSize(TEXT_SIZE_NORMAL);
            }
        }
    }

    public void setViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        setPagerChangerListener();
    }

    private void setPagerChangerListener(){
        if(viewPager != null){
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    tabLayout.scroll(position, positionOffset);
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(final int position) {
                    Log.i("pageEvent", "selected");
                    final int itemWidth = tabLayout.getWidth() / count;
                    TabIndicator.this.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(position <= 1){
                                TabIndicator.this.smoothScrollTo(0, 0);
                            }else if(position > 1 && position < (count - 3)){
                                TabIndicator.this.smoothScrollTo(((position - 2) * itemWidth), 0);
                            }else if(position == count - 1){
                                // not scroll
                            }else{
                                TabIndicator.this.smoothScrollTo((tabLayout.getWidth() - ((visibleCount - 1) * itemWidth)), 0);
                            }
                        }
                    }, 300);
                    onPageChangeListener.onPageSelected(position);
                    resetTextViewColor();
                    resetTextViewSize();
                    highLightTextView(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            });
        }
    }

    private void setItemClickListener(){
        for(int i = 0; i < tabLayout.getChildCount() ; i ++){
            final int poi = i;
            View view = tabLayout.getChildAt(poi);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(poi);
                    resetTextViewSize();
                    resetTextViewColor();
                    highLightTextView(poi);
                    if(viewPager != null){
                        viewPager.setCurrentItem(poi);
                    }
                }
            });
        }
    }

    public void notifyDataChanged(List<String> newTitles, int lastPosition){
        String lastTitle = titles.get(lastPosition);
        titles.clear();
        titles.addAll(newTitles);
        tabLayout.removeAllViews();
        count = titles.size();
        for (String title : titles) {
            tabLayout.addView(generateTextView(title));
        }
        tabLayout.addView(generateTextView(" "));
        count ++;
        setItemClickListener();
        if(titles.contains(lastTitle)){
            for(int i=0;i<titles.size();i++){
                if(lastTitle.equals(titles.get(i))){
                    highLightTextView(i);
                    viewPager.setCurrentItem(i);
                }
            }
        }else{
            highLightTextView(0);
            viewPager.setCurrentItem(0);
        }
        invalidate();
    }

    public int dip2px(int paramInt) {
        return (int) (0.5D + context.getResources().getDisplayMetrics().density * paramInt);
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener){
        this.onPageChangeListener = onPageChangeListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

    private class IndicatorLayout extends LinearLayout {
        private Paint mPaint;
        private int mLineWidth;
        private float mTranslationX;


        public IndicatorLayout(Context context) {
            super(context);
            initPaint();
        }

        public IndicatorLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            initPaint();
        }

        private void initPaint(){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.parseColor("#ff7a05"));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(4);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            canvas.save();
            canvas.translate(mTranslationX, getHeight() - mPaint.getStrokeWidth() + 0.5f);
            canvas.drawLine(0, 0, mLineWidth, 0, mPaint);
            canvas.restore();
            super.dispatchDraw(canvas);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            if(count > 0){
                mLineWidth = w / count;
            }
        }

        public void scroll(int position, float offset){
            if(count > 0) {
                if(position == (count - 1)){

                }else {
                    mTranslationX = getWidth() / count * (position + offset);
                    postInvalidate();
                }
            }
        }
    }
}
