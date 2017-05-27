package id.ac.ugm.smartcity.smarthome.View;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.R;

public class NewHomeActivity extends AppCompatActivity {

    @BindView(R.id.ic_home)
    TextView icHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);
        ButterKnife.bind(this);

        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icHome, iconFont);
    }
}
