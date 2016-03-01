package sirun.tannt275.funie3s.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import sirun.tannt275.funie3s.fragment.ReadingFragment;
import sirun.tannt275.funie3s.model.StoryModel;

/**
 * Created by TanNT on 12/1/2015.
 */
public class AdapterReadingFragment extends FragmentStatePagerAdapter{

    public static String TAG = AdapterReadingFragment.class.getSimpleName();
    private List<StoryModel> listStories;

    public AdapterReadingFragment(FragmentManager fm, List<StoryModel> listStories) {
        super(fm);
        this.listStories = listStories;
    }

    @Override
    public Fragment getItem(int position) {
        return ReadingFragment.newInstance(listStories.get(position));
    }

    @Override
    public int getCount() {
        return listStories.size();
    }

}
