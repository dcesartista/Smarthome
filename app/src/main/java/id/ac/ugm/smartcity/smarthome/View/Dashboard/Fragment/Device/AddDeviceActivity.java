package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.DevicePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.BaseActivity;
import retrofit2.Response;

public class AddDeviceActivity extends BaseActivity implements DeviceView {
    public static final int GALLERY_INTENT = 99;
    public static final String EDIT = "EDIT_DEVICE";

    @BindView(R.id.ic_back)
    TextView icBack;
    @BindView(R.id.iv_device)
    ImageView ivDevice;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_device_id)
    EditText etDeviceId;

    @Inject
    public Service service;

    private File image;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    private boolean imagePresent = false;

    private String homeId;
    private Device device;
    private DevicePresenter presenter;
    private boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ButterKnife.bind(this);
        getDeps().inject(this);

        edit = getIntent().getBooleanExtra(EDIT,false);
        if (edit){
            device = (Device) getIntent().getSerializableExtra(Device.ID);
            etName.setText(device.getName());
            etDeviceId.setText(device.getProductID());
            if (null != device.getImg().getUrl() && device.getImg().getUrl().length() > 0){
                Picasso.with(this)
                        .load(App.BASE_URL+device.getImg().getUrl())
                        .into(ivDevice);
            }
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        preferences = getSharedPreferences(App.USER_PREFERENCE,MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");

        presenter = new DevicePresenter(service, this, this);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icBack, iconFont);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            Log.e("zzz","lll");
            final Uri pic = data.getData();
            image = new File(pic.getPath());
            Picasso.with(this)
                    .load(pic)
                    .into(ivDevice);
            imagePresent = true;
        }

    }

    @OnClick(R.id.btn_save)
    void save(){
        if(null!= etName.getText() && !etName.getText().toString().isEmpty()){
            if(null!= etDeviceId.getText() && !etDeviceId.getText().toString().isEmpty()){
                if(imagePresent) {
                    if(edit){
                        presenter.editDevice(homeId, device.getId().toString(), etName.getText().toString(), etDeviceId.getText().toString(), image);
                    } else {
                        presenter.addDevice(homeId, etName.getText().toString(), etDeviceId.getText().toString(), image);
                    }
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put(Device.NAME,etName.getText().toString());
                    params.put(Device.PRODUCT_ID,etDeviceId.getText().toString());
                    if (edit){
                        presenter.editDevice(homeId, device.getId().toString(), params);
                    } else {
                        presenter.addDevice(homeId, params);
                    }
                }

            } else {
                Toast.makeText(this,"Harap mengisi device id",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Harap mengisi nama device",Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ic_back)
    void back(){
        onBackPressed();
    }

    @OnClick(R.id.iv_device)
    void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
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
        Toast.makeText(this,"Gagal menambahkan device, harap periksa koneksi internet anda!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addDeviceSuccess(Response<Device> response) {
        finish();
    }


}
