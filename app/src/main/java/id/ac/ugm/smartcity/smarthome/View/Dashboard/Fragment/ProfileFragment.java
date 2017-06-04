package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.User;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.LoginPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import id.ac.ugm.smartcity.smarthome.View.LoginActivity;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public static final String PROFILE_ARG = "PROFILE_ARG";

    private Service service;
    private View rootView;
    private DashboardView dashboardView;

    LoginPresenter presenter;
    private ProgressDialog progressDialog;

    public static ProfileFragment newInstance(int page, Service service, DashboardView dashboardView) {
        Bundle args = new Bundle();
        args.putInt(PROFILE_ARG, page);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        fragment.service = service;
        fragment.dashboardView = dashboardView;
        return fragment;
    }


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        presenter = new LoginPresenter(service, null, getContext());
        ButterKnife.bind(this,rootView);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        if (getUserVisibleHint()){
            dashboardView.setToolbarText("Profile");
            dashboardView.setHomeSelectorVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            dashboardView.setToolbarText("Profile");
            dashboardView.setHomeSelectorVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_logout)
    void logout(){
        SharedPreferences preferences = getContext().getSharedPreferences(App.USER_PREFERENCE,MODE_PRIVATE);

        Map<String,String> params = new HashMap<>();
        params.put(User.FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());

        presenter.updateFcmToken(preferences.getString(App.ID,""),params);

        Intent intent = new Intent(getContext(), LoginActivity.class);
        preferences.edit().clear().apply();
        startActivity(intent);
    }

}
