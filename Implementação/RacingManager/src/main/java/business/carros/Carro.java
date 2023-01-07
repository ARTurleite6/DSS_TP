package business.carros;

import business.campeonatos.GDU;
import business.campeonatos.Piloto;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class Carro {

    /**
     * Modelo do carro
     */
    private final String modelo;
    /**
     * Marca do carro
     */
    private final String marca;
    /**
     * Cilindrada do carro
     */
    private final int cilindrada;
    /**
     * Potência de combustão do carro
     */
    private final int potenciaCombustao;
    /**
     * Estado do carro
     */
    private float estado;

    /**
     * Tipo de pneu que carro está a usar
     */
    private TipoPneu tipoPneu;
    /**
     * Modo de motor que o carro está a usar
     */
    private ModoMotor modoMotor;
    /**
     * Piloto que está a conduzir o carro
     */
    private Piloto piloto;
    /**
     * Verifica se carro não terminou a corrida
     */
    private boolean dnf;
    /**
     * Tempo do carro numa corrida decorrente
     */
    private int tempo;
    /**
     * Verifica se carro despistou
     */
    private boolean despiste;

    /**
     * Construtor default da classe Carro
     */
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

    /**
     * Construtor parametrizado da classe Carro
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param cilindrada Cilindrada do carro
     * @param potenciaCombustao Potência de combustão do carro
     */
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

    /**
     * Construtor parametrizado da classe Carro
     * @param modelo Modelo do carro
     * @param marca Marca do carro
     * @param cilindrada Cilindrada do carro
     * @param potenciaCombustao Potência de combustão do carro
     * @param estado Estado do carro
     * @param modoMotor Modo de motor que o carro está a usar
     * @param tipoPneu Tipo de pneu que carro está a usar
     * @param piloto Piloto que está a conduzir o carro
     * @param dnf Verifica se carro não terminou a corrida
     * @param tempo Tempo do carro numa corrida decorrente
     * @param despiste Verifica se carro despistou
     */
    public Carro(String modelo, String marca, int cilindrada, int potenciaCombustao, float estado, ModoMotor modoMotor, TipoPneu tipoPneu, @NotNull Piloto piloto, boolean dnf, int tempo, boolean despiste) {
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

    /**
     * Construtor de cópia da classe Carro
     * @param c Carro a ser copiado
     */
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

    /**
     * Devolve o modelo do carro
     * @return Modelo do carro
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Devolve a marca do carro
     * @return Marca do carro
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Devolve a cilindrada do carro
     * @return Cilindrada do carro
     */
    public int getCilindrada() {
        return cilindrada;
    }

    /**
     * Devolve a potência de combustão do carro
     * @return Potência de combustão do carro
     */
    public int getPotenciaCombustao() {
        return potenciaCombustao;
    }

    /**
     * Devolve a fiabilidade do carro
     * @return Fiabilidade do carro
     */
    public abstract int getFiabilidade();

    public float getEstado() {
        return estado;
    }

    /**
     * Devolve o piloto que está a conduzir o carro
     * @return Piloto que está a conduzir o carro
     */
    public Piloto getPiloto() {
        return piloto;
    }

    /**
     * Verifica se carro não terminou a corrida
     * @return True se carro não terminou a corrida, false caso contrário
     */
    public boolean isDnf() {
        return dnf;
    }

    /**
     * Atualiza o valor de dnf
     * @param dnf Novo valor de dnf
     */
    public void setDnf(boolean dnf) {
        this.dnf = dnf;
    }

    /**
     * Verifica se o carro vai conseguir terminar corrida
     * @param volta Volta atual
     * @param chuva Verifica se está a chover
     * @return True se carro vai conseguir terminar corrida, false caso contrário
     */
    public abstract boolean dnf(int volta, boolean chuva);

    /**
     * Devolve o tempo do carro numa corrida decorrente
     * @return Tempo do carro numa corrida decorrente
     */
    public int getTempo() {
        return tempo;
    }

    /**
     * Atualiza o valor de tempo
     * @param tempo Novo valor de tempo
     */
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    /**
     * Verifica se carro despistou
     * @return True se carro despistou, false caso contrário
     */
    public boolean isDespiste() {
        return despiste;
    }

    /**
     * Devolve o tipo de pneu que carro está a usar
     * @return Tipo de pneu que carro está a usar
     */
    public TipoPneu getTipoPneu() {
        return tipoPneu;
    }

    /**
     * Devolve o modo de motor que o carro está a usar
     * @return Modo de motor que o carro está a usar
     */
    public ModoMotor getModoMotor() {
        return modoMotor;
    }

    /**
     * Atualiza o valor de tipo de pneu
     * @param tipoPneu Novo valor de tipo de pneu
     */
    public void setTipoPneu(TipoPneu tipoPneu) {
        this.tipoPneu = tipoPneu;
    }

    /**
     * Atualiza o piloto que se encontra a conduzir o carro
     * @param piloto Novo piloto que se encontra a conduzir o carro
     */
    public void setPiloto(@NotNull Piloto piloto) {
        this.piloto = piloto.clone();
    }

    /**
     * Atualiza o valor para despiste
     * @param despiste Novo valor para despiste
     */
    public void setDespiste(boolean despiste) {
        this.despiste = despiste;
    }

    /**
     * Atualiza o valor de modoMotor
     * @param modoMotor Novo valor de modoMotor
     */
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

    @Override
    public abstract String toString();

    public boolean despiste(int volta, boolean chuva) {
        if(!this.dnf(volta, chuva)) return false;
        Random random = new Random();
        this.estado -= random.nextFloat(1);
        return true;
    }

    /**
     * Devolve o tempo que o carro demora a fazer de uma seccao para a proxima
     * @param seccao Secção da pista que o carro está a percorrer
     * @param chuva Verifica se está a chover
     * @param volta Volta atual
     * @return Tempo que o carro demora a fazer de uma seccao para a proxima
     */
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

        return tempoMedio + tempoDespiste + tempoChuva - fatorSorte + fatorPotenciaCilindrada - agressividade - desempenhoPneu - fatorMotor - qualidadeTempoSeco;
    }

    /**
     * Devolve a potencia total do carro
     * @return Potencia total do carro
     */
    private int getPotencia() {
        int potenciaEletrica = 0;
        if(this instanceof Hibrido h) potenciaEletrica = h.getPotenciaEletrica();
        return this.potenciaCombustao + potenciaEletrica;
    }

    /**
     * Verifica se pode ultrapassar proximo carro
     * @param seccao Secção da pista que o carro está a percorrer
     * @param volta Volta atual
     * @param chuva Verifica se está a chover
     * @param carFrente Carro que está a frente
     * @param tempoAnteriorFrente Tempo que o carro que está a frente demorou a fazer a seccao anterior
     * @return True se pode ultrapassar o carro que está a frente, false caso contrario
     */
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

    /**
     * Verifica se pode ultrapassar proximo carro
     * @param seccao Secção da pista que o carro está a percorrer
     * @param volta Volta atual
     * @param chuva Verifica se está a chover
     * @param carFrente Carro que está a frente
     * @return True se pode ultrapassar, false caso contrario
     */
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

    /**
     * Devolve o desempenho total do carro, tendo em conta todos os fatores
     * @param volta Volta atual
     * @param chuva Verifica se está a chover
     * @return Desempenho total do carro
     */
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

    /**
     * Metodo que calcula o tempo que terá de estar de diferença para o carro da frente para ultrapassar
     * @param categoria Categoria do carro da frente
     * @return Tempo que terá de estar de diferença para o carro da frente para ultrapassar
     */
    protected abstract int categoryCompare(String categoria);
}
