package com.grampuses.androiddemo.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.grampuses.androiddemo.R;

public class BaseActivityTest extends BaseActivity implements View.OnClickListener {
    private static final String TAG = BaseActivityTest.class.getSimpleName();

    private enum DynamicButton implements IDynamicCtrl {
        btn_1(R.string.base_activity_test_btn)
        , btn_2(R.string.base_activity_test_btn)
        , btn_3(R.string.base_activity_test_btn)
        , btn_4(R.string.base_activity_test_btn)
        , btn_5(R.string.base_activity_test_btn)
        , btn_6(R.string.base_activity_test_btn)
        , btn_7(R.string.base_activity_test_btn)
        , btn_8(R.string.base_activity_test_btn)
        , btn_9(R.string.base_activity_test_btn)
        ;

        DynamicButton(int nameResId) {
            _nameResId = nameResId;
            _ctrlId = getUniqueId();
        }

        /** attributes */
        private int _nameResId;
        private int _ctrlId;

        @Override
        public int getCtrlId() {
            return _ctrlId;
        }

        @Override
        public int getNameResId() {
            return _nameResId;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addDynamicButtons(this, DynamicButton.values());
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        for (IDynamicCtrl item : DynamicButton.values()) {
            if (id == item.getCtrlId()) {
                int nameResId = item.getNameResId();
                String text = "id = " + id + ", name = " + this.getResources().getString(nameResId);
                Log.d(TAG, text);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
