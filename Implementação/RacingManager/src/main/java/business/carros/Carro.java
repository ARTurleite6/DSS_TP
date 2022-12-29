package business.carros;

import business.campeonatos.GDU;
import business.campeonatos.Piloto;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class Carro {

    private String modelo;
    private String marca;
    private int cilindrada;
    private int potenciaCombustao;
    private float estado;

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

        this.estado = 0;
        this.tipoPneu = TipoPneu.Macio;
        this.modoMotor = ModoMotor.Base;
        this.piloto = null;
        this.dnf = false;
        this.tempo = 0;
        this.despiste = false;
    }

    public Carro(String modelo, String marca, int cilindrada, int potenciaCombustao) {
        this.modelo = modelo;
        this.marca = marca;
        this.cilindrada = cilindrada;
        this.potenciaCombustao = potenciaCombustao;

        this.estado = 100;
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

        this.estado = 100;
        this.tipoPneu = TipoPneu.Macio;
        this.modoMotor = ModoMotor.Base;
        this.piloto = null;
        this.dnf = false;
        this.tempo = 0;
        this.despiste = false;
    }

    public Carro(String modelo, String marca, int cilindrada, int potenciaCombustao, int estado, ModoMotor modoMotor, TipoPneu tipoPneu, @NotNull Piloto piloto, boolean dnf, int tempo, boolean despiste) {
        this.modelo = modelo;
        this.marca = marca;
        this.cilindrada = cilindrada;
        this.potenciaCombustao = potenciaCombustao;
        this.estado = estado;
        this.modoMotor = modoMotor;
        this.tipoPneu = tipoPneu;
        this.piloto = piloto.clone();
        this.dnf = dnf;
        this.tempo = tempo;
        this.despiste = despiste;
    }

    public Carro(@NotNull Carro c) {
        this.modelo = c.getModelo();
        this.marca = c.getMarca();
        this.cilindrada = c.getCilindrada();
        this.potenciaCombustao = c.getPotenciaCombustao();
        this.estado = c.getEstado();
        this.piloto = c.getPiloto();
        this.dnf = c.isDnf();
        this.tempo = c.getTempo();
        this.despiste = c.isDespiste();
        this.tipoPneu = c.getTipoPneu();
        this.modoMotor = c.getModoMotor();
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

    public abstract int getFiabilidade();

    public float getEstado() {
        return estado;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public boolean isDnf() {
        return dnf;
    }

    public void setDnf(boolean dnf) {
        this.dnf = dnf;
    }

    public abstract boolean dnf(int volta, boolean chuva);

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

    public void setPiloto(@NotNull Piloto piloto) {
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
        if (getEstado() != carro.getEstado()) return false;
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
        result = 31 * result + Float.hashCode(getEstado());
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

    public boolean despiste(int volta, boolean chuva) {
        if(!this.dnf(volta, chuva)) return false;
        Random random = new Random();
        this.estado -= random.nextFloat(1);
        return true;
    }

    public int tempoProxSeccao(@NotNull GDU seccao, boolean chuva, int volta) {
        int tempoMedio = seccao.getTempoMedio();
        int fatorPotenciaCilindrada = this.cilindrada / this.getPotencia() * 100;
        int minimo = 0;
        int maximo = 1000;
        int fatorSorte = minimo + Double.valueOf(Math.random() * (maximo - minimo)).intValue();
        int qualidadeTempoSeco = this.piloto.getQualidadeTempoSeco();

        int tempoChuva = 0;
        if(chuva) {
            qualidadeTempoSeco = 0;
            int maximoChuva = 2000;
            int fatorPneuChuva = 0;
            if(this.tipoPneu != TipoPneu.Chuva)
                fatorPneuChuva = 5000;
            int qualidadeChuva = this.piloto.getQualidadeChuva();
            tempoChuva = 9000 - (minimo + Double.valueOf(Math.random() * (maximoChuva - minimo)).intValue() + qualidadeChuva - fatorPneuChuva);
        }

        int agressividade = this.piloto.getAgressividade() * 10;
        int desempenhoPneu = this.tipoPneu.getDesempenho(volta);
        int fatorMotor = this.modoMotor.getDesempenhoAdicional();

        int tempoDespiste = 10000;
        if(!this.despiste) {
            tempoDespiste = 0;
        }

        System.out.println("tempo medio " + tempoMedio);
        System.out.println("tempo despiste " + tempoDespiste);
        System.out.println("tempo chuva " + tempoChuva);
        System.out.println("tempo potenciaCilindrada " + fatorPotenciaCilindrada);
        System.out.println("fator sorte " + fatorSorte);
        System.out.println("agressividade " + agressividade);
        System.out.println("desempenho pneu " + desempenhoPneu);
        System.out.println("qualidadeTempoSeco " + qualidadeTempoSeco);

        return tempoMedio + tempoDespiste + tempoChuva - fatorSorte + fatorPotenciaCilindrada - agressividade - desempenhoPneu - fatorMotor - qualidadeTempoSeco;
    }

    private int getPotencia() {
        int potenciaEletrica = 0;
        if(this instanceof Hibrido h) potenciaEletrica = h.getPotenciaEletrica();
        return this.potenciaCombustao + potenciaEletrica;
    }

    public boolean podeUltrapassar(GDU seccao, int volta, boolean chuva, @NotNull Carro carFrente, int tempoAnteriorFrente) {
        boolean dnf = carFrente.isDnf();
        boolean despistou = carFrente.isDespiste();
        if(dnf) return true;
        if(!this.despiste && despistou) return true;
        if(this.despiste && !despistou) return false;

        int tempoRequerido = this.categoryCompare(carFrente.getClass().getSimpleName());
        if(Math.abs(tempoAnteriorFrente - this.tempo) > tempoRequerido) return false;

        int desempenho1 = this.getDesempenho(volta, chuva);
        int desempenho2 = this.getDesempenho(volta, chuva);
        int fatorDificuldade = seccao.getDificuldade();
        return desempenho1 - fatorDificuldade > desempenho2;
    }

    public boolean podeUltrapassar(GDU seccao, int volta, boolean chuva, @NotNull Carro carFrente) {
        boolean dnf = carFrente.isDnf();
        boolean despistou = carFrente.isDespiste();
        if(dnf) return true;
        if(!this.despiste && despistou) return true;
        if(this.despiste && !despistou) return false;

        int desempenho1 = this.getDesempenho(volta, chuva);
        int desempenho2 = this.getDesempenho(volta, chuva);
        int fatorDificuldade = seccao.getDificuldade();
        return desempenho1 - fatorDificuldade > desempenho2;
    }

    protected int getDesempenho(int volta, boolean chuva) {
        int fatorPotenciaCilindrada = this.cilindrada / this.getPotencia();
        int qualidadeTempoSeco = this.piloto.getQualidadeTempoSeco();

        int fatorPneuChuva = 0;
        int qualidadeChuva = 0;
        if(chuva) {
            qualidadeTempoSeco = 0;
            if(this.tipoPneu != TipoPneu.Chuva)
                fatorPneuChuva = 10;
            qualidadeChuva = this.piloto.getQualidadeChuva();
        }

        int agressividade = this.piloto.getAgressividade();
        int desempenhoPneu = this.tipoPneu.getDesempenho(volta);
        int fatorMotor = this.modoMotor.getDesempenhoAdicional();

        return fatorPotenciaCilindrada + agressividade + desempenhoPneu + fatorMotor + qualidadeTempoSeco + fatorPneuChuva + qualidadeChuva;
    }

    protected abstract int categoryCompare(String categoria);
}
