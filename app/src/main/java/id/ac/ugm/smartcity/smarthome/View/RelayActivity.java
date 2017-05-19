package id.ac.ugm.smartcity.smarthome.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.RelayPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.ACUtils;
import retrofit2.Response;

public class RelayActivity extends BaseActivity implements RelayView {

    @BindView(R.id.toggle1)
    ToggleButton toggle1;
    @BindView(R.id.toggle2)
    ToggleButton toggle2;
    @BindView(R.id.toggle3)
    ToggleButton toggle3;
    @BindView(R.id.toggle4)
    ToggleButton toggle4;
    @BindView(R.id.toggle5)
    ToggleButton toggle5;
    @BindView(R.id.toggle6)
    ToggleButton toggle6;
    @BindView(R.id.toggle7)
    ToggleButton toggle7;
    @BindView(R.id.toggle8)
    ToggleButton toggle8;
    @BindView(R.id.ac_power)
    ToggleButton acPower;
    @BindView(R.id.ac_container)
    View acContainer;
    @BindView(R.id.ac_temp)
    TextView tvTemp;
    @BindView(R.id.ac_temp_up)
    View btnUpTemp;
    @BindView(R.id.ac_temp_down)
    View btnDownTemp;
    @BindView(R.id.fan_auto)
    TextView fanAuto;
    @BindView(R.id.fan_high)
    TextView fanHigh;
    @BindView(R.id.fan_mid_high)
    TextView fanMidHigh;
    @BindView(R.id.fan_mid)
    TextView fanMid;
    @BindView(R.id.fan_mid_low)
    TextView fanMidLow;
    @BindView(R.id.fan_low)
    TextView fanLow;
    @BindView(R.id.swing_auto)
    TextView swingAuto;
    @BindView(R.id.swing_hor)
    TextView swingHorizontal;
    @BindView(R.id.swing_mid_hor)
    TextView swingMidHorizontal;
    @BindView(R.id.swing_mid)
    TextView swingMiddle;
    @BindView(R.id.swing_mid_ver)
    TextView swingMidVertical;
    @BindView(R.id.swing_ver)
    TextView swingVertical;
    @BindView(R.id.swing_fixed)
    TextView swingFixed;
    @BindView(R.id.mode_auto)
    TextView modeAuto;
    @BindView(R.id.mode_cold)
    TextView modeCold;
    @BindView(R.id.mode_dry)
    TextView modeDry;
    @BindView(R.id.mode_fan)
    TextView modeFan;
    @BindView(R.id.mode_hot)
    TextView modeHot;
    @BindView(R.id.spr_brand)
    Spinner spinnerBrand;
    @BindView(R.id.tv_mode)
    View tvMode;

    @Inject
    Service service;

    private RelayPresenter presenter;
    private String homeId;
    ToggleButton[] toggleButtons;
    int[] relayData;
    String deviceId;
    String relayId;
    TextView fanSelected;
    TextView swingSelected;
    TextView modeSelected;
    int currentTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relay);
        ButterKnife.bind(this);
        getDeps().inject(this);
        fanSelected = fanAuto;
        swingSelected = swingAuto;
        modeSelected = modeAuto;
        deviceId = getIntent().getStringExtra(Device.ID);
        toggleButtons = new ToggleButton[]{toggle1,toggle2,toggle3,toggle4,toggle5,toggle6,toggle7,toggle8};
        presenter = new RelayPresenter(service, this, this);
        homeId = getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACTIVE_HOME,"");
        presenter.getRelayData(deviceId);

        ArrayAdapter brandAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.ac_brand));
        spinnerBrand.setAdapter(brandAdapter);
    }

    @OnCheckedChanged(R.id.ac_power)
    void acOnOff(ToggleButton tb){
        if(tb.isChecked()){
            acContainer.setVisibility(View.VISIBLE);
        } else {
            acContainer.setVisibility(View.GONE);
        }
    }

    @OnItemSelected(R.id.spr_brand)
    void onItemSelected(int position) {
        Map<String, String> params = new HashMap<>();
        switch (position){
            case 0:
                modeAuto.setVisibility(View.VISIBLE);
                modeFan.setVisibility(View.GONE);
                modeDry.setVisibility(View.VISIBLE);
                modeCold.setVisibility(View.VISIBLE);
                modeHot.setVisibility(View.GONE);
                fanAuto.setVisibility(View.VISIBLE);
                fanHigh.setVisibility(View.VISIBLE);
                fanMidHigh.setVisibility(View.GONE);
                fanMid.setVisibility(View.VISIBLE);
                fanMidLow.setVisibility(View.GONE);
                fanLow.setVisibility(View.VISIBLE);
                swingAuto.setVisibility(View.VISIBLE);
                swingHorizontal.setVisibility(View.VISIBLE);
                swingMidHorizontal.setVisibility(View.VISIBLE);
                swingMiddle.setVisibility(View.VISIBLE);
                swingMidVertical.setVisibility(View.VISIBLE);
                swingVertical.setVisibility(View.VISIBLE);
                swingFixed.setVisibility(View.GONE);
                tvMode.setVisibility(View.VISIBLE);
                params.put(Relay.AC_BRAND,Relay.PANASONIC);
                presenter.changeRelayData(deviceId,relayId,params);
                break;
            case 1:
                modeAuto.setVisibility(View.VISIBLE);
                modeFan.setVisibility(View.VISIBLE);
                modeDry.setVisibility(View.VISIBLE);
                modeCold.setVisibility(View.VISIBLE);
                modeHot.setVisibility(View.VISIBLE);
                fanAuto.setVisibility(View.VISIBLE);
                fanHigh.setVisibility(View.VISIBLE);
                fanMidHigh.setVisibility(View.VISIBLE);
                fanMid.setVisibility(View.VISIBLE);
                fanMidLow.setVisibility(View.VISIBLE);
                fanLow.setVisibility(View.VISIBLE);
                swingAuto.setVisibility(View.VISIBLE);
                swingHorizontal.setVisibility(View.GONE);
                swingMidHorizontal.setVisibility(View.GONE);
                swingMiddle.setVisibility(View.GONE);
                swingMidVertical.setVisibility(View.GONE);
                swingVertical.setVisibility(View.GONE);
                swingFixed.setVisibility(View.VISIBLE);
                tvMode.setVisibility(View.VISIBLE);
                params.put(Relay.AC_BRAND,Relay.SAMSUNG);
                presenter.changeRelayData(deviceId,relayId,params);
                break;
            case 2:
                modeAuto.setVisibility(View.VISIBLE);
                modeFan.setVisibility(View.VISIBLE);
                modeDry.setVisibility(View.VISIBLE);
                modeCold.setVisibility(View.VISIBLE);
                modeHot.setVisibility(View.VISIBLE);
                fanAuto.setVisibility(View.VISIBLE);
                fanHigh.setVisibility(View.VISIBLE);
                fanMidHigh.setVisibility(View.GONE);
                fanMid.setVisibility(View.VISIBLE);
                fanMidLow.setVisibility(View.GONE);
                fanLow.setVisibility(View.VISIBLE);
                swingAuto.setVisibility(View.VISIBLE);
                swingHorizontal.setVisibility(View.GONE);
                swingMidHorizontal.setVisibility(View.GONE);
                swingMiddle.setVisibility(View.GONE);
                swingMidVertical.setVisibility(View.GONE);
                swingVertical.setVisibility(View.GONE);
                swingFixed.setVisibility(View.VISIBLE);
                tvMode.setVisibility(View.VISIBLE);
                params.put(Relay.AC_BRAND,Relay.DAIKIN);
                presenter.changeRelayData(deviceId,relayId,params);
                break;
            case 3:
                modeAuto.setVisibility(View.VISIBLE);
                modeFan.setVisibility(View.GONE);
                modeDry.setVisibility(View.VISIBLE);
                modeCold.setVisibility(View.VISIBLE);
                modeHot.setVisibility(View.VISIBLE);
                fanAuto.setVisibility(View.VISIBLE);
                fanHigh.setVisibility(View.VISIBLE);
                fanMidHigh.setVisibility(View.GONE);
                fanMid.setVisibility(View.VISIBLE);
                fanMidLow.setVisibility(View.GONE);
                fanLow.setVisibility(View.VISIBLE);
                swingAuto.setVisibility(View.VISIBLE);
                swingHorizontal.setVisibility(View.GONE);
                swingMidHorizontal.setVisibility(View.GONE);
                swingMiddle.setVisibility(View.GONE);
                swingMidVertical.setVisibility(View.GONE);
                swingVertical.setVisibility(View.GONE);
                swingFixed.setVisibility(View.VISIBLE);
                tvMode.setVisibility(View.VISIBLE);
                params.put(Relay.AC_BRAND,Relay.LG);
                presenter.changeRelayData(deviceId,relayId,params);
                break;
            case 4:
                modeAuto.setVisibility(View.VISIBLE);
                modeFan.setVisibility(View.VISIBLE);
                modeDry.setVisibility(View.VISIBLE);
                modeCold.setVisibility(View.VISIBLE);
                modeHot.setVisibility(View.VISIBLE);
                fanAuto.setVisibility(View.VISIBLE);
                fanHigh.setVisibility(View.VISIBLE);
                fanMidHigh.setVisibility(View.VISIBLE);
                fanMid.setVisibility(View.VISIBLE);
                fanMidLow.setVisibility(View.VISIBLE);
                fanLow.setVisibility(View.VISIBLE);
                swingAuto.setVisibility(View.VISIBLE);
                swingHorizontal.setVisibility(View.VISIBLE);
                swingMidHorizontal.setVisibility(View.VISIBLE);
                swingMiddle.setVisibility(View.VISIBLE);
                swingMidVertical.setVisibility(View.VISIBLE);
                swingVertical.setVisibility(View.VISIBLE);
                swingFixed.setVisibility(View.GONE);
                tvMode.setVisibility(View.VISIBLE);
                params.put(Relay.AC_BRAND,Relay.SHARP);
                presenter.changeRelayData(deviceId,relayId,params);
                break;
            case 5:
                modeAuto.setVisibility(View.VISIBLE);
                modeFan.setVisibility(View.VISIBLE);
                modeDry.setVisibility(View.VISIBLE);
                modeCold.setVisibility(View.VISIBLE);
                modeHot.setVisibility(View.GONE);
                fanAuto.setVisibility(View.VISIBLE);
                fanHigh.setVisibility(View.VISIBLE);
                fanMidHigh.setVisibility(View.GONE);
                fanMid.setVisibility(View.VISIBLE);
                fanMidLow.setVisibility(View.GONE);
                fanLow.setVisibility(View.VISIBLE);
                swingAuto.setVisibility(View.GONE);
                swingHorizontal.setVisibility(View.GONE);
                swingMidHorizontal.setVisibility(View.GONE);
                swingMiddle.setVisibility(View.GONE);
                swingMidVertical.setVisibility(View.GONE);
                swingVertical.setVisibility(View.GONE);
                swingFixed.setVisibility(View.GONE);
                tvMode.setVisibility(View.GONE);
                params.put(Relay.AC_BRAND,Relay.TOSHIBA);
                presenter.changeRelayData(deviceId,relayId,params);
                break;
        }
    }

    @OnClick(R.id.ac_power)
    void acOnOffClick(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_POWER,Relay.AC_ON);
            presenter.changeRelayData(deviceId,relayId,params);
            acContainer.setVisibility(View.VISIBLE);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_POWER,Relay.AC_OFF);
            presenter.changeRelayData(deviceId,relayId,params);
            acContainer.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.fan_auto)
    void fanAuto(TextView tv){
        if(fanSelected != tv){
            setButtonSelected(tv,fanSelected);
            fanSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SPEED,Relay.FAN_SPEED_AUTO);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.fan_high)
    void fanHigh(TextView tv){
        if(fanSelected != tv){
            setButtonSelected(tv,fanSelected);
            fanSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SPEED,Relay.FAN_SPEED_HIGH);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.fan_mid_high)
    void fanMidHigh(TextView tv){
        if(fanSelected != tv){
            setButtonSelected(tv,fanSelected);
            fanSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SPEED,Relay.FAN_SPEED_MID_HIGH);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.fan_mid)
    void fanMid(TextView tv){
        if(fanSelected != tv){
            setButtonSelected(tv,fanSelected);
            fanSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SPEED,Relay.FAN_SPEED_MIDDLE);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.fan_mid_low)
    void fanMidLow(TextView tv){
        if(fanSelected != tv){
            setButtonSelected(tv,fanSelected);
            fanSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SPEED,Relay.FAN_SPEED_MID_LOW);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.fan_low)
    void fanLow(TextView tv){
        if(fanSelected != tv){
            setButtonSelected(tv,fanSelected);
            fanSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SPEED,Relay.FAN_SPEED_LOW);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.swing_auto)
    void setSwingAuto(TextView tv){
        if(swingSelected != tv){
            setButtonSelected(tv,swingSelected);
            swingSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SWING,Relay.SWING_AUTO);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.swing_hor)
    void setSwingHorizontal(TextView tv){
        if(swingSelected != tv){
            setButtonSelected(tv,swingSelected);
            swingSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SWING,Relay.SWING_HORIZONTAL);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.swing_mid_hor)
    void setSwingMidHorizontal(TextView tv){
        if(swingSelected != tv){
            setButtonSelected(tv,swingSelected);
            swingSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SWING,Relay.SWING_MID_HORIZONTAL);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.swing_mid)
    void setSwingMiddle(TextView tv){
        if(swingSelected != tv){
            setButtonSelected(tv,swingSelected);
            swingSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SWING,Relay.SWING_MIDDLE);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.swing_mid_ver)
    void setSwingMidVertical(TextView tv){
        if(swingSelected != tv){
            setButtonSelected(tv,swingSelected);
            swingSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SWING,Relay.SWING_MID_VERTICAL);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.swing_ver)
    void setSwingVertical(TextView tv){
        if(swingSelected != tv){
            setButtonSelected(tv,swingSelected);
            swingSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SWING,Relay.SWING_VERTICAL);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.swing_fixed)
    void setSwingFixed(TextView tv){
        if(swingSelected != tv){
            setButtonSelected(tv,swingSelected);
            swingSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_SWING,Relay.SWING_FIXED);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.mode_auto)
    void setModeAuto(TextView tv){
        if(modeSelected != tv){
            setButtonSelected(tv,modeSelected);
            modeSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_MODE,Relay.MODE_AUTO);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.mode_fan)
    void setModeFan(TextView tv){
        if(modeSelected != tv){
            setButtonSelected(tv,modeSelected);
            modeSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_MODE,Relay.MODE_FAN);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.mode_dry)
    void setModeDry(TextView tv){
        if(modeSelected != tv){
            setButtonSelected(tv,modeSelected);
            modeSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_MODE,Relay.MODE_DRY);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.mode_cold)
    void setModeCold(TextView tv){
        if(modeSelected != tv){
            setButtonSelected(tv,modeSelected);
            modeSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_MODE,Relay.MODE_COLD);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }
    @OnClick(R.id.mode_hot)
    void setModeHot(TextView tv){
        if(modeSelected != tv){
            setButtonSelected(tv,modeSelected);
            modeSelected = tv;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_MODE,Relay.MODE_HOT);
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.ac_temp_up)
    void tempUp(){
        if(currentTemp<30) {
            currentTemp++;
            tvTemp.setText(currentTemp + "");
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_TEMP, ACUtils.setTemperature(currentTemp));
            presenter.changeRelayData(deviceId, relayId, params);
        }
    }

    @OnClick(R.id.ac_temp_down)
    void tempDown(){
        if(currentTemp<30) {
            currentTemp--;
            tvTemp.setText(currentTemp + "");
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_TEMP, ACUtils.setTemperature(currentTemp));
            presenter.changeRelayData(deviceId, relayId, params);
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

    @OnClick(R.id.toggle1)
    void setToggle1(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_1,"1");
            presenter.changeRelayData(deviceId,relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_1,"0");
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.toggle2)
    void setToggle2(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_2,"1");
            presenter.changeRelayData(deviceId,relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_2,"0");
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.toggle3)
    void setToggle3(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_3,"1");
            presenter.changeRelayData(deviceId,relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_3,"0");
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.toggle4)
    void setToggle4(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_4,"1");
            presenter.changeRelayData(deviceId,relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_4,"0");
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.toggle5)
    void setToggle5(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_5,"1");
            presenter.changeRelayData(deviceId,relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_5,"0");
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.toggle6)
    void setToggle6(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_6,"1");
            presenter.changeRelayData(deviceId,relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_6,"0");
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.toggle7)
    void setToggle7(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_7,"1");
            presenter.changeRelayData(deviceId,relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_7,"0");
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }

    @OnClick(R.id.toggle8)
    void setToggle8(ToggleButton tb){
        if(tb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_8,"1");
            presenter.changeRelayData(deviceId,relayId,params);
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(Relay.RELAY_8,"0");
            presenter.changeRelayData(deviceId,relayId,params);
        }
    }



    @Override
    public void showRelayStatus(Response<Relay> response) {
        Log.e("HMMM", "HAHAHAJJ");
        Log.e("RESPONSE BOSKU", response.body().getRelay8()+"");
        Relay relay = response.body();
        relayId = relay.getId().toString();
        currentTemp = ACUtils.getTemperature(relay.getAcTemp());
        updateRelayUI(relay);
    }

    @Override
    public void changeRelayStatus() {

    }

    private void updateRelayUI(Relay relay){
        relayData= new int[]{relay.getRelay1(),relay.getRelay2(),relay.getRelay3(),relay.getRelay4(),
                relay.getRelay5(),relay.getRelay6(),relay.getRelay7(),relay.getRelay8()};
        for(int i=0; i< toggleButtons.length; i++) {
            if (relayData[i] == 1) {
                toggleButtons[i].setChecked(true);
            } else {
                toggleButtons[i].setChecked(false);
            }
        }
        if(relay.getAcPower().equals(Relay.AC_ON)){
            acPower.setChecked(true);
            acContainer.setVisibility(View.VISIBLE);
        } else {
            acPower.setChecked(false);
            acContainer.setVisibility(View.GONE);
        }

        spinnerBrand.setSelection(ACUtils.getAcBrandPosition(relay.getAcBrand()));
        tvTemp.setText(currentTemp+"");

        setButtonSelected(getFanByCode(relay.getAcSpeed()),fanSelected);
        setButtonSelected(getSwingByCode(relay.getAcSwing()),swingSelected);
        setButtonSelected(getModeByCode(relay.getAcMode()),modeSelected);

    }

    private void setButtonSelected(TextView tv, TextView selected){
        tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_primary));
        tv.setTextColor(getResources().getColor(R.color.white));
        selected.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_primary_outline));
        selected.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private TextView getFanByCode(String code){
        switch (code){
            case Relay.FAN_SPEED_AUTO:
                return fanAuto;
            case Relay.FAN_SPEED_HIGH:
                return fanHigh;
            case Relay.FAN_SPEED_MID_HIGH:
                return fanMidHigh;
            case Relay.FAN_SPEED_MIDDLE:
                return fanMid;
            case Relay.FAN_SPEED_MID_LOW:
                return fanMidLow;
            case Relay.FAN_SPEED_LOW:
                return fanLow;
            default:
                return fanAuto;
        }
    }

    private TextView getSwingByCode(String code){
        switch (code){
            case Relay.SWING_AUTO:
                return swingAuto;
            case Relay.SWING_HORIZONTAL:
                return swingHorizontal;
            case Relay.SWING_MID_HORIZONTAL:
                return swingMidHorizontal;
            case Relay.SWING_MIDDLE:
                return swingMiddle;
            case Relay.SWING_MID_VERTICAL:
                return swingMidVertical;
            case Relay.SWING_VERTICAL:
                return swingVertical;
            case Relay.SWING_FIXED:
                return swingFixed;
            default:
                return swingAuto;
        }
    }

    private TextView getModeByCode(String code){
        switch (code){
            case Relay.MODE_AUTO:
                return modeAuto;
            case Relay.MODE_COLD:
                return modeCold;
            case Relay.MODE_DRY:
                return modeDry;
            case Relay.MODE_FAN:
                return modeFan;
            case Relay.MODE_HOT:
                return modeHot;
            default:
                return modeAuto;
        }
    }
}
