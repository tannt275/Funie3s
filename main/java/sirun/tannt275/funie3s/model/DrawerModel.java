package sirun.tannt275.funie3s.model;

/**
 * Created by tannt_000 on 11/30/2015.
 */
public class DrawerModel  extends BaseModel{
    private int _id;
    private String _name;
    private String _url;
    private int _numberSeen;
    private int _numberNew;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public int get_numberSeen() {
        return _numberSeen;
    }

    public void set_numberSeen(int _numberSeen) {
        this._numberSeen = _numberSeen;
    }

    public int get_numberNew() {
        return _numberNew;
    }

    public void set_numberNew(int _numberNew) {
        this._numberNew = _numberNew;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
