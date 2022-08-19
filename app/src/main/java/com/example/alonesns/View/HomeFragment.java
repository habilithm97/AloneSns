package com.example.alonesns.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alonesns.HomeAdapter;
import com.example.alonesns.Model.MainModel;
import com.example.alonesns.Presenter.MainContract;
import com.example.alonesns.Presenter.MainPresenter;
import com.example.alonesns.R;

import java.util.List;

public class HomeFragment extends Fragment implements MainContract.View {
    private HomeAdapter adapter;

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

        MainPresenter mainPresenter = new MainPresenter(this);
        mainPresenter.setData();
    }

    @Override
    public void loadData(List<MainModel> items) {
        adapter.setItem(items);
    }

    @Override
    public void newPostIntent() {}

    @Override
    public void onTabSelected(int position) {}
}