package sirun.tannt275.funie3s.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import sirun.tannt275.funie3s.R;

/**
 * Created by TanNT on 12/2/2015.
 */
public class DrawerHolder extends RecyclerView.ViewHolder {

    public LinearLayout drawerLayoutItem;
    public CircleImageView imageItem;
    public TextView nameItem;
    public TextView numberSeen;
    public TextView numberNew;

    public DrawerHolder(View itemView) {
        super(itemView);
        drawerLayoutItem = (LinearLayout) itemView.findViewById(R.id.itemDrawerLayout);
        imageItem = (CircleImageView) itemView.findViewById(R.id.itemDrawerIcon);
        nameItem = (TextView) itemView.findViewById(R.id.itemDrawerName);
        numberSeen = (TextView) itemView.findViewById(R.id.itemDrawerNumberSeen);
        numberNew = (TextView) itemView.findViewById(R.id.itemDrawerNumberNew);
    }

    public interface ClickItemInDrawer {
        public void onClickItemInDrawer(View view, int position);
    }
}
