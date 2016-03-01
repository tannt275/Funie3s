package sirun.tannt275.funie3s.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import sirun.tannt275.funie3s.R;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.model.CategoryModel;
import sirun.tannt275.funie3s.model.StoryModel;
import sirun.tannt275.funie3s.viewholder.MainViewHolder;

/**
 * Created by TanNT on 12/1/2015.
 */
public class AdapterMainStory extends RecyclerView.Adapter<MainViewHolder> {

    public static String TAG = AdapterMainStory.class.getSimpleName();

    private List<CategoryModel> listCategories;
    private List<StoryModel> listStories;
    private Context context;
    private boolean isRandom;

    private MainViewHolder.ClickRecyclerViewItem clickItem;

    public List<StoryModel> getListStories() {
        return listStories;
    }

    public void setListStories(List<StoryModel> listStories) {
        this.listStories = listStories;
    }

    public MainViewHolder.ClickRecyclerViewItem getClickItem() {
        return clickItem;
    }

    public void setClickItem(MainViewHolder.ClickRecyclerViewItem clickItem) {
        this.clickItem = clickItem;
    }

    public AdapterMainStory(List<StoryModel> listStories, Context context,List<CategoryModel> listCategories, boolean isRandom) {
        this.listStories = listStories;
        this.context = context;
        this.listCategories = listCategories;
        this.isRandom = isRandom;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_recycler_main, parent, false);
        return new MainViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        StoryModel storyModel = listStories.get(position);
        holder.itemImage.setVisibility(isRandom ? View.VISIBLE : View.GONE);
        holder.itemCategory.setVisibility(isRandom ? View.VISIBLE : View.GONE);
        holder.itemContent.setText(Html.fromHtml(storyModel.getContent()));
        holder.itemName.setText(storyModel.getName());
        holder.itemState.setText(storyModel.isRead() ? context.getString(R.string.state_read) : context.getString(R.string.state_unread));

        for (int i = 0; i < listCategories.size(); i ++){
            CategoryModel categoryModel = listCategories.get(i);
            if(categoryModel.getIdCategory() == storyModel.get_idCategory()){
                holder.itemCategory.setText(String.format(context.getString(R.string.category_prefix), categoryModel.getNameCategory()));
                ImageLoader.getInstance().displayImage(categoryModel.getUrl(), holder.itemImage, ConfigApp.OPTION_ICON);
            }
        }
        holder.itemMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.onClickItem(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStories.size();
    }
}
