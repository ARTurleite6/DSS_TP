package business.carros;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum TipoPneu {
    Chuva,
    Macio,
    Duro;

    @Contract(pure = true)
    public static @Nullable TipoPneu fromString(@NotNull String value) {
        switch (value) {
            case "Chuva" -> {
                return TipoPneu.Chuva;
            }
            case "Macio" -> {
                return TipoPneu.Macio;
            }
            case "Duro" -> {
                return TipoPneu.Duro;
            }
            default -> {
                return null;
            }
        }
    }

    public int getDesempenho(int volta) {
        if(this == Chuva) return 0;
        else if(this == Macio) return 500 / volta;
        else return 700;
    }
}
