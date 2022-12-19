package ui;

import business.users.Autenticavel;
import org.jetbrains.annotations.Nullable;

public interface SubMenu {
    void setUserAutenticado(@Nullable Autenticavel user);
    void run();
}
