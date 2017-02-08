package id.ac.ugm.smartcity.smarthome;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardActivity extends AppCompatActivity {
    @BindView(R.id.tabDashboard)
    TabLayout tab;
    @BindView(R.id.viewPagerDashboard)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        pager.setAdapter(new DashboardFragmentAdapter(getSupportFragmentManager(),
                DashBoardActivity.this));
        tab.setupWithViewPager(pager);
    }
}
