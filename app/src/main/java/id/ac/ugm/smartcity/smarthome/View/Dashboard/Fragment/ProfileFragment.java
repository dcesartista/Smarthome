package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.User;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Profile.AddDevice;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Profile.AddHouse;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Profile.HomeSetting;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Profile.SignOut;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.ProfilePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import id.ac.ugm.smartcity.smarthome.adapter.ProfileAdapter;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileView {
    public static final String PROFILE_ARG = "PROFILE_ARG";

    @BindView(R.id.rv_profile)
    RecyclerView rvProfile;

    private List<DisplayableItem> displayableItems;
    private List<Home> homes;
    private Service service;
    private View rootView;
    private ProfileAdapter adapter;
    private LinearLayoutManager layoutManager;
    private DashboardView dashboardView;
    private SharedPreferences preferences;

    ProfilePresenter presenter;
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
        presenter = new ProfilePresenter(service, this, getContext());
        ButterKnife.bind(this,rootView);

        setupRecyclerView();
        preferences = getContext().getSharedPreferences(App.USER_PREFERENCE,MODE_PRIVATE);
        presenter.getHomes();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        if (getUserVisibleHint()){
            dashboardView.changeColor(getResources().getColor(R.color.blueDark));
            dashboardView.setToolbarText("Profile");
            dashboardView.setHomeSelectorVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(null != presenter) {
                dashboardView.changeColor(getResources().getColor(R.color.blueDark));
                dashboardView.setToolbarText("Profile");
                dashboardView.setHomeSelectorVisibility(View.GONE);
            }
        }
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
    public void getHomeSuccess(Response<List<Home>> response) {
        homes=response.body();
        for (Home home: homes){
            presenter.getDeviceList(String.valueOf(home.getId()), homes.indexOf(home));
        }
        updateUI();
    }

    @Override
    public void getDeviceSuccess(Response<List<Device>> response, int index) {
        homes.get(index).setDevices(response.body());
        updateUI();
    }

    private void updateUI(){
        displayableItems.clear();
        displayableItems.add(new User(Integer.parseInt(preferences.getString(App.ID,"")),
                preferences.getString(App.USER_EMAIL,""),"",""));
        displayableItems.add(new HomeSetting());
        for(Home home : homes){
            displayableItems.add(home);
            for(Device device : home.getDevices()){
                displayableItems.add(device);
            }
            displayableItems.add(new AddDevice());
        }
        displayableItems.add(new AddHouse());
        displayableItems.add(new SignOut());
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        displayableItems = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvProfile.setLayoutManager(layoutManager);
        adapter = new ProfileAdapter(displayableItems, getContext(), service, this);
        rvProfile.setAdapter(adapter);
    }

    @Override
    public void checkAdminSuccess(Response<Boolean> response) {
        boolean isAdmin = response.body();
        if (isAdmin){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("ADMIN YEAY !!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getResources().getString(R.string.not_admin))
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }

    }

    /*@OnClick(R.id.tv_logout)
    void logout(){
        SharedPreferences preferences = getContext().getSharedPreferences(App.USER_PREFERENCE,MODE_PRIVATE);

        Map<String,String> params = new HashMap<>();
        params.put(User.FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());

        presenter.updateFcmToken(preferences.getString(App.ID,""),params);

        Intent intent = new Intent(getContext(), LoginActivity.class);
        preferences.edit().clear().apply();
        startActivity(intent);
    }*/

}
