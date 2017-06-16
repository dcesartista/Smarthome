package id.ac.ugm.smartcity.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.adapter.alert_delegates.AlertDayDelegate;
import id.ac.ugm.smartcity.smarthome.adapter.alert_delegates.AlertLogDelegate;
import id.ac.ugm.smartcity.smarthome.adapter.profile_delegate.AddDeviceDelegate;
import id.ac.ugm.smartcity.smarthome.adapter.profile_delegate.AddHouseDelegate;
import id.ac.ugm.smartcity.smarthome.adapter.profile_delegate.DeviceDelegate;
import id.ac.ugm.smartcity.smarthome.adapter.profile_delegate.HomeSettingDelegate;
import id.ac.ugm.smartcity.smarthome.adapter.profile_delegate.HouseDelegate;
import id.ac.ugm.smartcity.smarthome.adapter.profile_delegate.ProfileDelegate;
import id.ac.ugm.smartcity.smarthome.adapter.profile_delegate.SignOutDelegate;

/**
 * Created by dito on 09/02/17.
 */

public class ProfileAdapter extends RecyclerView.Adapter {
    private List<DisplayableItem> items;
    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;

    public ProfileAdapter(List<DisplayableItem> items, Context context) {
        this.items = items;
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new ProfileDelegate(context));
        delegatesManager.addDelegate(new HomeSettingDelegate(context));
        delegatesManager.addDelegate(new HouseDelegate(context));
        delegatesManager.addDelegate(new DeviceDelegate(context));
        delegatesManager.addDelegate(new AddDeviceDelegate(context));
        delegatesManager.addDelegate(new AddHouseDelegate(context));
        delegatesManager.addDelegate(new SignOutDelegate(context));
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
