package com.samapps310.bcscanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class ResultActivity extends AppCompatActivity {

    private TextView tv_content, tv_format;
    private String content;
    private static boolean isURL = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();

        tv_format = findViewById(R.id.tv_format);
        tv_format.setText(bundle.getString("format"));

        tv_content = findViewById(R.id.tv_content);
        content = bundle.getString("content");

        if (isValid(content)){
            isURL = true;
            tv_content.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_blue_dark));
        }

        tv_content.setText(content);
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isURL){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
                    startActivity(intent);
                }
            }
        });

        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
        tv_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData = ClipData.newPlainText("Source Text", content);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(ResultActivity.this, "content copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
