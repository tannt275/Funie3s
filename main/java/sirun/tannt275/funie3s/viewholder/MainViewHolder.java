package sirun.tannt275.funie3s.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import sirun.tannt275.funie3s.R;

/**
 * Created by TanNT on 12/1/2015.
 */
public class MainViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout itemMain;
    public CircleImageView itemImage;
    public TextView itemName;
    public TextView itemCategory;
    public TextView itemState;
    public TextView itemContent;

    public MainViewHolder(View itemView) {
        super(itemView);
        itemImage = (CircleImageView) itemView.findViewById(R.id.itemRecyclerIcon);
        itemName = (TextView) itemView.findViewById(R.id.itemRecyclerName);
        itemCategory = (TextView) itemView.findViewById(R.id.itemRecyclerCategory);
        itemState = (TextView) itemView.findViewById(R.id.itemRecyclerState);
        itemContent = (TextView) itemView.findViewById(R.id.itemRecyclerContent);
        itemMain = (RelativeLayout) itemView.findViewById(R.id.itemRecyclerMain);
    }

    public interface ClickRecyclerViewItem{
        public void onClickItem(View view, int position);
    }
}
