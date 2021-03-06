package id.ac.ugm.smartcity.smarthome.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.LoginPresenter;
import id.ac.ugm.smartcity.smarthome.Presenter.RegisterPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements RegisterView {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @Inject
    public Service service;

    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter = new RegisterPresenter(service, this);
    }

    @OnClick(R.id.btn_register)
    void register(){
        Map<String,String> registerParams = new HashMap<>();
        registerParams.put("email",etUserName.getText().toString());
        registerParams.put("password",etPassword.getText().toString());
        registerParams.put("password_confirmation",etConfirmPassword.getText().toString());
        presenter.register(registerParams);
    }

    @OnClick(R.id.login)
    void goToSignIn(){
        Log.e("HAHAHA","ASDASD");
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void registerFailed(String appErrorMessage) {
        Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccess(Response<RegisterUser> response) {
        SharedPreferences.Editor editor = getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        editor.putString(App.USER_EMAIL, response.body().getData().getEmail());
        editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
        editor.putString(App.CLIENT, response.headers().get("Client"));
        editor.putString(App.EXPIRY, response.headers().get("Expiry"));
        editor.putString(App.UID, response.headers().get("Uid"));
        editor.commit();

        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
    }
}
