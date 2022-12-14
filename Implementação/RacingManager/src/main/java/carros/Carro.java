package carros;

import campeonatos.Piloto;

public abstract class Carro {

    private String modelo;
    private String marca;
    private int cilindrada;
    private int potenciaCombustao;
    private float fiabilidade;
    private int estadoPneu;

    private TipoPneu tipoPneu;
    private ModoMotor modoMotor;
    private Piloto piloto;
    private boolean dnf;
    private int tempo;
    private boolean despiste;

    public Carro() {
        this.modelo = "";
        this.marca = "";
        this.cilindrada = 0;
        this.potenciaCombustao = 0;
        this.fiabilidade = 0;
        this.estadoPneu = 0;
        this.cilindrada = 0;
        this.potenciaCombustao = 0;
        this.fiabilidade = 0;
        this.estadoPneu = 0;
        this.tipoPneu = TipoPneu.Macio;
        this.modoMotor = ModoMotor.Base;
        this.piloto = null;
        this.dnf = false;
        this.tempo = 0;
        this.despiste = false;
    }

    public Carro(String modelo, String marca, int cilindrada, int potenciaCombustao, float fiabilidade) {
        this.modelo = modelo;
        this.marca = marca;
        this.cilindrada = cilindrada;
        this.potenciaCombustao = potenciaCombustao;
        this.fiabilidade = fiabilidade;

        this.estadoPneu = 100;
        this.tipoPneu = TipoPneu.Macio;
        this.modoMotor = ModoMotor.Base;
        this.piloto = null;
        this.dnf = false;
        this.tempo = 0;
        this.despiste = false;
    }

    public Carro(String modelo, String marca, int cilindrada, int potenciaCombustao, float fiabilidade, int estadoPneu, ModoMotor modoMotor, TipoPneu tipoPneu,Piloto piloto, boolean dnf, int tempo, boolean despiste) {
        this.modelo = modelo;
        this.marca = marca;
        this.cilindrada = cilindrada;
        this.potenciaCombustao = potenciaCombustao;
        this.fiabilidade = fiabilidade;
        this.estadoPneu = estadoPneu;
        this.modoMotor = modoMotor;
        this.tipoPneu = tipoPneu;
        this.piloto = piloto.clone();
        this.dnf = dnf;
        this.tempo = tempo;
        this.despiste = despiste;
    }

    public Carro(Carro c) {
        this.modelo = c.getModelo();
        this.marca = c.getMarca();
        this.cilindrada = c.getCilindrada();
        this.potenciaCombustao = c.getPotenciaCombustao();
        this.fiabilidade = c.getFiabilidade();
        this.estadoPneu = c.getEstadoPneu();
        this.piloto = c.getPiloto();
        this.dnf = c.isDnf();
        this.tempo = 0;
        this.despiste = c.isDespiste();
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public int getPotenciaCombustao() {
        return potenciaCombustao;
    }

    public float getFiabilidade() {
        return fiabilidade;
    }

    public int getEstadoPneu() {
        return estadoPneu;
    }

    public Piloto getPiloto() {
        if(this.piloto == null) return null;
        return piloto.clone();
    }

    public boolean isDnf() {
        return dnf;
    }

    public void setDnf(boolean dnf) {
        this.dnf = dnf;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public boolean isDespiste() {
        return despiste;
    }

    public TipoPneu getTipoPneu() {
        return tipoPneu;
    }

    public ModoMotor getModoMotor() {
        return modoMotor;
    }

    public void setTipoPneu(TipoPneu tipoPneu) {
        this.tipoPneu = tipoPneu;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto.clone();
    }

    public void setDespiste(boolean despiste) {
        this.despiste = despiste;
    }

    public void setModoMotor(ModoMotor modoMotor) {
        this.modoMotor = modoMotor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carro carro = (Carro) o;

        if (getCilindrada() != carro.getCilindrada()) return false;
        if (getPotenciaCombustao() != carro.getPotenciaCombustao()) return false;
        if (Float.compare(carro.getFiabilidade(), getFiabilidade()) != 0) return false;
        if (getEstadoPneu() != carro.getEstadoPneu()) return false;
        if (isDnf() != carro.isDnf()) return false;
        if (getTempo() != carro.getTempo()) return false;
        if (isDespiste() != carro.isDespiste()) return false;
        if (!getModelo().equals(carro.getModelo())) return false;
        if (!getMarca().equals(carro.getMarca())) return false;
        if (getTipoPneu() != carro.getTipoPneu()) return false;
        if (getModoMotor() != carro.getModoMotor()) return false;
        return getPiloto().equals(carro.getPiloto());
    }

    @Override
    public int hashCode() {
        int result = getModelo().hashCode();
        result = 31 * result + getMarca().hashCode();
        result = 31 * result + getCilindrada();
        result = 31 * result + getPotenciaCombustao();
        result = 31 * result + (getFiabilidade() != +0.0f ? Float.floatToIntBits(getFiabilidade()) : 0);
        result = 31 * result + getEstadoPneu();
        result = 31 * result + getTipoPneu().hashCode();
        result = 31 * result + getModoMotor().hashCode();
        result = 31 * result + getPiloto().hashCode();
        result = 31 * result + (isDnf() ? 1 : 0);
        result = 31 * result + getTempo();
        result = 31 * result + (isDespiste() ? 1 : 0);
        return result;
    }

    public abstract Carro clone();
    public abstract String toString();
}
