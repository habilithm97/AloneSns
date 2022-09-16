package com.example.alonesns.Home;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alonesns.ItemAdapter;
import com.example.alonesns.Model.MainModel;
import com.example.alonesns.R;
import com.example.alonesns.Room.RoomDB;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {
    HomePresenter homePresenter;
    RecyclerView recyclerView;

    public static List<MainModel> items;
    public static RoomDB roomDB;
    public static ItemAdapter adapter;
    Activity activityContext = getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        init(rootView);
        return rootView;
    }

    private void init(ViewGroup rootView) {
        homePresenter = new HomePresenter(this);
        items = homePresenter.items; // presenter의 ArrayList를 가져옴

        roomDB = RoomDB.getInstance(getContext());
        items = roomDB.mainDao().getAll(); // RoomDB에 있는 데이터를 모두 가져와서 리스트로 표시
        adapter = new ItemAdapter(activityContext, items);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        // 아이템 순서를 역순으로 표시
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        recyclerView.smoothScrollToPosition(adapter.getItemCount()); // 홈 프래그먼트 실행 시 마지막 아이템 위치로 포커스를 이동시킴(맨 위)
    }
}