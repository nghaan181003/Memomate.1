package com.example.memomate.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.memomate.R;

public class CreateClassActivity extends AppCompatActivity {
    TextView btnSave;
    ImageButton btnClose;
    EditText edtClassTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        getFormWidgets();
        addEvents();
    }
    public void  addEvents()
    {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSave.setVisibility(View.INVISIBLE);
        edtClassTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s))
                {
                    btnSave.setVisibility(View.INVISIBLE);
                }
                else btnSave.setVisibility(View.VISIBLE);
            }
        });
    }
    public void getFormWidgets()
    {
        btnClose = findViewById(R.id.btnClose);
        btnSave = findViewById(R.id.btnSave);
        edtClassTitle = findViewById(R.id.edtClassTitle);

    }
}