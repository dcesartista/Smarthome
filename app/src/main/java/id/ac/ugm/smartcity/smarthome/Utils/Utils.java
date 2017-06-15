package id.ac.ugm.smartcity.smarthome.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.ac.ugm.smartcity.smarthome.R;

/**
 * Created by dito on 26/05/17.
 */

public class Utils {
    public static void setSelected(TextView tv, Context context){
        tv.setTextColor(context.getResources().getColor(R.color.white));
        tv.setBackgroundDrawable(context.getResources().getDrawable(R.color.colorAccent));
    }

    public static void setUnselected(TextView tv, Context context){
        tv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        tv.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
    }

    public static void setSelected (View v, ImageView iv, Context context, int id){
        iv.setImageResource(id);
        v.setBackgroundDrawable(context.getResources().getDrawable(R.color.colorAccent));
    }

    public static void setUnselected(View v, ImageView iv, Context context, int id){
        iv.setImageResource(id);
        v.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
