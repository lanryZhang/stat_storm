package com.ifeng.utils;

/**
 * Created by duanyb on 2016/10/20.
 */
public enum  Ptype_vdn {

    niushi("vNsPlayer_","VNSPlayer"),documentary("vDocPlayer_","VDocPlayer"),
    othershort("VZHPlayer","vFreePlayer","ZTLPlayer","vBlogPlayer","vSeobdPlayer","VPlayer"),
    extplay("ExtPlayer_","EXtPlayer_"),
    otherlong("vTvPlayer_"),
    live("vLivePlayer","LiveVPlayer","vZTLivePlayer"),
    vip("VipPlayer");
    private String[] contains;


     Ptype_vdn(String... contains){
        this.contains = contains;
    }

    public String[] getContains() {
        return contains;
    }

    public static void main(String[] args) {

        Ptype_vdn.documentary.getContains();


    }

}
