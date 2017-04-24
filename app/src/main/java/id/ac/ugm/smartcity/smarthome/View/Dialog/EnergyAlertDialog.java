package id.ac.ugm.smartcity.smarthome.View.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.HomeView;

/**
 * Created by dito on 24/04/17.
 */

public class EnergyAlertDialog extends Dialog implements View.OnClickListener {

    private HomeView view;
    private EditText etLimit;
    private TextView btnSimpan, btnBatal;

    public EnergyAlertDialog(@NonNull Context context, HomeView view) {
        super(context);
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_energy_alert);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        etLimit = (EditText) findViewById(R.id.et_enegy_limit);
        btnSimpan = (TextView) findViewById(R.id.btn_simpan);
        btnBatal = (TextView) findViewById(R.id.btn_batal);
        btnSimpan.setOnClickListener(this);
        btnBatal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simpan:
                if(null != etLimit.getText() || !etLimit.getText().toString().isEmpty()){
                    view.updateEnergyLimit(etLimit.getText().toString());
                }
                break;
            case R.id.btn_batal:
                dismiss();
                break;
        }
    }
}
