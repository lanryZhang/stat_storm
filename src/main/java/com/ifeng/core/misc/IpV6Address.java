package com.ifeng.core.misc;

import java.net.Inet6Address;

/**
 *待实现的ipv6地址支持类.
 * 
 * @author jinmy
 */
public class IpV6Address extends IpAddress {

    private static final long serialVersionUID = 3257563992938262837L;
    private byte[] value;

    public IpV6Address(Inet6Address value) {
        this(value.getAddress());
    }
    
    public IpV6Address(byte[] value) {
        setValue(value);
    }
    
    public static IpV6Address valueOf(Inet6Address value) {
        return new IpV6Address(value);
    }
    
    protected void setValue(byte[] value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see com.ifeng.common.misc.IpAddress#getNext()
     */
    public IpAddress getNext() {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ipIpAddress#getPre()
     */
    public IpAddress getPre() {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ipIpAddress#toByteArray()
     */
    public byte[] toByteArray() {
        return this.value;
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ipIpAddress#not()
     */
    protected IpAddress not() {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ipIpAddress#and(com.ifeng.common.ipIpAddress)
     */
    protected IpAddress and(IpAddress addr) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.ifeng.common.ipIpAddress#or(com.ifeng.common.ipIpAddress)
     */
    protected IpAddress or(IpAddress addr) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        throw new UnsupportedOperationException();
    }
}
