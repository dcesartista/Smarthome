package id.ac.ugm.smartcity.smarthome.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HistoryPresenter;
import id.ac.ugm.smartcity.smarthome.Presenter.NewHomePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;
import retrofit2.Response;

public class NewHomeActivity extends BaseActivity implements NewHomeView {

    @BindView(R.id.ic_home)
    TextView icHome;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_home_id)
    EditText etHomeId;
    @BindView(R.id.et_gateway1)
    EditText etGateway1;
    @BindView(R.id.et_gateway2)
    EditText etGateway2;
    @BindView(R.id.et_gateway3)
    EditText etGateway3;
    @BindView(R.id.et_gateway4)
    EditText etGateway4;

    @Inject
    public Service service;

    private NewHomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);
        ButterKnife.bind(this);
        getDeps().inject(this);

        presenter = new NewHomePresenter(service, this, this);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icHome, iconFont);
    }

    @OnClick(R.id.btn_save)
    void saveHome(){
        Map<String, String> params = new HashMap<>();
        params.put(Home.NAME,etName.getText().toString());
        params.put(Home.ID, etHomeId.getText().toString());
        params.put(Home.GATEWAY, etGateway1.getText().toString()+"-"+etGateway2.getText().toString()
                    +"-"+etGateway3.getText().toString()+"-"+etGateway4.getText().toString());
        presenter.postNewHome(params);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void saveHomeFailed(String appErrorMessage) {

    }

    @Override
    public void saveHomeSuccess(Response<Home> response) {
        SharedPreferences.Editor editor = getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        editor.putString(App.ACTIVE_HOME, String.valueOf(response.body().getId()));
        editor.commit();
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
    }
}
