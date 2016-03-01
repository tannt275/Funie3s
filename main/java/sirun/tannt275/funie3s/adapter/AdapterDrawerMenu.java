package sirun.tannt275.funie3s.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import sirun.tannt275.funie3s.R;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.model.CategoryModel;
import sirun.tannt275.funie3s.viewholder.DrawerHolder;

/**
 * Created by TanNT on 12/2/2015.
 */
public class AdapterDrawerMenu extends RecyclerView.Adapter<DrawerHolder> {

    public static String TAG = AdapterDrawerMenu.class.getSimpleName();

    private Context context;
    private List<CategoryModel> listCategories;

    private DrawerHolder.ClickItemInDrawer clickItemInDrawer;

    public DrawerHolder.ClickItemInDrawer getClickItemInDrawer() {
        return clickItemInDrawer;
    }

    public void setClickItemInDrawer(DrawerHolder.ClickItemInDrawer clickItemInDrawer) {
        this.clickItemInDrawer = clickItemInDrawer;
    }

    public AdapterDrawerMenu(Context context, List<CategoryModel> listCategories) {
        this.context = context;
        this.listCategories = listCategories;
    }

    @Override
    public DrawerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_recycler_menu_drawer, parent, false);
        return new DrawerHolder(rootView);
    }

    @Override
    public void onBindViewHolder(DrawerHolder holder, final int position) {
        CategoryModel categoryModel = listCategories.get(position);
        holder.nameItem.setText(categoryModel.getNameCategory());
        ImageLoader.getInstance().displayImage(categoryModel.getUrl(), holder.imageItem, ConfigApp.OPTION_ICON);
        holder.numberSeen.setText(String.valueOf(categoryModel.getNumberRead()));
        holder.numberNew.setText(String.valueOf(categoryModel.getSizeCategory() - categoryModel.getNumberRead()));
        holder.drawerLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemInDrawer.onClickItemInDrawer(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }
}
