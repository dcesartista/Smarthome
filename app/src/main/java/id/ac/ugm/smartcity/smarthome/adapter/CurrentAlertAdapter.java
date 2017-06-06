package id.ac.ugm.smartcity.smarthome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Alert;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.DateFormatter;
import id.ac.ugm.smartcity.smarthome.View.DeviceDetailActivity;

/**
 * Created by dito on 09/02/17.
 */

public class CurrentAlertAdapter extends RecyclerView.Adapter<CurrentAlertAdapter.AlertViewHolder> {
    private Context context;
    private List<Alert> alertList;

    public CurrentAlertAdapter(List<Alert> alertList, Context context) {
        this.alertList = alertList;
        this.context = context;
    }

    @Override
    public AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device, parent, false);

        return new AlertViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlertViewHolder holder, int position) {
        if (alertList != null && alertList.size() > 0) {
            final Alert alert= alertList.get(position);
            try {
                holder.bindItem(alert);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return alertList.size();
    }

    public class AlertViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_alert_title)
        TextView tvTitle;
        @BindView(R.id.tv_alert_desc)
        TextView tvDesc;
        @BindView(R.id.icon_alert)
        ImageView ivAlert;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public AlertViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Alert alert) throws ParseException {
            if(alert.getAlertType() != null ){
                switch (alert.getAlertType()){
                    case "Energy":
                        ivAlert.setImageResource(R.drawable.ic_energy_yellow);
                        break;
                    case "Temperature":
                        ivAlert.setImageResource(R.drawable.icon_temp);
                        break;
                    case "Humidity":
                        ivAlert.setImageResource(R.drawable.icon_humidity);
                        break;
                    case "Cost":
                        break;
                    case "Light":
                        break;
                    case "Carbondioxide":
                        break;
                    default:
                        ivAlert.setImageResource(R.drawable.ic_energy_yellow);
                        break;
                }
            }
            tvTitle.setText(alert.getAlertType());
            tvDesc.setText(alert.getStatus());
            tvTime.setText(DateFormatter.convertDateToStringTime(alert.getCreatedAt()));
        }
    }
}
