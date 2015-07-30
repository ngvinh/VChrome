package vn.viettel.browser.models;

/**
 * Created by vinh on 7/28/15.
 */
public class GesturesAction {
    private int type;
    private String data;

    public GesturesAction(int type, String data){
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
