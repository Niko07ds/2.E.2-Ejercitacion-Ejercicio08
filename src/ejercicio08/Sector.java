package ejercicio08;

/**
 * Sectores del hospital que mueven insumos.
 */
public enum Sector {
    WAREHOUSE("Depósito"),
    CONSULTING_ROOMS("Consultorios"),
    LABORATORY("Laboratorio");

    private final String displayName;

    Sector(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
