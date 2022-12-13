package data;

import org.junit.jupiter.api.Test;
import users.Admin;
import users.JogadorAutenticavel;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO dao = UserDAO.getInstance();

    @Test
    void size() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void containsKey() {
    }

    @Test
    void containsValue() {
    }

    @Test
    void get() {
    }

    @Test
    void put() {
        JogadorAutenticavel jogador = new JogadorAutenticavel("Test1", "1234");
        int antes = dao.size();
        assertNull(dao.put(jogador.getUsername(), jogador));
        assertEquals(antes + 1, dao.size());
        Admin a = new Admin("Test2", "1234");
        assertNull(dao.put(a.getUsername(), a));
        assertEquals(antes + 2, dao.size());
        dao.clear();
        assertEquals(0, dao.size());
    }

    @Test
    void remove() {
        JogadorAutenticavel jogador = new JogadorAutenticavel("Teste1", "1234");
        var size = dao.size();
        assertNull(dao.put(jogador.getUsername(), jogador));
        assertEquals(size + 1, dao.size());
        var jogador2 = dao.remove(jogador.getUsername());
        assertEquals(size, dao.size());
        assertNotNull(jogador2);
        assertEquals(jogador, jogador2);
    }

    @Test
    void putAll() {
    }

    @Test
    void clear() {
    }

    @Test
    void keySet() {
    }

    @Test
    void values() {
    }

    @Test
    void entrySet() {
    }
}