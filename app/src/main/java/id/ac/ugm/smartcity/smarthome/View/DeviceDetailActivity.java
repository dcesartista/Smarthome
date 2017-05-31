package id.ac.ugm.smartcity.smarthome.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;

public class DeviceDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ic_down)
    TextView icDown;
    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.tv_humidity)
    TextView tvHum;
    @BindView(R.id.tv_co2)
    TextView tvCo2;
    @BindView(R.id.tv_light)
    TextView tvLight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_ac)
    Toolbar toolbarAC;
    @BindView(R.id.tv_ac)
    TextView tvAc;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.toolbar_relay1)
    Toolbar toolbarRelay1;
    @BindView(R.id.tv_relay1)
    TextView tvRelay1;
    @BindView(R.id.toggle1)
    Switch toggle1;
    @BindView(R.id.toolbar_relay2)
    Toolbar toolbarRelay2;
    @BindView(R.id.tv_relay2)
    TextView tvRelay2;
    @BindView(R.id.toggle2)
    Switch toggle2;
    @BindView(R.id.toolbar_relay3)
    Toolbar toolbarRelay3;
    @BindView(R.id.tv_relay3)
    TextView tvRelay3;
    @BindView(R.id.toggle3)
    Switch toggle3;
    @BindView(R.id.toolbar_relay4)
    Toolbar toolbarRelay4;
    @BindView(R.id.tv_relay4)
    TextView tvRelay4;
    @BindView(R.id.toggle4)
    Switch toggle4;
    @BindView(R.id.toolbar_relay5)
    Toolbar toolbarRelay5;
    @BindView(R.id.tv_relay5)
    TextView tvRelay5;
    @BindView(R.id.toggle5)
    Switch toggle5;
    @BindView(R.id.toolbar_relay6)
    Toolbar toolbarRelay6;
    @BindView(R.id.tv_relay6)
    TextView tvRelay6;
    @BindView(R.id.toggle6)
    Switch toggle6;
    @BindView(R.id.toolbar_relay7)
    Toolbar toolbarRelay7;
    @BindView(R.id.tv_relay7)
    TextView tvRelay7;
    @BindView(R.id.toggle7)
    Switch toggle7;
    @BindView(R.id.toolbar_relay8)
    Toolbar toolbarRelay8;
    @BindView(R.id.tv_relay8)
    TextView tvRelay8;
    @BindView(R.id.toggle8)
    Switch toggle8;

    @Inject
    public Service service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        ButterKnife.bind(this);
        getDeps().inject(this);


    }
}
