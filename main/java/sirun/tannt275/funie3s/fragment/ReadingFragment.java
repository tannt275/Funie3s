package sirun.tannt275.funie3s.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import sirun.tannt275.funie3s.MainActivity;
import sirun.tannt275.funie3s.R;
import sirun.tannt275.funie3s.appcontroll.ConfigApp;
import sirun.tannt275.funie3s.database.DataBaseHandler;
import sirun.tannt275.funie3s.model.StoryModel;

/**
 * Created by TanNT on 12/1/2015.
 */
public class ReadingFragment extends Fragment {

    public static String TAG = ReadingFragment.class.getSimpleName();

    private TextView nameStory;
    private TextView nameCategory;
    private TextView contentStory;
    private ImageView closeIcon;
    private ImageView shareIcon;

    private DataBaseHandler dataBaseHandler;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    public static String CONTENT_OBJECT = "CONTENT_OBJECT";

    public static ReadingFragment newInstance(StoryModel storyModel){
        ReadingFragment fragment = new ReadingFragment();
        Bundle  bundle = new Bundle();
        bundle.putParcelable(CONTENT_OBJECT, storyModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reading_fragment, container, false);
        nameStory = (TextView) rootView.findViewById(R.id.readingName);
        nameCategory = (TextView) rootView.findViewById(R.id.readingCategory);
        contentStory = (TextView) rootView.findViewById(R.id.readingContent);
        closeIcon = (ImageView) rootView.findViewById(R.id.closeIcon);
        shareIcon = (ImageView) rootView.findViewById(R.id.shareIcon);
        dataBaseHandler = new DataBaseHandler(getActivity());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());
        fillView();
        return rootView;
    }
    private void fillView() {
        Bundle bundle = getArguments();
        final StoryModel storyModel = bundle.getParcelable(CONTENT_OBJECT);
        nameCategory.setText(String.format(getString(R.string.reading_category), dataBaseHandler.getNameCategory(storyModel.get_idCategory())));
        nameStory.setText(String.format(getString(R.string.reading_name), storyModel.getName()));

        //update state read to database
        dataBaseHandler.updateReadData(storyModel.get_id());

        contentStory.setText(Html.fromHtml(storyModel.getContent()));
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeCurrentStory();
            }
        });
        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCurrentStory(storyModel);
            }
        });
    }

    private void shareCurrentStory(StoryModel storyModel) {
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentTitle(storyModel.getName())
                .setContentDescription(ConfigApp.convertHtmlToString(storyModel.getContent()))
                .setContentUrl(Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName()))
                .build();
        Log.e(TAG, "content: " + ConfigApp.convertHtmlToString(storyModel.getContent()));
        if (ShareDialog.canShow(ShareLinkContent.class)){
            shareDialog.show(shareLinkContent);
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Log.e(TAG, "onSuccess");
                }

                @Override
                public void onCancel() {
                    Log.e(TAG, "onCancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.e(TAG, "onError");
                }
            });
        }
    }

    private void closeCurrentStory() {
        Intent toMainActivity = new Intent(getActivity(), MainActivity.class);
        startActivity(toMainActivity);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
