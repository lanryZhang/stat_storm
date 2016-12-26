
package com.ifeng.core.misc;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 本类用于定义ipv4模式下的ip地址，ipv4指采用点分十进制定义每一位，共有四
 * 位的地址。当前的网络地址大量为ipv4模式。例如： 192.168.0.1.
 * 为了保证系统可以适应ip地址位数的变化，定义了IpAddress接口来
 * 统一ip地址的格式和一些对应的工具方法。
 * 
 * 此类的对象是Immutable对象
 * @author jinmy
 */
public class IpV4Address extends IpAddress {

	private static final long serialVersionUID = 1L;
	private long value;

    public IpV4Address() {
    }
    
    public IpV4Address(long value) {
        this.value = value & 0xFFFFFFFFL;
    }

    public IpV4Address(String value) {
        try {
            setValue(InetAddress.getByName(value).getAddress());
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("can't convert String '" 
                    + value + "' to IpV4Address, exception: " + e);
        }
    }

    public IpV4Address(Inet4Address value) {
        this(value.getAddress());
    }

    public IpV4Address(byte[] value) {
        setValue(value);
    }
    
    protected void setValue(byte[] value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        if (value.length != 4) {
            throw new IllegalArgumentException("value length must be 4");
        }
        this.value = ((value[0] << 24) & 0xFF000000L)
                | ((value[1] << 16) & 0x00FF0000)
                | ((value[2] << 8) & 0x0000FF00) 
                | ((value[3] & 0xFF));
    }

    /**
     * valueOf方法，提供标准Immutable的接口形式
     */
    public static IpV4Address valueOf(long value) {
        return new IpV4Address(value);
    }
    
    public static IpV4Address valueOf(String value) {
        return new IpV4Address(value);
    }
    
    public static IpV4Address valueOf(Inet4Address value) {
        return new IpV4Address(value);
    }
    
    public static IpV4Address valueOf(byte[] value) {
        return new IpV4Address(value);
    }
    
    public static IpV4Address valueOf(Object value) {
        if (value instanceof byte[]) {
            return valueOf((byte[])value);
        }
        if (value instanceof String) {
            return valueOf((String)value);
        }
        if (value instanceof Inet4Address) {
            return valueOf((Inet4Address)value);
        }
        if (value instanceof Number) {
            return valueOf(((Number)value).longValue());
        }
        throw new IllegalArgumentException("Invalid type of value: " + value);
    }
    
    /**
     * 判断一个地址字符串是否是一个合法的字符串
     */
    public static boolean isValidString(String s) {
        try {
            String[] tmp = RegexPatternCache.split("\\.", s);
            if (tmp.length != 4) {
                return false;
            }
    
            for (int i = 0; i < tmp.length; i++) {
                if ((tmp[i] == null) || tmp[i].equals("")) {
                    return false;
                }
                int ipInt = Integer.parseInt(tmp[i]);
    
                if (ipInt < 0 || ipInt > 255) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            // 任何异常。一般只是NumberFormatException 
            return false;
        }
    }
    
    /* (non-Javadoc)
     * @see com.ifeng.common.ip.IpAddress#getNext()
     */
    public IpAddress getNext() {
        return new IpV4Address(this.value + 1);
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ip.IpAddress#getPre()
     */
    public IpAddress getPre() {
        return new IpV4Address(this.value - 1);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append((int)(this.value >> 24));
        buffer.append(".");
        buffer.append(((int)this.value & 0xFF0000) >> 16);
        buffer.append(".");
        buffer.append(((int)this.value & 0xFF00) >> 8);
        buffer.append(".");
        buffer.append((int)this.value & 0xFFL);

        return buffer.toString();
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ip.IpAddress#toByteArray()
     */
    public byte[] toByteArray() {
        return new byte[] {
            (byte)(this.value >> 24),
            (byte)(this.value >> 16),
            (byte)(this.value >> 8),
            (byte)this.value
        };
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        if (o instanceof IpV4Address) {
            long diff = this.value - ((IpV4Address)o).value;
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            } else {
                return 0;
            }
        } else {
            throw new IllegalArgumentException("Invalid input arg type");
        }
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ip.IpAddress#and(com.ifeng.common.ip.IpAddress)
     */
    protected IpAddress and(IpAddress ip) {
        IpV4Address ipV4 = (IpV4Address) ip;
        return new IpV4Address(value & ipV4.value);
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ip.IpAddress#or(com.ifeng.common.ip.IpAddress)
     */
    protected IpAddress or(IpAddress ip) {
        IpV4Address ipV4 = (IpV4Address) ip;
        return new IpV4Address(value | ipV4.value);
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ip.IpAddress#not()
     */
    protected IpAddress not() {
        return new IpV4Address(~value & 0xFFFFFFFFL);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof IpV4Address)) {
            return false;
        }

        IpV4Address ip = (IpV4Address) o;

        return this.value == ip.value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return (new Long(this.value)).hashCode();
    }
}


