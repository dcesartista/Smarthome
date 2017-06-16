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
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Profile.SignOut;
import id.ac.ugm.smartcity.smarthome.R;

/**
 * Created by dito on 09/02/17.
 */

public class SignOutDelegate extends AdapterDelegate<List<DisplayableItem>> {
    private Context context;

    public SignOutDelegate(Context context){
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SignOut;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AddDeviceViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.sign_out, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final AddDeviceViewHolder addDeviceViewHolder=
                (AddDeviceViewHolder) holder;
        addDeviceViewHolder.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public class AddDeviceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sign_out)
        View signOut;

        public AddDeviceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
