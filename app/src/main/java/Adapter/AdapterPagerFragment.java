package Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter fragment viewpager
 */

public class AdapterPagerFragment extends FragmentPagerAdapter {
    private List<Fragment> mFragment = new ArrayList<>();
    private List<String> mFragmentTitle= new ArrayList<>();

    public void addFragment(Fragment fragment,String title) {
        mFragment.add(fragment);
        mFragmentTitle.add(title);
    }

    public AdapterPagerFragment(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitle.get(position);
    }
}
