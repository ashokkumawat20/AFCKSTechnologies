package in.afckstechnologies.mail.afckstechnologies.Models;

/**
 * Created by admin on 3/7/2017.
 */

public class BatchesDAO {
    String id = "";
    String course_id = "";
    String branch_id = "";
    String batch_code = "";
    String start_date = "";
    String timings = "";
    String notes = "";
    String frequency = "";
    String fees = "";
    String duration = "";
    String course_name="";
    String branch_name="";
    String batchtype="";

    public BatchesDAO() {

    }

    public BatchesDAO(String id, String course_id, String branch_id, String batch_code, String start_date, String timings, String notes, String frequency, String fees, String duration, String course_name, String branch_name, String batchtype) {
        this.id = id;
        this.course_id = course_id;
        this.branch_id = branch_id;
        this.batch_code = batch_code;
        this.start_date = start_date;
        this.timings = timings;
        this.notes = notes;
        this.frequency = frequency;
        this.fees = fees;
        this.duration = duration;
        this.course_name = course_name;
        this.branch_name = branch_name;
        this.batchtype = batchtype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String batch_code) {
        this.batch_code = batch_code;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBatchtype() {
        return batchtype;
    }

    public void setBatchtype(String batchtype) {
        this.batchtype = batchtype;
    }

    @Override
    public String toString() {
        return batch_code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BatchesDAO) {
            BatchesDAO c = (BatchesDAO) obj;
            if (c.getBatch_code().equals(batch_code) && c.getId() == id) return true;
        }

        return false;
    }
}
