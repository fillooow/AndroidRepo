package com.fillooow.yandextranslateproject;

/**
 * Created by Fillow on 28.03.2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class DealsFragment extends Fragment {
    public DealsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deals, container, false);
    }
}

