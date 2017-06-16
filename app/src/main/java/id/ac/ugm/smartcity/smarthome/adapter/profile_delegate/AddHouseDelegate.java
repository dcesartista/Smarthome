package id.ac.ugm.smartcity.smarthome.adapter.profile_delegate;

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
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Profile.AddDevice;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Profile.AddHouse;
import id.ac.ugm.smartcity.smarthome.R;

/**
 * Created by dito on 09/02/17.
 */

public class AddHouseDelegate extends AdapterDelegate<List<DisplayableItem>> {
    private Context context;

    public AddHouseDelegate(Context context){
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof AddHouse;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AddHouseViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.add_house, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final AddHouseViewHolder addHouseViewHolder=
                (AddHouseViewHolder) holder;

    }

    public class AddHouseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root)
        TextView root;

        public AddHouseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.root)
        void addHouse(){

        }
    }
}
