package com.example.memomate.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.memomate.Activties.ChangePassWordActivity;
import com.example.memomate.Activties.IntroActivity;
import com.example.memomate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


public class ProfileFragment extends Fragment {

    Button btnChangePassword;
    EditText edtEmail;
    CardView cardUserName;
    TextView txtUserName1, txtUserName2;
    Button btnLogout, btnDeleteAccount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFormWidgets(view);
        addEvents(view);
        setProfileByEmail();
        setProfileByGoogle();
    }

    public void getFormWidgets(View v)
    {
        btnChangePassword = v.findViewById(R.id.btnChangePassword);
        edtEmail = v.findViewById(R.id.edtEmail);
        cardUserName = v.findViewById(R.id.cardUserName);
        txtUserName1 = v.findViewById(R.id.txtUserName1);
        txtUserName2 = v.findViewById(R.id.txtUserName2);
        btnLogout = v.findViewById(R.id.btnLogout);
        btnDeleteAccount = v.findViewById(R.id.btnDeleteAccount);
    }

    public void addEvents(View v)
    {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChangePassWordActivity.class);
                startActivityForResult(i, 34);
            }
        });
        cardUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChangUserName(v);
            }
        });
        txtUserName2.setText(txtUserName1.getText().toString());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), IntroActivity.class);
                startActivity(i);
            }
        });
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogWarining(v);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 34 && resultCode == 65) {
            String newPassword = data.getStringExtra("newPass");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "User password updated.");
                            }
                        }
                    });


        }
    }
    private void setProfileByEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
        {
            return;
        }
        txtUserName1.setText(user.getDisplayName());
        //txtUserName2.setText(user.getDisplayName());
        //Uri avatar = user.getPhotoUrl();
        edtEmail.setText(user.getEmail());
    }
    private void setProfileByGoogle()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                txtUserName1.setText(profile.getDisplayName());
                //txtUserName2.setText(profile.getDisplayName());
                edtEmail.setText(profile.getEmail());
                //Uri avatar = profile.getPhotoUrl();
            }
        }
    }
    private void showDialogChangUserName(View v)
    {
        final Dialog dialog = new Dialog(v.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_username);
        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.BottomDialogAnimation);

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);

        Button btnCancelDialog = dialog.findViewById(R.id.btnCancelDialog);
        Button btnSaveDialog = dialog.findViewById(R.id.btnSaveDialog);
        EditText edtUserName = dialog.findViewById(R.id.edtUserName);
        edtUserName.setText(txtUserName2.getText().toString());
        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSaveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUserName1.setText(edtUserName.getText().toString());
                txtUserName2.setText(edtUserName.getText().toString());

                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void showDialogWarining(View v)
    {
        final Dialog dialog = new Dialog(v.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_delete_account);
        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.BottomDialogAnimation);

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("TAG", "User account deleted.");
                                }
                            }
                        });
                dialog.dismiss();
                Intent i = new Intent(getActivity(), IntroActivity.class);
                startActivity(i);
            }
        });
        dialog.show();
    }




}