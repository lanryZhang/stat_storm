package com.ifeng.hbase;

import com.ifeng.core.query.Select;
import com.ifeng.core.query.SelectField;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;

import java.util.List;

/**
 * Created by zhanglr on 2016/10/12.
 */
public class HBaseSelect extends Select {
    private String startRow;
    private String stopRow;

    public HBaseSelect(){
        super(null, 0, Integer.MAX_VALUE);
    }
    public HBaseSelect(List<SelectField> fields, int pageIndex, int pageSize) {
        super(fields, pageIndex, pageSize);
    }

    public Scan createScan(){
        Scan scan = new Scan();
        if (null != startRow && !startRow.equals("")) {
            scan.setStartRow(startRow.getBytes());
        }
        if (null != stopRow && !stopRow.equals("")) {
            scan.setStopRow(stopRow.getBytes());
        }
        if (fields != null) {
            fields.forEach(r -> scan.addColumn(r.getFamily().getBytes(), r.getName().getBytes()));
        }
        return scan;
    }

    public Get createGet(){
        return new Get(startRow.getBytes());
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public void setStopRow(String stopRow) {
        this.stopRow = stopRow;
    }
}
