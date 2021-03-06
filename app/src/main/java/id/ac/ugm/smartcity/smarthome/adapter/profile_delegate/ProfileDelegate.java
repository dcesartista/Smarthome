package id.ac.ugm.smartcity.smarthome.adapter.profile_delegate;

import android.content.Context;
import android.graphics.Typeface;
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
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.User;
import id.ac.ugm.smartcity.smarthome.R;

/**
 * Created by dito on 09/02/17.
 */

public class ProfileDelegate extends AdapterDelegate<List<DisplayableItem>> {
    private Context context;

    public ProfileDelegate(Context context){
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof User;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ProfileViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.profile, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final ProfileViewHolder profileViewHolder=
                (ProfileViewHolder) holder;

        if (items != null && items.size() > 0) {
            final User user = (User) items.get(position);
            profileViewHolder.bindItem(user);
        }
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_profile)
        ImageView ivProfile;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_email)
        TextView tvEmail;
        @BindView(R.id.ic_right1)
        TextView icRight1;
        @BindView(R.id.ic_right2)
        TextView icRight2;
        @BindView(R.id.ic_right3)
        TextView icRight3;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(icRight1,iconFont);
            FontManager.markAsIconContainer(icRight2,iconFont);
            FontManager.markAsIconContainer(icRight3,iconFont);
        }

        public void bindItem(User user) {
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
        }
    }
}
