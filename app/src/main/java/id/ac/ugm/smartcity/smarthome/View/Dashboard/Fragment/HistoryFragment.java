package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
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
public class HistoryFragment extends Fragment {
    @BindView(R.id.chart_history)
    ColumnChartView chartView;
    @BindView(R.id.spr_device)
    Spinner spinnerDevice;

    private ColumnChartData chartData;
    private double[] data1 = new double[7];
    private double[] data2 = new double[7];
    private double[] data3 = new double[7];

    public static final String HISTORY_ARG = "HISTORY_ARG";

    public static HistoryFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(HISTORY_ARG, page);
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public HistoryFragment() {
        // Required empty public constructor
    }

    @OnItemSelected(R.id.spr_device)
    void onItemSelected(int position) {
        switch (position){
            case 0:
                generateChart(data1);
                break;
            case 1:
                generateChart(data2);
                break;
            case 2:
                generateChart(data3);
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this,rootView);
        generateDummyData();
        generateChart(data1);
        ArrayList<String> deviceData = new ArrayList<>();
        deviceData.add(0, "Semua Device");
        deviceData.add(1, "Device 1");
        deviceData.add(2, "Device 2");
        ArrayAdapter deviceAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                deviceData);
        spinnerDevice.setAdapter(deviceAdapter);

        return rootView;
    }

    private void generateDummyData(){
        for(int i = 0; i<7;i++) {
            data1[i] = Math.random() * 50f + 5;
            data2[i] = Math.random() * 50f + 5;
            data3[i] = Math.random() * 50f + 5;
        }
    }

    private void generateChart(double[] data){
        int numSubcolumns = 1;
        int numColumns = 7;
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            for (int j = 0; j < numSubcolumns; ++j) {
                float value = (float) (data[i]);
                int color;
                if (value >= 40)
                    values.add(new SubcolumnValue(value, ChartUtils.COLOR_RED ));
                else if (value >=20)
                    values.add(new SubcolumnValue(value, ChartUtils.COLOR_ORANGE ));
                else
                    values.add(new SubcolumnValue(value, ChartUtils.COLOR_GREEN ));

            }

            Column column = new Column(values);
            column.setHasLabels(true);
            columns.add(column);
        }

        chartData = new ColumnChartData(columns);

        List<AxisValue> axisValues = new ArrayList<>();
        axisValues.add(new AxisValue(0, "1 Jan".toCharArray()));
        axisValues.add(new AxisValue(1, "2 Jan".toCharArray()));
        axisValues.add(new AxisValue(2, "3 Jan".toCharArray()));
        axisValues.add(new AxisValue(3, "4 Jan".toCharArray()));
        axisValues.add(new AxisValue(4, "5 Jan".toCharArray()));
        axisValues.add(new AxisValue(5, "6 Jan".toCharArray()));
        axisValues.add(new AxisValue(6, "7 Jan".toCharArray()));
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

}
