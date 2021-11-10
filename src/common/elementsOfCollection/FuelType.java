package common.elementsOfCollection;

import java.util.Arrays;
import java.util.List;

/**
 * Перечисляемый тип FuelType.
 */
public enum FuelType implements Comparable<FuelType> {
    /**
     * Керосин.
     */
    KEROSENE("KEROSENE"),
    /**
     * Сантиметры.
     */
    MANPOWER("MANPOWER"),
    /**
     * Граммы.
     */
    NUCLEAR("NUCLEAR"),
    /**
     * Миллиграммы.
     */
    PLASMA("PLASMA");

    /**
     * Конструктор
     *
     * @param text Строковое поле
     */
    FuelType(String text) {
    }

    /**
     * Список возможных значений FuelType.
     */
    private static final List<FuelType> possibleValues = Arrays.asList(FuelType.values());

    /**
     * Метод, возвращающий возможные значения FuelType.
     *
     * @return Список возможных значений FuelType.
     */
    public static List<FuelType> getPossibleValues() {
        return possibleValues;
    }

    public static int compareFuelType(FuelType fl) {
        switch (fl) {
            case PLASMA:
                return 4;
            case NUCLEAR:
                return 3;
            case KEROSENE:
                return 2;
            case MANPOWER:
                return 1;
            default:
                return 0;
        }
    }
}
