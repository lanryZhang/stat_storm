package com.ifeng.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;


public class SerializeUtil {
	private static final Logger logger = Logger.getLogger(SerializeUtil.class);
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				logger.error("Unable to close " + closeable);
			}
		}
	}

	public static <T> byte[] serialize(T value) {
			if (value == null) {
				throw new NullPointerException("Can't serialize null");
			}
		byte[] rv = null;

		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			
			os = new ObjectOutputStream(bos);
			os.writeObject(value);
			rv = bos.toByteArray();
		} catch (Exception e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		} finally {
			close(os);
			close(bos);
		}
		return rv;
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] in) {

		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				T t = (T) is.readObject();
				return t;
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			close(is);
			close(bis);
		}
		return null;
	}

	public static <T> String toJsonString(T en){
		if (en == null) {
			throw new NullPointerException("Can't serialize null");
		}
		return JSON.toJSONString(en);
	}

	public static <T> T toObject(String value,Class<T> clazz){
		if (value == null) {
			throw new NullPointerException("Can't serialize null");
		}
		return JSON.toJavaObject(JSON.parseObject(value),clazz);
	}
}
