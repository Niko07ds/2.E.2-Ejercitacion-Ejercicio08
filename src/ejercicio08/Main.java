package ejercicio08;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Ejercicio 8: Registro compartido de movimientos de insumos ===");

        SectorModule warehouse = new SectorModule(Sector.WAREHOUSE);
        SectorModule consultingRooms = new SectorModule(Sector.CONSULTING_ROOMS);
        SectorModule laboratory = new SectorModule(Sector.LABORATORY);

        System.out.println("\n--- Comprobación de identidad ---");
        boolean sameLog = warehouse.getLog() == consultingRooms.getLog()
                && consultingRooms.getLog() == laboratory.getLog();
        System.out.println("¿Los tres sectores usan el mismo registro? " + (sameLog ? "Sí" : "No"));

        System.out.println("\n--- Saldos iniciales (movimientos de entrada) ---");
        warehouse.registerEntry("GASA-01", 100);
        warehouse.registerEntry("JERINGA-5ML", 200);
        warehouse.registerEntry("ALCOHOL-1L", 40);

        System.out.println("\n--- Salidas y nuevas entradas de los sectores ---");
        consultingRooms.registerExit("GASA-01", 30);
        laboratory.registerExit("JERINGA-5ML", 50);
        consultingRooms.registerExit("ALCOHOL-1L", 10);
        warehouse.registerEntry("GASA-01", 50);
        laboratory.registerExit("GASA-01", 20);

        System.out.println("\n--- Validaciones ---");
        try {
            consultingRooms.registerExit("GASA-01", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            laboratory.registerEntry("JERINGA-5ML", 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            laboratory.registerExit("JERINGA-5ML", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            laboratory.registerExit("GUANTES-M", 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            warehouse.registerEntry("  ", 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        SupplyMovementLog log = SupplyMovementLog.INSTANCE;

        System.out.println("\n--- Historial completo ---");
        for (SupplyMovement movement : log.getHistory()) {
            System.out.println(movement);
        }

        System.out.println("\n--- Historial del insumo GASA-01 ---");
        for (SupplyMovement movement : log.getBySupply("GASA-01")) {
            System.out.println(movement);
        }

        System.out.println("\n--- Historial del sector Consultorios ---");
        for (SupplyMovement movement : log.getBySector(Sector.CONSULTING_ROOMS)) {
            System.out.println(movement);
        }

        System.out.println("\n--- Saldo de cada insumo ---");
        for (Map.Entry<String, Integer> entry : log.getBalances().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " unidades");
        }

        System.out.println("\n--- Protección del historial ---");
        try {
            log.getHistory().clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("No se pueden editar ni eliminar movimientos desde el código cliente.");
        }
        System.out.println("Movimientos registrados: " + log.getHistory().size());
    }
}
