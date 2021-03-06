package id.ac.ugm.smartcity.smarthome.View.Dashboard.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.AlertFragment;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.DeviceFragment;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.HistoryFragment;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.HomeFragment;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.ProfileFragment;

/**
 * Created by dito on 08/02/17.
 */

public class DashboardFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Home", "Device", "History", "Profile" };
    private int[] tabIcon = { R.drawable.ic_home, R.drawable.ic_router, R.drawable.ic_history, R.drawable.ic_profile};
    private Context context;
    private Service service;
    private DashboardView dashboardView;
    private Home home;

    public DashboardFragmentAdapter(FragmentManager fm, DashboardView dashboardView, Context context, Service service, Home home) {
        super(fm);
        this.context = context;
        this.service = service;
        this.home = home;
        this.dashboardView = dashboardView;
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.tab_menu_layout, null);
        TextView tv = (TextView) v.findViewById(R.id.title_tab);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.icon_tab);
        img.setImageResource(tabIcon[position]);
        return v;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeFragment.newInstance(position + 1, service, dashboardView, home);
            case 1:
                return DeviceFragment.newInstance(position + 1, service, dashboardView);
            case 2:
                return HistoryFragment.newInstance(position + 1, service, dashboardView);
            case 3:
                return ProfileFragment.newInstance(position + 1, service, dashboardView);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
