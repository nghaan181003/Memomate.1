package com.example.memomate.Activties;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memomate.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView txtForgotUserName, txtForgotPassword, txtMessageEmail, txtMessagePass;
    MaterialButton btnLogin;
    DatePickerDialog datePickerDialog;
    EditText txtEmail, txtPassWord;
    FirebaseAuth mAuth;
    Button btnGoogle;
    ProgressDialog progressDialog;
    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;
    //    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
//    private boolean showOneTapUI = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        LoginWithGoogle();
        LoginWithEmail();
    }

    private void initView()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait a minute");


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtForgotUserName = findViewById(R.id.txtForgotUserName);
        txtForgotUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForgot(R.layout.dialog_forgot_username);
            }
        });

        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForgot(R.layout.dialog_forgot_password);
            }
        });
        txtEmail = findViewById(R.id.txtEmail);
        txtPassWord = findViewById(R.id.txtPassWord);
        txtMessageEmail = findViewById(R.id.txtMesssageEmail);
        txtMessageEmail.setVisibility(View.GONE);
        txtMessagePass = findViewById(R.id.txtMessagePass);
        txtMessagePass.setVisibility(View.GONE);


    }
    public void LoginWithGoogle()
    {
        btnGoogle = findViewById(R.id.btnGoogle);
        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id))// trong strings.xml
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();
        //Đăng nhập bằng gg
        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        });
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                oneTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {
                                progressDialog.dismiss();
                                IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                                activityResultLauncher.launch(intentSenderRequest);
                            }
                        })
                        .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // No Google Accounts found. Just continue presenting the signed-out UI.
                                Log.d("TAG", e.getLocalizedMessage());
                            }
                        });
            }
        });
    }

    public void LoginWithEmail()
    {
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String email = txtEmail.getText().toString().trim();
                //Log.d("Login successful", email);
                String pass = txtPassWord.getText().toString();
                //Log.d("Login", pass);
                if (txtEmail.getText().toString().isEmpty())
                {
                    txtMessageEmail.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
                if (txtPassWord.getText().toString().isEmpty())
                {
                    txtMessagePass.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                show_dialog_login_failed();
                                txtEmail.setText("");
                                txtPassWord.setText("");
                                txtEmail.requestFocus();
                            }
                        }
                    });
                }
            }
        });
    }


    private void showDialogForgot(int layout)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);

        EditText edtEmail = dialog.findViewById(R.id.edtConfirmEmail);
        TextView txtCancel = dialog.findViewById(R.id.txtCancel);
        TextView txtOk = dialog.findViewById(R.id.txtOk);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (layout == R.layout.dialog_forgot_username)
//                    // Code xử lý cấp lại username
//
//                    Toast.makeText(LoginActivity.this, edtEmail.getText() + "UsrName", Toast.LENGTH_SHORT).show();
//                else
//                    // Code xử lý cấp lại mật khẩu
//                    Toast.makeText(LoginActivity.this, edtEmail.getText() + "Password", Toast.LENGTH_SHORT).show();
                progressDialog.show();
                String confirm_email = edtEmail.getText().toString();
                mAuth.sendPasswordResetEmail(confirm_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            //Toast.makeText(LoginActivity.this, "send to email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dialog.show();
    }

    private void show_dialog_login_failed()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login_failed);
        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);

        TextView txtOk = dialog.findViewById(R.id.txtOk);

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}