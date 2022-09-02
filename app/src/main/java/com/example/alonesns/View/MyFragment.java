package com.example.alonesns.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alonesns.Presenter.MyContract;
import com.example.alonesns.R;

public class MyFragment extends Fragment implements MyContract.View {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my, container, false);
        return rootView;
    }
}