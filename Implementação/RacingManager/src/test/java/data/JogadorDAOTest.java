package data;

import business.data.JogadorDAO;
import org.junit.jupiter.api.Test;
import business.users.JogadorAutenticavel;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JogadorDAOTest {

    private JogadorDAO dao = JogadorDAO.getInstance();

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
        var a = new JogadorAutenticavel("Test2", "1234");
        assertNull(dao.put(a.getUsername(), a));
        assertEquals(antes + 2, dao.size());
        dao.clear();
        assertEquals(0, dao.size());
    }

    @Test
    void remove() {
        JogadorAutenticavel jogador = new JogadorAutenticavel("Teste1", "1234");
        dao.clear();
        var size = dao.size();
        assertEquals(0, size);
        assertNull(dao.put(jogador.getUsername(), jogador));
        assertEquals(size + 1, dao.size());
        var jogador2 = dao.remove(jogador.getUsername());
        assertEquals(size, dao.size());
        assertNotNull(jogador2);
        assertEquals(jogador, jogador2);
    }

    @Test
    void putAll() {
        dao.clear();
        assertEquals(0, dao.size());
        Map<String, JogadorAutenticavel> jogadores = Map.of(
                "Teste1", new JogadorAutenticavel("Teste1", "1234"),
                "Teste2", new JogadorAutenticavel("Teste2", "1234"),
                "Teste3", new JogadorAutenticavel("Teste3", "1234"),
                "Teste4", new JogadorAutenticavel("Teste4", "1234"),
                "Teste5", new JogadorAutenticavel("Teste5", "1234")
        );
        dao.putAll(jogadores);
        assertEquals(jogadores.size(), dao.size());
        for(Map.Entry<String, JogadorAutenticavel> jogador: jogadores.entrySet()) {
            assertEquals(jogador.getValue(), dao.get(jogador.getKey()));
        }
        dao.clear();
    }
}