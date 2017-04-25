package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.DevicePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.BaseActivity;
import retrofit2.Response;

public class AddDeviceActivity extends BaseActivity implements DeviceView {

    @BindView(R.id.ic_back)
    TextView icBack;
    @BindView(R.id.pic)
    View pic;
    @BindView(R.id.iv_device)
    ImageView ivDevice;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_product_id)
    EditText etProductId;

    @Inject
    public Service service;

    //TODO : HOME ID DIBIKIN GAK STATIS, BIKIN HOME SELECTION ACTIVITY
    private String homeId = "1";
    private DevicePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ButterKnife.bind(this);
        getDeps().inject(this);

        presenter = new DevicePresenter(service, this, this);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icBack, iconFont);
    }

    @OnClick(R.id.btn_save)
    void save(){
        if(null!= etName.getText() && !etName.getText().toString().isEmpty()){
            if(null!= etProductId.getText() && !etProductId.getText().toString().isEmpty()){
                Map<String, String> params = new HashMap<>();
                params.put(Device.NAME,etName.getText().toString());
                params.put(Device.PRODUCT_ID, etProductId.getText().toString());
                presenter.addDevice(homeId, params);
            } else {
                Toast.makeText(this,"Harap mengisi product id",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Harap mengisi nama device",Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this,"Gagal menambahkan device, harap periksa koneksi internet anda!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addDeviceSuccess(Response<Device> response) {
        finish();
    }
}
