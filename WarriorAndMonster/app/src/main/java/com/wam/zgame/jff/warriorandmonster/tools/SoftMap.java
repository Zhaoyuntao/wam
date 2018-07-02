package com.wam.zgame.jff.warriorandmonster.tools;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by zhaoyuntao on 2017/9/29.
 */

public class SoftMap<K, V> extends HashMap<K, V> {

    private HashMap<K, ZSoftReference<K, V>> temp;
    private ReferenceQueue<V> queue;

    public SoftMap() {
        temp = new HashMap<K, ZSoftReference<K, V>>();
        queue = new ReferenceQueue<V>();
    }

    @Override
    public V put(K key, V value) {
        ZSoftReference<K, V> sr = new ZSoftReference<K, V>(key, value, queue);
        temp.put(key, sr);
        return null;
    }

    @Override
    public V get(Object key) {
        clear();
        ZSoftReference<K, V> sr = temp.get(key);
        if (sr != null) {
            return sr.get();
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public void clear() {
        ZSoftReference<K, V> poll = (ZSoftReference<K, V>) queue.poll();
        while (poll != null) {
            temp.remove(poll.key);
            poll = (ZSoftReference<K, V>) queue.poll();
        }
    }
    private class ZSoftReference<K, V> extends SoftReference<V> {
        private Object key;
        public ZSoftReference(K key, V r, ReferenceQueue<? super V> q) {
            super(r, q);
            this.key = key;
        }
    }
}
