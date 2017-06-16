package id.ac.ugm.smartcity.smarthome.View;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Alert;
import id.ac.ugm.smartcity.smarthome.Model.AlertGroup;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert.AlertDay;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.AlertDetailPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.DateFormatter;
import id.ac.ugm.smartcity.smarthome.adapter.AlertAdapter;
import retrofit2.Response;

public class AlertDetailActivity extends BaseActivity implements AlertDetailView {
    @BindView(R.id.ic_back)
    TextView icBack;
    @BindView(R.id.rv_alert)
    RecyclerView rvAlert;
    @BindView(R.id.pb_alert)
    View pbAlert;

    @Inject
    public Service service;

    private List<DisplayableItem> displayableItems;
    private AlertAdapter adapter;
    private LinearLayoutManager layoutManager;
    private AlertDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_detail);
        ButterKnife.bind(this);
        getDeps().inject(this);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icBack, iconFont);

        presenter = new AlertDetailPresenter(service, this, this);

        setupRecyclerView();
        presenter.getAlerts(getIntent().getStringExtra(Home.ID));
    }

    private void setupRecyclerView() {
        displayableItems = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAlert.setLayoutManager(layoutManager);
        adapter = new AlertAdapter(displayableItems, this);
        rvAlert.setAdapter(adapter);
    }

    @OnClick(R.id.ic_back)
    void back(){
        super.onBackPressed();
    }

    @Override
    public void showLoading() {
        pbAlert.setVisibility(View.VISIBLE);
        rvAlert.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        pbAlert.setVisibility(View.GONE);
        rvAlert.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void showAlert(Response<List<AlertGroup>> response) throws ParseException {
        Log.e("ASDASDASDASDAS", "HAHAHEHEIHUHU");
        List<AlertGroup> alertGroups = response.body();
        displayableItems.clear();

        for (AlertGroup alertGroup : alertGroups){
            Log.e("ASDASDASDASDAS", alertGroup.getDate());
            Calendar c = Calendar.getInstance();
            c.setTimeZone(new SimpleTimeZone(7, "GMT"));
            c.set(Calendar.HOUR_OF_DAY, 0-7);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0+1);
            c.set(Calendar.MILLISECOND, 0);
            Date date = c.getTime();
            Log.e("DATE1",date.toString());
            Date alertDate = DateFormatter.convertServerDateFormat(alertGroup.getDate());
            Log.e("DATE2",alertDate.toString());
            if(alertDate.equals(date)){
                displayableItems.add(new AlertDay("hari ini"));
            } else {
                displayableItems.add(new AlertDay(DateFormatter.convertDateToStringDate(alertGroup.getDate())));
            }
            List<Alert> alerts = alertGroup.getValue();
            if(null != alerts && alerts.size()>0){
                for (Alert alert : alerts){
                    Log.e("ASDASDASDASDAS", alert.getStatus());
                    displayableItems.add(alert);
                }
            }

        }
        adapter.notifyDataSetChanged();
    }
}
