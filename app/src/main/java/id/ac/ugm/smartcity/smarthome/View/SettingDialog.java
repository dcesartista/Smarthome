package id.ac.ugm.smartcity.smarthome.View;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.DaggerDeps;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkModule;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HomeSettingPresenter;
import id.ac.ugm.smartcity.smarthome.R;

/**
 * Created by dito on 06/06/17.
 */

public class SettingDialog extends Dialog {

    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.et_name)
    TextView etName;
    @BindView(R.id.card_cancel)
    CardView cardCancel;
    @BindView(R.id.card_ok)
    CardView cardOk;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.gateway1)
    View gateway1;
    @BindView(R.id.gateway2)
    View gateway2;
    @BindView(R.id.et_gateway1)
    EditText etGateway1;
    @BindView(R.id.et_gateway2)
    EditText etGateway2;
    @BindView(R.id.et_gateway3)
    EditText etGateway3;
    @BindView(R.id.et_gateway4)
    EditText etGateway4;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_number1)
    EditText etNumber1;
    @BindView(R.id.et_number2)
    EditText etNumber2;
    @BindView(R.id.et_upper_lower)
    View etUpperLower;

    public Activity activity;
    public Dialog d;
    private String type;
    private Home home;
    private Service service;
    private HomeSettingPresenter presenter;


    public SettingDialog(Activity a, Home home, String type, Service service, HomeSettingPresenter presenter) {
        super(a);
        this.activity = a;
        this.home = home;
        this.type = type;
        this.service = service;
        this.presenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_with_edittext);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        updateUI();
    }

    @OnClick(R.id.card_cancel)
    void cancelSave(){
        dismiss();
    }

    @OnClick(R.id.card_ok)
    void saveChange(){
        switch (type){
            case Home.NAME:
                if(null != etName.getText() && etName.getText().toString().length() >0) {
                    Map<String, String> params = new HashMap<>();
                    params.put(Home.NAME, etName.getText().toString());
                    presenter.changeHome(home.getId().toString(),params);
                    dismiss();
                }
                break;
            case Home.ID:
                if(null != etName.getText() && etName.getText().toString().length() >0) {
                    Map<String, String> params = new HashMap<>();
                    params.put(Home.ID, etName.getText().toString());
                    presenter.changeHome(home.getId().toString(),params);
                    dismiss();
                }
                break;
            case Home.GATEWAY:
                if(null != etGateway1.getText() && etGateway1.getText().toString().length() >0
                        && null != etGateway2.getText() && etGateway2.getText().toString().length() >0
                        && null != etGateway3.getText() && etGateway3.getText().toString().length() >0
                        && null != etGateway4.getText() && etGateway4.getText().toString().length() >0) {
                    Map<String, String> params = new HashMap<>();
                    params.put(Home.GATEWAY, etGateway1.getText().toString()+"-"+etGateway2.getText().toString()+"-"+
                                etGateway3.getText().toString()+"-"+etGateway4.getText().toString());
                    presenter.changeHome(home.getId().toString(),params);
                    dismiss();
                }
                break;
            case Home.ENERGY:
                Map<String, String> params = new HashMap<>();
                if(null != etNumber.getText() && etNumber.getText().toString().length() >0) {
                    params.put(Home.ENERGY, etNumber.getText().toString());
                } else {
                    params.put(Home.ENERGY, "100000000");
                }
                presenter.changeHome(home.getId().toString(),params);
                dismiss();
                break;
            case Home.COST:
                Map<String, String> params2 = new HashMap<>();
                if(null != etNumber.getText() && etNumber.getText().toString().length() >0) {
                    params2.put(Home.COST, etNumber.getText().toString());
                } else {
                    params2.put(Home.COST, "100000000");
                }
                presenter.changeHome(home.getId().toString(),params2);
                dismiss();
                break;
            case Home.UPPER_TEMPERATURE:
                Map<String, String> params3 = new HashMap<>();
                if(null != etNumber1.getText() && etNumber1.getText().toString().length() >0) {
                    params3.put(Home.UPPER_TEMPERATURE, etNumber1.getText().toString());
                } else {
                    params3.put(Home.UPPER_TEMPERATURE, "10000000");
                }
                if(null != etNumber2.getText() && etNumber2.getText().toString().length() >0) {
                    params3.put(Home.LOWER_TEMPERATURE, etNumber2.getText().toString());
                } else {
                    params3.put(Home.LOWER_TEMPERATURE, "0");
                }
                presenter.changeHome(home.getId().toString(),params3);
                dismiss();
                break;
            case Home.UPPER_HUMIDITY:
                Map<String, String> params4 = new HashMap<>();
                if(null != etNumber1.getText() && etNumber1.getText().toString().length() >0) {
                    params4.put(Home.UPPER_HUMIDITY, etNumber1.getText().toString());
                } else {
                    params4.put(Home.UPPER_HUMIDITY, "10000000");
                }
                if(null != etNumber2.getText() && etNumber2.getText().toString().length() >0) {
                    params4.put(Home.LOWER_HUMIDITY, etNumber2.getText().toString());
                } else {
                    params4.put(Home.LOWER_HUMIDITY, "0");
                }
                presenter.changeHome(home.getId().toString(),params4);
                dismiss();
                break;
            case Home.UPPER_CO2:
                Map<String, String> params5 = new HashMap<>();
                if(null != etNumber1.getText() && etNumber1.getText().toString().length() >0) {
                    params5.put(Home.UPPER_CO2, etNumber1.getText().toString());
                } else {
                    params5.put(Home.UPPER_CO2, "10000000");
                }
                if(null != etNumber2.getText() && etNumber2.getText().toString().length() >0) {
                    params5.put(Home.LOWER_CO2, etNumber2.getText().toString());
                } else {
                    params5.put(Home.LOWER_CO2, "0");
                }
                presenter.changeHome(home.getId().toString(),params5);
                dismiss();
                break;
            case Home.UPPER_LIGHT:
                Map<String, String> params6 = new HashMap<>();
                if(null != etNumber1.getText() && etNumber1.getText().toString().length() >0) {
                    params6.put(Home.UPPER_LIGHT, etNumber1.getText().toString());
                } else {
                    params6.put(Home.UPPER_LIGHT, "10000000");
                }
                if(null != etNumber2.getText() && etNumber2.getText().toString().length() >0) {
                    params6.put(Home.LOWER_LIGHT, etNumber2.getText().toString());
                } else {
                    params6.put(Home.LOWER_LIGHT, "0");
                }
                presenter.changeHome(home.getId().toString(),params6);
                dismiss();
                break;
        }
    }


    public void updateUI(){
        switch (type){
            case Home.NAME:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_name));
                gateway1.setVisibility(View.GONE);
                gateway2.setVisibility(View.GONE);
                etUpperLower.setVisibility(View.GONE);
                etName.setText(home.getName());
                etName.setVisibility(View.VISIBLE);
                etNumber.setVisibility(View.GONE);
                break;
            case Home.ID:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_home_id));
                gateway1.setVisibility(View.GONE);
                gateway2.setVisibility(View.GONE);
                etUpperLower.setVisibility(View.GONE);
                etName.setText(home.getDevid());
                etName.setVisibility(View.VISIBLE);
                etNumber.setVisibility(View.GONE);
                break;
            case Home.GATEWAY:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_gateway));
                gateway1.setVisibility(View.VISIBLE);
                gateway2.setVisibility(View.VISIBLE);
                etUpperLower.setVisibility(View.GONE);
                String[] gateway = home.getGatewayId().split("\\-");
                etName.setVisibility(View.GONE);
                etNumber.setVisibility(View.GONE);
                etGateway1.setText(gateway[0]);
                etGateway2.setText(gateway[1]);
                etGateway3.setText(gateway[2]);
                etGateway4.setText(gateway[3]);
                break;
            case Home.ENERGY:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_energy));
                gateway1.setVisibility(View.GONE);
                gateway2.setVisibility(View.GONE);
                etUpperLower.setVisibility(View.GONE);
                etName.setVisibility(View.GONE);
                etNumber.setVisibility(View.VISIBLE);
                if(Float.parseFloat(home.getUpperenergy()) < 10000000){
                    etNumber.setText(home.getUpperenergy());
                }
                break;
            case Home.COST:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_cost));
                gateway1.setVisibility(View.GONE);
                gateway2.setVisibility(View.GONE);
                etUpperLower.setVisibility(View.GONE);
                etName.setVisibility(View.GONE);
                etNumber.setVisibility(View.VISIBLE);
                if(Float.parseFloat(home.getCostLimit()) < 100000000){
                    etNumber.setText(home.getCostLimit());
                }
                break;
            case Home.UPPER_TEMPERATURE:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_temp));
                gateway1.setVisibility(View.GONE);
                gateway2.setVisibility(View.GONE);
                etUpperLower.setVisibility(View.VISIBLE);
                etName.setVisibility(View.GONE);
                etNumber.setVisibility(View.GONE);
                if(Float.parseFloat(home.getUppertemp()) < 10000000){
                    etNumber1.setText(home.getUppertemp());
                }
                if(Float.parseFloat(home.getLowertemp()) > 0){
                    etNumber2.setText(home.getLowertemp());
                }
                break;
            case Home.UPPER_HUMIDITY:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_hum));
                gateway1.setVisibility(View.GONE);
                gateway2.setVisibility(View.GONE);
                etUpperLower.setVisibility(View.VISIBLE);
                etName.setVisibility(View.GONE);
                etNumber.setVisibility(View.GONE);
                if(Float.parseFloat(home.getUpperhum()) < 10000000){
                    etNumber1.setText(home.getUpperhum());
                }
                if(Float.parseFloat(home.getLowerhum()) > 0){
                    etNumber2.setText(home.getLowerhum());
                }
                break;
            case Home.UPPER_CO2:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_co2));
                gateway1.setVisibility(View.GONE);
                gateway2.setVisibility(View.GONE);
                etUpperLower.setVisibility(View.VISIBLE);
                etName.setVisibility(View.GONE);
                etNumber.setVisibility(View.GONE);
                if(Float.parseFloat(home.getUpperco()) < 10000000){
                    etNumber1.setText(home.getUpperco());
                }
                if(Float.parseFloat(home.getLowerco()) > 0){
                    etNumber2.setText(home.getLowerco());
                }
                break;
            case Home.UPPER_LIGHT:
                tvPrompt.setText(activity.getResources().getString(R.string.prompt_light));
                gateway1.setVisibility(View.GONE);
                gateway2.setVisibility(View.GONE);
                etUpperLower.setVisibility(View.VISIBLE);
                etName.setVisibility(View.GONE);
                etNumber.setVisibility(View.GONE);
                if(Float.parseFloat(home.getUpperflux()) < 10000000){
                    etNumber1.setText(home.getUpperflux());
                }
                if(Float.parseFloat(home.getLowerflux()) > 0){
                    etNumber2.setText(home.getLowerflux());
                }
                break;
        }
    }

}
