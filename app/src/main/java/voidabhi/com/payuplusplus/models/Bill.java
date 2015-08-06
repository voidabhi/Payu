package voidabhi.com.payuplusplus.models;

import android.location.Location;
import android.net.Uri;

import com.orm.SugarRecord;

import java.util.List;

/**
 * POJO for bill
 */
public class Bill extends SugarRecord<Bill> {

    public Bill(){

    }

    String title;
    Uri imageUri;
    String tags;
    String location;

    public Bill(Uri imageUri, String title) {
        super();
        this.imageUri = imageUri;this.title = title;}

    public Bill(String title, Uri imageUri, String tags, String location) {
        super();
        this.title = title;
        this.imageUri = imageUri;
        this.tags = tags;
        this.location = location;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
