package id.ac.ugm.smartcity.smarthome.View;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.ACPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.ACUtils;
import id.ac.ugm.smartcity.smarthome.Utils.Utils;
import retrofit2.Response;

public class ACActivity extends BaseActivity implements ACView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ic_back)
    TextView icBack;
    @BindView(R.id.toolbar_ac)
    Toolbar toolbarAc;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.ll_temp)
    View llTemp;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.rl_speed_swing)
    View rlSpeedSwing;
    @BindView(R.id.ic_speed)
    ImageView icSpeed;
    @BindView(R.id.tv_speed)
    TextView tvSpeed;
    @BindView(R.id.tv_swing)
    TextView tvSwing;
    @BindView(R.id.ic_swing)
    ImageView icSwing;
    @BindView(R.id.btn_power)
    TextView btnPower;
    @BindView(R.id.tv_off)
    View tvOff;
    @BindView(R.id.btn_swing)
    TextView btnSwing;

    @Inject
    Service service;

    private Device device;
    private Relay relay;
    private ProgressDialog progressDialog;
    private ACPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);
        ButterKnife.bind(this);
        getDeps().inject(this);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icBack, iconFont);

        presenter = new ACPresenter(service, this, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        device = (Device) getIntent().getSerializableExtra(Device.ID);
        relay = (Relay) getIntent().getSerializableExtra(Relay.ID);

        updateUI();

    }

    @OnClick(R.id.ic_back)
    void back(){
        super.onBackPressed();
    }

    @OnClick(R.id.btn_power)
    void power(){
        Map<String, String> params = new HashMap<>();
        params.put(Relay.AC_ENABLE,Relay.AC_ENABLED);
        if(relay.getAcPower().equals(Relay.AC_ON)){
            params.put(Relay.AC_POWER,Relay.AC_OFF);
        } else {
            params.put(Relay.AC_POWER,Relay.AC_ON);
        }
        presenter.changeRelayData(device.getId().toString(),relay.getId().toString(),params);
    }

    @OnClick(R.id.btn_temp_up)
    void tempUp(){
        int currentTemp = ACUtils.getTemperature(relay.getAcTemp());
        if(currentTemp<30) {
            currentTemp++;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_ENABLE,Relay.AC_ENABLED);
            params.put(Relay.AC_TEMP, ACUtils.setTemperature(currentTemp));
            presenter.changeRelayData(device.getId().toString(),relay.getId().toString(),params);
        }
    }

    @OnClick(R.id.btn_temp_down)
    void tempDown(){
        int currentTemp = ACUtils.getTemperature(relay.getAcTemp());
        if(currentTemp>16) {
            currentTemp--;
            Map<String, String> params = new HashMap<>();
            params.put(Relay.AC_ENABLE,Relay.AC_ENABLED);
            params.put(Relay.AC_TEMP, ACUtils.setTemperature(currentTemp));
            presenter.changeRelayData(device.getId().toString(),relay.getId().toString(),params);
        }
    }

    @OnClick(R.id.btn_mode)
    void changeMode(){
        String newMode = ACUtils.changeMode(relay.getAcBrand(),relay.getAcMode());
        Map<String, String> params = new HashMap<>();
        params.put(Relay.AC_ENABLE,Relay.AC_ENABLED);
        params.put(Relay.AC_MODE,newMode);
        presenter.changeRelayData(device.getId().toString(),relay.getId().toString(),params);
    }

    @OnClick(R.id.btn_swing)
    void changeSwing(){
        String newSwing = ACUtils.changeSwing(relay.getAcBrand(),relay.getAcSwing());
        Map<String, String> params = new HashMap<>();
        params.put(Relay.AC_ENABLE,Relay.AC_ENABLED);
        params.put(Relay.AC_SWING,newSwing);
        presenter.changeRelayData(device.getId().toString(),relay.getId().toString(),params);
    }

    @OnClick(R.id.btn_speed)
    void changeSpeed(){
        String newSpeed = ACUtils.changeFanSpeed(relay.getAcBrand(),relay.getAcSpeed());
        Map<String, String> params = new HashMap<>();
        params.put(Relay.AC_ENABLE,Relay.AC_ENABLED);
        params.put(Relay.AC_SPEED,newSpeed);
        presenter.changeRelayData(device.getId().toString(),relay.getId().toString(),params);
    }

    private void updateUI(){
        tvTitle.setText("AC "+device.getName());
        tvBrand.setText(ACUtils.getAcBrandName(relay.getAcBrand()));
        tvTemp.setText(String.valueOf(ACUtils.getTemperature(relay.getAcTemp())));
        tvMode.setText(ACUtils.getAcMode(relay.getAcMode()));
        tvSpeed.setText(ACUtils.getAcSpeed(relay.getAcSpeed()));
        tvSwing.setText(ACUtils.getAcSwing(relay.getAcSwing()));

        if(relay.getAcPower().equals(Relay.AC_ON)){
            btnPower.setText(getResources().getString(R.string.ac_off));
            btnPower.setTextColor(getResources().getColor(R.color.red));
            showAConState();
        } else {
            btnPower.setText(getResources().getString(R.string.ac_on));
            btnPower.setTextColor(getResources().getColor(R.color.green));
            showACoffState();
        }

        if(relay.getAcBrand().equals(Relay.TOSHIBA)){
            btnSwing.setTextColor(R.color.hintColor);
            icSwing.setVisibility(View.INVISIBLE);
            tvSwing.setVisibility(View.INVISIBLE);
        } else {
            btnSwing.setTextColor(R.color.textPrimary);
            icSwing.setVisibility(View.VISIBLE);
            tvSwing.setVisibility(View.VISIBLE);
        }
    }

    private void showACoffState(){
        toolbarAc.setVisibility(View.GONE);
        tvBrand.setVisibility(View.GONE);
        llTemp.setVisibility(View.GONE);
        tvMode.setVisibility(View.GONE);
        rlSpeedSwing.setVisibility(View.GONE);
        tvOff.setVisibility(View.VISIBLE);
    }

    private void showAConState(){
        toolbarAc.setVisibility(View.VISIBLE);
        tvBrand.setVisibility(View.VISIBLE);
        llTemp.setVisibility(View.VISIBLE);
        tvMode.setVisibility(View.VISIBLE);
        rlSpeedSwing.setVisibility(View.VISIBLE);
        tvOff.setVisibility(View.GONE);
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
    public void onFailure() {
        Toast.makeText(this,"Failed to Connect to Server! Please Check your internet connection",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRelayStatus(Response<Relay> response) {
        relay = response.body();
        updateUI();
    }

}
