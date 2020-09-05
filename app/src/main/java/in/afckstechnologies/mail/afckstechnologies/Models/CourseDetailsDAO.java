package in.afckstechnologies.mail.afckstechnologies.Models;

public class CourseDetailsDAO {
    String id = "";
    String course_name = "";
    String course_type = "";
    String course_code = "";

    public CourseDetailsDAO() {

    }

    public CourseDetailsDAO(String id, String course_name, String course_type, String course_code) {
        this.id = id;
        this.course_name = course_name;
        this.course_type = course_type;
        this.course_code = course_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    @Override
    public String toString() {
        return course_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CourseDetailsDAO) {
            CourseDetailsDAO c = (CourseDetailsDAO) obj;
            if (c.getCourse_name().equals(course_name) && c.getId() == id) return true;
        }

        return false;
    }
}
