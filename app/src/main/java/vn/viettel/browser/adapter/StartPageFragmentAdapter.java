package vn.viettel.browser.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import acr.browser.lightning.R;
import vn.viettel.browser.Fragment.HomePageStartPageFragment;

/**
 * Created by vinh on 7/30/15.
 */
public class StartPageFragmentAdapter extends FragmentPagerAdapter{

    private String[] tabTitles;

    private Context mContext;

    public StartPageFragmentAdapter(FragmentManager fm, Context context){
        super(fm);
        this.mContext = context;
        tabTitles = new String[]{
                mContext.getString(R.string.home_page),
                mContext.getString(R.string.bookmark),
        };
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomePageStartPageFragment.newInstance(R.layout.phone_start_page_fragment, mContext);
            case 1:
                return HomePageStartPageFragment.newInstance(R.layout.phone_start_page_fragment, mContext);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
