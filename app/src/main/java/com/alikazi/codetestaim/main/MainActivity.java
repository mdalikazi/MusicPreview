package com.alikazi.codetestaim.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ViewGroup;

import com.alikazi.codetestaim.R;
import com.alikazi.codetestaim.utils.AimAnimationUtils;
import com.alikazi.codetestaim.utils.AppConstants;
import com.alikazi.codetestaim.utils.DLog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements AimAnimationUtils.ToolbarAnimationListener {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        checkStoragePermission();
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
        mToolbar.setTitle(getString(R.string.toolbar_title_radio));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MainFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitNow();
    }

    private void checkStoragePermission() {
        DLog.i(AppConstants.AIM_LOG_TAG, "checkStoragePermission");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            DLog.d(AppConstants.AIM_LOG_TAG, "STORAGE PERMISSION_GRANTED");
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showStoragePermissionExplanation();
            } else {
                requestStoragePermission();
            }
        }
    }

    private void showStoragePermissionExplanation() {
        DLog.i(AppConstants.AIM_LOG_TAG, "showStoragePermissionExplanation");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.permission_explanation_storage))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestStoragePermission();
                    }
                })
                .show();
    }

    private void requestStoragePermission() {
        DLog.i(AppConstants.AIM_LOG_TAG, "requestStoragePermission");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                AppConstants.REQUEST_CODE_PERMISSION_ACCESS_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        DLog.i(AppConstants.AIM_LOG_TAG, "onRequestPermissionsResult");
        if (requestCode == AppConstants.REQUEST_CODE_PERMISSION_ACCESS_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                DLog.d(AppConstants.AIM_LOG_TAG, "STORAGE PERMISSION_GRANTED");
            }
        }
    }
}
