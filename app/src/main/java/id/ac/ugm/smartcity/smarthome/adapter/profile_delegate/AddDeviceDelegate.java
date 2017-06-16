package id.ac.ugm.smartcity.smarthome.adapter.profile_delegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Profile.AddDevice;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.ProfilePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.ProfileView;

/**
 * Created by dito on 09/02/17.
 */

public class AddDeviceDelegate extends AdapterDelegate<List<DisplayableItem>> {
    private Context context;
    private ProfilePresenter presenter;

    public AddDeviceDelegate(Context context, Service service, ProfileView view){
        this.context = context;
        presenter = new ProfilePresenter(service, view, context);
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof AddDevice;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AddDeviceViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.add_device, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final AddDeviceViewHolder addDeviceViewHolder=
                (AddDeviceViewHolder) holder;
        addDeviceViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public class AddDeviceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root)
        View root;

        public AddDeviceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
