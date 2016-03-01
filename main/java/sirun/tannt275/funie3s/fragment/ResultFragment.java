package sirun.tannt275.funie3s.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import sirun.tannt275.funie3s.adapter.AdapterResult;
import sirun.tannt275.funie3s.R;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.database.DataBaseHandler;
import sirun.tannt275.funie3s.model.CategoryModel;
import sirun.tannt275.funie3s.model.StoryModel;

public class ResultFragment extends Fragment {

    public static String TAG = ResultFragment.class.getSimpleName();

    private String query;
    private ListView listResult;
    private TextView resultText;
    private ProgressBar progressBar;

    private DataBaseHandler dataBaseHandler;
    private List<StoryModel> storyModelList;
    private List<CategoryModel> categoryModelList;
    private AdapterResult adapterResult;

    public static ResultFragment newInstance(String query) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ConfigApp.DATA_SEARCH, query);
        fragment.setArguments(args);
        return fragment;
    }

    public ResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(ConfigApp.DATA_SEARCH, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        listResult = (ListView) rootView.findViewById(R.id.result_listView);
        resultText = (TextView) rootView.findViewById(R.id.result_return);
        dataBaseHandler = new DataBaseHandler(getActivity());
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressLoad);
        progressBar.setVisibility(View.VISIBLE);
        fillData();
        return rootView;
    }

    private void fillData() {

        categoryModelList = dataBaseHandler.getAllCategories();
        storyModelList = dataBaseHandler.getStorySearchQuery(query);
        if (storyModelList.size() == 0){
            resultText.setText(getString(R.string.result_return_0));
            listResult.setVisibility(View.GONE);
        } else {
            resultText.setText(String.format(getString(R.string.result_return_1), storyModelList.size()));
            adapterResult = new AdapterResult(getActivity(), storyModelList, categoryModelList);
            listResult.setVisibility(View.VISIBLE);
            listResult.setAdapter(adapterResult);
        }
        progressBar.setVisibility(View.GONE);
    }
}
