package com.exaccu.smartbulter.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exaccu.smartbulter.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 * 管家服务Fragment
 */
public class BulterFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bulter, container, false);
    }


}