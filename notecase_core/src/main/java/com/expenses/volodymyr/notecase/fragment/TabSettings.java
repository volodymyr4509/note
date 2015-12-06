package com.expenses.volodymyr.notecase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;

import com.expenses.volodymyr.notecase.R;
import com.expenses.volodymyr.notecase.activity.AddEditCategoryActivity;
import com.expenses.volodymyr.notecase.activity.ViewCategoryActivity;
import com.expenses.volodymyr.notecase.entity.Category;
import com.expenses.volodymyr.notecase.util.DBHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vkret on 04.12.15.
 */
public class TabSettings extends Fragment {
    public static final String CATEGORY_ID_KEY = "categoryId";
    private List<String> settings;

    public TabSettings() {
        super();
        settings =  new ArrayList<>();
        settings.add("Manage category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.tab_settings, container, false);
        ListView listView = (ListView) view.findViewById(R.id.settings_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, settings);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //add logic for different setting options
                Intent intent = new Intent(getActivity(), ViewCategoryActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}