package com.example.mlbastan;

import java.util.ArrayList;

public class GorevModel {

    private String gorev;
    private String gorevnum;

    public GorevModel(){
    }

    public GorevModel(String gorev, String gorevnum) {
        this.gorev = gorev;
        this.gorevnum = gorevnum;
    }

    public String getgorev() {
        return gorev;
    }

    public void setgorev(String gorevnum) {
        this.gorevnum = gorevnum;
    }

    public String getgorevnum() { return gorevnum; }

    public void setgorevnum(String gorevnum) { this.gorevnum = gorevnum; }


    public static ArrayList<GorevModel> getData() {
        ArrayList<GorevModel> gorevList = new ArrayList<GorevModel>();
        ArrayList<String> gorevler = new ArrayList<>(ProfileActivitiy.gorevler);

        for (int i = 0; i < gorevler.size(); i++) {
            String x = String.valueOf(i+1);
            GorevModel temp = new GorevModel(gorevler.get(i),x);
            //      temp.setgorev(gorevler.get(i));
            //     temp.setgorevnum(x);
            gorevList.add(temp);

        }

        return gorevList;


    }

}


