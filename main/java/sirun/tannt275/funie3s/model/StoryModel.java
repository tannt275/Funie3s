package sirun.tannt275.funie3s.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TanNT on 12/1/2015.
 */
public class StoryModel extends BaseModel implements Parcelable{

    private String name;
    private String content;
    private boolean read;
    private int _id;
    private int _idCategory;

    public StoryModel(){

    }

    protected StoryModel(Parcel in) {
        name = in.readString();
        content = in.readString();
        read = in.readByte() != 0;
        _id = in.readInt();
        _idCategory = in.readInt();
    }

    public static final Creator<StoryModel> CREATOR = new Creator<StoryModel>() {
        @Override
        public StoryModel createFromParcel(Parcel in) {
            return new StoryModel(in);
        }

        @Override
        public StoryModel[] newArray(int size) {
            return new StoryModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_idCategory() {
        return _idCategory;
    }

    public void set_idCategory(int _idCategory) {
        this._idCategory = _idCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(content);
        dest.writeByte((byte) (read ? 1 : 0));
        dest.writeInt(_id);
        dest.writeInt(_idCategory);
    }
}
