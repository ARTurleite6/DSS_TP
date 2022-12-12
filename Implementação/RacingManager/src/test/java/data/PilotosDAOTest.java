package data;

import campeonatos.Piloto;

import static org.junit.jupiter.api.Assertions.*;

class PilotosDAOTest {

    @org.junit.jupiter.api.Test
    void size() {
        var dao = PilotosDAO.getInstance();
        int size = dao.size();
        var piloto = new Piloto("Jorge", 1, 1);
        //size incrementa quando adicionado
        //garantir que piloto é adicionado
        assertNull(dao.put(piloto.getNome(), piloto));
        assertEquals(size + 1, dao.size());
        assertNotNull(dao.remove("Jorge"));
        assertEquals(size, dao.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        PilotosDAO dao = PilotosDAO.getInstance();
        assertNull(dao.put("Artur", new Piloto("Artur", 1, 1)));
        assertFalse(dao.isEmpty());
        dao.clear();
        assertTrue(dao.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void containsKey() {
        PilotosDAO dao = PilotosDAO.getInstance();
        assertNull(dao.put("Artur", new Piloto("Artur", 1, 1)));
        assertTrue(dao.containsKey("Artur"));
        dao.remove("Artur");
        assertFalse(dao.containsKey("Artur"));
    }

    @org.junit.jupiter.api.Test
    void containsValue() {
        PilotosDAO dao = PilotosDAO.getInstance();
        assertNull(dao.put("Artur", new Piloto("Artur", 1, 1)));
        assertTrue(dao.containsValue(new Piloto("Artur", 1, 1)));
        dao.remove("Artur");
        assertFalse(dao.containsValue(new Piloto("Artur", 1 ,1)));
    }
}