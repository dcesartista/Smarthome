package id.ac.ugm.smartcity.smarthome;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;

public class DashBoardActivity extends BaseActivity implements DashBoardView {
    @BindView(R.id.tabDashboard)
    TabLayout tabLayout;
    @BindView(R.id.viewPagerDashboard)
    ViewPager pager;
    DashboardFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        adapter = new DashboardFragmentAdapter(getSupportFragmentManager(), DashBoardActivity.this, this);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getAlertSuccess(Alert alert) {

    }
}
