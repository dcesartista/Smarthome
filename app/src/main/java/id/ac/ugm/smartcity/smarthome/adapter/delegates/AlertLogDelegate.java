package id.ac.ugm.smartcity.smarthome.adapter.delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.Alert;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.AlertDay;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.DateFormatter;

/**
 * Created by dito on 09/02/17.
 */

public class AlertLogDelegate extends AdapterDelegate<List<DisplayableItem>> {
    private Context context;

    public AlertLogDelegate(Context context){
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof Alert;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AlertViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.alert_log, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final AlertViewHolder alertViewHolder=
                (AlertViewHolder) holder;

        if (items != null && items.size() > 0) {
            final Alert alert= (Alert) items.get(position);
            try {
                alertViewHolder.bindItem(alert);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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