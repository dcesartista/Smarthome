package id.ac.ugm.smartcity.smarthome.View;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.CurrentSensor;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.DeviceDetailPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.NumberFormatter;
import retrofit2.Response;

public class DeviceDetailActivity extends BaseActivity implements DeviceDetailView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ic_back)
    TextView icBack;
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
    @BindView(R.id.pb_temp)
    ProgressBar pbTemp;
    @BindView(R.id.pb_hum)
    ProgressBar pbHum;
    @BindView(R.id.pb_co2)
    ProgressBar pbCo2;
    @BindView(R.id.pb_light)
    ProgressBar pbLight;
    @BindView(R.id.iv_relay1)
    ImageView ivRelay1;
    @BindView(R.id.iv_relay2)
    ImageView ivRelay2;
    @BindView(R.id.iv_relay3)
    ImageView ivRelay3;
    @BindView(R.id.iv_relay4)
    ImageView ivRelay4;
    @BindView(R.id.iv_relay5)
    ImageView ivRelay5;
    @BindView(R.id.iv_relay6)
    ImageView ivRelay6;
    @BindView(R.id.iv_relay7)
    ImageView ivRelay7;
    @BindView(R.id.iv_relay8)
    ImageView ivRelay8;

    @Inject
    public Service service;

    private DeviceDetailPresenter presenter;
    private Device device;
    private Relay relay;
    private String relayId;
    private Switch[] toggles;
    private TextView[] tvRelayNames;
    private ImageView[] ivRelays;
    private ProgressDialog progressDialog;
    private int[] relayData;
    private String[] relayNameData;
    private final BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getCurrentSensorData(device.getId().toString());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        ButterKnife.bind(this);
        getDeps().inject(this);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icBack, iconFont);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));

        device = (Device) getIntent().getSerializableExtra(Device.ID);
        tvTitle.setText(device.getName());
        ivRelays = new ImageView[]{ivRelay1, ivRelay2, ivRelay3, ivRelay4, ivRelay5, ivRelay6, ivRelay7, ivRelay8};
        toggles = new Switch[]{toggle1,toggle2,toggle3,toggle4,toggle5,toggle6,toggle7,toggle8};
        presenter = new DeviceDetailPresenter(service, this, this);
        presenter.getCurrentSensorData(device.getId().toString());
        presenter.getRelayData(device.getId().toString());
        registerReceiver(updateReceiver, new IntentFilter(App.UPDATE_SENSOR));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateReceiver);
    }

    private void updateUI(){
        tvRelayNames = new TextView[]{tvRelay1,tvRelay2,tvRelay3,tvRelay4,tvRelay5,tvRelay6,tvRelay7,tvRelay8};
        for(int i=0; i< toggles.length; i++) {
            tvRelayNames[i].setText(relayNameData[i]);
            Log.e("RELAY "+(i+1),relayData[i]+"");
            if (relayData[i] == 1) {
                ivRelays[i].setImageResource(R.drawable.ic_lamp_yellow);
                toggles[i].setChecked(true);
            } else {
                ivRelays[i].setImageResource(R.drawable.ic_lamp);
                toggles[i].setChecked(false);
            }
        }
    }

    @OnClick(R.id.card_ac)
    void goToAc(){
        Intent intent = new Intent(this, ACActivity.class);
        intent.putExtra(Device.ID,device);
        intent.putExtra(Relay.ID,relay);
        startActivity(intent);
    }

    @OnClick(R.id.ic_back)
    void back(){
        super.onBackPressed();
    }

    @OnClick(R.id.toggle1)
    void setToggle1(Switch tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_1,"1");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_1,"0");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        }
    }

    @OnClick(R.id.toggle2)
    void setToggle2(Switch tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_2,"1");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_2,"0");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        }
    }

    @OnClick(R.id.toggle3)
    void setToggle3(Switch tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_3,"1");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_3,"0");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        }
    }

    @OnClick(R.id.toggle4)
    void setToggle4(Switch tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_4,"1");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_4,"0");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        }
    }

    @OnClick(R.id.toggle5)
    void setToggle5(Switch tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_5,"1");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_5,"0");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        }
    }

    @OnClick(R.id.toggle6)
    void setToggle6(Switch tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_6,"1");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_6,"0");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        }
    }

    @OnClick(R.id.toggle7)
    void setToggle7(Switch tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_7,"1");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_7,"0");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        }
    }

    @OnClick(R.id.toggle8)
    void setToggle8(Switch tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_8,"1");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_8,"0");
            presenter.changeRelayData(device.getId().toString(),relayId,params);
        }
    }

    @Override
    public void showProgressBar() {
        tvTemp.setVisibility(View.GONE);
        tvHum.setVisibility(View.GONE);
        tvCo2.setVisibility(View.GONE);
        tvLight.setVisibility(View.GONE);
        pbTemp.setVisibility(View.VISIBLE);
        pbHum.setVisibility(View.VISIBLE);
        pbCo2.setVisibility(View.VISIBLE);
        pbLight.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        tvTemp.setVisibility(View.VISIBLE);
        tvHum.setVisibility(View.VISIBLE);
        tvCo2.setVisibility(View.VISIBLE);
        tvLight.setVisibility(View.VISIBLE);
        pbTemp.setVisibility(View.GONE);
        pbHum.setVisibility(View.GONE);
        pbCo2.setVisibility(View.GONE);
        pbLight.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void showDeviceData(Response<Device> response) {

    }

    @Override
    public void showRelayStatus(Response<Relay> response) {
        relay = response.body();
        relayId = relay.getId().toString();
        relayData= new int[]{relay.getRelay1(),relay.getRelay2(),relay.getRelay3(),relay.getRelay4(),
                relay.getRelay5(),relay.getRelay6(),relay.getRelay7(),relay.getRelay8()};
        relayNameData= new String[]{relay.getRelay1name(),relay.getRelay2name(),relay.getRelay3name(),relay.getRelay4name(),
                relay.getRelay5name(),relay.getRelay6name(),relay.getRelay7name(),relay.getRelay8name()};
        updateUI();
    }

    @Override
    public void showCurrentSensorData(Response<CurrentSensor> response) {
        CurrentSensor currentSensor = response.body();
        pbTemp.setVisibility(View.GONE);
        pbHum.setVisibility(View.GONE);
        pbCo2.setVisibility(View.GONE);
        pbLight.setVisibility(View.GONE);
        tvTemp.setText(String.valueOf(currentSensor.getTemperature()));
        tvTemp.setVisibility(View.VISIBLE);
        tvHum.setText(NumberFormatter.formatWithDots(currentSensor.getHumidity()));
        tvHum.setVisibility(View.VISIBLE);
        tvCo2.setText(String.valueOf(currentSensor.getCo2()));
        tvCo2.setVisibility(View.VISIBLE);
        tvLight.setText(String.valueOf(currentSensor.getFlux()));
        tvLight.setVisibility(View.VISIBLE);

    }

    @Override
    public void changeRelayStatus(Response<Relay> response) {
        relay = response.body();
        relayId = relay.getId().toString();
        relayData= new int[]{relay.getRelay1(),relay.getRelay2(),relay.getRelay3(),relay.getRelay4(),
                relay.getRelay5(),relay.getRelay6(),relay.getRelay7(),relay.getRelay8()};
        relayNameData= new String[]{relay.getRelay1name(),relay.getRelay2name(),relay.getRelay3name(),relay.getRelay4name(),
                relay.getRelay5name(),relay.getRelay6name(),relay.getRelay7name(),relay.getRelay8name()};
        updateUI();
    }
}
