package Motevich.cr2.gr6.labs;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class TableRenderer implements TableCellRenderer{

    private final JPanel panel = new JPanel();
    private final JLabel lbl = new JLabel();

    private String rangeFind = null;

    private Double range1 = null;
    private Double range2 = null;
    private boolean flag = false;
    private String find = null;
    private final DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();


    public TableRenderer(){

        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);

        DecimalFormatSymbols symb = formatter.getDecimalFormatSymbols();
        symb.setDecimalSeparator('.');

        formatter.setDecimalFormatSymbols(symb);


        panel.add(lbl);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT));


    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                             boolean hasFocus, int row, int column) {

        String Val = formatter.format(value);
        lbl.setText(Val);



        if(range2 != null && range2 < Double.parseDouble(Val) && (column == 1 || column == 2))
        {
            flag = true;
        }

        if(range1 != null && range1 <= Double.parseDouble(Val) && !flag && (column == 1 || column == 2))
        {
            panel.setBackground(Color.GREEN);
            return panel;
        }

        if(column == 1 && find != null && find.equals(Val))
        {
            panel.setBackground(Color.BLUE);
        } else{
            if((row % 2 == 0 && column % 2 == 0) || (row % 2 == 1 && column % 2 == 1)) {
                panel.setBackground(Color.BLACK);
                lbl.setForeground(Color.WHITE);
            } else {
            panel.setBackground(Color.WHITE);
            lbl.setForeground(Color.BLACK);
            }
        }
        return panel;
    }

    void setFind(String val)
    {
        find = val;
    }

    void setRangeFind(String val)
    {
        if(val != null) {
            rangeFind = val;
            String[] str = rangeFind.split(" ");
            range1 = Double.parseDouble(str[0]);
            range2 = Double.parseDouble(str[1]);
            flag = false;
        }
    }

}
