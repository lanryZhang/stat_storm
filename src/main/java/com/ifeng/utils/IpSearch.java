package com.ifeng.utils;


import com.ifeng.core.misc.Area;
import com.ifeng.core.misc.IpRange;
import com.ifeng.core.misc.IpServerException;
import com.ifeng.core.misc.IpV4Address;
import org.apache.commons.lang3.ObjectUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 重写 功能简单 加载速度和查询速度快点
 * Created by duanyb on 2016/10/19.
 */
public class IpSearch {
    private String separator = "|";
    private List<Range> rangeList = new ArrayList<Range>();

    public Range getRange(String ip) throws UnknownHostException {
        long ipv4 = ipv4(ip);
        int low = 0;
        int high = rangeList.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if ((ipv4 >= rangeList.get(mid).getStart()) && (ipv4 <= rangeList.get(mid).getEnd()))
                return rangeList.get(mid);
            else if (ipv4 < rangeList.get(mid).getStart())
                high = mid - 1;
            else low = mid + 1;
        }
        return null;
    }

    public void initIp() {
        BufferedReader br = null;
        try {
            InputStream inputStream = IpRange.class.getClassLoader().getResourceAsStream("ipArea.txt");
            InputStreamReader reader = new InputStreamReader(inputStream);
            br = new BufferedReader(reader);
            String line = br.readLine();
            while ((line != null) && (line.trim().length() > 0)) {
                StringTokenizer st = new StringTokenizer(line, separator);
                if (st.countTokens() >= 5) {
                    Long startIp = ipv4(st.nextToken());
                    Long endIp = ipv4(st.nextToken());
                    Area area = new Area();
                    area.setNetName(st.nextToken());
                    area.setProvince(st.nextToken());
                    area.setCity(st.nextToken());
                    Range range = new Range(startIp, endIp, area);
                    rangeList.add(range);
                }
                line = br.readLine();
            }
            rangeList.sort((Range h1, Range h2) -> {
                if (h1.getStart() > h2.getStart()) {
                    return 1;
                } else {
                    return -1;
                }
            });
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public long ipv4(String ip) throws UnknownHostException {
        byte[] value = InetAddress.getByName(ip).getAddress();
        return ((value[0] << 24) & 0xFF000000L)
                | ((value[1] << 16) & 0x00FF0000)
                | ((value[2] << 8) & 0x0000FF00)
                | ((value[3] & 0xFF));
    }

    public static class Range implements Serializable {

        private static final long serialVersionUID = -369519134774L;

        private long start;
        private long end;
        private Object param;

        public Object getParam() {
            return param;
        }

        public void setParam(Object param) {
            this.param = param;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }

        public Range(long start, long end, Object param) {
            this.start = start;
            this.end = end;
            this.param = param;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Range) {
                Range other = (Range) obj;
                return !ObjectUtils.notEqual(this.start, other.start)
                        && !ObjectUtils.notEqual(this.end, other.end)
                        && !ObjectUtils.notEqual(this.param, other.param);
            }
            return false;
        }


    }

    public static void main(String[] args) {

        long l = System.currentTimeMillis();
        IpSearch ipSearch = new IpSearch();
        ipSearch.initIp();

        try {
            long l2_ = System.currentTimeMillis();
            System.out.println(((Area)ipSearch.getRange("116.238.9.11").getParam()).toString());
            long l2 = System.currentTimeMillis();
            System.out.println((l2 - l) + "  " + (l2 - l2_));

            long l3 = System.currentTimeMillis();
            IpRange ipRange = new IpRange();
            ipRange.initRangeSet();
            long l3_ = System.currentTimeMillis();
            ipRange.getAreaByIp(new IpV4Address("116.238.9.11"));
            System.out.println(ipRange.getAreaByIp(new IpV4Address("116.238.9.11")).toString());
            long l4 = System.currentTimeMillis();
            System.out.println(l4 - l3+ "  " + (l4 - l3_));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IpServerException e) {
            e.printStackTrace();
        }

    }
}


