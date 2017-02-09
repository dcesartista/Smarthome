package id.ac.ugm.smartcity.smarthome;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;

public class DashBoardActivity extends BaseActivity {
    @BindView(R.id.tabDashboard)
    TabLayout tabLayout;
    @BindView(R.id.viewPagerDashboard)
    ViewPager pager;
    DashboardFragmentAdapter adapter;
    @Inject
    public  Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        adapter = new DashboardFragmentAdapter(getSupportFragmentManager(), DashBoardActivity.this, this, service);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
    }
}
