package com.example.fillow.denisrestaurant.LogInFragm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fillow.denisrestaurant.R;
import com.example.fillow.denisrestaurant.UserInfoFragm.UserInfoFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment implements View.OnClickListener{

    private TextInputEditText tietTelNumberLogIn;
    private TextInputLayout tilTelNumberLogIn;
    private TextInputEditText tietPassLogIn;
    private TextInputLayout tilPassLogIn;
    private Fragment userInfoFragment;
    private FragmentTransaction mTransaction;
    private Button btnLogIn;
    private Button btnRegister;
    private TextView tvForgotPassLogIn;


    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogIn = view.findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogIn:
                userInfoFragment = new UserInfoFragment();
                mTransaction = getFragmentManager().beginTransaction()
                .add(R.id.frgmCont, userInfoFragment);
                mTransaction.commit();
                break;
            default:
                break;
        }
    }
}
