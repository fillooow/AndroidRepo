package com.example.fillow.denisrestaurant.UserInfoFragm.ChangePhone;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fillow.denisrestaurant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePhoneNumberFragment extends Fragment implements View.OnClickListener{

    private Button btnChangePhone;
    private FragmentTransaction transaction;
    private Fragment changePhoneCodeFragment;


    public ChangePhoneNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }

        Log.d("ewq", "changePhoneNumber");

        return inflater.inflate(R.layout.fragment_change_phone_number, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.text_change_phone);
        btnChangePhone = view.findViewById(R.id.btnSendChangePhoneNumber);
        btnChangePhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSendChangePhoneNumber:
                changePhoneCodeFragment = new ChangePhoneCodeFragment();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frgmCont, changePhoneCodeFragment);
                transaction.addToBackStack(null).commit();
                break;
            default:

                break;
        }
    }

}
