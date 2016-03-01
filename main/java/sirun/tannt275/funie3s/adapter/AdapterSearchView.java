package sirun.tannt275.funie3s.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sirun.tannt275.funie3s.R;

/**
 * Created by TanNT on 12/16/2015.
 */
public class AdapterSearchView extends ArrayAdapter<String> {

    public AdapterSearchView(Context context, List<String> models){
        super(context, 0, models);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AutoCompleteHolder autoCompleteHolder;
        if (convertView == null){
            autoCompleteHolder = new AutoCompleteHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.popup_searchview_layout, parent, false);
            autoCompleteHolder.textView = (TextView) convertView.findViewById(R.id.search_history);
            convertView.setTag(autoCompleteHolder);
        } else autoCompleteHolder = (AutoCompleteHolder) convertView.getTag();
        autoCompleteHolder.textView.setText(getItem(position));
        return convertView;
    }
    public class AutoCompleteHolder{
        TextView textView;
        ImageView imageView;
    }
}
