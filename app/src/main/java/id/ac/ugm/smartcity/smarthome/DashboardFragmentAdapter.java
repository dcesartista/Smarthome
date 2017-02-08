package id.ac.ugm.smartcity.smarthome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dito on 08/02/17.
 */

public class DashboardFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Home", "Alert", "Device", "History", "Profile" };
    private Context context;

    public DashboardFragmentAdapter(FragmentManager fm, DashBoardActivity dashBoardActivity) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeFragment.newInstance(position + 1);
            case 1:
                return AlertFragment.newInstance(position + 1);
            case 2:
                return DeviceFragment.newInstance(position + 1);
            case 3:
                return HistoryFragment.newInstance(position + 1);
            case 4:
                return ProfileFragment.newInstance(position + 1);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
