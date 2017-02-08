package id.ac.ugm.smartcity.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.adapter.delegates.AlertDayDelegate;

/**
 * Created by dito on 09/02/17.
 */

public class AlertAdapter extends RecyclerView.Adapter {
    private List<DisplayableItem> items;
    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;

    public AlertAdapter(List<DisplayableItem> items, Context context) {
        this.items = items;
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new AlertDayDelegate(context));
    }

    @Override public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override public int getItemCount() {
        return items.size();
    }
}
