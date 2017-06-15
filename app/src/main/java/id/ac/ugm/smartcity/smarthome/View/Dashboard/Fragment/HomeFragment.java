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
import com.squareup.picasso.Picasso;

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
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
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
    @BindView(R.id.chart_container)
    LinearLayout chartContainer;
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
    @BindView(R.id.line_chart)
    LineChartView lineChartView;
    @BindView(R.id.tv_rp)
    TextView tvRp;
    @BindView(R.id.tv_kwh)
    TextView tvKwh;
    @BindView(R.id.tv_energy_consumption)
    TextView tvEnergyConsumption;
    @BindView(R.id.iv_rupiah)
    ImageView ivRupiah;
    @BindView(R.id.empty_chart)
    View emptyChart;

    private List<Alert> alerts;
    private LineChartData lineChartData;
    private LinearLayoutManager layoutManager;
    private CurrentAlertAdapter adapter;
    private List<Integer> energyChartData;
    private List<Integer> costChartData;
    private PieChartData data;
    private String homeId;
    private View rootView;
    private Service service;
    private CurrentEnergy currentEnergy;
    private String cost;
    private DashboardView dashboardView;
    private HomePresenter presenter;
    private ProgressDialog progressDialog;
    private Home home;
    private Date currentDate;
    private int currentShown = App.ENERGY;
    private final BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (currentShown){
                case App.ENERGY:
                    presenter.getEnergyChart(homeId);
                    break;
                case App.COST:
                    //TODO:ADD GET COST CHART
                    break;
            }
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

        currentDate = calendar.getTime();
        tvDate.setText(DateFormatter.formatDateToString(currentDate,"dd MMMM yyyy"));

        SharedPreferences preferences = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");
        presenter = new HomePresenter(service, this, getContext());
        Log.e("HMMMMppp222","sss1"+getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACCESS_TOKEN,""));
        if (getUserVisibleHint()){
            dashboardView.setToolbarText("SmartHome");
            dashboardView.setSettingVisibility(View.VISIBLE);
            dashboardView.setHomeSelectorVisibility(View.VISIBLE);
            presenter.getAlerts(homeId);
            presenter.getEnergyChart(homeId);
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
                presenter.getEnergyChart(homeId);
                presenter.getCurrentEnergy(homeId);
                presenter.getCurrentCost(homeId);
            }
        }
    }

    @OnClick(R.id.btn_change)
    void change(){
        switch (currentShown){
            case App.ENERGY:
                currentShown = App.COST;
                break;
            case App.COST:
                currentShown = App.ENERGY;
                break;
        }
        updateUICostEnergy();
    }

    private void generateChartData(List<Integer> chartData){
        List<Line> lines = new ArrayList<>();


        for(int i =0;i<3;i++){
            List<PointValue> values = new ArrayList<>();
            for (int j = 0; j < 24; j++) {
                switch (i){
                    case 0:
                        values.add(new PointValue(j, chartData.get(j)));
                        break;
                    case 1:
                        if((Calendar.getInstance().get(Calendar.HOUR_OF_DAY)-1) == j) {
                            values.add(new PointValue(j, chartData.get(j)));
                        }
                        break;
                    case 2:
                        if(j <= (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)-1)){
                            values.add(new PointValue(j, chartData.get(j)));
                        }
                        break;
                }
            }

            Line line = new Line(values);
            switch (i){
                case 0:
                    line.setHasLines(false);
                    line.setHasPoints(false);
                    break;
                case 1:
                    line.setHasLines(false);
                    line.setHasPoints(true);
                    break;
                case 2:
                    line.setHasLines(true);
                    line.setHasPoints(false);
            }
            line.setColor(getResources().getColor(R.color.white));
            line.setShape(ValueShape.CIRCLE);
            line.setCubic(false);
            line.setFilled(false);
            line.setHasLabels(false);
            line.setHasLabelsOnlyForSelected(false);
            lines.add(line);
        }

        lineChartData = new LineChartData(lines);
        lineChartData.setBaseValue(0f);
        lineChartView.setLineChartData(lineChartData);
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

    private void updateUICostEnergy(){
        switch (currentShown){
            case App.ENERGY:
                generateChartData(energyChartData);
                dashboardView.changeColor(getResources().getColor(R.color.blueDark));
                tvEnergyConsumption.setTextColor(getResources().getColor(R.color.blueLight));
                tvEnergyConsumption.setText("Energy Consumption");
                tvEnergyLimit.setTextColor(getResources().getColor(R.color.blueLight));
                tvEnergy.setText(NumberFormatter.formatWithDots(currentEnergy.getValue()/1000));
                arcView.deleteAll();
                arcView.addSeries(new SeriesItem.Builder(getResources().getColor(R.color.blueLight))
                        .setRange(0, Float.parseFloat(home.getUpperenergy()), (float) currentEnergy.getValue())
                        .setLineWidth(Utils.pxFromDp(getContext(), 16))
                        .setCapRounded(false)
                        .build());
                tvEnergyLimit.setText("Monthly limit : " + (Float.parseFloat(home.getUpperenergy()) / 1000) + "Kwh");
                tvRp.setVisibility(View.GONE);
                tvKwh.setVisibility(View.VISIBLE);
                tvBiaya.setText("Total Cost Rp " +NumberFormatter.formatWithDots(Integer.parseInt(cost.split("\\.")[0])));
                tvBiaya.setTextColor(getResources().getColor(R.color.greenDark));
                Picasso.with(getContext())
                        .load(R.drawable.ic_rupiah)
                        .into(ivRupiah);
                tvCostLimit.setText("Monthly limit Rp "+home.getCostLimit());
                int costbar = Integer.parseInt(cost.split("\\.")[0]);
                int costLimit = Integer.parseInt(home.getCostLimit().split("\\.")[0]);
                progressBar.setProgress(costbar/costLimit);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_green));
                emptyChart.setBackgroundDrawable(getResources().getDrawable(R.drawable.chart_back_blue));
                break;
            case App.COST:
                generateChartData(costChartData);
                dashboardView.changeColor(getResources().getColor(R.color.greenDark));
                tvEnergyConsumption.setTextColor(getResources().getColor(R.color.greenLight));
                tvEnergyConsumption.setText("Total Cost");
                tvEnergyLimit.setTextColor(getResources().getColor(R.color.greenLight));
                tvBiaya.setText("Energy Consumption " +NumberFormatter.formatWithDots(currentEnergy.getValue()/1000) + " Kwh");
                tvBiaya.setTextColor(getResources().getColor(R.color.blueDark));
                Picasso.with(getContext())
                        .load(R.drawable.ic_energy_consumption_blue)
                        .into(ivRupiah);
                tvCostLimit.setText("Monthly limit "+(Float.parseFloat(home.getUpperenergy()) / 1000) + " Kwh");
                int energy = (int)currentEnergy.getValue();
                int energyLimit = Integer.parseInt(home.getUpperenergy().split("\\.")[0]);
                progressBar.setProgress(energy/energyLimit);
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_blue));
                tvEnergy.setText(NumberFormatter.formatWithDots(Integer.parseInt(cost.split("\\.")[0])));
                arcView.deleteAll();
                arcView.addSeries(new SeriesItem.Builder(getResources().getColor(R.color.greenLight))
                        .setRange(0, Float.parseFloat(home.getCostLimit()), Float.parseFloat(cost))
                        .setLineWidth(Utils.pxFromDp(getContext(), 16))
                        .setCapRounded(false)
                        .build());
                tvEnergyLimit.setText("Monthly limit : " + NumberFormatter.formatWithDots(Integer.parseInt(home.getCostLimit().split("\\.")[0])));
                tvRp.setVisibility(View.VISIBLE);
                tvKwh.setVisibility(View.GONE);
                emptyChart.setBackgroundDrawable(getResources().getDrawable(R.drawable.chart_back_green));
                break;
        }
    }

    @Override
    public void showCurrentEnergy(Response<CurrentEnergy> response) {
        Log.e("LALALA",response.body().toString());
        currentEnergy = response.body();
        Log.e("ENERGY SEKARANG",""+(float)currentEnergy.getValue());
        Log.e("ENERGY LIMIT",""+Float.parseFloat(home.getUpperenergy()));
        updateUICostEnergy();
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
        cost = response.body();
        updateUICostEnergy();
        switch (currentShown){
            case App.ENERGY:

                break;
            case App.COST:

                break;
        }

    }

    @Override
    public void showEnergyChart(Response<List<Integer>> response) {
        energyChartData = response.body();
        updateUICostEnergy();
    }

    @Override
    public void showCostChart(Response<List<Double>> response) {
        for (Double d : response.body()){
            costChartData.add((int) Math.round(d));
        }
        updateUICostEnergy();
    }
}
