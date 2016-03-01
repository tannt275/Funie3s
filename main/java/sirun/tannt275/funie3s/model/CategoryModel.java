package sirun.tannt275.funie3s.model;

/**
 * Created by TanNT on 12/1/2015.
 */
public class CategoryModel extends BaseModel {

    private int idCategory;
    private String url;
    private String nameCategory;
    private int sizeCategory;
    private int numberRead;

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public int getSizeCategory() {
        return sizeCategory;
    }

    public void setSizeCategory(int sizeCategory) {
        this.sizeCategory = sizeCategory;
    }

    public int getNumberRead() {
        return numberRead;
    }

    public void setNumberRead(int numberRead) {
        this.numberRead = numberRead;
    }
}
