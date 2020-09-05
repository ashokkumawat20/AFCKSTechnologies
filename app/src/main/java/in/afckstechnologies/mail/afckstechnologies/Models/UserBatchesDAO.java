package in.afckstechnologies.mail.afckstechnologies.Models;

/**
 * Created by admin on 2/22/2017.
 */

public class UserBatchesDAO {
    String id = "";
    String course_name = "";

    public UserBatchesDAO() {
    }

    public UserBatchesDAO(String id, String course_name) {
        this.id = id;
        this.course_name = course_name;
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

    @Override
    public String toString() {
        return course_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserBatchesDAO) {
            UserBatchesDAO c = (UserBatchesDAO) obj;
            if (c.getCourse_name().equals(course_name) && c.getId() == id) return true;
        }

        return false;
    }
}
