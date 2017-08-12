package com.grampuses.androiddemo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.grampuses.androiddemo.R;

/**
 * Created by yangzh6 on 8/11/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private LinearLayout mContainer;

    /** Can be used in some internal classes or functions. */
    protected Activity mActivity;

    /**
     * increase the gui widget's id automatically.
     */
    private static int uniqueId = 0;
    protected static int getUniqueId() {
        return uniqueId++;
    }

    /** Used as a parameter of "addDynamicButtons()". */
    interface IDynamicCtrl {
        int getCtrlId();
        int getNameResId();
    }

    /** Generate & Add the buttons, defined by items array, into current layout. */
    public void addDynamicButtons(View.OnClickListener listener, IDynamicCtrl[] items) {
        Button btn;
        LinearLayout.LayoutParams lp;
        final int marginPixels = getResources().getDimensionPixelSize(
                R.dimen.activity_vertical_margin);

        boolean isFirst = true;
        for (IDynamicCtrl item : items) {
            Log.d(TAG, "isFirst = " + isFirst
                    + ", item.getCtrlId = " + item.getCtrlId()
                    + ", item.getNameResId = " + item.getNameResId());
            btn = new Button(this);
            btn.setId(item.getCtrlId());

            // Notice: Should not assign "lp" out of for statement.
            lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (isFirst) {
                isFirst = false;
                lp.setMargins(0, 0, 0, 0);
            } else {
                lp.setMargins(0, marginPixels, 0, 0);
            }
            btn.setLayoutParams(lp);
            btn.setText(item.getNameResId());
            btn.setOnClickListener(listener);
            mContainer.addView(btn);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Can used by the children of BaseActivity.
        mActivity = this;

        // Always display the BACK button at the left of the ActionBar.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // initContentView.
        initContentView();
    }

    /** Scroll view with a vertical LinearLayout inside. */
    private void initContentView() {
        ViewGroup.LayoutParams layoutParams;
        // LinearLayout - mContainer
        mContainer = new LinearLayout(this);
        mContainer.setId(getUniqueId());
        mContainer.setOrientation(LinearLayout.VERTICAL);
        final int paddingHorizontal = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        final int paddingVertical = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
        mContainer.setPadding(paddingHorizontal, paddingVertical,
                paddingHorizontal, paddingVertical);

        // ScrollView - root view
        ScrollView scrollView = new ScrollView(this);
        layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView.addView(mContainer, layoutParams);

        // setContentView
        ScrollView.LayoutParams scrollViewLayoutParams = new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT);
        setContentView(scrollView, scrollViewLayoutParams);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
