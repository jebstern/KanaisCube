package com.jebstern.kanaiscube;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Locale;

public class NormalFragment extends Fragment {

    private DBHelper db;
    EditText et_search;
    NormalAdapter adapter;

    public NormalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_normal, container, false);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Normal");

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String displayMode = SP.getString("displaymode", "all");
        String query1;
        if (displayMode.equalsIgnoreCase("all")) {
            query1 = " WHERE normal>=0";
        } else if (displayMode.equalsIgnoreCase("cubed")) {
            query1 = " WHERE normal=1";
            stringBuilder.append(" [cubed]");
        } else {
            query1 = " WHERE normal=0";
            stringBuilder.append(" [not cubed]");
        }


        String displayItems = SP.getString("displayitems", "all");
        String query2;
        if (displayItems.equalsIgnoreCase("all")) {
            query2 = "";
        } else if (displayItems.equalsIgnoreCase("weapons")) {
            query2 = " AND type='weapon'";
            stringBuilder.append(" [weapons]");
        } else if (displayItems.equalsIgnoreCase("armor")) {
            query2 = " AND type='armor'";
            stringBuilder.append(" [armor]");
        } else {
            query2 = " AND type='jewelry'";
            stringBuilder.append(" [jewelry]");
        }

        final String query = query1 + query2;

        List<Items> items = db.getAllItems(query);
        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        adapter = new NormalAdapter(getActivity(), items);
        lv.setAdapter(adapter);

        String fragmentTitle = stringBuilder.toString();


        et_search = (EditText) rootView.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                List<Items> new_items;
                if (text.length() == 0) {
                    new_items = db.getAllItems(query);
                } else {
                    String query3 = query + " AND name like '" + text + "%'";
                    new_items = db.searchItems(query3);
                }
                adapter.filter(new_items);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });


        getActivity().setTitle(fragmentTitle);
        return rootView;
    }

}