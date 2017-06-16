package id.ac.ugm.smartcity.smarthome.adapter.profile_delegate;

import android.content.Context;
import android.graphics.Typeface;
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
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.ProfilePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.ProfileView;

/**
 * Created by dito on 09/02/17.
 */

public class HouseDelegate extends AdapterDelegate<List<DisplayableItem>> {
    private Context context;
    private ProfilePresenter presenter;

    public HouseDelegate(Context context, Service service, ProfileView view){
        this.context = context;
        presenter = new ProfilePresenter(service, view, context);
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof Home;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HouseViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.home, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final HouseViewHolder houseViewHolder=
                (HouseViewHolder) holder;

        if (items != null && items.size() > 0) {
            final Home home = (Home) items.get(position);
            houseViewHolder.bindItem(home);
        }
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ic_right)
        TextView icRight;
        @BindView(R.id.root)
        View root;

        public HouseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(icRight,iconFont);
        }

        public void bindItem(final Home home) {
            tvName.setText(home.getName());
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.checkAdmin(String.valueOf(home.getId()));
                }
            });
        }
    }
}
