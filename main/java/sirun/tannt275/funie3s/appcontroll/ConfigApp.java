package sirun.tannt275.funie3s.appcontroll;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import sirun.tannt275.funie3s.R;

/**
 * Created by TanNT on 11/30/2015.
 */
public class ConfigApp  {

    public static String IS_FIRST_USE = "IS_FIRST_USE";
    public static int STATE_RANDOM = 1;
    public static int STATE_CATEGORY = 2;
    public static int ID_CATEGORY_RANDOM = 9999;
    public static int FILTER_CODE_NONE = 9998;
    public static int FILTER_CODE_READ = 9997;
    public static int FILTER_CODE_UNREAD = 9996;

    public static String SEARCH_CATEGORY_MODE = "SEARCH_CATEGORY_MODE";
    public static String SEARCH_STORY_MODE = "SEARCH_STORY_MODE";

    public static String DATE_DATA = "DATE_DATA";
    public static String MAIN_SHOW_AD_COUNT_IN_DAY = "MAIN_SHOW_AD_COUNT_IN_DAY";
    public static String READING_SHOW_AD_COUNT_IN_DAY = "READING_SHOW_AD_COUNT_IN_DAY";

    public static final String VI_FIND_STRING = "áàảãạâấầẩẫậăắằẳẵặđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ";
    public static final String VI_REPLACE_STRING = "aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAAAAADEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYY";

    public static String READ_CURRENT_POSITION = "READ_CURRENT_POSITION";

    public static String DATA_SEARCH = "DATA_SEARCH";

    public static DisplayImageOptions OPTION_ICON = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .cacheInMemory(true).cacheOnDisk(true).build();

    public static List<Integer> generateRandomInList(int maxValue, int size){
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        while (list.size() < size){
            int i = random.nextInt(maxValue);
            if (!list.contains(i))
                list.add(i);
        }
        return list;
    }
    public static String convertToStandar(String inputString){
        String input = inputString.toLowerCase(Locale.getDefault());
        final String convert_Find_Text = VI_FIND_STRING;
        final String convert_Replace_Text = VI_REPLACE_STRING;
        char[] arrChar = convert_Find_Text.toCharArray();
        for (int i = 0; i < arrChar.length; i++) {
            char ch = arrChar[i];
            if (input.indexOf(ch) != -1) {
                input = input.replace(ch, convert_Replace_Text.charAt(i));
            }
        }
        return input;
    }

    public static String convertListToString(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s);
            stringBuilder.append("\t");
        }
        return (stringBuilder.toString());
    }

    public static void hideKeyBoard(Activity context){
        View view = context.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean checkToShowAd(String screen){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MMM dd yyyy");
        Date date = new Date();
        String currentDate = simpleDateFormat.format(date);
        String olderDate = SharePrefApp.getString(DATE_DATA, "");
        int show_ad_count = SharePrefApp.getInt(screen, 0);
        if (TextUtils.isEmpty(olderDate)){
            return true;
        } else {
            if (!TextUtils.equals(currentDate, olderDate))
                return true;
            else {
                if (show_ad_count == 999)
                    return false;
                else
                    return true;
            }
        }
    }
    public static void updateShowAd(String screen){
        Log.e("ConfigApp ", "method is: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MMM dd yyyy");
        Date date = new Date();
        SharePrefApp.putString(DATE_DATA, simpleDateFormat.format(date));
        int current_show = SharePrefApp.getInt(screen, 0);
        current_show ++;
        SharePrefApp.putInt(screen, current_show);
    }

    public static String convertHtmlToString(String htmlString){
        htmlString = htmlString.replaceAll("<p>","");
        htmlString = htmlString.replaceAll("</p>", "\n");
        return htmlString;
    }
}
