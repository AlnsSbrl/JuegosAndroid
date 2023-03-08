package com.example.march17th;

import org.jetbrains.annotations.Contract;

/**
 * Listado de menús
 */
public enum Menus {
    MENU_PRINCIPAL(0),
    CHAR_SELECT(1),
    OPCIONES(2),
    CREDITOS(3),
    RECORDS(4),
    INICIO(5);

        private final int menu;
        Menus(int menu){
            this.menu =menu;
        }

        @Contract(pure = true)
        public int getMenu(){
            return menu;
        }
}
