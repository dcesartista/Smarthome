package id.ac.ugm.smartcity.smarthome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertFragment extends Fragment {

    public static final String ALERT_ARG = "ALERT_ARG";

    public static AlertFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ALERT_ARG, page);
        AlertFragment fragment = new AlertFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public AlertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert, container, false);
    }

}
