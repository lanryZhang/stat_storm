package com.ifeng.hbase;

import com.ifeng.core.data.HBaseDataLoader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhanglr on 2016/10/12.
 */
public class HBaseClient implements IHBaseClient {
    private Logger logger = Logger.getLogger(HBaseClient.class);
    private Configuration configuration;
    private static Connection connection = null;
    private static Admin admin = null;
    private Table table;
    private Object condition = new Object();
    public HBaseClient(){
        init();
    }

    private void init() {
        try {
            if (configuration == null) {
                synchronized (condition) {
                    if (configuration == null) {
                        configuration = HBaseConfiguration.create();
                    }
                }
            }

            if (connection == null) {
                synchronized (condition) {
                    if (connection == null) {
                        connection = ConnectionFactory.createConnection(configuration);
                    }
                }
            }

        } catch (Exception err) {
            logger.error(err);
        }
    }

    @Override
    public void changeTable(String namespace,String tableName) throws IOException {
        TableName tn = (namespace == null || namespace.equals("")) ? TableName.valueOf(tableName):TableName.valueOf(namespace + ":" + tableName);
        table = connection.getTable(tn);
    }

    @Override
    public <T extends HBaseCodec> void insert(T t) throws IOException {
        Put put = t.encode();
        try {
            table.put(put);

        } catch (IOException er) {
            logger.error(er);
            throw er;
        }finally {
            closeTable();
        }
    }


    @Override
    public <T extends HBaseCodec> void insertMany(List<T> list) throws IOException {
        try {
            List<Put> puts = new ArrayList<>();

            if (list != null) {
                for (T t : list) {
                    puts.add(t.encode());
                }
                table.put(puts);
            }
        } catch (IOException er) {
            logger.error(er);
            throw er;
        }finally {
            closeTable();
        }
    }


    @Override
    public <T extends HBaseCodec> List<T> getList(HBaseSelect select, Class<T> clazz) throws Exception {
        List<T> results = new ArrayList<>();
        try {
            Scan scan = select.createScan();
            ResultScanner result = table.getScanner(scan);

            for (Iterator<Result> it = result.iterator(); it.hasNext(); ) {
                Result rs = it.next();
                String rowKey = new String(rs.getRow());
                List<Cell> cells = rs.listCells();
                HBaseDataLoader loader = new HBaseDataLoader(cells, rowKey);
                T t = clazz.newInstance();
                t.decode(loader);
                results.add(t);
            }
        } catch (IOException er) {
            logger.error(er);
            throw er;
        } catch (Exception er) {
            logger.error(er);
            throw er;
        }finally {
            closeTable();
        }
        return results;
    }

    @Override
    public <T extends HBaseCodec> T getOne(HBaseSelect select, Class<T> clazz) throws Exception {
        T t = null;
        try {
            t = clazz.newInstance();
            Get get = select.createGet();
            Result res = table.get(get);
            String rowKey = new String(res.getRow());
            t.decode(new HBaseDataLoader(res.listCells(), rowKey));

        } catch (IOException er) {
            logger.error(er);
            throw er;
        } catch (Exception er) {
            throw er;
        }finally {
            closeTable();
        }
        return t;
    }

    @Override
    public void createTable(String tableName, String namespaces, String[] families) throws IOException {
        try {
            admin = connection.getAdmin();
            TableName tn = TableName.valueOf(namespaces + ":" + tableName);
            if (admin.getNamespaceDescriptor(namespaces) == null) {
                admin.createNamespace(NamespaceDescriptor.create(namespaces).build());
            }

            if (admin.tableExists(tn)){
                return;
            }

            HTableDescriptor tableDesc = new HTableDescriptor(tn);
            tableDesc.setDurability(Durability.SKIP_WAL);
            for (String family : families) {
                HColumnDescriptor columnDescriptor = new HColumnDescriptor(family);
                tableDesc.addFamily(columnDescriptor);
            }
            admin.createTable(tableDesc);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            closeAdmin();
        }
    }

    @Override
    public void dropTable(String namespace, String tableName) throws Exception {
        try {
            admin = connection.getAdmin();
            String tbName = namespace+":"+tableName;
            admin.disableTable(TableName.valueOf(tbName));
            admin.deleteTable(TableName.valueOf(tbName));
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            closeAdmin();
        }
    }


    @Override
    public void dropTable(String tableName) throws Exception {
        try {
            admin = connection.getAdmin();
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            closeAdmin();
        }
    }


    @Override
    public boolean existsTable(String tableName) throws Exception {
        try {
            admin = connection.getAdmin();
            return admin.tableExists(TableName.valueOf(tableName));
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            closeAdmin();
        }
    }
    @Override
    public void createNamespace(String namespace) throws Exception {
        try {
            admin = connection.getAdmin();
            NamespaceDescriptor descriptor = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(descriptor);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            closeAdmin();
        }
    }

    @Override
    public void deleteNamespace(String namespace) throws Exception {
        try {
            admin = connection.getAdmin();
            admin.deleteNamespace(namespace);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            closeAdmin();
        }
    }

    @Override
    public <T extends HBaseCodec> Boolean incr(T t) throws Exception {
        Increment increment= t.incr();
        try {
            table.increment(increment);
            return true;
        } catch (IOException er) {
            logger.error(er);
            return false;
        }finally {
            closeTable();
        }
    }

    @Override
    public Long incr(String row,String family, String qualifier, Long value) throws Exception {
        try {
            return table.incrementColumnValue(row.getBytes(),family.getBytes(),qualifier.getBytes(),value);
        } catch (IOException er) {
            logger.error(er);
            return -1L;
        }finally {
            closeTable();
        }
    }

    @Override
    public <T extends HBaseCodec> Boolean incr(List<T> list) throws Exception {
        for (T t : list){
            if (!incr(t)){
                return false;
            }
        }
        return true;
    }

    private void closeTable(){
        if (table != null){
            try {
                table.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    private void closeAdmin(){
        if (admin != null){
            try {
                admin.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        if (configuration != null) {
            configuration.clear();
        }
    }
}
