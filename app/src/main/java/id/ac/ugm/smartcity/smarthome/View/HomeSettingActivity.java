package id.ac.ugm.smartcity.smarthome.View;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HomeSettingPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.NumberFormatter;
import retrofit2.Response;

public class HomeSettingActivity extends BaseActivity implements HomeSettingView {

    @BindView(R.id.ic_back)
    TextView icBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_home_id)
    TextView tvHomeID;
    @BindView(R.id.tv_gateway1)
    TextView tvGateway1;
    @BindView(R.id.tv_gateway2)
    TextView tvGateway2;
    @BindView(R.id.tv_energy)
    TextView tvEnergy;
    @BindView(R.id.tv_cost)
    TextView tvCost;
    @BindView(R.id.tv_temp_max)
    TextView tvTempMax;
    @BindView(R.id.tv_temp_min)
    TextView tvTempMin;
    @BindView(R.id.tv_hum_max)
    TextView tvHumMax;
    @BindView(R.id.tv_hum_min)
    TextView tvHumMin;
    @BindView(R.id.tv_co2_max)
    TextView tvCo2Max;
    @BindView(R.id.tv_co2_min)
    TextView tvCo2Min;
    @BindView(R.id.tv_light_max)
    TextView tvLightMax;
    @BindView(R.id.tv_light_min)
    TextView tvLightMin;

    @Inject
    public Service service;

    private ProgressDialog progressDialog;
    private HomeSettingPresenter presenter;
    private Home home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_setting);
        ButterKnife.bind(this);
        getDeps().inject(this);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icBack, iconFont);

        presenter = new HomeSettingPresenter(service,this,this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        home = (Home) getIntent().getSerializableExtra(Home.ID);
        updateUI();
    }

    private void updateUI(){
        tvTitle.setText(home.getName() + " Setting");
        tvName.setText(home.getName());
        tvHomeID.setText(home.getDevid());
        String[] gateway = home.getGatewayId().split("\\-");
        tvGateway1.setText(gateway[0]+"-"+gateway[1]);
        tvGateway2.setText(gateway[2]+"-"+gateway[3]);
        if(Float.parseFloat(home.getUpperenergy()) >= 10000000){
            tvEnergy.setText("max not set");
        } else {
            tvEnergy.setText("max " + NumberFormatter.formatWithDots(Float.parseFloat(home.getUpperenergy())) + " kWh");
        }
        if(Float.parseFloat(home.getCostLimit()) >= 100000000){
            tvCost.setText("max not set");
        } else {
            tvCost.setText("max Rp " + NumberFormatter.formatWithDots(Float.parseFloat(home.getCostLimit())));
        }
        if(Float.parseFloat(home.getUppertemp()) >= 10000000){
            tvTempMax.setText("max not set");
        } else {
            tvTempMax.setText("max " + NumberFormatter.formatWithDots(Float.parseFloat(home.getUppertemp())));
        }
        if(Float.parseFloat(home.getLowertemp()) == 0){
            Float f = Float.parseFloat(home.getLowertemp());
            Log.e("TEMP MIN", f.toString());
            tvTempMin.setText("min not set");
        } else {
            tvTempMin.setText("min " + NumberFormatter.formatWithDots(Float.parseFloat(home.getLowertemp())));
        }

        if(Float.parseFloat(home.getUpperhum()) >= 10000000){
            tvHumMax.setText("max not set");
        } else {
            tvHumMax.setText("max " + NumberFormatter.formatWithDots(Float.parseFloat(home.getUpperhum())));
        }
        if(Float.parseFloat(home.getLowerhum()) == 0){
            tvHumMin.setText("min not set");
        } else {
            tvHumMin.setText("min " + NumberFormatter.formatWithDots(Float.parseFloat(home.getLowerhum())));
        }

        if(Float.parseFloat(home.getUpperco()) >= 10000000){
            tvCo2Max.setText("max not set");
        } else {
            tvCo2Max.setText("max " + NumberFormatter.formatWithDots(Float.parseFloat(home.getUpperco())));
        }
        if(Float.parseFloat(home.getLowerco()) == 0){
            tvCo2Min.setText("min not set");
        } else {
            tvCo2Min.setText("min " + NumberFormatter.formatWithDots(Float.parseFloat(home.getLowerco())));
        }

        if(Float.parseFloat(home.getUpperflux()) >= 10000000){
            tvLightMax.setText("max not set");
        } else {
            tvLightMax.setText("max " + NumberFormatter.formatWithDots(Float.parseFloat(home.getUpperflux())));
        }
        if(Float.parseFloat(home.getLowerflux()) == 0){
            tvLightMin.setText("min not set");
        } else {
            tvLightMin.setText("min " + NumberFormatter.formatWithDots(Float.parseFloat(home.getLowerflux())));
        }

    }

    @OnClick(R.id.ic_back)
    void back(){
        super.onBackPressed();
    }

    @OnClick(R.id.name)
    void changeName(){
        SettingDialog dialog = new SettingDialog(this,home, Home.NAME,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.home_id)
    void changeHomeId(){
        SettingDialog dialog = new SettingDialog(this,home, Home.ID,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.gateway)
    void changeGateway(){
        SettingDialog dialog = new SettingDialog(this,home, Home.GATEWAY,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.energy)
    void changeEnergy(){
        SettingDialog dialog = new SettingDialog(this,home, Home.ENERGY,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.cost)
    void changeCost(){
        SettingDialog dialog = new SettingDialog(this,home, Home.COST,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.temperature)
    void changeTemperature(){
        SettingDialog dialog = new SettingDialog(this,home, Home.UPPER_TEMPERATURE,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.humidity)
    void changeHumidity(){
        SettingDialog dialog = new SettingDialog(this,home, Home.UPPER_HUMIDITY,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.co2)
    void changeCo2(){
        SettingDialog dialog = new SettingDialog(this,home, Home.UPPER_CO2,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.light)
    void changeLight(){
        SettingDialog dialog = new SettingDialog(this,home, Home.UPPER_LIGHT,service,presenter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void showLoading() {
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void updateUI(Response<Home> response) {
        home = response.body();
        updateUI();
    }
}
