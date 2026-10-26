package ejercicio08;

import java.time.LocalDate;

/**
 * Módulo de un sector del hospital. Todos los sectores usan el mismo registro de movimientos.
 */
public class SectorModule {

    private final Sector sector;
    private final SupplyMovementLog log = SupplyMovementLog.INSTANCE;

    public SectorModule(Sector sector) {
        if (sector == null) {
            throw new IllegalArgumentException("El sector no puede ser nulo.");
        }
        this.sector = sector;
    }

    public SupplyMovementLog getLog() {
        return log;
    }

    public void registerEntry(String supplyCode, int quantity) {
        log.register(supplyCode, MovementType.ENTRY, quantity, sector, LocalDate.now());
        System.out.println("[" + sector.getDisplayName() + "] Entrada registrada: " + quantity + " de " + supplyCode);
    }

    public void registerExit(String supplyCode, int quantity) {
        log.register(supplyCode, MovementType.EXIT, quantity, sector, LocalDate.now());
        System.out.println("[" + sector.getDisplayName() + "] Salida registrada: " + quantity + " de " + supplyCode);
    }
}
