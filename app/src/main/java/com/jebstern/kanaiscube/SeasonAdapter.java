package com.jebstern.kanaiscube;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class SeasonAdapter extends BaseAdapter {

    private List<Items> itemlist;
    Context context;
    private static LayoutInflater inflater = null;


    public SeasonAdapter(Context context, List<Items> itemlist) {
        this.itemlist = itemlist;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public Object getItem(int position) {
        return itemlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView item_name;
        TextView item_affix;
        TextView item_cubed;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.row, null);
        holder.item_name = (TextView) rowView.findViewById(R.id.armor_name);
        holder.item_affix = (TextView) rowView.findViewById(R.id.armor_affix);
        holder.item_cubed = (TextView) rowView.findViewById(R.id.item_cubed);

        final Items item = itemlist.get(position);

        holder.item_name.setText(item.getName());
        holder.item_affix.setText(item.getAffix());

        if (item.getSeasonMode() == 1) {
            holder.item_cubed.setText("Cubed");
            holder.item_cubed.setTextColor(Color.BLUE);
        } else {
            holder.item_cubed.setText("Not cubed");
            holder.item_cubed.setTextColor(Color.RED);
        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);

                String displayMode = SP.getString("displaymode", "all");

                final boolean removeItem;
                final String alertTitle, alertMessage, cubedStatus;
                final int cubed = item.getSeasonMode(), cubeValue, color;

                if (cubed == 1) {
                    if (displayMode.equalsIgnoreCase("cubed")) {
                        removeItem = true;
                    } else {
                        removeItem = false;
                    }
                    alertTitle = "Remove cubed status";
                    alertMessage = "Remove cubed status from '" + item.getName() + "' ?";
                    cubedStatus = "Not cubed";
                    cubeValue = 0;
                    color = Color.RED;
                } else {
                    if (displayMode.equalsIgnoreCase("noncubed")) {
                        removeItem = true;
                    } else {
                        removeItem = false;
                    }
                    alertTitle = "Set item as cubed";
                    alertMessage = "Set '" + item.getName() + "' as cubed?";
                    cubedStatus = "Cubed";
                    cubeValue = 1;
                    color = Color.BLUE;
                }


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(alertTitle);
                alertDialogBuilder.setMessage(alertMessage);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DBHelper helper = new DBHelper(context);
                        item.setSeasonMode(cubeValue);
                        holder.item_cubed.setText(cubedStatus);
                        holder.item_cubed.setTextColor(color);
                        helper.updateItem(item, "season");
                        if (removeItem) {
                            itemlist.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
        return rowView;
    }

    public void filter(List<Items> new_items) {
        itemlist.clear();
        itemlist = new_items;
        notifyDataSetChanged();
    }

}