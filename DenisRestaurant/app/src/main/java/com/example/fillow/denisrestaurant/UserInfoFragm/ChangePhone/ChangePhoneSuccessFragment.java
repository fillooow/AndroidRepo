package com.example.fillow.denisrestaurant.UserInfoFragm.ChangePhone;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fillow.denisrestaurant.R;
import com.example.fillow.denisrestaurant.UserInfoFragm.UserInfoFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePhoneSuccessFragment extends Fragment implements View.OnClickListener{

    private Button btnBackToProfile;
    private FragmentTransaction transaction;
    private Fragment userInfoFragment;


    public ChangePhoneSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        Log.d("ewq", "changePhoneSuccess");
        return inflater.inflate(R.layout.fragment_change_phone_success, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBackToProfile = view.findViewById(R.id.btnButt);
        btnBackToProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnButt:
                userInfoFragment = new UserInfoFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.frgmCont, userInfoFragment);
                transaction.commit();
                break;
            default:
                break;
        }
    }
}
