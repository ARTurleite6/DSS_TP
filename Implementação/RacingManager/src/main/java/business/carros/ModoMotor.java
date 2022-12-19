package business.carros;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum ModoMotor {
    //TODO por definir valores associados à probabilidade de avaria
    Conservador(100), Agressivo(100), Base(100);

    private int probAvaria;

    private ModoMotor(int probAvaria) {
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
}
