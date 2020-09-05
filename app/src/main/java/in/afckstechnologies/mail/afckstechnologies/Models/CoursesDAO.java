package in.afckstechnologies.mail.afckstechnologies.Models;

/**
 * Created by admin on 2/20/2017.
 */

public class CoursesDAO {

    String id = "";
    String frequency = "";
    String course_type_id = "";
    String course_code = "";
    String course_name = "";
    String time_duration = "";
    String prerequisite = "";
    String recommonded = "";
    String status = "";
    String fees = "";
    String image_name = "";
    String syllabuspath = "";
    String you_tube_link = "";
    String isselected="";
    private boolean isSelected;
    public CoursesDAO() {

    }

    public CoursesDAO(String id, String frequency, String course_type_id, String course_code, String course_name, String time_duration, String prerequisite, String recommonded, String status, String fees, String image_name, String syllabuspath, String you_tube_link, String isselected, boolean isSelected) {
        this.id = id;
        this.frequency = frequency;
        this.course_type_id = course_type_id;
        this.course_code = course_code;
        this.course_name = course_name;
        this.time_duration = time_duration;
        this.prerequisite = prerequisite;
        this.recommonded = recommonded;
        this.status = status;
        this.fees = fees;
        this.image_name = image_name;
        this.syllabuspath = syllabuspath;
        this.you_tube_link = you_tube_link;
        this.isselected = isselected;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCourse_type_id() {
        return course_type_id;
    }

    public void setCourse_type_id(String course_type_id) {
        this.course_type_id = course_type_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTime_duration() {
        return time_duration;
    }

    public void setTime_duration(String time_duration) {
        this.time_duration = time_duration;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public String getRecommonded() {
        return recommonded;
    }

    public void setRecommonded(String recommonded) {
        this.recommonded = recommonded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getSyllabuspath() {
        return syllabuspath;
    }

    public void setSyllabuspath(String syllabuspath) {
        this.syllabuspath = syllabuspath;
    }

    public String getYou_tube_link() {
        return you_tube_link;
    }

    public void setYou_tube_link(String you_tube_link) {
        this.you_tube_link = you_tube_link;
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
