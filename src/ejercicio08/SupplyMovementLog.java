package ejercicio08;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registro compartido de movimientos de insumos.
 * Singleton implementado con enum.
 */
public enum SupplyMovementLog {

    INSTANCE;

    private final List<SupplyMovement> movements = new ArrayList<>();

    /**
     * Registra un movimiento y verifica que exista saldo suficiente para las salidas.
     */
    public synchronized void register(
            String supplyCode,
            MovementType type,
            int quantity,
            Sector sector,
            LocalDate date) {

        SupplyMovement movement =
                new SupplyMovement(supplyCode, type, quantity, sector, date);

        if (movement.type() == MovementType.EXIT) {
            int balance = getBalance(movement.supplyCode());

            if (movement.quantity() > balance) {
                throw new IllegalArgumentException(
                        "Saldo insuficiente del insumo " + movement.supplyCode()
                                + ": saldo disponible " + balance
                                + ", salida solicitada " + movement.quantity() + "."
                );
            }
        }

        movements.add(movement);
    }

    /**
     * Devuelve el historial completo en orden de registro.
     */
    public synchronized List<SupplyMovement> getHistory() {
        return List.copyOf(movements);
    }

    /**
     * Filtra los movimientos por código de insumo.
     */
    public synchronized List<SupplyMovement> getBySupply(String supplyCode) {
        String code = supplyCode == null ? null : supplyCode.trim();

        return movements.stream()
                .filter(movement -> movement.supplyCode().equals(code))
                .toList();
    }

    /**
     * Filtra los movimientos por sector.
     */
    public synchronized List<SupplyMovement> getBySector(Sector sector) {
        return movements.stream()
                .filter(movement -> movement.sector() == sector)
                .toList();
    }

    /**
     * Calcula el saldo de un insumo: entradas menos salidas.
     */
    public synchronized int getBalance(String supplyCode) {
        String code = supplyCode == null ? null : supplyCode.trim();

        return movements.stream()
                .filter(movement -> movement.supplyCode().equals(code))
                .mapToInt(movement ->
                        movement.type() == MovementType.ENTRY
                                ? movement.quantity()
                                : -movement.quantity())
                .sum();
    }

    /**
     * Devuelve los saldos de todos los insumos en un mapa no modificable.
     */
    public synchronized Map<String, Integer> getBalances() {
        Map<String, Integer> balances = new LinkedHashMap<>();

        for (SupplyMovement movement : movements) {
            balances.putIfAbsent(
                    movement.supplyCode(),
                    getBalance(movement.supplyCode())
            );
        }

        return Map.copyOf(balances);
    }
}