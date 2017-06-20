package com.example.vishal.edhusk_final;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by vishal on 14/06/17.
 */

public class FoldingCellListAdapter extends ArrayAdapter<student_data> {
    ArrayList<student_data> student_list;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    Main_Home main;
    int Resource;
    LayoutInflater vi;



    public FoldingCellListAdapter(Context context, int resource, ArrayList<student_data> objects) {
        super(context, resource, objects);

        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        student_list = objects;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        student_data data = getItem(position);

        FoldingCell cell = (FoldingCell) convertView;

        ViewHolder viewHolder;

        if (cell == null){
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell,parent,false);
            viewHolder.name = (TextView) cell.findViewById(R.id.name);
            viewHolder.street = (TextView) cell.findViewById(R.id.address);
            viewHolder.age = (TextView) cell.findViewById(R.id.age);
            cell.setTag(viewHolder);


        }
        else {

            viewHolder = (ViewHolder) cell.getTag();

        }
        String i = Integer.toString(data.getAge());

        viewHolder.name.setText(student_list.get(position).getName());
        viewHolder.street.setText(student_list.get(position).getStreet());
        viewHolder.age.setText(student_list.get(position).getStreet());

        return cell;


    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }



    private static class ViewHolder {
        TextView _id;
        TextView type;
        TextView days;
        TextView travel;
        TextView age;
        TextView batch;
        TextView name;
        TextView gender;
        TextView email;
        TextView contact;
        TextView street;
        TextView location;
        TextView subjects;
        TextView comment;
        TextView latitude;
        TextView longitude;
    }


}
