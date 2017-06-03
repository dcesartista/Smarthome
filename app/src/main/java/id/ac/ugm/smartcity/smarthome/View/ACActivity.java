package id.ac.ugm.smartcity.smarthome.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;

public class ACActivity extends BaseActivity {

    @Inject
    Service service;

    private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);
        ButterKnife.bind(this);
        getDeps().inject(this);

        device = (Device) getIntent().getSerializableExtra(Device.ID);
    }
}
