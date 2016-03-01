package sirun.tannt275.funie3s.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import sirun.tannt275.funie3s.R;
import sirun.tannt275.funie3s.ReadingAcivity;
import sirun.tannt275.funie3s.adapter.AdapterMainStory;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.database.DataBaseHandler;
import sirun.tannt275.funie3s.model.CategoryModel;
import sirun.tannt275.funie3s.model.StoryModel;
import sirun.tannt275.funie3s.viewholder.MainViewHolder;

/**
 * Created by TanNT on 12/1/2015.
 */
public class MainFragment extends Fragment implements MainViewHolder.ClickRecyclerViewItem {

    public static String TAG = MainFragment.class.getSimpleName();

    public static String STATE = "STATE";
    public static String CATEGORY_ID = "CATEGORY_ID";
    public static String FILTER_CODE = "FILTER_CODE";
    private boolean isRandom;
    private int idCategory;
    private int filterCode;

    private AdView mAdview;
    private AdRequest mAdRequest;
    private RecyclerView recyclerView;
    private TextView textViewState;
    private List<StoryModel> listStories;
    private List<CategoryModel> listCategories;
    private AdapterMainStory adapterMainStory;
    private DataBaseHandler dataBaseHandler;

    public static MainFragment newInstance(int _state, int _categoryId, int _code){
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STATE, _state);
        bundle.putInt(CATEGORY_ID, _categoryId);
        bundle.putInt(FILTER_CODE, _code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.mainRecylerView);
        textViewState = (TextView) rootView.findViewById(R.id.search_void);
        textViewState.setVisibility(View.GONE);
        mAdview = (AdView) rootView.findViewById(R.id.main_adView);
        mAdview.setVisibility(View.GONE);
        mAdRequest = new AdRequest.Builder()
                .addTestDevice("43A9BBF89F6FBB42618CB6511421A918")
                .build();

        dataBaseHandler = new DataBaseHandler(getActivity());
        initData();
        return rootView;
    }

    private void initData() {
        Bundle bundle = getArguments();
        int state = bundle.getInt(STATE);
        if (state == ConfigApp.STATE_RANDOM){
            isRandom = true;
            idCategory = ConfigApp.ID_CATEGORY_RANDOM;
        } else if (state == ConfigApp.STATE_CATEGORY){
            isRandom = false;
            idCategory = bundle.getInt(CATEGORY_ID);
        }
        filterCode = bundle.getInt(FILTER_CODE);

        Log.e(TAG, "when newInstance: " + "state: " + state + " id Category: " + idCategory + " filter code: " + filterCode);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        listCategories = dataBaseHandler.getAllCategories();
        if (isRandom)
            listStories = dataBaseHandler.generateRandomStories();
        else
            listStories = dataBaseHandler.getStoriesWithIdCategory(idCategory);

        if (filterCode == ConfigApp.FILTER_CODE_READ){
            for (int i = listStories.size() - 1; i >= 0; i --){
                StoryModel storyModel = listStories.get(i);
                if (!storyModel.isRead())
                    listStories.remove(i);
            }
            textViewState.setText(getString(R.string.filter_void_unread));

        } else if (filterCode == ConfigApp.FILTER_CODE_UNREAD){
            for (int i = listStories.size() - 1; i >= 0; i --){
                StoryModel storyModel = listStories.get(i);
                if (storyModel.isRead())
                    listStories.remove(i);
            }
            textViewState.setText(getString(R.string.filter_void_read));
        }
        textViewState.setVisibility( (listStories.size() == 0) ? View.VISIBLE : View.GONE);
        Log.e(TAG, "show ad or not: " + ConfigApp.checkToShowAd(ConfigApp.MAIN_SHOW_AD_COUNT_IN_DAY));
        if (ConfigApp.checkToShowAd(ConfigApp.MAIN_SHOW_AD_COUNT_IN_DAY)){
            mAdview.setVisibility(View.VISIBLE);
            mAdview.loadAd(mAdRequest);
            ConfigApp.updateShowAd(ConfigApp.MAIN_SHOW_AD_COUNT_IN_DAY);
        } else {
            mAdview.setVisibility(View.GONE);
        }
        adapterMainStory = new AdapterMainStory(listStories, getActivity(), listCategories, isRandom);
        adapterMainStory.setClickItem(this);
        recyclerView.setAdapter(adapterMainStory);

    }

    @Override
    public void onClickItem(View view, int position) {
        Log.e(TAG, "position: " + position);
        Intent toRead = new Intent(getActivity(), ReadingAcivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ConfigApp.READ_CURRENT_POSITION, position);
        bundle.putInt(STATE, idCategory);
        toRead.putExtras(bundle);
        getActivity().startActivity(toRead);
    }
}
