package com.biglabs.coap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import java.io.Serializable;

public class KryoWraper implements Serializable{
	private static final long serialVersionUID = -681409005973800858L;
	private KryoPool pool = null;
	private List<Class<?>> classes = null;


	public KryoWraper(List<Class<?>> classes) {
		classes = (classes != null) ? classes : Collections.emptyList();
		this.classes = classes;
	}

	private KryoPool getPool() {
		synchronized (this) {
			if (pool == null) {
				pool = new KryoPool.Builder(new KryoFactory() {
					public Kryo create() {
						Kryo kryo = new Kryo();
						for (Class<?> class1 : classes) {
							kryo.register(class1);
						}
						return kryo;
					}
				}).softReferences().build();
			}
			return pool;
		}
	}

	public byte[] serialize(Object object) {
		Kryo kryo = null;
		try {
			kryo = getPool().borrow();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			Output output = new ByteBufferOutput(outStream);
			kryo.writeObject(output, object);
			output.flush();
			return outStream.toByteArray();
		} finally {
			if (kryo != null) {
				pool.release(kryo);
			}
		}
	}

	public <T> T deserialize(byte[] bytes, Class<T> type) {
		Kryo kryo = null;
		try {
			kryo = getPool().borrow();
			Input input = new ByteBufferInput(new ByteArrayInputStream(bytes));
			T ret = kryo.readObject(input, type);
			return ret;

		}catch (NullPointerException ex){
			return null;
		}
		finally {
			if (kryo != null) {
				pool.release(kryo);
			}
		}

	}

}
