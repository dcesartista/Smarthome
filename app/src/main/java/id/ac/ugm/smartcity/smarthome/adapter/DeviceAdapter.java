package id.ac.ugm.smartcity.smarthome.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.AddDeviceActivity;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.GetDeviceView;
import id.ac.ugm.smartcity.smarthome.View.DeleteDeviceDialog;
import id.ac.ugm.smartcity.smarthome.View.DeviceDetailActivity;

/**
 * Created by dito on 09/02/17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private Context context;
    private List<Device> deviceList;

    public DeviceAdapter(List<Device> deviceList, Context context) {
        Log.e("HAHA","HEHE");
        this.deviceList = deviceList;
        this.context = context;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("HUMHUM3","CALLED!!");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device, parent, false);

        DeviceViewHolder holder = new DeviceViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        final Device device = deviceList.get(position);
        holder.tvName.setText(device.getName());
        Log.e("HUMHUM1","CALLED!!");
        if(null == device.getRelay()){
            holder.tvDeviceOff.setText("-");
            holder.tvDeviceOn.setText("-");
        } else {
            String on = "";
            String off = "";
            int[] relayData = new int[]{device.getRelay().getRelay1(), device.getRelay().getRelay2(),
                    device.getRelay().getRelay3(),device.getRelay().getRelay4(),device.getRelay().getRelay5(),
                    device.getRelay().getRelay6(), device.getRelay().getRelay7(), device.getRelay().getRelay8()};
            String[] relayNames = new String[]{device.getRelay().getRelay1name(), device.getRelay().getRelay2name(),
                    device.getRelay().getRelay3name(), device.getRelay().getRelay4name(), device.getRelay().getRelay5name(),
                    device.getRelay().getRelay6name(), device.getRelay().getRelay7name(), device.getRelay().getRelay8name()};
            for (int i=0;i<relayData.length; i++){
                if (relayData[i] == 1){
                    on = on+relayNames[i];
                    if(i<relayData.length-1){
                        on = on+", ";
                    }
                } else {
                    off = off+relayNames[i];
                    if(i<relayData.length-1){
                        off = off+", ";
                    }
                }
            }
            if (off.length() > 0){
                holder.tvDeviceOff.setText(off);
            } else {
                holder.tvDeviceOff.setText("-");
            }
            if (on.length() > 0){
                holder.tvDeviceOn.setText(on);
            } else {
                holder.tvDeviceOn.setText("-");
            }
            Log.e("HUMHUM2","CALLED!!");
        }


        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeviceDetailActivity.class);
                Log.e("POPOPOPO",device.getId()+"");
                intent.putExtra(Device.ID, device);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.root)
        View root;
        @BindView(R.id.ic_right)
        TextView icRight;
        @BindView(R.id.tv_device_on)
        TextView tvDeviceOn;
        @BindView(R.id.tv_device_off)
        TextView tvDeviceOff;

        public DeviceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(icRight, iconFont);
        }
    }
}
