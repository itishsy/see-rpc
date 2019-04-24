package com.see.rpc.common.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import de.javakaffee.kryoserializers.*;
import de.javakaffee.kryoserializers.guava.*;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.lang.reflect.InvocationHandler;
import java.util.*;

public final class KryoPoolFactory {

    private static volatile KryoPoolFactory poolFactory = null;

    /*
     * 私有化方法，保证对象不能通过构造方法实现
     */
    private KryoPoolFactory() {
    }

    private KryoPool pool = new KryoPool.Builder(() -> {
        Kryo kryo = new KryoReflectionFactorySupport() {
            @Override
            @SuppressWarnings({"rawtypes", "unchecked"})
            public Serializer<?> getDefaultSerializer(final Class type) {
                if (EnumSet.class.isAssignableFrom(type)) {
                    return new EnumSetSerializer();
                }
                if (EnumMap.class.isAssignableFrom(type)) {
                    return new EnumMapSerializer();
                }
                if (Collection.class.isAssignableFrom(type)) {
                    return new CopyForIterateCollectionSerializer();
                }
                if (Map.class.isAssignableFrom(type)) {
                    return new CopyForIterateMapSerializer();
                }
                if (Date.class.isAssignableFrom(type)) {
                    return new DateSerializer(type);
                }
                return super.getDefaultSerializer(type);
            }
        };
        //支持对象循环引用
        kryo.setReferences(true);


        kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
        kryo.register(Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer());
        kryo.register(Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer());
        kryo.register(Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer());
        kryo.register(Collections.singletonList("").getClass(), new CollectionsSingletonListSerializer());
        kryo.register(Collections.singleton("").getClass(), new CollectionsSingletonSetSerializer());
        kryo.register(Collections.singletonMap("", "").getClass(), new CollectionsSingletonMapSerializer());
        kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
        kryo.register(InvocationHandler.class, new JdkProxySerializer());
        UnmodifiableCollectionsSerializer.registerSerializers(kryo);
        SynchronizedCollectionsSerializer.registerSerializers(kryo);

        // custom serializers for non-jdk libs
        // guava ImmutableList, ImmutableSet, ImmutableMap, ImmutableMultimap, ReverseList, UnmodifiableNavigableSet
//        ImmutableListSerializer.registerSerializers(kryo);
//        ImmutableSetSerializer.registerSerializers(kryo);
//        ImmutableMapSerializer.registerSerializers(kryo);
//        ImmutableMultimapSerializer.registerSerializers(kryo);
//        ReverseListSerializer.registerSerializers(kryo);
//        UnmodifiableNavigableSetSerializer.registerSerializers(kryo);
        // guava ArrayListMultimap, HashMultimap, LinkedHashMultimap, LinkedListMultimap, TreeMultimap
        ArrayListMultimapSerializer.registerSerializers(kryo);
        HashMultimapSerializer.registerSerializers(kryo);
        LinkedHashMultimapSerializer.registerSerializers(kryo);
        LinkedListMultimapSerializer.registerSerializers(kryo);
        TreeMultimapSerializer.registerSerializers(kryo);

        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        return kryo;
    }).build();

    /**
     * 为什么不使用直接静态方法去构造 因为需要constant 但是需要线程共享副本，需要最新的副本pool内容
     *
     * @return
     */
    public static KryoPool getKryoPoolInstance() {
        if (poolFactory == null) {
            synchronized (KryoPoolFactory.class) {
                if (poolFactory == null) {
                    poolFactory = new KryoPoolFactory();
                }
            }
        }
        return poolFactory.getPool();
    }

    /**
     * 不再对外提供方法来获取
     *
     * @return
     */
    private KryoPool getPool() {
        return pool;
    }
}
