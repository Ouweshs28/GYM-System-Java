import java.io.Serializable;

public class Booking implements Serializable{
    private String bookingID;
    private String staffID;
    private String trainerID;
    private String clientname;
    private char gender;
    private String focus;
    private String date;
    private String time;
    private double duration;

    
    public Booking(String bid,String sid,String cid,String tid,String cname,char gender,String focus,String date,String time,double duration){
        this.bookingID=bid;
        this.staffID=sid;
        this.trainerID=tid;
        this.clientname=cname;
        this.gender=gender;
        this.focus=focus;
        this.date=date;
        this.time=time;
        this.duration=duration;

    }

    public Booking(){

    }
    public String getbookingID(){
        return bookingID;
    }

    public void setbookingID(String bid){
        this.bookingID=bid;
    }
    public String getstaffID(){
        return staffID;
    }

    public void setstaffID(String sid){
        this.staffID=sid;
    }
    
    public String gettrainerID(){
        return trainerID;
    }
    public void settrainerID(String tid){
        this.trainerID=tid;
    }

    public String getclientname(){
        return clientname;
    }

    public void setclientname(String cname){
        this.clientname=cname;
    }

    public char getgender(){
        return gender;
    }

    public void setgender(char g){
        this.gender=g;
    }

    public String getfocus(){
        return focus;
    }

    public void setfocus(String focus){
        this.focus=focus;
    }

    public String getdate(){
        return date;
    }

    public void setdate(String date){
        this.date=date;
    }

    public String gettime(){
        return time;
    }

    public double getduration(){
        return duration;
    }

    public void setduration(double d){
        this.duration=d;
    }

    public void printBooking(){
        System.out.println(getbookingID() + '\t'
        +getstaffID()+ '\t'
        +gettrainerID()+ '\t'
        +getclientname()+ '\t'
        +getgender()+ '\t'
        +getfocus()+ '\t'
        +getdate()+ '\t'
        +gettime()+ '\t'+getduration());
    }



}