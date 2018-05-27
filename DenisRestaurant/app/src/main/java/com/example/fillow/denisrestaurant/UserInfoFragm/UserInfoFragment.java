package com.example.fillow.denisrestaurant.UserInfoFragm;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fillow.denisrestaurant.ChangePasswordFragment;
import com.example.fillow.denisrestaurant.LogInFragm.LogInFragment;
import com.example.fillow.denisrestaurant.R;
import com.example.fillow.denisrestaurant.UserInfoFragm.ChangePhone.ChangePhoneNumberFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment implements View.OnClickListener {

    private TextView phoneTV;
    private TextView logOutTV;
    private TextView changePasswordTV;
    private Fragment changePhoneFragment;
    private Fragment changePasswordFragment;
    private Fragment logInFragment;
    private FragmentTransaction transaction;

    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changePasswordTV = view.findViewById(R.id.tvChangePasswordUserInfo);
        changePasswordTV.setOnClickListener(this);
        logOutTV = view.findViewById(R.id.tvLogOutUserInfo);
        logOutTV.setOnClickListener(this);
        phoneTV = view.findViewById(R.id.phoneUserInfoTextView);
        phoneTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        transaction = getFragmentManager().beginTransaction();

        switch (view.getId()){
            case R.id.phoneUserInfoTextView:
                changePhoneFragment = new ChangePhoneNumberFragment();
                transaction.replace(R.id.frgmCont, changePhoneFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.tvChangePasswordUserInfo:
                changePasswordFragment = new ChangePasswordFragment();
                transaction.replace(R.id.frgmCont, changePasswordFragment);
                transaction.addToBackStack(null).commit();
                break;
            case R.id.tvLogOutUserInfo:
                logInFragment = new LogInFragment();
                transaction.replace(R.id.frgmCont, logInFragment).commit();
                break;
            default:
                break;
        }
    }

}
