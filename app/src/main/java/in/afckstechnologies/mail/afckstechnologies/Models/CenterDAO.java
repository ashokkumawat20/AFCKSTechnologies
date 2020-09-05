package in.afckstechnologies.mail.afckstechnologies.Models;

/**
 * Created by admin on 2/18/2017.
 */

public class CenterDAO {
    String id = "";
    String branch_name = "";
    String address = "";
    String start_latitude = "";
    String start_longitude = "";
    String isselected="";
    private boolean isSelected;

    public CenterDAO() {
    }

    public CenterDAO(String id, String branch_name, String address, String start_latitude, String start_longitude, String isselected, boolean isSelected) {
        this.id = id;
        this.branch_name = branch_name;
        this.address = address;
        this.start_latitude = start_latitude;
        this.start_longitude = start_longitude;
        this.isselected = isselected;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    public String getIsselected() {
        return isselected;
    }

    public void setIsselected(String isselected) {
        this.isselected = isselected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
