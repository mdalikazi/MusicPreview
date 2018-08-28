package com.alikazi.codetestaim.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alikazi.codetestaim.R;
import com.alikazi.codetestaim.utils.AppConstants;
import com.alikazi.codetestaim.utils.DLog;
import com.alikazi.codetestaim.utils.Injector;
import com.alikazi.codetestaim.utils.LeftTopSnapHelper;
import com.alikazi.codetestaim.viewmodel.MainViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    private boolean mIsTabletMode;
    private MainViewModel mMainViewModel;
    private RecyclerView mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mEmptyMessageTextView;
    private FrameLayout mDetailFragmentContainer;

    private static MainFragment mInstance;

    public static MainFragment getInstance() {
        if (mInstance == null) {
            mInstance = new MainFragment();
        }

        return mInstance;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DLog.i(LOG_TAG, "onViewCreated");
        mRecyclerView = view.findViewById(R.id.main_recycler_view);
        mEmptyMessageTextView = view.findViewById(R.id.main_empty_list_message);
        if (view.findViewById(R.id.detail_fragment_container_w700dp) == null) {
            mIsTabletMode = false;
            mDetailFragmentContainer = view.findViewById(R.id.detail_fragment_container);
        } else {
            mIsTabletMode = true;
            mDetailFragmentContainer = view.findViewById(R.id.detail_fragment_container_w700dp);
        }
        DLog.d(LOG_TAG, "mIsTabletMode: " + mIsTabletMode);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DLog.i(LOG_TAG, "onActivityCreated");
        setHasOptionsMenu(true);
    }

    private void setupAdapter() {
        DLog.i(LOG_TAG, "setupAdapter");
//        mAdapter =
//        mAdapter = new PhotosAdapter(activity?.applicationContext!!, this);
//        mMainViewModel.feed.observe(this, Observer<PagedList<Photo>> {
//                showEmptyMessage(it?.size == 0);
//                mAdapter.submitList(it);
//        });
//        mMainViewModel.networkError.observe(this, Observer<String> {
//                Toast.makeText(getActivity(), "Wooops" + it, Toast.LENGTH_LONG).show();
//        });
//        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupRecyclerView() {
        DLog.i(LOG_TAG, "setupRecyclerView");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        new LeftTopSnapHelper().attachToRecyclerView(mRecyclerView);
        LayoutAnimationController layoutAnimation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.vertical_layout_animation);
        mRecyclerView.setLayoutAnimation(layoutAnimation);
        mRecyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        DLog.i(LOG_TAG, "onResume");
        mMainViewModel = ViewModelProviders.of(getActivity(), Injector.provideViewModelFactory(getActivity()))
                .get(MainViewModel.class);
        setupAdapter();
        setupRecyclerView();
        mMainViewModel.loadFeed();
    }
}
