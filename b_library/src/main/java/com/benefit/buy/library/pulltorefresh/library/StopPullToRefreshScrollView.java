/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.benefit.buy.library.pulltorefresh.library;

import com.benefit.buy.library.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class StopPullToRefreshScrollView extends PullToRefreshBase<ScrollView> {

    public StopPullToRefreshScrollView(Context context) {
        super(context);
    }

    public StopPullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StopPullToRefreshScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public StopPullToRefreshScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    private OnScrollListener onScrollListener;

    /**
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
     */
    private int lastScrollY;

    @Override
    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        ScrollView scrollView;
        if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            scrollView = new InternalScrollViewSDK9(context, attrs);
        }
        else {
            scrollView = new ScrollView(context, attrs);
        }
        scrollView.setId(R.id.scrollview);
        return scrollView;
    }

    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 滚动的回调接口
     * @author xiaanming
     */
    public interface OnScrollListener {

        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY 、
         */
        public void onScroll(int scrollY);
    }

    @Override
    protected boolean isReadyForPullStart() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }

    @TargetApi(9)
    final class InternalScrollViewSDK9 extends ScrollView {

        public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            if (onScrollListener != null) {
                onScrollListener.onScroll(lastScrollY = scrollY);
            }
            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                    maxOverScrollX, maxOverScrollY, isTouchEvent);
            // Does all of the hard work...
            OverscrollHelper.overScrollBy(StopPullToRefreshScrollView.this, deltaX, scrollX, deltaY, scrollY,
                    getScrollRange(), isTouchEvent);
            return returnValue;
        }

        /**
         * Taken from the AOSP ScrollView source
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
    }
}
