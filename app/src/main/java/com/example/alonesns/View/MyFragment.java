package com.example.alonesns.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alonesns.HomeAdapter;
import com.example.alonesns.Model.MainModel;
import com.example.alonesns.Presenter.HomeContract;
import com.example.alonesns.Presenter.HomePresenter;
import com.example.alonesns.Presenter.MyContract;
import com.example.alonesns.Presenter.MyPresenter;
import com.example.alonesns.R;

import java.util.List;

public class MyFragment extends Fragment implements MyContract.View {
    private HomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        adapter = new HomeAdapter();
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        MyPresenter myPresenter = new MyPresenter(this);
        myPresenter.setData();
    }

    @Override
    public void loadData(List<MainModel> items) {
        adapter.setItem(items);
    }
}