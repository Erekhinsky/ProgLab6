package common.elementsOfCollection;

import java.io.Serializable;

/**
 * Класс местоположения.
 */
public class Coordinates implements Serializable {

    /**
     * Координата X. Максимальное значение поля: 464, Поле не может быть null.
     */
    private double x;
    /**
     * Координата Y. Поле не может быть null.
     */
    private double y;

    /**
     * Конструктор.
     *
     * @param x Координата X.
     * @param y Координата Y.
     */
    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод, возвращающий координаты транспорта.
     *
     * @return Координаты транспорта.
     */
    @Override
    public String toString() {
        return "x = " + x + "; y = " + y;
    }

    /**
     * Метод, возвращающий координату X.
     *
     * @return Координата X.
     */
    public double getX() {
        return x;
    }

    /**
     * Метод, добавляющий координату X.
     *
     * @param x Координата X.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Метод, возвращающий координату Y.
     *
     * @return Координата Y.
     */
    public double getY() {
        return y;
    }

    /**
     * Метод, добавляющий координату Y.
     *
     * @param y Координата Y.
     */
    public void setY(double y) {
        this.y = y;
    }

}
