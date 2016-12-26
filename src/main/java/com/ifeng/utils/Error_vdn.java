package com.ifeng.utils;

/**
 * Created by duanyb on 2016/10/20.
 */
public enum Error_vdn {

    firstFrame("firstFrame", "303000"),
    requestSum("requestSum", "208000"),
    pausedOne("pausedOne", "304001"),
    pausedTwo("pausedTwo", "304002"),
    pausedThree("pausedThree", "304003"),
    pausedMore("pausedMore", "304004"),
    flowFault("flowFault", "301020"),
    seekFault("seekFault", "301010"),
    imperfectFault("imperfectFault", "301030"),
    epgFail("epgFail", "110000"),
    otherflowFault("otherflowFault", "301040"),
    ipPortTimeout("ipPortTimeout", "601000"),
    ipsPortFail("ipsPortFail", "602000"),
    AllSum("AllSum", "100000");

    private String code;
    private String name;

    Error_vdn(String name, String errorcode) {
        this.code = errorcode;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        System.out.println(Error_vdn.getName("208000"));
    }

    // 普通方法
    public static String getName(String index) {
        for (Error_vdn c : Error_vdn.values()) {
            if (c.getCode() == index) {
                return c.name;
            }
        }
        return null;
    }
}
