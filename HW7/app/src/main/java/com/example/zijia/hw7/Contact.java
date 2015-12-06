package com.example.zijia.hw7;
//自定义联系人类Contact，包括学号、姓名、手机等信息及其访问方法
public class Contact {
    private String _no;
    private String _name;
    private String _pnumber;
    public Contact() {
        _no = "";
        _name = "";
        _pnumber = "";
    }
    public void setNo(String no) {
        _no = no;
    }
    public void setName(String name) {
        _name = name;
    }
    public void setPnumber(String pnumber) {
        _pnumber = pnumber;
    }
    public String getNo() {
        return _no;
    }
    public String getName() {
        return _name;
    }
    public String getPnumber() {
        return _pnumber;
    }
}
