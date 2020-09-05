package in.afckstechnologies.mail.afckstechnologies.Models;

/**
 * Created by admin on 2/22/2017.
 */

public class NewBatchesDAO {
    String id = "";
    String batch_code = "";
    String start_date = "";
    String start_time = "";
    String end_time="";
    String course_id="";
    String batchtype="";
    String first_name="";
    String last_name="";
    String course_code="";
    String course_name="";
    String branch_name="";
    String  isbooked="";
    String notes="";
    String frequency="";
    String fees="";
    String duration="";
    String wa_invite_link="";
    String isstatus="";
    public NewBatchesDAO()
    {}

    public NewBatchesDAO(String id, String batch_code, String start_date, String start_time, String end_time, String course_id, String batchtype, String first_name, String last_name, String course_code, String course_name, String branch_name, String isbooked, String notes, String frequency, String fees, String duration, String isstatus) {
        this.id = id;
        this.batch_code = batch_code;
        this.start_date = start_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.course_id = course_id;
        this.batchtype = batchtype;
        this.first_name = first_name;
        this.last_name = last_name;
        this.course_code = course_code;
        this.course_name = course_name;
        this.branch_name = branch_name;
        this.isbooked = isbooked;
        this.notes = notes;
        this.frequency = frequency;
        this.fees = fees;
        this.duration = duration;
        this.isstatus = isstatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getBatchtype() {
        return batchtype;
    }

    public void setBatchtype(String batchtype) {
        this.batchtype = batchtype;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getIsbooked() {
        return isbooked;
    }

    public void setIsbooked(String isbooked) {
        this.isbooked = isbooked;
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

    public String getIsstatus() {
        return isstatus;
    }

    public void setIsstatus(String isstatus) {
        this.isstatus = isstatus;
    }

    public String getWa_invite_link() {
        return wa_invite_link;
    }

    public void setWa_invite_link(String wa_invite_link) {
        this.wa_invite_link = wa_invite_link;
    }
}
