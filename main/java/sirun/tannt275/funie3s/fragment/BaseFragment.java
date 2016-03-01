package sirun.tannt275.funie3s.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by TanNT on 12/1/2015.
 */
public class BaseFragment  extends Fragment{

    public static String TAG = BaseFragment.class.getSimpleName();

    public static String STATE = "STATE";
    public static String CATEGORY_ID = "CATEGORY_ID";

    public static BaseFragment newInstance(int state, int _categoryId){
        return null;
    }
}
