package com.ifeng.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglr on 2016/3/29.
 */
public class FieldsCombination {
    private List<String> arr;
    private List<List<String>> colList = new ArrayList<>();

    public List<List<String>> getIpsCols() {
        colList = new ArrayList<>();
        arr = new ArrayList();
        arr.add("clientType");
        arr.add("nodeIp");
        arr.add("requestType");
        arr.add("hostIp");
        caculate(0, new ArrayList<>(), arr);
        List<String> time = new ArrayList<String>();
        time.add("createDate");
        time.add("hm");
        colList.add(time);
        return colList;
    }

    public List<List<String>> getVdnPcCols() {
        arr = new ArrayList();
        arr.add("statistype");
        arr.add("videotype");
        arr.add("chentype");
        arr.add("frm");
        arr.add("netName");
        arr.add("city");
        caculate(0, new ArrayList<String>(), arr);

        List<List<String>> cols = new ArrayList<List<String>>();
        for (int i = 0; i < colList.size(); i++) {
            if (colList.get(i).contains("statistype")) {
                cols.add(colList.get(i));
            }
        }
        return cols;
    }

    public List<List<String>> getPeerLogCols() {
        colList = new ArrayList<>();
        arr = new ArrayList();
        arr.add("netName");
        arr.add("groups");
        arr.add("requestType");
        caculate(0, new ArrayList<>(), arr);
        List<String> time = new ArrayList<>();
        time.add("dateTime");
        colList.add(time);
        return colList;
    }

    public List<List<String>> getHBaseCols() {
        colList = new ArrayList<>();
        arr = new ArrayList();
        arr.add("mediaId");
        arr.add("pgcId");
        arr.add("createDate");
        arr.add("createTime");
        colList.add(arr);

//        arr = new ArrayList();
//        arr.add("mediaId");
//        arr.add("pgcId");
//        arr.add("createDate");
//        colList.add(arr);
//
//        arr = new ArrayList();
//        arr.add("mediaId");
//        arr.add("pgcId");
//        colList.add(arr);

        arr = new ArrayList();
        arr.add("pgcId");
        arr.add("createDate");
        arr.add("createTime");
        colList.add(arr);

//        arr = new ArrayList();
//        arr.add("pgcId");
//        arr.add("createDate");
//        colList.add(arr);
//
//        arr = new ArrayList();
//        arr.add("pgcId");
//        colList.add(arr);

        return colList;
    }

    private void caculate(int i, List<String> str, List<String> cols) {
        if (i == cols.size()) {
            if (str.size() > 0) {
                colList.add(str);
            }
            return;
        }

        List<String> res = new ArrayList<>();
        res.addAll(str);
        res.add(cols.get(i));
        caculate(i + 1, res, cols);
        caculate(i + 1, str, cols);
    }

    public static void main(String[] args) {

        for (List<String> ki : new FieldsCombination().getVdnPcCols()) {
            for (String str : ki) {
                System.out.print(str + " ");
            }
            System.out.println();

        }

    }

    public List<List<String>> getCommectsCols() {
        colList = new ArrayList<>();
        arr = new ArrayList();
        arr.add("mediaId");
        arr.add("type");
        arr.add("createDate");
        arr.add("createTime");
        colList.add(arr);
        return colList;
    }

    public List<List<String>> getProfileCols() {
        colList = new ArrayList<>();
        arr = new ArrayList();
        arr.add("pgcId");
        arr.add("createDate");
        arr.add("createTime");
        colList.add(arr);
        return colList;
    }
}
