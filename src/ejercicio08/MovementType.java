package ejercicio08;

/**
 * Tipo de movimiento de un insumo.
 */
public enum MovementType {
    ENTRY("Entrada"),
    EXIT("Salida");

    private final String displayName;

    MovementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
