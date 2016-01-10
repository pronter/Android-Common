package com.example.su.zuhe;

/**
 * Created by su on 2016/1/3.
 */
public class Contact {
    private String _name;
    private String _time;

    public Contact() {
        _name = "";
        _time = "";
    }

    public void setName(String name) {
        _name = name;
    }

    public void setTime(String time) {
        _time = time;
    }

    public String getName() {
        return _name;
    }

    public String getTime() {
        return _time;
    }
}

