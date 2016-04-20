package sirun.tannt275.funie3s.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sirun.tannt275.funie3s.MainActivity;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.appcontroll.FileUtils;
import sirun.tannt275.funie3s.model.CategoryModel;
import sirun.tannt275.funie3s.model.StoryModel;

/**
 * Created by TanNT on 12/2/2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    public static String TAG = DataBaseHandler.class.getSimpleName();

    private static int DATABASE_VERSION = 1;
    private String DATA_PATH = "";

    /*table in database*/
    private String TABLE_CATEGORIES = "categories";
    private String TABLE_STORIES = "stories";
    /*field in categories table*/
    private String CATEGORIES_ID_KEY = "id";
    private String CATEGORIES_NAME_KEY = "name";
    private String CATEGORIES_COUNT_KEY = "count";
    private String CATEGORIES_IMAGE_KEY = "image";
    /*field in stories table*/
    private String STORIES_ID_KEY = "id";
    private String STORIES_NAME_KEY = "name";
    private String STORIES_CONTENT_KEY = "content";

    private String STORIES_CAT_ID_KEY = "cat_id";
    private String STORIES_IS_READ_KEY = "is_read";
    private String STORIES_UNSIGN_NAME_KEY = "unsign";
    private String STORIES_UNSIGN_CONTENT_KEY = "unsign_content";

    public DataBaseHandler(Context context) {
        super(context, FileUtils.getDataBasePath(context), null, DATABASE_VERSION);
        this.DATA_PATH = FileUtils.getDataBasePath(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " +TABLE_CATEGORIES + "(" + CATEGORIES_ID_KEY + " INTEGER PRIMARY KEY NOT NULL, "
                + CATEGORIES_NAME_KEY + " TEXT NOT NULL, " + CATEGORIES_COUNT_KEY + " INTEGER NOT NULL, " + CATEGORIES_IMAGE_KEY + " TEXT)";
        String CREATE_STORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STORIES + "(" + STORIES_ID_KEY + " INTEGER PRIMARY KEY NOT NULL, " + STORIES_NAME_KEY + " TEXT NOT NULL, "
                + STORIES_CONTENT_KEY + " TEXT, " + STORIES_CAT_ID_KEY + " INTEGER NOT NULL, " + STORIES_UNSIGN_NAME_KEY + " TEXT, " + STORIES_UNSIGN_CONTENT_KEY + " TEXT, "
                + STORIES_IS_READ_KEY + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_STORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        onCreate(db);
    }

    public void addUnSignForSearch(FileUtils.AddColumnUnsign callBack){
        List<ContentValues> listContentValues = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_STORIES;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ContentValues contentValues = new ContentValues();
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_ID_KEY));
                String unsignName = ConfigApp.convertToStandar(cursor.getString(cursor.getColumnIndexOrThrow(STORIES_NAME_KEY)));
                String unsignContent = ConfigApp.convertToStandar(cursor.getString(cursor.getColumnIndexOrThrow(STORIES_CONTENT_KEY)));
                contentValues.put(STORIES_UNSIGN_NAME_KEY, unsignName);
                contentValues.put(STORIES_UNSIGN_CONTENT_KEY, unsignContent);
//                listContentValues.add(contentValues);
                database.update(TABLE_STORIES, contentValues, STORIES_ID_KEY + "=?", new String[] {String.valueOf(id)});
            } while (cursor.moveToNext());
        }

        database.close();
        cursor.close();
        Log.e(TAG, "add column is ok");
        if (callBack != null)
            callBack.addColumnOk();
    }

    public List<CategoryModel> getAllCategories(){
        List<CategoryModel> list = new ArrayList<>();
        String strQuerry = "SELECT * FROM " + TABLE_CATEGORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strQuerry, null);
        if (cursor.moveToFirst()){
            do {
                CategoryModel categoryModel = new CategoryModel();
                int _idCategory = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORIES_ID_KEY));
                categoryModel.setIdCategory(_idCategory);
                categoryModel.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(CATEGORIES_IMAGE_KEY)));
                categoryModel.setNameCategory(cursor.getString(cursor.getColumnIndexOrThrow(CATEGORIES_NAME_KEY)));
                int sizeCategory = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORIES_COUNT_KEY));
                categoryModel.setSizeCategory(sizeCategory);
                categoryModel.setNumberRead(sizeCategory - countNumberRead(_idCategory));
                list.add(categoryModel);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<StoryModel> getALlStories(){
        List<StoryModel> list = new ArrayList<>();
        String strQuery = "SELECT * FROM " + TABLE_STORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.moveToFirst()){
            do {
                StoryModel storyModel = new StoryModel();
                storyModel.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_ID_KEY)));
                storyModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(STORIES_NAME_KEY)));
                storyModel.setContent(cursor.getString(cursor.getColumnIndexOrThrow(STORIES_CONTENT_KEY)));
                storyModel.set_idCategory(cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_CAT_ID_KEY)));
                storyModel.setRead((cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_IS_READ_KEY)) == 1));
                list.add(storyModel);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * get all stories in database belong to Id category
     * @param _idCategory
     * @return
     */
    public List<StoryModel> getStoriesWithIdCategory(int _idCategory){
        SQLiteDatabase database = this.getReadableDatabase();
        List<StoryModel> list = new ArrayList<>();
        String strQuery = "SELECT * FROM " + TABLE_STORIES + " WHERE " + STORIES_CAT_ID_KEY + "=" +_idCategory;
        Cursor cursor = database.rawQuery(strQuery, null);
        if (cursor.moveToFirst()){
            do {
                StoryModel storyModel = new StoryModel();
                storyModel.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_ID_KEY)));
                storyModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(STORIES_NAME_KEY)));
                storyModel.setContent(cursor.getString(cursor.getColumnIndexOrThrow(STORIES_CONTENT_KEY)));
                storyModel.set_idCategory(cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_CAT_ID_KEY)));
                storyModel.setRead((cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_IS_READ_KEY)) == 1));
                list.add(storyModel);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public String getNameCategory(int _idCategory){
        String result = "";
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + CATEGORIES_ID_KEY + "=" + _idCategory;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                result = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORIES_NAME_KEY));
            } while (cursor.moveToNext());
        }

        return result;
    }

    public List<StoryModel> getStorySearchQuery(String search){

        List<StoryModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String queryName = "SELECT * FROM " + TABLE_STORIES + " WHERE "+ STORIES_UNSIGN_NAME_KEY + " LIKE " + "'%" + search + "%" + "'";
        String queryContent = "SELECT * FROM " + TABLE_STORIES + " WHERE "+ STORIES_UNSIGN_CONTENT_KEY + " LIKE " + "'%" + search + "%" + "'";
        Cursor cursor = database.rawQuery(queryName, null);
        if (cursor.moveToFirst()){
            do {
                StoryModel storyModel = new StoryModel();
                storyModel.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_ID_KEY)));
                storyModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(STORIES_NAME_KEY)));
                storyModel.setContent(cursor.getString(cursor.getColumnIndexOrThrow(STORIES_CONTENT_KEY)));
                storyModel.set_idCategory(cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_CAT_ID_KEY)));
                storyModel.setRead(cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_IS_READ_KEY)) == 1);
                list.add(storyModel);
            } while (cursor.moveToNext());
        }
        Cursor cursor1 = database.rawQuery(queryContent, null);
        return list;
    }

    public boolean updateReadData(int _idStory){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STORIES_IS_READ_KEY, 1);
        return database.update(TABLE_STORIES, contentValues, STORIES_ID_KEY + "=?", new String[]{String.valueOf(_idStory)}) > 0 ;
    }

    public List<StoryModel> generateRandomStories(){
        List<StoryModel> listStories = getALlStories();
        List<Integer> listIndex = MainActivity.listRandom;
        
        List<StoryModel> listRandom = new ArrayList<>();
        for (Integer i : listIndex){
            listRandom.add(listStories.get(i));
        }
        return listRandom;
    }

    /**
     * get number story read in database
     * @param _idCategory
     * @return
     */
    public int countNumberRead(int _idCategory){
        SQLiteDatabase database = this.getReadableDatabase();
        int count = 0;
        String strQuery = "SELECT * FROM " + TABLE_STORIES + " WHERE " + STORIES_CAT_ID_KEY + "=" + _idCategory;
        Cursor cursor = database.rawQuery(strQuery, null);
        if (cursor.moveToFirst()){
            do {
                int state = cursor.getInt(cursor.getColumnIndexOrThrow(STORIES_IS_READ_KEY));
                if (state == 1) count++;
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return count;
    }
}
