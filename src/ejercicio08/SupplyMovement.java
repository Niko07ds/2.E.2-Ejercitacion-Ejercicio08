package ejercicio08;

import java.time.LocalDate;

/**
 * Movimiento de un insumo. Es inmutable: una vez registrado no puede editarse.
 */
public record SupplyMovement(String supplyCode, MovementType type, int quantity, Sector sector, LocalDate date) {

    public SupplyMovement {
        if (supplyCode == null || supplyCode.isBlank()) {
            throw new IllegalArgumentException("El código de insumo no puede estar vacío.");
        }
        if (type == null) {
            throw new IllegalArgumentException("El tipo de movimiento no puede ser nulo.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        if (sector == null) {
            throw new IllegalArgumentException("El sector no puede ser nulo.");
        }
        if (date == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        supplyCode = supplyCode.trim();
    }

    @Override
    public String toString() {
        return "[" + date + "] " + sector.getDisplayName() + " - " + type.getDisplayName()
                + " de " + quantity + " unidades del insumo " + supplyCode;
    }
}
