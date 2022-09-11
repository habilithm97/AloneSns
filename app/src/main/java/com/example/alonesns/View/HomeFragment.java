package com.example.alonesns.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alonesns.ItemAdapter;
import com.example.alonesns.Model.MainModel;
import com.example.alonesns.Presenter.HomeContract;
import com.example.alonesns.Presenter.HomePresenter;
import com.example.alonesns.R;
import com.example.alonesns.RoomDB;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {
    public static ItemAdapter adapter;
    HomePresenter homePresenter;

    public static RoomDB roomDB;
    Activity context = getActivity();
    public static List<MainModel> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        homePresenter = new HomePresenter(this);
        items = homePresenter.items;

        roomDB = RoomDB.getInstance(getContext());
        items = roomDB.mainDao().getAll();
        adapter = new ItemAdapter(context, items);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}