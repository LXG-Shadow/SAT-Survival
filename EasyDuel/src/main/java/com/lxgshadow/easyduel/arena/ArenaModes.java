package com.lxgshadow.easyduel.arena;

public enum  ArenaModes {
    Mode1(1,"决战到死"),
    Mode2(2,"一击定胜负");

    public String name;
    public int mode;
    private ArenaModes(int mode,String name){
        this.mode = mode;
        this.name = name;
    }

    public static String getName(int mode) {
        for (ArenaModes a:ArenaModes.values()){
            if (a.mode == mode){
                return a.name;
            }
        }
        return null;
    }
}
