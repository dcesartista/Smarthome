package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
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
    @BindView(R.id.pb_history)
    View pbHistory;
    @BindView(R.id.card_value)
    View cardValue;
    @BindView(R.id.tv_date)
    TextView tvDate;

    String homeId;
    private List<Device> devices;
    private String[] devicesName;
    private SharedPreferences preferences;
    private int selected = App.ENERGY;
    private Service service;
    private DashboardView dashboardView;
    private HistoryPresenter presenter;
    private Date date;
    private Calendar c;
    private String startDate;
    public ProgressDialog progressDialog;
    int type = App.ENERGY;
    int range = App.DAILY;
    private List<Home> homes;
    private String[] homeNames;
    private boolean initiateFetch;
    private DatePickerDialog.OnDateSetListener dateListener;

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

        dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                startDate = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                tvDate.setText(startDate);
                Log.e("DATE SELECTED",startDate);
                switch (type){
                    case App.CURRENT:
                        presenter.getCurrentHistory(startDate,range, homeId, type);
                        break;
                    case App.VOLTAGE:
                        presenter.getVoltageHistory(startDate,range, homeId, type);
                        break;
                }
            }

        };

        if(getUserVisibleHint()){
            dashboardView.changeColor(getResources().getColor(R.color.blueDark));
            dashboardView.changeHomeSelectorBackground(getResources().getColor(R.color.white));
            dashboardView.setToolbarText("History");
            dashboardView.setSettingVisibility(View.GONE);
            dashboardView.setHomeSelectorVisibility(View.VISIBLE);
//                presenter.getEnergyHistory(startDate,App.DAILY,homeId);
            setSelectedHistory(type);
//            presenter.getDeviceList(homeId);
        }

        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icDown1, iconFont);
        FontManager.markAsIconContainer(icDown2, iconFont);

        setStartdateToToday();
        presenter = new HistoryPresenter(service, this, getContext());

        setupSpinnerRange(type, true);

        //generateDummyData();
        //generateChart(data1);
        /*ArrayList<String> deviceData = new ArrayList<>();
        deviceData.add(0, "Semua Device");
        deviceData.add(1, "Device 1");
        deviceData.add(2, "Device 2");*/


        return rootView;
    }

    private void setupSpinnerRange(int t, boolean first){
        ArrayAdapter adapter;
        int s;
        switch (t){
            case App.ENERGY:
                tvDate.setOnClickListener(null);
                tvDate.setVisibility(View.GONE);
                spValue.setVisibility(View.VISIBLE);
                if(!first)
                    s = spRange.getSelectedItemPosition();
                else
                    s = 0;
                adapter = new ArrayAdapter(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.range1));
                spRange.setAdapter(adapter);
                if(type == App.CURRENT || type == App.VOLTAGE){
                    type = t;
                    spRange.setSelection(0);
                } else {
                    type = t;
                    spRange.setSelection(s);
                }
                break;
            case App.COST:
                tvDate.setOnClickListener(null);
                tvDate.setVisibility(View.GONE);
                spValue.setVisibility(View.VISIBLE);
                s = spRange.getSelectedItemPosition();
                adapter = new ArrayAdapter(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.range3));
                spRange.setAdapter(adapter);
                if(type == App.CURRENT || type == App.VOLTAGE){
                    type = t;
                    spRange.setSelection(0);
                } else {
                    type = t;
                    if(range == App.YEARLY){
                        spRange.setSelection(0);
                    } else {
                        spRange.setSelection(s);
                    }
                }
                break;
            default:
                s = spRange.getSelectedItemPosition();
                adapter = new ArrayAdapter(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.range2));
                spRange.setAdapter(adapter);
                type = t;
                if (s == 0){
                    spRange.setSelection(1);
                } else {
                    spRange.setSelection(0);
                }
                break;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (null != presenter){
                dashboardView.changeColor(getResources().getColor(R.color.blueDark));
                dashboardView.changeHomeSelectorBackground(getResources().getColor(R.color.white));
                dashboardView.setToolbarText("History");
                dashboardView.setSettingVisibility(View.GONE);
                dashboardView.setHomeSelectorVisibility(View.VISIBLE);
//                presenter.getEnergyHistory(startDate,App.DAILY,homeId);
                setSelectedHistory(type);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onStop();
    }

    private void setStartdateToToday(){
        c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 0);
        date = c.getTime();
        startDate = DateFormatter.formatDateToString(date, "yyyy-MM-dd");
    }

    private void setSelectedHistory(int selected){
        switch (selected){
            case App.ENERGY:
                Utils.setSelected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_white);
                Utils.setUnselected(tvCurrent, getContext());
                Utils.setUnselected(tvCost, getContext());
                Utils.setUnselected(tvVolt, getContext());
                break;
            case App.COST:
                Utils.setSelected(tvCost, getContext());
                Utils.setUnselected(tvVolt, getContext());
                Utils.setUnselected(tvCurrent, getContext());
                Utils.setUnselected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_yellow);
                break;
            case App.VOLTAGE:
                Utils.setSelected(tvVolt, getContext());
                Utils.setUnselected(tvCurrent, getContext());
                Utils.setUnselected(tvCost, getContext());
                Utils.setUnselected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_yellow);
                break;
            case App.CURRENT:
                Utils.setSelected(tvCurrent, getContext());
                Utils.setUnselected(tvCost, getContext());
                Utils.setUnselected(tvVolt, getContext());
                Utils.setUnselected(btnEnergy,ivEnergy, getContext(),R.drawable.ic_energy_yellow);
                break;
        }
    }

    @OnClick(R.id.btn_volt)
    void showVoltageGraph(){
        /*type = App.TEMPERATURE;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));*/
        type = App.VOLTAGE;
        setSelectedHistory(type);
        setupSpinnerRange(App.VOLTAGE, false);;
    }

    @OnClick(R.id.btn_energy)
    void showEnergyGraph(){

        setupSpinnerRange(App.ENERGY, false);
        type = App.ENERGY;
        setSelectedHistory(type);
       /* type = App.HUMIDITIY;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));*/
    }

    @OnClick(R.id.btn_cost)
    void showCostGraph(){
        setupSpinnerRange(App.COST, false);
        /*type = App.CARBONDIOXIDE;
        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));*/
        type = App.COST;
        setSelectedHistory(type);
    }

    @OnClick(R.id.btn_current)
    void showCurrentGraph(){
        /*type = App.ENERGY;
        presenter.getEnergyHistory(startDate,range, homeId);*/
        type = App.CURRENT;
        setSelectedHistory(type);
        setupSpinnerRange(App.CURRENT, false);

    }

    @OnItemSelected(R.id.sp_range)
    void onRangeSelected(int position) {
        Log.e("UYUYUY",""+type);
        Log.e("UYUYUY2",""+position);
        switch (type){
            case App.ENERGY:
                switch (position){
                    case 0:
                        setStartdateToToday();
                        range = App.DAILY;
                        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.month));
                        cardValue.setVisibility(View.VISIBLE);
                        spValue.setAdapter(adapter);
                        spValue.setSelection(Integer.parseInt(startDate.split("\\-")[1])-1);
                        break;
                    case 1:
                        setStartdateToToday();
                        range = App.MONTHLY;
                        int year = c.get(Calendar.YEAR);
                        String[] years = new String[]{String.valueOf(year-6),String.valueOf(year-5),String.valueOf(year-4),
                                String.valueOf(year-3),String.valueOf(year-2),String.valueOf(year-1),
                                String.valueOf(year),String.valueOf(year+1),String.valueOf(year+2),
                                String.valueOf(year+3),String.valueOf(year+4),String.valueOf(year+5)};
                        for(String y : years){
                            Log.e("YYY", y);
                        }
                        ArrayAdapter adapter2 = new ArrayAdapter(getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                years);
                        cardValue.setVisibility(View.VISIBLE);
                        spValue.setAdapter(adapter2);
                        spValue.setSelection(Arrays.asList(years).indexOf(startDate.split("\\-")[0]));
                        break;
                    case 2:
                        setStartdateToToday();
                        range = App.YEARLY;
                        cardValue.setVisibility(View.GONE);
                        presenter.getEnergyHistory(startDate,range,homeId);
                        break;
                }
                break;
            case App.COST:
                switch (position) {
                    case 0:
                        setStartdateToToday();
                        range = App.DAILY;
                        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.month));
                        cardValue.setVisibility(View.VISIBLE);
                        spValue.setAdapter(adapter);
                        spValue.setSelection(Integer.parseInt(startDate.split("\\-")[1])-1);
                        break;
                    case 1:
                        setStartdateToToday();
                        range = App.MONTHLY;
                        int year = c.get(Calendar.YEAR);
                        String[] years = new String[]{String.valueOf(year - 6), String.valueOf(year - 5), String.valueOf(year - 4),
                                String.valueOf(year - 3), String.valueOf(year - 2), String.valueOf(year - 1),
                                String.valueOf(year), String.valueOf(year + 1), String.valueOf(year + 2),
                                String.valueOf(year + 3), String.valueOf(year + 4), String.valueOf(year + 5)};
                        for (String y : years) {
                            Log.e("YYY", y);
                        }
                        ArrayAdapter adapter2 = new ArrayAdapter(getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                years);
                        cardValue.setVisibility(View.VISIBLE);
                        spValue.setAdapter(adapter2);
                        spValue.setSelection(Arrays.asList(years).indexOf(startDate.split("\\-")[0]));
                        break;
                }
                break;
            default:
                switch (position) {
                    case 0:
                        range = App.HOURLY;
                        Log.e("CALLED HOURLY","CALLED!!");
                        spValue.setVisibility(View.INVISIBLE);
                        tvDate.setVisibility(View.VISIBLE);
                        tvDate.setText(startDate);
                        tvDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar cal = Calendar.getInstance();
                                Date d = cal.getTime();
                                try {
                                    d = DateFormatter.formatDate(startDate,"yyyy-MM-dd");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                cal.setTime(d);
                                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateListener
                                        , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime());
                                datePickerDialog.show();
                            }
                        });
                        switch (type){
                            case App.CURRENT:
                                presenter.getCurrentHistory(startDate,range, homeId, type);
                                break;
                            case App.VOLTAGE:
                                presenter.getVoltageHistory(startDate,range, homeId, type);
                                break;
                        }
                        break;
                    case 1:
                        tvDate.setOnClickListener(null);
                        spValue.setVisibility(View.VISIBLE);
                        tvDate.setVisibility(View.GONE);
                        setStartdateToToday();
                        range = App.DAILY;
                        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.month));
                        cardValue.setVisibility(View.VISIBLE);
                        spValue.setAdapter(adapter);
                        spValue.setSelection(Integer.parseInt(startDate.split("\\-")[1])-1);
                        break;
                }
                break;
        }
//        presenter.getHistory(startDate,type,range, homeId, String.valueOf(selectedDevice.getId()));
    }

    @OnItemSelected(R.id.sp_value)
    void selectValue(AdapterView<?> parent, View view, int position, long id){
        switch (type){
            case App.ENERGY:
                switch (range){
                    case App.DAILY:
                        startDate = startDate.split("\\-")[0]+"-"+(position+1)+"-"+startDate.split("\\-")[2];
                        Log.e("BULAN: ", startDate);
                        break;
                    case App.MONTHLY:
                        startDate = parent.getItemAtPosition(position)+"-"+startDate.split("\\-")[1]+"-"+startDate.split("\\-")[2];
                        Log.e("TAHUN: ", startDate);
                        break;
                }
                presenter.getEnergyHistory(startDate,range,homeId);
                break;
            case App.COST:
                switch (range){
                    case App.DAILY:
                        startDate = startDate.split("\\-")[0]+"-"+(position+1)+"-"+startDate.split("\\-")[2];
                        Log.e("BULAN: ", startDate);
                        break;
                    case App.MONTHLY:
                        startDate = parent.getItemAtPosition(position)+"-"+startDate.split("\\-")[1]+"-"+startDate.split("\\-")[2];
                        Log.e("TAHUN: ", startDate);
                        break;
                }
                presenter.getCostHistory(startDate,range,homeId);
                break;
            default:
                switch (range){
                    case App.DAILY:
                        startDate = startDate.split("\\-")[0]+"-"+(position+1)+"-"+startDate.split("\\-")[2];
                        Log.e("BULAN: ", startDate);
                        break;
                    case App.HOURLY:
                        Log.e("TAHUN: ", startDate);
                        break;
                }
                switch (type){
                    case App.CURRENT:
                        presenter.getCurrentHistory(startDate,range, homeId, type);
                        break;
                    case App.VOLTAGE:
                        presenter.getVoltageHistory(startDate,range, homeId, type);
                        break;
                }
        }

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

            values = new ArrayList<>();
            float value = Float.parseFloat(h.getValue());
            Log.e("DATA!!!!", value+"");
            int color;
            /*if (value >= 40)
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_RED ));
            else if (value >=20)
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_ORANGE ));
            else*/
                values.add(new SubcolumnValue(value, getResources().getColor(R.color.colorAccent) ));
            String axisValue = h.getDate();
            if (range == App.DAILY){
                Date date = null;
                try {
                    date = DateFormatter.formatDate(h.getDate(), "yyyy-MM-dd");
                } catch (ParseException e) {

                }
                axisValue = DateFormatter.formatDateToString(date, "dd");
            } else if(range == App.HOURLY){
                Date date = null;
                try {
                    date = DateFormatter.formatDate(h.getDate(), "yyyy-MM-dd");
                } catch (ParseException e) {

                }
                Log.e("DATE!!",h.getDate());
                axisValue = h.getDate().substring(12,14);
            }


            Column column = new Column(values);
            column.setHasLabels(true);
            SimpleColumnChartValueFormatter formatter = new SimpleColumnChartValueFormatter();
            formatter.setDecimalDigitsNumber(2);
            column.setFormatter(formatter);
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
        chartView.setViewportCalculationEnabled(true);
        chartView.setZoomEnabled(false);
        chartView.setInteractive(true);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chartView.setColumnChartData(chartData);
        Viewport v = new Viewport(chartView.getMaximumViewport());
        v.left = 0;
        v.right = 10;
        chartView.setCurrentViewport(v);
        chartView.setMaxZoom(4);
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
                if((i+1) < 10){
                    axisValue = "0"+(i+1);
                } else {
                    axisValue = String.valueOf(i+1);
                }
            } else if(range == App.MONTHLY){
                axisValue = months[i].substring(0,3);
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
        chartView.setViewportCalculationEnabled(true);
        chartView.setZoomEnabled(false);
        chartView.setInteractive(true);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chartView.setColumnChartData(chartData);
        Viewport v = new Viewport(chartView.getMaximumViewport());
        v.left = 10;
        v.right = 10;
        chartView.setCurrentViewport(v);
        if (range == App.DAILY){
            Log.e("SETZOOM","ZOOM!!");
            chartView.setMaxZoom(5);
        } else {
            Log.e("SETZOOM2","ZOOM2!!");
            chartView.setMaxZoom(2);
        }

    }

    @Override
    public void showLoading() {
        if(getUserVisibleHint() && !progressDialog.isShowing()){
            Log.e("HISTORY","CALLED");
            progressDialog.show();
        }
        /*chartView.setVisibility(View.GONE);
        pbHistory.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void hideLoading() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();

        }
        /*chartView.setVisibility(View.VISIBLE);
        pbHistory.setVisibility(View.GONE);*/
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Toast.makeText(getContext(),getResources().getString(R.string.network_error),Toast.LENGTH_SHORT).show();
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
            case App.VOLTAGE:
                tvGraph.setText("Grafik Tegangan");
                break;
            case App.CURRENT:
                tvGraph.setText("Grafik Arus");
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
        switch (type){
            case App.COST:
                tvGraph.setText("Grafik Biaya");
                break;
            case App.ENERGY:
                tvGraph.setText("Grafik Energi");
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
//        spinnerDevice.setAdapter(deviceAdapter);
    }
}
