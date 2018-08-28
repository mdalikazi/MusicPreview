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
import com.alikazi.codetestaim.utils.LeftTopSnapHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    private boolean mIsTabletMode;
    private ViewModel mMainViewModel;
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
        mRecyclerView = view.findViewById(R.id.main_recycler_view);
        mEmptyMessageTextView = view.findViewById(R.id.main_empty_list_text_view);
        if (view.findViewById(R.id.detail_fragment_container_w700dp) == null) {
            mIsTabletMode = true;
            mDetailFragmentContainer = view.findViewById(R.id.detail_fragment_container);
        } else {
            mIsTabletMode = false;
            mDetailFragmentContainer = view.findViewById(R.id.detail_fragment_container_w700dp);
        }
        DLog.d(AppConstants.AIM_LOG_TAG, "mIsTabletMode: " + mIsTabletMode);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

//        mMainViewModel = ViewModelProviders.of(activityReference, Injector.provideViewModelFactory(activity?.applicationContext!!)).get(MainViewModel.class)
        setupAdapter();
        setupRecyclerView();
//        mMainViewModel.getPhotosFromDb()
    }

    private void setupAdapter() {
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        new LeftTopSnapHelper().attachToRecyclerView(mRecyclerView);
        LayoutAnimationController layoutAnimation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.vertical_layout_animation);
        mRecyclerView.setLayoutAnimation(layoutAnimation);
        mRecyclerView.scheduleLayoutAnimation();
    }
}
