package Motevich.cr2.gr6.labs;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

    private final Double[] coefficients;
    private final double from;
    private final double to;
    private final double step;
    private double last2;
    private double last3;

    public TableModel(Double[] coefficients, double from, double to, double step) {
        this.coefficients = coefficients;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public double getStep() {
        return step;
    }

    public double getTo() {
        return to;
    }

    public double getFrom() {
        return from;
    }


    @Override
    public int getRowCount() {
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        double x = from + step * rowIndex;
        double res = 0;

        if(columnIndex == 0)
        {
            return x;
        } else if(columnIndex == 1)
        {
            int n = coefficients.length;
                for (int i = n - 1; i >= 0; i--) {
                    res = res * x + coefficients[i];
                }

                last2 = res;
            return res;
        } else if(columnIndex == 2)
        {

            for (Double coefficient : coefficients) {
                res = res * x + coefficient;
            }

                last3 = res;
            return res;
        } else if(columnIndex == 3)
        {
            res = last3 - last2;
            return res;
        }

        return res;
    }


    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Значение x";
            case 1 -> "Значение многочлена";
            case 2 -> "Значение обратного вычисления";
            default -> "Разница вычислений";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Double.class;
    }
}
