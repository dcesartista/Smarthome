package id.ac.ugm.smartcity.smarthome.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import id.ac.ugm.smartcity.smarthome.R;


/**
 * Created by dito on 30/05/17.
 */

public class CustomSpinner extends Spinner implements OnItemClickListener {
    private AlertDialog mDialog = null;
    private OnClickListener mButtonClickListener = null;
    private final Rect mTempRect = new Rect();
    private Context context;
    private AttributeSet attrs;

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
    }

    public CustomSpinner(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public boolean performClick() {
        Context context = getContext();

        // get the set adapter
        final DropDownAdapter adapter = new DropDownAdapter(getAdapter());

        // inflate our layout
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View v = inflater.inflate(R.layout.custom_spinner, null);

        // set up list view
        final ListView lv = (ListView) v.findViewById(R.id.list);
        lv.setAdapter(adapter);
        lv.setSelection(getSelectedItemPosition());
        lv.setOnItemClickListener(this);

        // set up button
        final Button btn = (Button) v.findViewById(R.id.addButton);
        btn.setOnClickListener(mButtonClickListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(getPopupContext());
//        mDialog = builder.create();

        if (getPrompt() != null) {
            builder.setTitle(getPrompt());
        }

        // create and show dialog
        mDialog = builder.setView(v).create();
        mDialog.show();

        return true;
    }



    @Override
    public void onItemClick(AdapterView<?> view,
                            View itemView, int position, long id) {
        setSelection(position);
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    private class DropdownPopup extends ListPopupWindow {
        private CharSequence mHintText;
        private ListAdapter mAdapter;

        public DropdownPopup(
                Context context, AttributeSet attrs) {
            super(context, attrs);

            setAnchorView(CustomSpinner.this);
            setModal(true);
            setPromptPosition(POSITION_PROMPT_ABOVE);
        }

        @Override
        public void setAdapter(ListAdapter adapter) {
            super.setAdapter(adapter);
            mAdapter = adapter;
        }

        public CharSequence getHintText() {
            return mHintText;
        }

        public void setPromptText(CharSequence hintText) {
            // Hint text is ignored for dropdowns, but maintain it here.
            mHintText = hintText;
        }

        void computeContentWidth() {
            final Drawable background = getBackground();
            int hOffset = 0;
            if (background != null) {
                background.getPadding(mTempRect);
                hOffset = mTempRect.right;
            } else {
                mTempRect.left = mTempRect.right = 0;
            }

            final int spinnerPaddingLeft = CustomSpinner.this.getPaddingLeft();
            final int spinnerPaddingRight = CustomSpinner.this.getPaddingRight();
            final int spinnerWidth = CustomSpinner.this.getWidth();


            int contentWidth =  CustomSpinner.this.getWidth();
            final int contentWidthLimit = getContext().getResources()
                    .getDisplayMetrics().widthPixels - mTempRect.left - mTempRect.right;
            if (contentWidth > contentWidthLimit) {
                contentWidth = contentWidthLimit;
            }
            setContentWidth(Math.max(
                    contentWidth, spinnerWidth - spinnerPaddingLeft - spinnerPaddingRight));



            hOffset += spinnerPaddingLeft;

            setHorizontalOffset(hOffset);
        }

        public void show(int textDirection, int textAlignment) {
            final boolean wasShowing = isShowing();

            computeContentWidth();

            setInputMethodMode(ListPopupWindow.INPUT_METHOD_NOT_NEEDED);
            super.show();
            final ListView listView = getListView();
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView.setTextDirection(textDirection);
            listView.setTextAlignment(textAlignment);
            setSelection(CustomSpinner.this.getSelectedItemPosition());

            if (wasShowing) {
                // Skip setting up the layout/dismiss listener below. If we were previously
                // showing it will still stick around.
                return;
            }

            // Make sure we hide if our anchor goes away.
            // TODO: This might be appropriate to push all the way down to PopupWindow,
            // but it may have other side effects to investigate first. (Text editing handles, etc.)

        }
    }



    /**
     * <p>Wrapper class for an Adapter. Transforms the embedded Adapter instance
     * into a ListAdapter.</p>
     */

    private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter;

        /**
         * <p>Creates a new ListAddapter wrapper for the specified adapter.</p>
         *
         * @param adapter the Adapter to transform into a ListAdapter
         */
        public DropDownAdapter(SpinnerAdapter adapter) {
            this.mAdapter = adapter;
        }

        public int getCount() {
            return mAdapter == null ? 0 : mAdapter.getCount();
        }

        public Object getItem(int position) {
            return mAdapter == null ? null : mAdapter.getItem(position);
        }

        public long getItemId(int position) {
            return mAdapter == null ? -1 : mAdapter.getItemId(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return mAdapter == null ? null :
                    mAdapter.getDropDownView(position, convertView, parent);
        }

        public boolean hasStableIds() {
            return mAdapter != null && mAdapter.hasStableIds();
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            if (mAdapter != null) {
                mAdapter.registerDataSetObserver(observer);
            }
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (mAdapter != null) {
                mAdapter.unregisterDataSetObserver(observer);
            }
        }

        /**
         * <p>Always returns false.</p>
         *
         * @return false
         */
        public boolean areAllItemsEnabled() {
            return true;
        }

        /**
         * <p>Always returns false.</p>
         *
         * @return false
         */
        public boolean isEnabled(int position) {
            return true;
        }

        public int getItemViewType(int position) {
            return 0;
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }
    }
}