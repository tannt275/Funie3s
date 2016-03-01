package sirun.tannt275.funie3s;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sirun.tannt275.funie3s.adapter.AdapterDrawerMenu;
import sirun.tannt275.funie3s.adapter.AdapterSearchView;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.appcontroll.SharePrefApp;
import sirun.tannt275.funie3s.database.DataBaseHandler;
import sirun.tannt275.funie3s.fragment.MainFragment;
import sirun.tannt275.funie3s.fragment.ResultFragment;
import sirun.tannt275.funie3s.model.CategoryModel;
import sirun.tannt275.funie3s.viewholder.DrawerHolder;

public class MainActivity extends AppCompatActivity implements DrawerHolder.ClickItemInDrawer {

    public static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView menuDrawerRecyclerView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private SearchView searchView;
    private SearchView.SearchAutoComplete autoComplete;

    private List<CategoryModel> listCategories;
    private DataBaseHandler dataBaseHandler;
    private AdapterDrawerMenu adapterDrawerMenu;

    private static int _currentState;
    private static int _currentCategory;

    public static List<Integer> listRandom;

    private List<String> listSearch;
    // only for test
    private String urlIcon = "http://media.salon.com/2012/12/kristen_stewart.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHandler = new DataBaseHandler(this);
        listRandom = ConfigApp.generateRandomInList(dataBaseHandler.getALlStories().size(), 15);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuDrawerRecyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        menuDrawerRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        menuDrawerRecyclerView.setLayoutManager(layoutManager);

        initViews();
    }

    private void initViews() {

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        listCategories = dataBaseHandler.getAllCategories();
        adapterDrawerMenu = new AdapterDrawerMenu(this, listCategories);
        adapterDrawerMenu.setClickItemInDrawer(this);
        menuDrawerRecyclerView.setAdapter(adapterDrawerMenu);
        Log.e(TAG, "size menu: " + listCategories.size());

        _currentState = ConfigApp.STATE_RANDOM;
        _currentCategory = ConfigApp.ID_CATEGORY_RANDOM;
        MainFragment mainFragment = MainFragment.newInstance(ConfigApp.STATE_RANDOM, ConfigApp.ID_CATEGORY_RANDOM, ConfigApp.FILTER_CODE_NONE);
        displayFragmentMain(mainFragment);
        getSupportActionBar().setTitle(getString(R.string.category_random));
    }

    private void displayFragmentMain(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerMain, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setIconifiedByDefault(false);
        ImageView searchIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        LinearLayout ll = (LinearLayout) searchView.getChildAt(0);
        LinearLayout ll2 = (LinearLayout) ll.getChildAt(2);
        LinearLayout ll3 = (LinearLayout) ll2.getChildAt(1);
        autoComplete = (SearchView.SearchAutoComplete) ll3.getChildAt(0);
        autoComplete.setThreshold(0);
        // set the hint text color
        autoComplete.setHintTextColor(Color.WHITE);
        // set the text color
        autoComplete.setTextColor(Color.WHITE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String dataWasSearch = SharePrefApp.getString(ConfigApp.DATA_SEARCH, "");
                dataWasSearch += (query + ",");
                SharePrefApp.putString(ConfigApp.DATA_SEARCH, dataWasSearch);
                Log.e(TAG, "data search was search: " + dataWasSearch);
                searchStoryOnDatabase(query);
                menuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String dataWasSearch = SharePrefApp.getString(ConfigApp.DATA_SEARCH, "");
                if (!TextUtils.isEmpty(dataWasSearch)) {
                    listSearch = new ArrayList<>(Arrays.asList(dataWasSearch.split(",")));
                    if (listSearch.size() != 0) {
                        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.popup_action_search, listSearch);
                        autoComplete.setAdapter(arrayAdapter);*/
                        AdapterSearchView adapterSearchView = new AdapterSearchView(MainActivity.this, listSearch);
                        autoComplete.setAdapter(adapterSearchView);
                    }
                }
                return false;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                String data = listSearch.get(position);
                Log.e(TAG, "onSuggestionClick " + data);
                searchStoryOnDatabase(data);
                menuItem.collapseActionView();
                return true;
            }
        });
        return true;
    }

    private void searchStoryOnDatabase(String newText) {
        ConfigApp.hideKeyBoard(this);
        Log.e(TAG, "current Thread: " + Thread.currentThread().getStackTrace()[2].getMethodName() + " value was input search: " + newText);
        getSupportActionBar().setTitle("Search: " + newText);
        ResultFragment resultFragment = ResultFragment.newInstance(newText);
        displayFragmentMain(resultFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        int id = item.getItemId();
        switch (id){
            case R.id.menu_filter_read:

                MainFragment mainFragmentRead = MainFragment.newInstance(_currentState, _currentCategory, ConfigApp.FILTER_CODE_READ);
                displayFragmentMain(mainFragmentRead);

                break;
            case R.id.menu_filter_unread:
                MainFragment mainFragmentUnRead = MainFragment.newInstance(_currentState, _currentCategory, ConfigApp.FILTER_CODE_UNREAD);
                displayFragmentMain(mainFragmentUnRead);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItemInDrawer(View view, int position) {
        CategoryModel categoryModel = listCategories.get(position);
        Log.e(TAG, "category is: " + categoryModel.convertToString());
        drawer.closeDrawer(GravityCompat.START);

        _currentState = ConfigApp.STATE_CATEGORY;
        _currentCategory = categoryModel.getIdCategory();

        MainFragment mainFragment = MainFragment.newInstance(ConfigApp.STATE_CATEGORY, categoryModel.getIdCategory(), ConfigApp.FILTER_CODE_NONE);
        displayFragmentMain(mainFragment);
        getSupportActionBar().setTitle(categoryModel.getNameCategory());

    }

}
