package com.example.fillow.denisrestaurant.UserInfoFragm.ChangePhone;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fillow.denisrestaurant.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePhoneCodeFragment extends Fragment implements View.OnClickListener{

    private Button btnSendCode;
    private FragmentTransaction transaction;
    private Fragment changePhoneSuccessFragment;
    private TextInputLayout tilChangePhoneCode;
    private TextInputEditText tietChangePhoneCode;

    public ChangePhoneCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        Log.d("ewq", "changePhoneCode");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_phone_code, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSendCode = view.findViewById(R.id.btnSendCodeChangePhoneCode);
        btnSendCode.setOnClickListener(this);
        tilChangePhoneCode = view.findViewById(R.id.tilFragmentChangePhoneCode);
        tietChangePhoneCode = view.findViewById(R.id.tietFragmentChangePhoneCode);
    }


    @Override
    public void onClick(View view) {
        if(tietChangePhoneCode.getText().toString().isEmpty()){
            tilChangePhoneCode.setError("error");
        } else {
            tilChangePhoneCode.setError(null);


            switch(view.getId()){
                case R.id.btnSendCodeChangePhoneCode:
                    changePhoneSuccessFragment = new ChangePhoneSuccessFragment();
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frgmCont, changePhoneSuccessFragment);
                    transaction.addToBackStack(null).commit();
                    break;
                default:
                    break;
            }
        }
    }
}
