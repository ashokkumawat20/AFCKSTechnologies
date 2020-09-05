package in.afckstechnologies.mail.afckstechnologies.Models;

/**
 * Created by admin on 4/4/2017.
 */

public class BankDeatilsDAO {

    String id="";
    String acc_holder_name="";
    String account_no="";
    String bank_name="";
    String branch_name="";
    String ifsc_code="";
    String account_type="";

    public BankDeatilsDAO()
    {

    }
    public BankDeatilsDAO(String id, String acc_holder_name, String account_no, String bank_name, String branch_name, String ifsc_code, String account_type) {
        this.id = id;
        this.acc_holder_name = acc_holder_name;
        this.account_no = account_no;
        this.bank_name = bank_name;
        this.branch_name = branch_name;
        this.ifsc_code = ifsc_code;
        this.account_type = account_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcc_holder_name() {
        return acc_holder_name;
    }

    public void setAcc_holder_name(String acc_holder_name) {
        this.acc_holder_name = acc_holder_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }
}
