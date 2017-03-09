package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HistoryPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryView {
    @BindView(R.id.chart_history)
    ColumnChartView chartView;
    @BindView(R.id.spr_device)
    Spinner spinnerDevice;

    private Service service;
    private HistoryPresenter presenter;

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
        presenter = new HistoryPresenter(service, this, getContext());
        presenter.getTemperatureDaily("2016-12-01");
        //generateDummyData();
        //generateChart(data1);
        /*ArrayList<String> deviceData = new ArrayList<>();
        deviceData.add(0, "Semua Device");
        deviceData.add(1, "Device 1");
        deviceData.add(2, "Device 2");
        ArrayAdapter deviceAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                deviceData);
        spinnerDevice.setAdapter(deviceAdapter);*/

        return rootView;
    }

    @OnClick(R.id.temperature)
    void showTempGraph(){

    }

    @OnItemSelected(R.id.spr_device)
    void onItemSelected(int position) {
        switch (position){
            /*case 0:
                generateChart(data1);
                break;
            case 1:
                generateChart(data2);
                break;
            case 2:
                generateChart(data3);
                break;*/
        }
    }

    private void generateDummyData(){
        for(int i = 0; i<7;i++) {
            data1[i] = Math.random() * 50f + 5;
            data2[i] = Math.random() * 50f + 5;
            data3[i] = Math.random() * 50f + 5;
        }
    }

    private void generateChart(List<HistoryData> histories){
        int numSubcolumns = 1;
        int numColumns = histories.size();
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<>();
        int i = 0;
        for (HistoryData h : histories) {
            Log.e("SIZE", String.valueOf(h.getValue()));
            values = new ArrayList<>();
            float value = (float) (h.getValue());
            int color;
            /*if (value >= 40)
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_RED ));
            else if (value >=20)
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_ORANGE ));
            else*/
                values.add(new SubcolumnValue(value, ChartUtils.COLOR_GREEN ));

            Column column = new Column(values);
            column.setHasLabels(true);
            columns.add(column);
            axisValues.add(new AxisValue(i, h.getDate().toCharArray()));
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void showHistoryData(List<HistoryData> hostories) {
        Log.e("SIZE", String.valueOf(hostories.size()));
        generateChart(hostories);
    }
}
