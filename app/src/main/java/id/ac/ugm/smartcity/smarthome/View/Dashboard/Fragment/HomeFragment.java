package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Alert;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HomePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.DateFormatter;
import id.ac.ugm.smartcity.smarthome.Utils.NumberFormatter;
import id.ac.ugm.smartcity.smarthome.Utils.Utils;
import id.ac.ugm.smartcity.smarthome.View.AlertDetailActivity;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import id.ac.ugm.smartcity.smarthome.adapter.CurrentAlertAdapter;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {

    public static final String HOME_ARG = "HOME_ARG";

    @BindView(R.id.tv_energy)
    TextView tvEnergy;
    @BindView(R.id.tv_biaya)
    TextView tvBiaya;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.alert_container)
    LinearLayout alertContainer;
    @BindView(R.id.pb_notif)
    View pbNotif;
    @BindView(R.id.arc_view)
    DecoView arcView;
    @BindView(R.id.tv_monthly_limit)
    TextView tvEnergyLimit;
    @BindView(R.id.tv_cost_limit)
    TextView tvCostLimit;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private List<Alert> alerts;
    private LinearLayoutManager layoutManager;
    private CurrentAlertAdapter adapter;
    private PieChartData data;
    private String homeId;
    private View rootView;
    private Service service;
    private DashboardView dashboardView;
    private HomePresenter presenter;
    private ProgressDialog progressDialog;
    private Home home;
    private final BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getCurrentEnergy(homeId);
            presenter.getCurrentCost(homeId);
        }
    };

    public static HomeFragment newInstance(int page, Service service, DashboardView dashboardView, Home home) {
        Bundle args = new Bundle();
        args.putInt(HOME_ARG, page);
        HomeFragment fragment = new HomeFragment();
        fragment.service = service;
        fragment.dashboardView = dashboardView;
        fragment.home = home;
        fragment.setArguments(args);
        return fragment;
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,rootView);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.blueDark));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(new SimpleTimeZone(7, "GMT"));

        Date currentDate = calendar.getTime();
        tvDate.setText(DateFormatter.formatDateToString(currentDate,"dd MMM yyyy"));

        SharedPreferences preferences = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");
        presenter = new HomePresenter(service, this, getContext());
        Log.e("HMMMMppp222","sss1"+getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACCESS_TOKEN,""));
        if (getUserVisibleHint()){
            dashboardView.setToolbarText("SmartHome");
            dashboardView.setSettingVisibility(View.VISIBLE);
            dashboardView.setHomeSelectorVisibility(View.VISIBLE);
            presenter.getAlerts(homeId);
            presenter.getCurrentEnergy(homeId);
            presenter.getCurrentCost(homeId);
        }


        /*arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(32f)
                .build());*/


        generateData();

        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);

        getContext().registerReceiver(updateReceiver, new IntentFilter(App.UPDATE_ENERGY));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(updateReceiver);
        presenter.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (null != presenter){
                dashboardView.setToolbarText("SmartHome");
                dashboardView.setSettingVisibility(View.VISIBLE);
                dashboardView.setHomeSelectorVisibility(View.VISIBLE);
                presenter.getAlerts(homeId);
                presenter.getCurrentEnergy(homeId);
                presenter.getCurrentCost(homeId);
            }
        }
    }

    private void generateData() {

        List<SliceValue> values = new ArrayList<SliceValue>();
        SliceValue sliceValue = new SliceValue(100000f, getResources().getColor(android.R.color.transparent));
        SliceValue sliceValue2 = new SliceValue(200000f, getResources().getColor(R.color.blueLight));
        values.add(sliceValue);
        values.add(sliceValue2);

        data = new PieChartData(values);
        data.setHasCenterCircle(true);
        data.setCenterCircleScale(0.75f);
        data.setSlicesSpacing(0);

    }

    @OnClick(R.id.tv_show_notif)
    void goToNotif(){
        Intent intent = new Intent(getContext(), AlertDetailActivity.class);
        intent.putExtra(Home.ID, homeId);
        startActivity(intent);
    }


    @Override
    public void showLoading() {
        if(getUserVisibleHint() && !progressDialog.isShowing()){
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
    public void showProgressBar(int type) {
        switch (type){
            case App.ENERGY:
                tvEnergy.setVisibility(View.GONE);
//                tvDaya.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void hideProgressBar(int type) {
        switch (type){
            case App.ENERGY:
                tvEnergy.setVisibility(View.VISIBLE);
//                tvDaya.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void showNotifProgressBar() {
        pbNotif.setVisibility(View.VISIBLE);
        alertContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideNotifProgressBar() {
        pbNotif.setVisibility(View.GONE);
        alertContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void showCurrentEnergy(Response<CurrentEnergy> response) {
        Log.e("LALALA",response.body().toString());
        CurrentEnergy currentEnergy = response.body();
        tvEnergy.setText(NumberFormatter.formatWithDots(currentEnergy.getValue()/1000));
//        tvDaya.setText(NumberFormatter.formatWithDots(currentEnergy.getPower()));
        Log.e("ENERGY SEKARANG",""+(float)currentEnergy.getValue());
        Log.e("ENERGY LIMIT",""+Float.parseFloat(home.getUpperenergy()));
        arcView.addSeries(new SeriesItem.Builder(getResources().getColor(R.color.blueLight))
                .setRange(0, Float.parseFloat(home.getUpperenergy()),(float)currentEnergy.getValue())
                .setLineWidth(Utils.pxFromDp(getContext(),16))
                .setCapRounded(false)
                .build());
        tvEnergyLimit.setText("Monthly limit : "+(Float.parseFloat(home.getUpperenergy())/1000)+"Kwh");
    }

    @Override
    public void showAlert(Response<List<Alert>> response) throws ParseException {
        alerts = response.body();
        alertContainer.removeAllViews();
        for(Alert alert : alerts){
            View layout = LayoutInflater.from(getContext()).inflate(R.layout.alert_log, alertContainer, false);
            ImageView ivAlert = (ImageView) layout.findViewById(R.id.icon_alert);
            TextView tvTitle = (TextView)layout.findViewById(R.id.tv_alert_title);
            TextView tvDesc = (TextView)layout.findViewById(R.id.tv_alert_desc);
            TextView tvTime = (TextView)layout.findViewById(R.id.tv_time);

            if(alert.getAlertType() != null ){
                switch (alert.getAlertType()){
                    case "Energy":
                        ivAlert.setImageResource(R.drawable.ic_energy_yellow);
                        break;
                    case "Temperature":
                        ivAlert.setImageResource(R.drawable.icon_temp);
                        break;
                    case "Humidity":
                        ivAlert.setImageResource(R.drawable.icon_humidity);
                        break;
                    case "Cost":
                        break;
                    case "Light":
                        break;
                    case "Carbondioxide":
                        break;
                    default:
                        ivAlert.setImageResource(R.drawable.ic_energy_yellow);
                        break;
                }
            }
            tvTitle.setText(alert.getAlertType());
            tvDesc.setText(alert.getStatus());
            tvTime.setText(DateFormatter.convertDateToStringTime(alert.getCreatedAt()));

            alertContainer.addView(layout);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showCostProgressBar() {
//        pbBiaya.setVisibility(View.VISIBLE);
        tvBiaya.setVisibility(View.GONE);
    }

    @Override
    public void hideCostProgressBar() {
//        pbBiaya.setVisibility(View.GONE);
        tvBiaya.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCost(Response<String> response) {
        String r = response.body();
        tvBiaya.setText("Total Cost Rp " +NumberFormatter.formatWithDots(Integer.parseInt(r.split("\\.")[0])));
        tvCostLimit.setText("Monthly limit Rp "+home.getCostLimit());
        int cost = Integer.parseInt(r.split("\\.")[0]);
        int costLimit = Integer.parseInt(home.getCostLimit().split("\\.")[0]);
        Log.e("COST", ""+cost);
        Log.e("COST LIMIT", ""+costLimit);
        progressBar.setProgress(cost/costLimit);
    }
}
