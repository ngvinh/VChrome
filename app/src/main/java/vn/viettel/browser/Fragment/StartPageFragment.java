package vn.viettel.browser.Fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import acr.browser.lightning.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import vn.viettel.browser.adapter.StartPageFragmentAdapter;
import vn.viettel.browser.utils.widget.SlidingTabLayout;

/**
 * Created by vinh on 7/30/15.
 */
public class StartPageFragment extends BaseFragment{


    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;

    StartPageFragmentAdapter mViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_page_layout, container, false);
        ButterKnife.bind(this, view);
        mViewPagerAdapter = new StartPageFragmentAdapter(getActivity().getSupportFragmentManager(),
                getActivity());
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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
