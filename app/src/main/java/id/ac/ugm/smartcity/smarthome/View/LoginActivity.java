package id.ac.ugm.smartcity.smarthome.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.User;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Login.LoginUser;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.LoginPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements LoginView {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Inject
    public Service service;

    LoginPresenter presenter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(service, this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
    }

    @OnClick(R.id.btn_sign_in)
    void signIn(){
        Map<String,String> loginParams = new HashMap<>();
        loginParams.put("email",etUserName.getText().toString());
        loginParams.put("password",etPassword.getText().toString());
        presenter.signIn(loginParams);
    }

    @OnClick(R.id.register)
    void goToRegister(){
        Log.e("HAHAHA","ASDASD");
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this,"invalid username or password",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(Response<LoginUser> response) {
        SharedPreferences.Editor editor = getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        editor.putString(App.ID, String.valueOf(response.body().getData().getId()));
        editor.putString(App.USER_EMAIL, response.body().getData().getEmail());
        editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
        editor.putString(App.CLIENT, response.headers().get("Client"));
        editor.putString(App.EXPIRY, response.headers().get("Expiry"));
        editor.putString(App.UID, response.headers().get("Uid"));
        //TODO : HOME ID DIBIKIN GAK STATIS, BIKIN HOME SELECTION ACTIVITY
        editor.commit();

        LoginUser user = response.body();
        Map<String,String> params = new HashMap<>();
        params.put(User.FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());

        presenter.updateFcmToken(String.valueOf(user.getData().getId()),params);
        presenter.getHomes();
    }

    @Override
    public void getHomeSuccess(Response<List<Home>> response) {
        Intent intent;
        if(null != response.body() && response.body().size()>0) {
            SharedPreferences.Editor editor = getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
            editor.putString(App.ACTIVE_HOME, String.valueOf(response.body().get(0).getId()));
            editor.commit();
            intent = new Intent(this, DashBoardActivity.class);
        } else {
            intent = new Intent(this, NewHomeActivity.class);
        }
        startActivity(intent);
    }
}
