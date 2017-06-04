package id.ac.ugm.smartcity.smarthome.View.Dashboard;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Presenter.DashboardPresenter;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.adapter.DashboardFragmentAdapter;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.BaseActivity;
import retrofit2.Response;

public class DashBoardActivity extends BaseActivity implements DashboardView {
    @BindView(R.id.tabDashboard)
    TabLayout tabLayout;
    @BindView(R.id.viewPagerDashboard)
    ViewPager pager;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.sp_home)
    Spinner spHome;
    @BindView(R.id.ll_home_selector)
    View homeSelector;
    @BindView(R.id.card_setting)
    View cardSetting;
    @BindView(R.id.ic_gear)
    TextView icGear;
    @BindView(R.id.ic_down)
    TextView icDown;

    DashboardFragmentAdapter adapter;
    @Inject
    public Service service;

    private List<Home> homes;
    private String[] homeNames;
    private String homeId;
    private SharedPreferences preferences;
    private DashboardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);

        preferences = getSharedPreferences(App.USER_PREFERENCE,MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");

        presenter = new DashboardPresenter(service, this, this);
        presenter.getHomes();

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icGear, iconFont);
        FontManager.markAsIconContainer(icDown, iconFont);

        adapter = new DashboardFragmentAdapter(getSupportFragmentManager(), DashBoardActivity.this, this, service);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
    }

    @Override
    public void setToolbarText(String text) {
        tvToolbar.setText(text);
    }

    @Override
    public void showHomes(Response<List<Home>> response) {
        Log.e("HMMMMzzz", String.valueOf(response.code()));
        SharedPreferences.Editor editor = getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        /*if(response.code() == 200) {
            editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
            editor.putString(App.CLIENT, response.headers().get("Client"));
            editor.putString(App.EXPIRY, response.headers().get("Expiry"));
            editor.putString(App.UID, response.headers().get("Uid"));
            editor.commit();
        }*/
        Log.e("HMMMMppp222","sss"+getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACCESS_TOKEN,""));
        homes = response.body();
        int i = 0;
        homeNames = new String[homes.size()];
        int selected = 0;
        for (Home home: homes){
            if (String.valueOf(home.getId()).equals(homeId)){
                selected = homes.indexOf(home);
            }
            homeNames[i] = home.getName();
            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                homeNames);
        spHome.setAdapter(adapter);
        spHome.setSelection(selected);
    }

    @Override
    public void setSettingVisibility(int visibility) {
        cardSetting.setVisibility(visibility);
    }

    @Override
    public void setHomeSelectorVisibility(int visibility) {
        homeSelector.setVisibility(visibility);
    }
}
