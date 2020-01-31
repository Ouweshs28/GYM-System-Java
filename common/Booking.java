package common;
import java.io.Serializable;

public class Booking implements Serializable{
    private String bookingID;
    private String trainerID;
    private String clientID;
    private String focus;
    private String date;
    private String startTime;
    private String duration;
    private String endTime;


    
    public Booking(String bid,String tid,String cid,String focus,String date,String st,String d,String ed){
        this.bookingID=bid;
        this.trainerID=tid;
        this.clientID=cid;
        this.focus=focus;
        this.date=date;
        this.startTime=st;
        this.duration=d;
        this.endTime=ed;

    }

    public Booking(){

    }
    public String getbookingID(){
        return bookingID;
    }

    public void setbookingID(String bid){
        this.bookingID=bid;
    }
    public String gettrainerID(){
        return trainerID;
    }

    public void settrainerID(String tid){
        this.trainerID=tid;
    }
    
    public String getclientID(){
        return clientID;
    }
    public void setclientID(String cid){
        this.clientID=cid;
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

    public String getstarttime(){
        return startTime;
    }

    public void setstattime(String st){
        this.startTime=st;
    }

    public String getduration(){
        return duration;
    }

    public void setduration(String d){
        this.duration=d;
    }

    public String getendtime(){
        return endTime;
    }

    public void setendtime(String et){
        this.endTime=et;
    }

    public void printBooking(){
        System.out.println(getbookingID() + '\t'
        +gettrainerID()+ '\t'
        +getclientID()+ '\t'
        +getfocus()+ '\t'
        +getdate()+ '\t'
        +getstarttime()+ '\t'
        +getduration()+ '\t'
        +getendtime());
    }


}