package com.arhat.chattest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arhat.arhat.chattest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchSubscriptionFragment extends Fragment {


    public SearchSubscriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("carl","进入onCreateView");
        return inflater.inflate(R.layout.fragment_search_subscription, container, false);
    }

}
