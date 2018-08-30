package com.alikazi.codetestaim.main;

import android.os.Bundle;
import android.view.ViewGroup;

import com.alikazi.codetestaim.R;
import com.alikazi.codetestaim.utils.AimAnimationUtils;
import com.alikazi.codetestaim.utils.AppConstants;
import com.alikazi.codetestaim.utils.DLog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements AimAnimationUtils.ToolbarAnimationListener {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (savedInstanceState == null) {
            AimAnimationUtils.animateToolbar(this, mToolbar, this);
        } else {
            // User changed orientation, reset toolbar size to default one
            handleOrientationChange();
        }
    }

    private void handleOrientationChange() {
        DLog.i(LOG_TAG, "handleOrientationChange");
        ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
        layoutParams.height = (int) AimAnimationUtils.getDefaultActionBarHeightInPixels(this);
    }

    @Override
    public void onToolbarAnimationEnd() {
        DLog.i(LOG_TAG, "onToolbarAnimationEnd");
        mToolbar.setTitle(getString(R.string.toolbar_title_top_charts));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MainFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitNow();
    }
}
