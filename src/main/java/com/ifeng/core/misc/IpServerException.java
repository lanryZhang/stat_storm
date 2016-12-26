package com.ifeng.core.misc;

public class IpServerException extends Exception {

	private static final long serialVersionUID = -3887541565158745551L;

	public IpServerException() {
        super();
    }

    public IpServerException(String message) {
        super(message);
    }

    public IpServerException(
        String message,
        Throwable cause) {
        super(message, cause);
    }

    public IpServerException(Throwable cause) {
        super(cause);
    }
}
