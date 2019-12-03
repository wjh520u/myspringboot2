package com.wjh.shiro.config.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.RedisSerializer;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

public class KryoSerializer<T> implements RedisSerializer<T> {

    private static final ThreadLocal<Kryo> kryos = ThreadLocal.withInitial(Kryo::new);

    private Class<T>  clazz;

    public KryoSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {

        Kryo kryo = kryos.get();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false );
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Output output = new Output(baos)) {
            kryo.writeClassAndObject(output, t);
            output.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        Kryo kryo = kryos.get();
        //kryo.register(type.getClass());
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        try (Input input = new Input(bytes)) {
            return (T) kryo.readClassAndObject(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
