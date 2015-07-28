package com.leo.viewpagerscrollindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.leo.viewpagerscrollindicator.library.TabIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabIndicator.OnItemClickListener,
                                                                TabIndicator.OnPageChangeListener {
    private TabIndicator indicator;
    private ViewPager viewPager;
    final List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<10; i++){
            titles.add("title-" + i);
        }

        indicator = (TabIndicator)findViewById(R.id.indicator);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles.size()));
        indicator.setWidth(getResources().getDisplayMetrics().widthPixels);
        indicator.setTabs(titles);
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(this);
        indicator.setOnItemClickListener(this);
        indicator.invalidate();
    }



    @Override
    public void onItemClick(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private int count;

        public ViewPagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.count = count;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Fragment getItem(int i) {
            return new PagerItem();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PagerItem pagerItem = (PagerItem)super.instantiateItem(container, position);
            pagerItem.setText(titles.get(position));

            return pagerItem;
        }


    }

}
