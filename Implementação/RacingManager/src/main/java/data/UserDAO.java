package data;

import users.Autenticavel;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class UserDAO implements Map<String, Autenticavel> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Autenticavel get(Object key) {
        return null;
    }

    @Override
    public Autenticavel put(String key, Autenticavel value) {
        return null;
    }

    @Override
    public Autenticavel remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Autenticavel> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Autenticavel> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Autenticavel>> entrySet() {
        return null;
    }
}
