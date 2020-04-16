package com.example.lab3_ui;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] names = new String[]
            {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private int[] imageIds = new int[]
            {R.drawable.lion, R.drawable.tiger,
                    R.drawable.monkey, R.drawable.dog,
                    R.drawable.cat, R.drawable.elephant};
    private ListView listView;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = (EditText) findViewById(R.id.txt);
        showAnimalList();
        setActionMode();
        setOverflowShowingAlways();
    }

    private void showAnimalList() {
        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("name", names[i]);
            listItem.put("header", imageIds[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                listItems, R.layout.simple_item,
                new String[]{"name", "header"},
                new int[]{R.id.name, R.id.header});
        listView = findViewById(R.id.myList);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener((parent, view, position, id) ->
        {
            Toast toast = Toast.makeText(MainActivity.this, names[position],
                    Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private void showAlertDialog(View resource) {
        View view = View.inflate(this, R.layout.alter_dialog_ui, null);
        new AlertDialog.Builder(this)
                .setTitle("ANDROID APP")
                .setView(view)
                .create()
                .show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu_ui, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {
            case R.id.small:
                edit.setTextSize(10);
                break;
            case R.id.middle:
                edit.setTextSize(16);
                break;
            case R.id.big:
                edit.setTextSize(20);
                break;
            case R.id.red:
                edit.setTextColor(Color.RED);
                break;
            case R.id.black:
                edit.setTextColor(Color.BLACK);
                break;
            case R.id.ordinary:
                Toast toast = Toast.makeText(MainActivity.this, "普通菜单项",
                        Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        return true;
    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setActionMode()
    {
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                mode.setTitle("Selected");
                listView.setSelector(R.drawable.background);
                return true;
            }

            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
            }

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an <code><a href="/reference/android/view/ActionMode.html#invalidate()">invalidate()</a></code> request
                return false;
            }
        });
    }
}



