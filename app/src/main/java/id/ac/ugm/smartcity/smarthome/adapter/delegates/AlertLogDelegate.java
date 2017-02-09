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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.AlertDay;
import id.ac.ugm.smartcity.smarthome.R;

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
            alertViewHolder.bindItem(alert);
        }
    }

    public class AlertViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_alert)
        TextView tvAlert;
        @BindView(R.id.icon_alert)
        ImageView ivAlert;

        public AlertViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Alert alert) {
            if(alert.getSensorName() != null ){
                switch (alert.getSensorName()){
                    case "energy":
                        ivAlert.setImageResource(R.drawable.icon_energy);
                        break;
                    case "suhu":
                        ivAlert.setImageResource(R.drawable.icon_temp);
                        break;
                    case "kelembaban":
                        ivAlert.setImageResource(R.drawable.icon_humidity);
                        break;
                }
            }
            tvAlert.setText(alert.getWarning());
        }
    }
}