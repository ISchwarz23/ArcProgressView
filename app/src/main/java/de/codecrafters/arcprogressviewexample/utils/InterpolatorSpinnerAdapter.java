package de.codecrafters.arcprogressviewexample.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.codecrafters.arcprogressviewexample.R;

/**
 * Created by Ingo on 29.08.2015.
 */
public class InterpolatorSpinnerAdapter extends ArrayAdapter<Interpolator> {

    public InterpolatorSpinnerAdapter(final Context context, final List<Interpolator> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.simple_string_item, parent, false);

        TextView textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText(getItem(position).getClass().getSimpleName());

        return rootView;
    }

    @Override
    public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
