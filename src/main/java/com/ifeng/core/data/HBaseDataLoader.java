package com.ifeng.core.data;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhanglr on 2016/10/9.
 */
public class HBaseDataLoader extends AbsDataLoader{
    private List<Cell> cells;
    private HashMap<String,HashMap<String,String>> contentMap = new HashMap<>();
    private String rowKey;

    public HBaseDataLoader(List<Cell> cells) throws Exception {
        this.cells = cells;
        init();
    }
    public HBaseDataLoader(List<Cell> cells,String rowkey) throws Exception {
        this.cells = cells;
        this.rowKey = rowkey;
        init();
    }
    private void init()  throws Exception {
        for (Cell cell : cells) {
            String qualifier = new String(CellUtil.cloneQualifier(cell), "UTF-8");
            String value = new String(CellUtil.cloneValue(cell), "UTF-8");
            String family = new String(CellUtil.cloneFamily(cell),"UTF-8");

            HashMap<String,String> innerMap = contentMap.get(family);
            if (innerMap == null){
                innerMap = new HashMap<>();
                contentMap.put(family,innerMap);
            }

            innerMap.put(qualifier,value);
        }
    }

    @Override
    public String getString(String family,String key) {
        if (contentMap.get(family) != null){
            return contentMap.get(family).get(key);
        }
        return null;
    }

    @Override
    public String getRowKey() {
        return rowKey;
    }
}
