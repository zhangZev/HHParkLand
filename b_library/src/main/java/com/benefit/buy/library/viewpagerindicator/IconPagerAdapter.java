package com.benefit.buy.library.viewpagerindicator;

public interface IconPagerAdapter {

    /**
     * Get icon representing the page at {@code index} in the adapter.
     */
    int getIconResId(int index);

    // From PagerAdapter
    int getCount();

    int getIconResclickId(int index);

    //TODO qyl
    int getItemHint(int index);
}
