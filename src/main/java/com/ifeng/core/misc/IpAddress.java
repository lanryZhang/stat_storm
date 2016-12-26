package com.ifeng.core.misc;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 统一的IP地址抽象类
 * 在非实例化一个ip地址的场景，应该统一使用这个类去编写涉及到ip地址的程序.
 * 
 * @author jinmy
 */
public abstract class IpAddress implements Serializable, Comparable {
    /**
     * 得到该地址的下一个地址。例如本地址为192.168.0.1得到的地址为192.168.0.2
     * 255.255.255.255的next是0.0.0.0 (compareTo(a, a.getNext()) < 0的例外)
     */
    public abstract IpAddress getNext();

    /**
     * 得到该地址的前一个地址。例如本地址为192.168.0.1得到的地址为192.168.0.0
     * 0.0.0.0的pre是255.255.255.255 (compareTo(a, a.getPre()) > 0的例外)
     */
    public abstract IpAddress getPre();

    /**
     * 判断一个地址是否在一个子网中(包含广播和子网地址)
     * @param subnetMask 子网掩码
     * @param anyInSubnet 任意子网内的地址，包括网关；或者子网地址
     * @return true:在这个地址池中 false :不在地址池中
     */
    public boolean inSubnet(IpAddress subnetMask, IpAddress anyInSubnet) {
        return anyInSubnet.and(subnetMask).equals(this.and(subnetMask));
    }

    /**
     * 判断一个地址是否在一个地址池中
     * @param start 开始地址(含)
     * @param end 结束地址(含)
     * @return true 在 false 不在
     */
    public boolean inRange(IpAddress start, IpAddress end) {
        return compareTo(start) >= 0 && compareTo(end) <= 0; 
    }

    /**
     * 根据子网掩码得到网络地址
     * @param subnetMask
     */
    public IpAddress getSubnetAddress(IpAddress subnetMask) {
        return and(subnetMask);
    }

    /**
     * 根据子网掩码得到广播地址
     * @param subnetMask
     */
    public IpAddress getBroadcastAddress(IpAddress subnetMask) {
        return getSubnetAddress(subnetMask).or(subnetMask.not());
    }
    
    /**
     * 得到标准的InetAddress
     */
    public InetAddress getInetAddress() {
        try {
            return InetAddress.getByAddress(toByteArray());
        } catch (UnknownHostException e) {
            // will this happen?
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 得到地址的byte array 方式表示
     */
    public abstract byte[] toByteArray();
    
    /**
     * 以下方法由实现类实现，提供地址的运算，供getBroadcastAddress等方法使用
     */
    protected abstract IpAddress not();
    
    protected abstract IpAddress and(IpAddress addr);
    
    protected abstract IpAddress or(IpAddress addr);

    /**
     * 得到本机的IP地址
     */
    public static IpAddress getLocalHost() {
        try {
            return fromInetAddress(InetAddress.getLocalHost());
        } catch (UnknownHostException ex) {
            throw new RuntimeException("Unknown Host.", ex);
        }
    }
    
    /**
     * 解析一个域名或字符串形式的地址，到IpAddress
     * @throws UnknownHostException
     */
    public static IpAddress getByName(String host) throws UnknownHostException {
        return fromInetAddress(InetAddress.getByName(host));
    }
    
    /**
     * 得到一个InetAddress对应的IpAddress
     * @param addr InetAddress
     * @return IpAddress
     */
    public static IpAddress fromInetAddress(InetAddress addr) {
        if (addr instanceof Inet4Address) {
            return IpV4Address.valueOf((Inet4Address)addr);
        }
        if (addr instanceof Inet6Address) {
            return IpV6Address.valueOf((Inet6Address)addr);
        }
        throw new IllegalArgumentException("Unknown type of InetAddress: "
                + addr.getClass());
    }

}


