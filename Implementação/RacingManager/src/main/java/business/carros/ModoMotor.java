package business.carros;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum ModoMotor {
    //TODO por definir valores associados à probabilidade de avaria
    Conservador(1), Agressivo(5), Base(2);

    private final int probAvaria;

    ModoMotor(int probAvaria) {
        this.probAvaria = probAvaria;
    }

    public int getProbAvaria() {
        return this.probAvaria;
    }

    @Contract(pure = true)
    public static @Nullable ModoMotor fromString(@NotNull String string) {
        switch (string) {
            case "Conservador" -> {
                return ModoMotor.Conservador;
            }
            case "Agressivo" -> {
                return ModoMotor.Agressivo;
            }
            case "Base" -> {
                return ModoMotor.Base;
            }
            default -> {
                return null;
            }
        }
    }

    public int getDesempenhoAdicional() {
        if(this == Conservador) return -500;
        else if(this == Agressivo) return 500;
        else return 0;
    }
}
