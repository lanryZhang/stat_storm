package stat_storm;

import com.ifeng.constant.CaculateStatTime;
import com.ifeng.constant.FieldsCombination;
import com.ifeng.core.data.ILoader;
import com.ifeng.entities.PageStatCounter;
import com.ifeng.hbase.HBaseClient;
import com.ifeng.hbase.IHBaseClient;
import com.ifeng.mongo.MongoCli;
import com.ifeng.mongo.MongoCodec;
import com.ifeng.mongo.MongoFactory;
import com.ifeng.mongo.MongoSelect;
import com.ifeng.redis.RedisClient;
import com.ifeng.redis.RedisFactory;
import com.mongodb.MongoClientOptions;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }



    public void testHH(){
        System.out.println(CaculateStatTime.caculte(506,5,4));
    }

    public void testRedisDb(){

        MongoCli cli = MongoFactory.createPgcMongoClient();
        RedisClient client = RedisFactory.newInstance();
        try {

            cli.changeDb("wemedia");
            cli.getCollection("video");
            long totalCount = cli.count();

            long pageCount = totalCount / 3000 + 1;
            MongoSelect select = new MongoSelect();

            for (int index = 1215; index <= pageCount;index++){
                select.page(index,3000);
                try {
                    List<EnInfo> list = cli.selectList(select, EnInfo.class);
                    if (null != list) {
                        for (EnInfo en : list) {
                            try {
                                client.set(en.getGuid(), en.getMediaID());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                cli.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void testProxy(){

        Map<String,PageStatCounter> map = new ConcurrentHashMap<>();
        map.put("1",new PageStatCounter());

        System.err.println(map.get("1").getPv());

        PageStatCounter en = map.get("1");
        en.setPv(2);

        System.err.println(map.get("1").getPv());



        try {
        IHBaseClient client = new HBaseClient();
        List<List<String>> res = new FieldsCombination().getHBaseCols();
        String namespace = "com_ifeng_pgcstats";
//        client.createNamespace(namespace);
        if (res != null) {
            for (List<String> list : res){
                String tableName = StringUtils.join(list,"_");

                try {
                    if (!client.existsTable(tableName)) {
                        client.createTable(tableName, namespace, new String[]{"pageStatInfo"});
                    }
//                    client.dropTable("com_ifeng_pgcstats",tableName);
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
//        client.deleteNamespace("com_ifeng_hbase");
    }catch (Exception e){
        e.printStackTrace();
    }
//        List<List<String>> res = new FieldsCombination().getHBaseCols();
//        if (res != null) {
//            for (List<String> list : res){
//                String tableName = StringUtils.join(list,"_");
//
//                try {
//                    client.createTable(tableName,"com_ifeng_hbase", new String[]{"pageStatInfo"});
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

//        TargetProxy proxy = new TargetProxy(new ProxyTarget());
//        ProxyTarget target = (ProxyTarget) proxy.newInstance();
//        target.init("asdasd");
    }
}
