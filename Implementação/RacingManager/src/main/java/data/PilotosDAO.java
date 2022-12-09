package data;

import campeonatos.Piloto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PilotosDAO implements Map<String, Piloto> {
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
    public Piloto get(Object key) {
        return null;
    }

    @Override
    public Piloto put(String key, Piloto value) {
        return null;
    }

    @Override
    public Piloto remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Piloto> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Piloto> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Piloto>> entrySet() {
        return null;
    }
}
