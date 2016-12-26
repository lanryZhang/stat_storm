package com.ifeng.hbase;

import com.ifeng.core.annotations.FieldFamily;
import com.ifeng.core.annotations.HBaseIncrement;
import com.ifeng.core.annotations.TypeFamily;
import com.ifeng.core.data.*;
import com.ifeng.entities.AbsHBaseRowKey;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;

import java.lang.reflect.Field;

/**
 * Created by zhanglr on 2016/2/24.
 */

/**
 * 实体序列化成Bson
 */
public abstract class HBaseCodec extends AbsHBaseRowKey implements IEncode,IDecode {
    /**
     * 将Bson转换成实体类
     * @param loader
     */
    @Override
    public abstract void decode(ILoader loader);

    /**
     * get Increament 对象
     */
    public Increment incr(){return null;}

    public Increment incr(String colName,long value){return null;}
    /**
     * 将实体转换成Put
     * @param
     * @return
     */
    @Override
    public Put encode(){
        Put put = new Put(this.getRowKey().getBytes());
        encode(this,put,null);
        return put;
    }


    private <T extends AbsHBaseRowKey> void encode(T t, Put put,String family){
        try {
            TypeFamily typeFamily = t.getClass().getAnnotation(TypeFamily.class);
            if (typeFamily != null){
                family = typeFamily.value();
            }
            Field[] fields = ClassDescriptorContainer.getFields(t.getClass());
            if (fields != null){
                for (Field field : fields) {

                    if (HBaseCodec.class.isAssignableFrom(field.getType())
                            && HBaseCodec.class.isAssignableFrom(field.getType())){
                        encode((T)field.get(t),put,family);
                    }
                    else {
                        byte[] familyBytes;
                        if (family != null){
                            familyBytes = family.getBytes();
                        }else{
                            FieldFamily fieldFamily = field.getAnnotation(FieldFamily.class);
                            if (fieldFamily != null){
                                familyBytes = fieldFamily.value().getBytes();
                            }else{
                                familyBytes = field.getType().getName().getBytes();
                            }
                        }
                        HBaseIncrement hBaseIncrement = field.getAnnotation(HBaseIncrement.class);
                        if (hBaseIncrement == null || !hBaseIncrement.value()){
                            put.addColumn(familyBytes,field.getName().getBytes(),
                                    String.valueOf(field.get(t)).getBytes());
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

    }
}
