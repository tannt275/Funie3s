package sirun.tannt275.funie3s;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.appcontroll.FileUtils;
import sirun.tannt275.funie3s.appcontroll.SharePrefApp;
import sirun.tannt275.funie3s.database.DataBaseHandler;

/**
 * Created by TanNT on 12/2/2015.
 */
public class SplashActivity extends AppCompatActivity {
    public static String TAG = SplashActivity.class.getSimpleName();

    private int TIMEOUT = 1 * 1000;
    private DataBaseHandler dataBaseHandler;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        imageView = (ImageView) findViewById(R.id.imageAdjust);

        if (SharePrefApp.getBoolean(ConfigApp.IS_FIRST_USE, true)){
            FileUtils.saveFileDataBase(SplashActivity.this, new FileUtils.SaveDataSuccess() {
                @Override
                public void onSaveDataSuccess() {
//                            onAddDataUnsignName();
                    jumpToMain();
                }

                @Override
                public void onSaceDataFail() {
                    failWhenSaveData();
                }
            });
        } else {
            jumpToMain();
        }
    }

    private void failWhenSaveData() {

    }

    private void onAddDataUnsignName(){
        dataBaseHandler = new DataBaseHandler(this);

        FileUtils.AddColumnUnsign callback = new FileUtils.AddColumnUnsign() {
            @Override
            public void addColumnOk() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jumpToMain();
                    }
                });
            }
        };
        dataBaseHandler.addUnSignForSearch(callback);
    }
    private void jumpToMain() {
       /* if (progressDialog != null)
            progressDialog.dismiss();*/
        imageView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toMain = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(toMain);
                finish();
            }
        }, TIMEOUT);
    }
}
