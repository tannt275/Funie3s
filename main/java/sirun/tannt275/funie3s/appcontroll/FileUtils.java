package sirun.tannt275.funie3s.appcontroll;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import sirun.tannt275.funie3s.R;

/**
 * Created by TanNT on 12/2/2015.
 */
public class FileUtils {

    public static String TAG = FileUtils.class.getSimpleName();
    public static String DATA_DIRECTORY = "app_data";
    public static String DATA_NAME = "funny_stories";

    public static String getDataBasePath(Context context){
        return context.getExternalFilesDir(DATA_DIRECTORY).getAbsolutePath() + "/" + DATA_NAME;
    }

    public static boolean saveFileDataBase(Context context, SaveDataSuccess callBack){
        InputStream is = context.getResources().openRawResource(R.raw.funny_stories);
        File directory = new File(String.valueOf(context.getExternalFilesDir(DATA_DIRECTORY)));
        if (! directory.mkdir()) Log.e(TAG, "directory exits");
        File file = new File(directory, DATA_NAME);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte [] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer)) > 0){
                fos.write(buffer, 0, read);
            }
            is.close();
            fos.close();
            Log.e(TAG, "save success.........");
            SharePrefApp.putBoolean(ConfigApp.IS_FIRST_USE, false);
            callBack.onSaveDataSuccess();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callBack.onSaceDataFail();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            callBack.onSaceDataFail();
            return false;
        }
    }

    public interface SaveDataSuccess{
        public void onSaveDataSuccess();
        public void onSaceDataFail();
    }
    public interface AddColumnUnsign {
        public void addColumnOk();
    }
}
