package sirun.tannt275.funie3s.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sirun.tannt275.funie3s.R;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.model.CategoryModel;
import sirun.tannt275.funie3s.model.StoryModel;

/**
 * Created by TanNT on 12/15/2015.
 */
public class AdapterResult extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<StoryModel> storyModels;
    private List<CategoryModel> categoryModels;

    public AdapterResult(Context context, List<StoryModel> storyModels, List<CategoryModel> categoryModels) {
        this.context = context;
        this.storyModels = storyModels;
        this.categoryModels = categoryModels;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return storyModels.size();
    }

    @Override
    public Object getItem(int position) {
        return storyModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_recycler_main, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (CircleImageView) convertView.findViewById(R.id.itemRecyclerIcon);
            viewHolder.name = (TextView) convertView.findViewById(R.id.itemRecyclerName);
            viewHolder.category = (TextView) convertView.findViewById(R.id.itemRecyclerCategory);
            viewHolder.state = (TextView) convertView.findViewById(R.id.itemRecyclerState);
            viewHolder.content = (TextView) convertView.findViewById(R.id.itemRecyclerContent);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        StoryModel storyModel = storyModels.get(position);
        for (int i = 0; i < categoryModels.size(); i ++) {
            CategoryModel categoryModel = categoryModels.get(i);
            if (storyModel.get_idCategory() == categoryModel.getIdCategory()){
                ImageLoader.getInstance().displayImage(categoryModel.getUrl(), viewHolder.imageView, ConfigApp.OPTION_ICON);
                viewHolder.category.setText(categoryModel.getNameCategory());
            }
        }
        viewHolder.name.setText(storyModel.getName());
        viewHolder.content.setText(Html.fromHtml(storyModel.getContent()));
        viewHolder.state.setText(storyModel.isRead() ? context.getString(R.string.state_read) : context.getString(R.string.state_unread));

        return convertView;
    }

    public class ViewHolder {
        CircleImageView imageView;
        TextView name;
        TextView category;
        TextView state;
        TextView content;
    }
}
