package vn.viettel.browser.utils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import acr.browser.lightning.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import vn.viettel.browser.adapter.StartPageFragmentAdapter;

/**
 * Created by vinh on 7/31/15.
 */
public class BaseStartPageLayout extends LinearLayout{

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;

    StartPageFragmentAdapter mViewPagerAdapter;

    private FragmentActivity mActivity;

    public BaseStartPageLayout(FragmentActivity activity){
        super(activity);
        this.mActivity = activity;
        initView();
    }

    public BaseStartPageLayout(Context context) {
        super(context);
        initView();
    }

    public BaseStartPageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BaseStartPageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

//    public abstract FragmentActivity getActivity();

    private void initView(){
//        this.mActivity = getActivity();
        View view = inflate(getContext(), R.layout.start_page_layout, null);
        ButterKnife.bind(this, view);
        mViewPagerAdapter = new StartPageFragmentAdapter(mActivity.getSupportFragmentManager(),
                mActivity);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setOffscreenPageLimit(2);

        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

        Resources res = getResources();
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.green_btn));
        mSlidingTabLayout.setDistributeEvenly(true);

        mSlidingTabLayout.setViewPager(mViewPager);

        if (mSlidingTabLayout != null) {
            mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
//                    enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
                }
            });
        }
        addView(view);
        initModels();
    }

    private void initModels(){

    }
}
