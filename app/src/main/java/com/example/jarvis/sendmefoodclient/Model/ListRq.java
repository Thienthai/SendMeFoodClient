package com.example.jarvis.sendmefoodclient.Model;

import java.util.List;

public class ListRq {
    List<RqData> rqDat;

    public ListRq(List<RqData> rqDat) {
        this.rqDat = rqDat;
    }

    public List<RqData> getRqDat() {
        return rqDat;
    }

    public void setRqDat(List<RqData> rqDat) {
        this.rqDat = rqDat;
    }
}
