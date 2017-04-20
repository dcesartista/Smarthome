package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HistoryPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.DateFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
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
    @BindView(R.id.spr_device)
    Spinner spinnerDevice;
    @BindView(R.id.spr_range)
    Spinner spinnerRange;
    @BindView(R.id.graph_title)
    TextView tvGraph;

    //TODO : HOME ID DIBIKIN GAK STATIS, BIKIN HOME SELECTION ACTIVITY
    String homeId = "2";
    private List<Device> devices;
    private String[] devicesName;
    private Device selectedDevice;
    private Service service;
    private HistoryPresenter presenter;
    private Date date;
    private String startDate;
    private ProgressDialog progressDialog;
    int type = App.CARBONDIOXIDE;
    int range = App.DAILY;

    private ColumnChartData chartData;
    private double[] data1 = new double[7];
    private double[] data2 = new double[7];
    private double[] data3 = new double[7];

    public static final String HISTORY_ARG = "HISTORY_ARG";

    public static HistoryFragment newInstance(int page, Service service) {
        Bundle args = new Bundle();
        args.putInt(HISTORY_ARG, page);
        HistoryFragment fragment = new HistoryFragment();
        fragment.service = service;
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
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        date = c.getTime();
        startDate = DateFormatter.formatDateToString(date, "yyyy-MM-dd");
        presenter = new HistoryPresenter(service, this, getContext());
        //generateDummyData();
        //generateChart(data1);
        /*ArrayList<String> deviceData = new ArrayList<>();
        deviceData.add(0, "Semua Device");
        deviceData.add(1, "Device 1");
        deviceData.add(2, "Device 2");*/

        if (getUserVisibleHint()){
            presenter.getDeviceList(homeId);
        }

        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (null != presenter){
                presenter.getDeviceList(homeId);
            }
        }
    }

    @OnItemSelected(R.id.spr_device)
    void onDeviceSelected(int position) {
        if(devices.size() > 0) {
            selectedDevice = devices.get(position);
            Log.e("HMMMM000","LALALALCALLEDLALAL");
            presenter.getHistory(startDate, type, range, homeId, String.valueOf(selectedDevice.getId()));
        }
    }


    @OnClick(R.id.temperature)
    void showTemperaturaGraph(){
        type = App.TEMPERATURE;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));
    }

    @OnClick(R.id.humidity)
    void showHumidityGraph(){
        type = App.HUMIDITIY;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));
    }

    @OnClick(R.id.co2)
    void showCO2Graph(){
        type = App.CARBONDIOXIDE;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));
    }

    @OnItemSelected(R.id.spr_range)
    void onRangeSelected(int position) {
        switch (position){
            case 0:
                range = App.DAILY;
                break;
            case 1:
                range = App.MONTHLY;
                break;
            case 2:
                range = App.YEARLY;
                break;
        }
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));
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
                axisValue = DateFormatter.formatDateToString(date, "dd MMM");
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
        chartView.setColumnChartData(chartData);
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
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void showHistoryData(Response<List<HistoryData>> response, int range, int type) {
        Log.e("LLLL","CALLED");
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
        spinnerDevice.setAdapter(deviceAdapter);
    }
}
