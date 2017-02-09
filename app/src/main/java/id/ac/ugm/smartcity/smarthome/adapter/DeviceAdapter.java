package id.ac.ugm.smartcity.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.R;

/**
 * Created by dito on 09/02/17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private Context context;
    private List<Device> deviceList;

    public DeviceAdapter(List<Device> deviceList, Context context) {
        this.deviceList = deviceList;
        this.context = context;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device, parent, false);

        return new DeviceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.txtDeviceName.setText(device.getName());
        holder.txtDeviceId.setText(device.getProductID());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.device_name)
        TextView txtDeviceName;
        @BindView(R.id.device_id)
        TextView txtDeviceId;

        public DeviceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
