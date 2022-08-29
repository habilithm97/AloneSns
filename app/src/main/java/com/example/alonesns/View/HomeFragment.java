package com.example.alonesns.View;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alonesns.HomeAdapter;
import com.example.alonesns.Model.MainModel;
import com.example.alonesns.MyDatabase;
import com.example.alonesns.Presenter.HomeContract;
import com.example.alonesns.Presenter.HomePresenter;
import com.example.alonesns.Presenter.MainContract;
import com.example.alonesns.Presenter.MainPresenter;
import com.example.alonesns.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {
    private HomeAdapter adapter;
    HomePresenter homePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        adapter = new HomeAdapter();
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homePresenter = new HomePresenter(this);
    }

    @Override
    public void loadData(List<MainModel> items) {
        adapter.setItem(items);
    }

    @Override
    public void onResume() {
        super.onResume();

        homePresenter.setData();
    }
}