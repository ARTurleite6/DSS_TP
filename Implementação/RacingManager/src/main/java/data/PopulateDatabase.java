package data;

import business.campeonatos.Piloto;
import business.carros.C1H;
import business.carros.C2;
import business.carros.GTH;
import business.exceptions.CilindradaInvalidaException;
import business.users.JogadorAutenticavel;

import java.util.ArrayList;
import java.util.List;

public class PopulateDatabase {

    public static void main(String[] args) throws CilindradaInvalidaException {
        JogadorDAO jogadorDAO = JogadorDAO.getInstance();
        if(!jogadorDAO.containsKey("Artur")) {
            jogadorDAO.put("Artur", new JogadorAutenticavel("Artur", "1234", true));
        }
        if(!jogadorDAO.containsKey("Afonso")) {
            jogadorDAO.put("Afonso", new JogadorAutenticavel("Afonso", "1234", false));
        }
        if(!jogadorDAO.containsKey("Diana")) {
            jogadorDAO.put("Diana", new JogadorAutenticavel("Diana", "1234", false));
        }
        if(!jogadorDAO.containsKey("Marta")) {
            jogadorDAO.put("Marta", new JogadorAutenticavel("Marta", "1234", true));
        }

        PilotosDAO pilotosDAO = PilotosDAO.getInstance();
        List<Piloto> pilotosAdicionar = new ArrayList<>();
        if(!pilotosDAO.containsKey("Artur")) {
            var piloto = new Piloto("Artur", 5, 5);
            pilotosAdicionar.add(piloto);
        }
        if(!pilotosDAO.containsKey("Afonso")) {
            var piloto = new Piloto("Afonso", 2, 7);
            pilotosAdicionar.add(piloto);
        }
        if(!pilotosDAO.containsKey("Diana")) {
            var piloto = new Piloto("Diana", 7, 3);
            pilotosAdicionar.add(piloto);
        }
        if(!pilotosDAO.containsKey("Marta")) {
            var piloto = new Piloto("Diana", 2, 2);
            pilotosAdicionar.add(piloto);
        }
        if(!pilotosDAO.containsKey("Gongas")) {
            var piloto = new Piloto("Gongas", 10, 10);
            pilotosAdicionar.add(piloto);
        }

        for(var piloto: pilotosAdicionar)
            pilotosDAO.put(piloto.getNome(), piloto);

        CarrosDAO carrosDAO = CarrosDAO.getInstance();
        if(carrosDAO.isEmpty()) {
            var c2 = new C2("488 GTE", "Ferrari", 3902, 661, 0.2f);
            carrosDAO.put(c2.getModelo(), c2);
            var c1H = new C1H("Classe A 250e", "Mercedes", 600, 0.5f, 100);
            carrosDAO.put(c1H.getModelo(), c1H);
            var GT = new GTH("Especial de Corrida", "Road Racer", 2023, 101, 0.69f, 420);
            carrosDAO.put(GT.getModelo(), GT);
        }
    }

}
