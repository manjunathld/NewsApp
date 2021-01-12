package com.dxminds.newsapp.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dxminds.newsapp.model.Constants;
import com.dxminds.newsapp.model.NewsAdapter;
import com.dxminds.newsapp.viewmodel.NewsViewModel;
import com.dxminds.newsapp.R;

public class HomeFragment extends Fragment {

    private NewsViewModel mNewsViewModel;
    private NewsAdapter mNewsAdapter;
    private RecyclerView mNewsSourceRecyclerView;
    private ConstraintLayout mHomeConstraintLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNewsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        if (isNetworkConnected()) {
            mNewsViewModel.getNewsDataAPI(Constants.QUE, Constants.DATE, Constants.PUBLISHED_AT, Constants.API_KEY);
        } else {
            Toast.makeText(getContext(), Constants.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
        }
        mNewsAdapter = mNewsViewModel.getNewsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHomeConstraintLayout = view.findViewById(R.id.cl_home_fragment);
        mNewsSourceRecyclerView = view.findViewById(R.id.rv_source_recycler_view_home_fragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mNewsSourceRecyclerView.setLayoutManager(linearLayoutManager);
        mNewsSourceRecyclerView.setAdapter(mNewsAdapter);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}