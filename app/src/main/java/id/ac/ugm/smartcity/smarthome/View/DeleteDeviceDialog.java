package id.ac.ugm.smartcity.smarthome.View;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.GetDevicePresenter;
import id.ac.ugm.smartcity.smarthome.Presenter.HomeSettingPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.GetDeviceView;

/**
 * Created by dito on 06/06/17.
 */

public class DeleteDeviceDialog extends Dialog {

    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.et_password)
    TextView etPassword;
    @BindView(R.id.card_cancel)
    CardView cardCancel;
    @BindView(R.id.card_ok)
    CardView cardOk;

    public Activity activity;
    public Dialog d;
    private String type;
    private String homeId;
    private String deviceId;
    private Service service;
    private GetDevicePresenter presenter;
    private GetDeviceView view;


    public DeleteDeviceDialog(Activity a, String homeId, String deviceId, Service service, GetDeviceView view) {
        super(a);
        this.activity = a;
        this.homeId = homeId;
        this.deviceId = deviceId;
        this.service = service;
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_device);
        ButterKnife.bind(this);

        presenter = new GetDevicePresenter(service, view, getContext());
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);

        updateUI();
    }

    @OnClick(R.id.card_cancel)
    void cancelSave(){
        dismiss();
    }

    @OnClick(R.id.card_ok)
    void saveChange(){
        if(null != etPassword.getText() && etPassword.getText().toString().length() > 0){
            presenter.deleteDevice(homeId, deviceId, etPassword.getText().toString());
//            presenter.getDeviceList(homeId);
        } else {
            Toast.makeText(getContext(),getContext().getResources().getString(R.string.blank_password),Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }


    public void updateUI() {
    }

}
