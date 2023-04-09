package com.example.caderno;

public class User {
    public String fullname,emailid,year,branch,dob;

    public User() {

    }
       public User(String name, String em,String y, String b,String date){
        this.fullname=name;
        this.emailid=em;
        this.year=y;
        this.branch=b;
        this.dob=date;

    }
}
