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
import com.example.alonesns.Presenter.HomeContract;
import com.example.alonesns.Presenter.HomePresenter;
import com.example.alonesns.R;

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
    public void onResume() {
        super.onResume();
        // Model에 접근해야하기 때문에 presenter에 정의되어 있는 loadDatabase()를 호출함
        homePresenter.loadDatabase();
    }

    @Override
    public void setData(List<MainModel> items) { // View에 접근해야하기 때문에 이 클래스에서 어댑터를 이용해 화면에 적용시킴
        adapter.setItem(items);
    }
}