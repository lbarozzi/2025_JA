package it.eforhum;

import jakarta.persistence.*;

@Entity
@Table(name = "phonesnumbers")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phoneid")
    private int phoneid;
    
    @Column(name = "userid",unique = false, nullable = false)
    private int userid;
    
    //This is my TYPO creating DB field "phonesNumber" instead of "phoneNumber"
    @Column(name = "phonesNumber")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;

    // Costruttori
    public PhoneNumber() {
        this(0,"");
    }
    
    public PhoneNumber(int userid, String phoneNumber) {
        this.userid = userid;
        this.phoneNumber = phoneNumber;
    }
    
    // Getter e setter
    public int getPhoneid() {
        return phoneid;
    }
    
    public void setPhoneid(int phoneid) {
        this.phoneid = phoneid;
    }
    
    public int getUserid() {
        return userid;
}
    
    public void setUserid(int userid) {
        this.userid = userid;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString() {
        return "PhoneNumber [phoneid=" + phoneid + ", userid=" + userid + ", phoneNumber=" + phoneNumber + "]";
    }
}