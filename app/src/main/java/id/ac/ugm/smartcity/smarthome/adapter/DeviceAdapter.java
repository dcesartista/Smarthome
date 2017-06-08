package id.ac.ugm.smartcity.smarthome.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.GetDevicePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.AddDeviceActivity;
import id.ac.ugm.smartcity.smarthome.View.DeleteDeviceDialog;
import id.ac.ugm.smartcity.smarthome.View.DeviceDetailActivity;
import id.ac.ugm.smartcity.smarthome.View.RelayActivity;
import id.ac.ugm.smartcity.smarthome.View.SettingDialog;

/**
 * Created by dito on 09/02/17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private Context context;
    private List<Device> deviceList;
    private Service service;
    private GetDevicePresenter presenter;
    private String homeId;
    private Activity activity;

    public DeviceAdapter(List<Device> deviceList, Context context, Service service
            , GetDevicePresenter presenter, String homeId, Activity activity) {
        this.deviceList = deviceList;
        this.context = context;
        this.service = service;
        this.presenter = presenter;
        this.homeId = homeId;
        this.activity = activity;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device, parent, false);
        DeviceViewHolder holder = new DeviceViewHolder(itemView);
        holder.toolbar.inflateMenu(R.menu.menu_device);

        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        final Device device = deviceList.get(position);
        holder.txtDeviceName.setText(device.getName());

        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.delete:
                        DeleteDeviceDialog dialog = new DeleteDeviceDialog(activity, homeId, device.getId().toString(),service,presenter);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        break;
                    case R.id.edit:
                        Intent intent = new Intent(context,AddDeviceActivity.class);
                        intent.putExtra(Device.ID,device);
                        intent.putExtra(AddDeviceActivity.EDIT,true);
                        context.startActivity(intent);
                }
                return false;
            }
        });
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeviceDetailActivity.class);
                Log.e("POPOPOPO",device.getId()+"");
                intent.putExtra(Device.ID, device);
                context.startActivity(intent);
            }
        });

        if(null != device.getImg().getUrl()) {
            Log.e("URL!!", device.getImg().getUrl());
            Picasso.with(context)
                    .load(App.BASE_URL+device.getImg().getUrl())
                    .into(holder.ivDevice);
        }
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView txtDeviceName;
        @BindView(R.id.iv_device)
        ImageView ivDevice;
        @BindView(R.id.root)
        View root;
        @BindView(R.id.toolbar)
        Toolbar toolbar;

        public DeviceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
