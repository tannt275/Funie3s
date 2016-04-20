package sirun.tannt275.funie3s;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import sirun.tannt275.funie3s.adapter.AdapterReadingFragment;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.database.DataBaseHandler;
import sirun.tannt275.funie3s.fragment.MainFragment;
import sirun.tannt275.funie3s.model.StoryModel;

/**
 * Created by TanNT on 12/1/2015.
 */
public class ReadingAcivity extends AppCompatActivity {

    public static String TAG = ReadingAcivity.class.getSimpleName();

    private TextView currentText;
    private ViewPager viewPager;
    private AdView reading_act_adview_bottom;
//    private AdView reading_act_adview;
    private AdRequest reading_act_adrequest;

    private AdapterReadingFragment adapterReadingFragment;
    private List<StoryModel> listStories;
    private int current_position = 9999;
    private int idCategory;
    private DataBaseHandler dataBaseHandler;

    private ShareDialog shareDialog;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_activity);

        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();

        dataBaseHandler = new DataBaseHandler(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            current_position = bundle.getInt(ConfigApp.READ_CURRENT_POSITION);
            idCategory = bundle.getInt(MainFragment.STATE);

        }
        Log.e(TAG, "data get " + "current post: " + current_position + " idCategory: " + idCategory );
//        currentText = (TextView) findViewById(R.id.readingCurrent);
//        currentText.setVisibility(View.INVISIBLE);
//        reading_act_adview = (AdView) findViewById(R.id.adview_reading);
//        reading_act_adview.setVisibility(View.GONE);
        reading_act_adview_bottom = (AdView) findViewById(R.id.adview_reading_bottom);
        reading_act_adview_bottom.setVisibility(View.GONE);
        reading_act_adrequest = new AdRequest.Builder()
                .addTestDevice("43A9BBF89F6FBB42618CB6511421A918").build();

        viewPager = (ViewPager) findViewById(R.id.readingViewPager);
        if (idCategory == ConfigApp.ID_CATEGORY_RANDOM){
            listStories = dataBaseHandler.generateRandomStories();
        } else {
            listStories = dataBaseHandler.getStoriesWithIdCategory(idCategory);
        }
//        listStories = testStoies();
        if (ConfigApp.checkToShowAd(ConfigApp.READING_SHOW_AD_COUNT_IN_DAY)) {
//            reading_act_adview.setVisibility(View.VISIBLE);
//            reading_act_adview.loadAd(reading_act_adrequest);
            reading_act_adview_bottom.setVisibility(View.VISIBLE);
            reading_act_adview_bottom.loadAd(reading_act_adrequest);
            ConfigApp.updateShowAd(ConfigApp.READING_SHOW_AD_COUNT_IN_DAY);
        } else{
//            reading_act_adview.setVisibility(View.GONE);
            reading_act_adview_bottom.setVisibility(View.GONE);
        }
        adapterReadingFragment = new AdapterReadingFragment(getSupportFragmentManager(), listStories);
        viewPager.setAdapter(adapterReadingFragment);
//        if (current_position != 9999 ){
//            currentText.setText(String.format(getString(R.string.reading_current), (current_position + 1), listStories.size()));
//            currentText.setVisibility(View.VISIBLE);
//            viewPager.setCurrentItem(current_position);
//        }
        viewPager.setCurrentItem(current_position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                currentText.setText(String.format(getString(R.string.reading_current), position, listStories.size()));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
