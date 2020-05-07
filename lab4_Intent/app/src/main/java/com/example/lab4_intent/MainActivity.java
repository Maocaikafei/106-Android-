package com.example.lab4_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Editable myurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        EditText editText=findViewById(R.id.editText);
        myurl= editText.getText();
        Button button = findViewById(R.id.button);//控件的获取
        button.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        Uri uri = Uri.parse(String.valueOf(myurl));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("url", String.valueOf(myurl));
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveList = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        if(resolveList.size() > 0) {
            String title = "choose application";
            Intent intentChooser = Intent.createChooser(intent, title);
            startActivity(intentChooser);
        }else {
            Toast.makeText(MainActivity.this, "no match activity to start!", Toast.LENGTH_SHORT).show();
        }
    }
}
