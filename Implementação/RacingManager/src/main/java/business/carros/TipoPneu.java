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
}
