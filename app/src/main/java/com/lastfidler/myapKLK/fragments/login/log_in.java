package com.lastfidler.myapKLK.fragments.login;

import android.app.Notification;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lastfidler.myapKLK.Online_mode;
import com.lastfidler.myapKLK.R;
import com.lastfidler.myapKLK.activities.MainActivity;


public class log_in extends Fragment implements View.OnClickListener {

    private View view;
    private FirebaseAuth mAuth;
    private LinearLayout login;
    private EditText email, password;
    private TextView singup, reset;
    private String S_email, S_password;
    private Button ofline;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.loging_page, container, false);
        inisialize();
        setOnclickManagemt();
        SetVisibility(false);
        return view;
    }

    private void getdata() {
        S_email = email.getText().toString();
        S_password = password.getText().toString();
    }

    private void inisialize() {
        ofline = view.findViewById(R.id.ofline);
        reset = view.findViewById(R.id.reset);
        login = view.findViewById(R.id.login);
        password = view.findViewById(R.id.password);
        email = view.findViewById(R.id.email);
        singup = view.findViewById(R.id.singup);
        progressBar = view.findViewById(R.id.progres);
        mAuth = FirebaseAuth.getInstance();
    }

    private void setOnclickManagemt() {
        reset.setOnClickListener(this);
        login.setOnClickListener(this);
        singup.setOnClickListener(this);
        ofline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == login.getId()) {
            Login();
        }
        if (v.getId() == singup.getId()) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Sing_up(), "singup").addToBackStack("singup").commit();
        }
        if (v.getId() == reset.getId()) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Reset(), "reset").addToBackStack("reset").commit();
        }
        if (v.getId() == ofline.getId()) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        }

    }

    private void Login() {
        getdata();
        if (S_email.isEmpty() || S_password.isEmpty()) {
            Toast.makeText(getActivity(), "please check you info", Toast.LENGTH_SHORT).show();
        } else {
            SetVisibility(true);
            mAuth.signInWithEmailAndPassword(S_email, S_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkVerefecation();
                    } else {
                        SetVisibility(false);
                        Toast.makeText(getActivity(), "email/password incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void checkVerefecation() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (user.isEmailVerified()) {
                updateUI(mAuth.getCurrentUser());
            } else {
                SetVisibility(false);
                Toast.makeText(getActivity(), "please verify your email", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
            }
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(getActivity(), Online_mode.class));
            getActivity().finish();
        }
    }

    private void SetVisibility(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
