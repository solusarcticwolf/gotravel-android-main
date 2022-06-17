package com.phptravelsnative.Models;

public class Travelhope_flightsdetails {


    private String code;

    private Datafligh data;


    private String status;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public Datafligh getData ()
    {
        return data;
    }

    public void setData (Datafligh data)
    {
        this.data = data;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", data = "+data+", status = "+status+"]";
    }
}
