package id.ac.ugm.smartcity.smarthome.adapter.delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.Alert;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.AlertDay;
import id.ac.ugm.smartcity.smarthome.R;

/**
 * Created by dito on 09/02/17.
 */

public class AlertDayDelegate extends AdapterDelegate<List<DisplayableItem>> {
    private Context context;

    public AlertDayDelegate(Context context){
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof AlertDay;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AlertDayViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.alert_day, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final AlertDayViewHolder alertDayViewHolder=
                (AlertDayViewHolder) holder;

        if (items != null && items.size() > 0) {
            final AlertDay alertDay = (AlertDay) items.get(position);
            alertDayViewHolder.bindItem(alertDay);
        }
    }

    public class AlertDayViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.alert_day_text)
        TextView tvAlertDay;

        public AlertDayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(AlertDay alertDay) {
            tvAlertDay.setText(alertDay.getDay());
        }
    }
}
