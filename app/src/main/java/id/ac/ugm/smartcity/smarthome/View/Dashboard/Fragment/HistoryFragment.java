package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HistoryPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.DateFormatter;
import id.ac.ugm.smartcity.smarthome.Utils.Utils;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryView {
    @BindView(R.id.chart_history)
    ColumnChartView chartView;
    @BindView(R.id.sp_value)
    Spinner spValue;
    @BindView(R.id.sp_range)
    Spinner spRange;
    @BindView(R.id.graph_title)
    TextView tvGraph;
    @BindView(R.id.ic_down1)
    TextView icDown1;
    @BindView(R.id.ic_down2)
    TextView icDown2;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_cost)
    TextView tvCost;
    @BindView(R.id.tv_volt)
    TextView tvVolt;
    @BindView(R.id.btn_energy)
    View btnEnergy;
    @BindView(R.id.iv_energy)
    ImageView ivEnergy;

    String homeId;
    private List<Device> devices;
    private String[] devicesName;
    private SharedPreferences preferences;
    private Device selectedDevice;
    private Service service;
    private DashboardView dashboardView;
    private HistoryPresenter presenter;
    private Date date;
    private Calendar c;
    private String startDate;
    private ProgressDialog progressDialog;
    int type = App.ENERGY;
    int range = App.DAILY;
    private List<Home> homes;
    private String[] homeNames;

    private ColumnChartData chartData;
    private double[] data1 = new double[7];
    private double[] data2 = new double[7];
    private double[] data3 = new double[7];

    public static final String HISTORY_ARG = "HISTORY_ARG";

    public static HistoryFragment newInstance(int page, Service service, DashboardView dashboardView) {
        Bundle args = new Bundle();
        args.putInt(HISTORY_ARG, page);
        HistoryFragment fragment = new HistoryFragment();
        fragment.service = service;
        fragment.dashboardView = dashboardView;
        fragment.setArguments(args);
        return fragment;
    }


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this,rootView);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        preferences = getContext().getSharedPreferences(App.USER_PREFERENCE,MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");

        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icDown1, iconFont);
        FontManager.markAsIconContainer(icDown2, iconFont);

        c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        date = c.getTime();
        startDate = DateFormatter.formatDateToString(date, "yyyy-MM-dd");
        presenter = new HistoryPresenter(service, this, getContext());

        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.range1));
        spRange.setAdapter(adapter);
        //generateDummyData();
        //generateChart(data1);
        /*ArrayList<String> deviceData = new ArrayList<>();
        deviceData.add(0, "Semua Device");
        deviceData.add(1, "Device 1");
        deviceData.add(2, "Device 2");*/

        if (getUserVisibleHint()){
            dashboardView.setToolbarText("History");
            dashboardView.setSettingVisibility(View.GONE);
            dashboardView.setHomeSelectorVisibility(View.VISIBLE);
            presenter.getEnergyHistory(startDate,App.DAILY,homeId);
            Utils.setSelected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_white);
        }

        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (null != presenter){
                dashboardView.setToolbarText("History");
                dashboardView.setSettingVisibility(View.GONE);
                dashboardView.setHomeSelectorVisibility(View.VISIBLE);
                presenter.getEnergyHistory(startDate,App.DAILY,homeId);
                Utils.setSelected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_white);
            }
        }
    }


    @OnClick(R.id.btn_volt)
    void showVoltageGraph(){
        /*type = App.TEMPERATURE;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));*/
        Utils.setSelected(tvVolt, getContext());
        Utils.setUnselected(tvCurrent, getContext());
        Utils.setUnselected(tvCost, getContext());
        Utils.setUnselected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_yellow);
    }

    @OnClick(R.id.btn_energy)
    void showEnergyGraph(){
        Utils.setSelected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_white);
        Utils.setUnselected(tvCurrent, getContext());
        Utils.setUnselected(tvCost, getContext());
        Utils.setUnselected(tvVolt, getContext());
       /* type = App.HUMIDITIY;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));*/
    }

    @OnClick(R.id.btn_cost)
    void showCostGraph(){
        /*type = App.CARBONDIOXIDE;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));*/
        Utils.setSelected(tvCost, getContext());
        Utils.setUnselected(tvVolt, getContext());
        Utils.setUnselected(tvCurrent, getContext());
        Utils.setUnselected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_yellow);
    }

    @OnClick(R.id.btn_current)
    void showCurrentGraph(){
        /*type = App.ENERGY;
        presenter.getEnergyHistory(startDate,range, homeId);*/
        Utils.setSelected(tvCurrent, getContext());
        Utils.setUnselected(tvCost, getContext());
        Utils.setUnselected(tvVolt, getContext());
        Utils.setUnselected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_yellow);
    }

    @OnItemSelected(R.id.sp_range)
    void onRangeSelected(int position) {
        switch (type){
            case App.ENERGY:
                switch (position){
                    case 0:
                        range = App.DAILY;
                        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.month));
                        spValue.setAdapter(adapter);
                        spValue.setSelection(Integer.parseInt(startDate.split("\\-")[1]));
                        break;
                    case 1:
                        range = App.MONTHLY;
                        int year = c.get(Calendar.YEAR);
                        String[] years = new String[]{String.valueOf(year-6),String.valueOf(year-5),String.valueOf(year-4),
                                String.valueOf(year-3),String.valueOf(year-2),String.valueOf(year-1),
                                String.valueOf(year),String.valueOf(year+1),String.valueOf(year+2),
                                String.valueOf(year+3),String.valueOf(year+4),String.valueOf(year+5)};
                        ArrayAdapter adapter2 = new ArrayAdapter(getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                years);
                        spValue.setAdapter(adapter2);
                        break;
                    case 2:
                        range = App.YEARLY;
                        break;
                }
            presenter.getEnergyHistory(startDate,range,homeId);
        }
//        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));
    }

    private void generateDummyData(){
        for(int i = 0; i<7;i++) {
            data1[i] = Math.random() * 50f + 5;
            data2[i] = Math.random() * 50f + 5;
            data3[i] = Math.random() * 50f + 5;
        }
    }

    private void generateChart(List<HistoryData> histories, int range){

        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<>();
        int i = 0;
        for (HistoryData h : histories) {
            Log.e("DATA", h.getValue());
            values = new ArrayList<>();
            float value = Float.parseFloat(h.getValue());
            int color;
            /*if (value >= 40)
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_RED ));
            else if (value >=20)
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_ORANGE ));
            else*/
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_GREEN ));
            String axisValue = h.getDate();
            if (range == App.DAILY){
                Date date = null;
                try {
                    date = DateFormatter.formatDate(h.getDate(), "yyyy-MM-dd");
                } catch (ParseException e) {

                }
                axisValue = DateFormatter.formatDateToString(date, "dd");
            }


            Column column = new Column(values);
            column.setHasLabels(true);
            columns.add(column);
            axisValues.add(new AxisValue(i, axisValue.toCharArray()));
            i++;
        }

        chartData = new ColumnChartData(columns);


        Axis axisX = new Axis(axisValues);
        axisX.setTextColor(R.color.textPrimary);
        axisX.setLineColor(R.color.textPrimary);
        Axis axisY = new Axis().setHasLines(true);
        axisY.setTextColor(R.color.textPrimary);
        axisY.setLineColor(R.color.textPrimary);
        chartData.setAxisXBottom(axisX);
        chartData.setAxisYLeft(axisY);
        chartView.setZoomEnabled(false);
        chartView.setInteractive(true);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chartView.setColumnChartData(chartData);
        Viewport v = new Viewport(chartView.getMaximumViewport());
        v.left = 0;
        v.right = 10;
        chartView.setCurrentViewport(v);
        chartView.setViewportCalculationEnabled(false);
        chartView.setMaxZoom(3);
    }

    private void generateChartEnergy(List<String> histories, int range){

        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<>();
        int i = 0;
        String[] months =getResources().getStringArray(R.array.month);
        int year = Integer.parseInt(startDate.split("\\-")[0]);
        Log.e("YEAR!!",year+" "+startDate);
        String[] years = new String[]{String.valueOf(year-6),String.valueOf(year-5),String.valueOf(year-4),
                String.valueOf(year-3),String.valueOf(year-2),String.valueOf(year-1),
                String.valueOf(year),String.valueOf(year+1),String.valueOf(year+2),
                String.valueOf(year+3),String.valueOf(year+4),String.valueOf(year+5)};
        for (String h : histories) {
            values = new ArrayList<>();
            float value = Float.parseFloat(h);
            int color;
            /*if (value >= 40)
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_RED ));
            else if (value >=20)
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_ORANGE ));
            else*/
            values.add(new SubcolumnValue(value, getResources().getColor(R.color.colorAccent) ));
            String axisValue = null;
            if (range == App.DAILY){
                if(histories.indexOf(h)+1 < 10){
                    axisValue = "0"+histories.indexOf(h)+1;
                } else {
                    axisValue = String.valueOf(histories.indexOf(h)+1);
                }
            } else if(range == App.MONTHLY){
                axisValue = months[i];
            } else if(range == App.YEARLY){
                axisValue = years[i];
            }


            Column column = new Column(values);
            column.setHasLabels(true);
            columns.add(column);
            axisValues.add(new AxisValue(i, axisValue.toCharArray()));
            i++;
        }

        chartData = new ColumnChartData(columns);


        Axis axisX = new Axis(axisValues);
        axisX.setTextColor(R.color.textPrimary);
        axisX.setLineColor(R.color.textPrimary);
        Axis axisY = new Axis().setHasLines(true);
        axisY.setTextColor(R.color.textPrimary);
        axisY.setLineColor(R.color.textPrimary);
        chartData.setAxisXBottom(axisX);
        chartData.setAxisYLeft(axisY);
        chartView.setZoomEnabled(false);
        chartView.setInteractive(true);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chartView.setColumnChartData(chartData);
        Viewport v = new Viewport(chartView.getMaximumViewport());
        v.left = 10;
        v.right = 10;
        chartView.setCurrentViewport(v);
        chartView.setViewportCalculationEnabled(true);
        if (range == App.DAILY){
            chartView.setMaxZoom(5);
        } else {
            chartView.setMaxZoom(2);
        }

    }

    @Override
    public void showLoading() {
        if(getUserVisibleHint()){
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
        Toast.makeText(getContext(),appErrorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHistoryData(Response<List<HistoryData>> response, int range, int type) {
        Log.e("LLLL","respon"+response.body());
        List<HistoryData> histories = response.body();
        SharedPreferences.Editor editor = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        if(response.code() == 200) {
            /*editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
            editor.putString(App.CLIENT, response.headers().get("Client"));
            editor.putString(App.EXPIRY, response.headers().get("Expiry"));
            editor.putString(App.UID, response.headers().get("Uid"));
            editor.commit();*/
        }
        generateChart(histories, range);
        switch (type){
            case App.TEMPERATURE:
                tvGraph.setText("Grafik Temperatur");
                break;
            case App.HUMIDITIY:
                tvGraph.setText("Grafik Kelembaban");
                break;
            case App.CARBONDIOXIDE:
                tvGraph.setText("Grafik CO2");
                break;
        }
    }

    @Override
    public void showHistoryEnergy(Response<List<String>> response, int range) {
        List<String> histories = response.body();
        SharedPreferences.Editor editor = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        if(response.code() == 200) {
            /*editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
            editor.putString(App.CLIENT, response.headers().get("Client"));
            editor.putString(App.EXPIRY, response.headers().get("Expiry"));
            editor.putString(App.UID, response.headers().get("Uid"));
            editor.commit();*/
        }
        generateChartEnergy(histories, range);
        tvGraph.setText("Konsumsi Energi per KwH");
    }

    @Override
    public void getDeviceSuccess(Response<List<Device>> response) {
        Log.e("HMMMMzzz", String.valueOf(response.code()));
        SharedPreferences.Editor editor = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        if(response.code() == 200) {
            /*editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
            editor.putString(App.CLIENT, response.headers().get("Client"));
            editor.putString(App.EXPIRY, response.headers().get("Expiry"));
            editor.putString(App.UID, response.headers().get("Uid"));
            editor.commit();*/
        }
        Log.e("HMMMMppp222","sss"+getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACCESS_TOKEN,""));
        devices = response.body();
        int i = 0;
        devicesName = new String[devices.size()];
        for (Device device : devices){
            devicesName[i] = device.getName();
            i++;
        }

        ArrayAdapter deviceAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                devicesName);
//        spinnerDevice.setAdapter(deviceAdapter);
    }
}
