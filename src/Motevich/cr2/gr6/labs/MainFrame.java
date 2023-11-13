package Motevich.cr2.gr6.labs;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {

    private final int HEIGHT = 700;
    private final int WIDTH = 800;

    private final Double[] coefficients;


    private JFileChooser fileChooser = null;
    private TableModel data;
    private final TableRenderer renderer = new TableRenderer();

    private final JMenuItem saveToTextItem;
    private final JMenuItem saveToCSVItem;
    private final JMenuItem saveToBinItem;
    private final JMenuItem searchValItem;
    private final JMenuItem searchRangeItem;
    private final JMenuItem informItem;

    Icon avatar;

    private final Box resBox;

    public MainFrame(Double[] coefficients2)
    {
        super("Табулирование по схеме Горнера");

        coefficients = coefficients2;


        Toolkit kit = Toolkit.getDefaultToolkit();

        setSize(WIDTH, HEIGHT);
        setLocation((kit.getScreenSize().width - this.WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);

        JMenuBar mBar = new JMenuBar();
        setJMenuBar(mBar);
        JMenu fileMenu = new JMenu("Файл");
        JMenu tableMenu = new JMenu("Таблица");
        JMenu spravMenu = new JMenu("Справка");
        mBar.add(fileMenu);
        mBar.add(tableMenu);
        mBar.add(new JSeparator());
        mBar.add(spravMenu);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.TXT", "*.*");


        avatar = new ImageIcon("C:\\Users\\HP\\Desktop\\JavaLabs\\Lab3\\src\\res\\ava.jpg");
        Action informAction = new AbstractAction("О программе") {
            @Override
            public void actionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(MainFrame.this, "Мотевич Максим\n 2 курс\n 6 группа",
                       "О программе", JOptionPane.INFORMATION_MESSAGE, avatar);
            }
        };

        informItem = spravMenu.add(informAction);
        informItem.setEnabled(true);

        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(fileChooser == null) {
                   fileChooser = new JFileChooser();
                   fileChooser.setCurrentDirectory(new File("."));
                   fileChooser.setFileFilter(filter);
                   fileChooser.setDialogTitle("Выбор директории");
               }

               if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
               {
                       saveToTextFile(fileChooser.getSelectedFile());
               }

            }
        };

        saveToTextItem = fileMenu.add(saveToTextAction);
        saveToTextItem.setEnabled(false);

        Action saveToBinAction = new AbstractAction("Сохранить в бинарный файл") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                    fileChooser.setFileFilter(filter);
                    fileChooser.setDialogTitle("Выбор директории");
                }

                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    saveToBinFile(fileChooser.getSelectedFile());
                }
            }
        };

        saveToBinItem = fileMenu.add(saveToBinAction);
        saveToBinItem.setEnabled(false);

        Action saveToCSVAction = new AbstractAction("Сохранить в CSV формате") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser == null)
                {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                    fileChooser.setDialogTitle("Выбор директории");
                    fileChooser.setFileFilter(filter);
                }

                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                {
                    saveToCSVFile(fileChooser.getSelectedFile());
                }
            }
        };

        saveToCSVItem = fileMenu.add(saveToCSVAction);
        saveToCSVItem.setEnabled(false);

        Action searchValAction = new AbstractAction("Поиск значения") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String val = JOptionPane.showInputDialog(MainFrame.this,
                        "Введите значение для поиска", "Поиск значения",
                        JOptionPane.QUESTION_MESSAGE);

                renderer.setFind(val);

                getContentPane().repaint();
            }
        };

        searchValItem = tableMenu.add(searchValAction);
        searchValItem.setEnabled(false);

        Action searchRangeAction = new AbstractAction("Поиск по диапазону") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String val = JOptionPane.showInputDialog(MainFrame.this,
                        "Введите диапазон для поиска", "Поиск по диапазону",
                        JOptionPane.QUESTION_MESSAGE);


                renderer.setRangeFind(val);

                getContentPane().repaint();
            }
        };

        searchRangeItem = tableMenu.add(searchRangeAction);
        searchRangeItem.setEnabled(false);



        //UPP BOX

        JLabel lblFrom = new JLabel("Значения Х от: ");
        JLabel lblTo = new JLabel("До: ");
        JLabel lblStep = new JLabel("Шаг: ");

        JTextField fieldFrom = new JTextField("0", 15);
        fieldFrom.setMaximumSize(fieldFrom.getPreferredSize());
        JTextField fieldTo = new JTextField("20", 15);
        fieldTo.setMaximumSize(fieldTo.getPreferredSize());
        JTextField fieldStep = new JTextField("1", 15);
        fieldStep.setMaximumSize(fieldStep.getPreferredSize());

        Box rangeBox = Box.createHorizontalBox();


        rangeBox.setBorder(BorderFactory.createBevelBorder(1));
        rangeBox.add(Box.createHorizontalGlue());
        rangeBox.add(lblFrom);
        rangeBox.add(Box.createHorizontalStrut(5));
        rangeBox.add(fieldFrom);
        rangeBox.add(Box.createHorizontalStrut(5));
        rangeBox.add(lblTo);
        rangeBox.add(Box.createHorizontalStrut(5));
        rangeBox.add(fieldTo);
        rangeBox.add(Box.createHorizontalStrut(5));
        rangeBox.add(lblStep);
        rangeBox.add(Box.createHorizontalStrut(5));
        rangeBox.add(fieldStep);
        rangeBox.add(Box.createHorizontalGlue());


        Box buttBox = Box.createHorizontalBox();

        JButton calcButton = new JButton("Вычислить");
        JButton clearButton = new JButton("Очистить поля");

        buttBox.add(Box.createHorizontalGlue());
        buttBox.add(calcButton);
        buttBox.add(Box.createHorizontalStrut(5));
        buttBox.add(clearButton);
        buttBox.add(Box.createHorizontalGlue());

        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double from = Double.parseDouble(fieldFrom.getText());
                double to = Double.parseDouble(fieldTo.getText());
                double step = Double.parseDouble(fieldStep.getText());

                data = new TableModel(MainFrame.this.coefficients, from, to, step);

                JTable table = new JTable(data);

                table.setDefaultRenderer(Double.class, renderer);
                table.setRowHeight(30);
                resBox.removeAll();

                resBox.add(new JScrollPane(table));
                getContentPane().validate();

                saveToBinItem.setEnabled(true);
                saveToTextItem.setEnabled(true);
                searchValItem.setEnabled(true);
                searchRangeItem.setEnabled(true);
                saveToCSVItem.setEnabled(true);

            }
        });


        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldTo.setText("0");
                fieldStep.setText("0");
                fieldFrom.setText("0");

                resBox.removeAll();
                resBox.add(new JPanel());

                searchValItem.setEnabled(false);
                saveToTextItem.setEnabled(false);
                saveToBinItem.setEnabled(false);
                searchRangeItem.setEnabled(false);
                saveToCSVItem.setEnabled(false);

                getContentPane().validate();
            }
        });


        resBox = Box.createHorizontalBox();
        resBox.add(new JPanel());



        getContentPane().add(rangeBox, BorderLayout.NORTH);
        getContentPane().add(buttBox, BorderLayout.SOUTH);
        getContentPane().add(resBox, BorderLayout.CENTER);


    }

    private void saveToTextFile(File outF)
    {
        try {
            PrintStream out = new PrintStream(outF);

            out.println("Результаты табулирования многочлена по схеме Горнера");

            out.println("Многочлен: ");
            for(int i = 0; i < coefficients.length; i++)
            {
                out.print(coefficients[i] + "*X^" + (coefficients.length-i-1));

                if(i != coefficients.length-1)
                    out.print(" + ");
            }

            out.println();
            out.println("Интервал от: " + data.getFrom() + " До: " + data.getTo() + " С шагом: " + data.getStep());
            out.println("-----------------------------------------------------------------------------");

            for(int i = 0; i < data.getRowCount(); i++)
            {
                out.println("Значение в точке " + data.getValueAt(i, 0) + " = " + data.getValueAt(i, 1) +
                        " Обратное: " + data.getValueAt(i, 2) + " Разница: " + data.getValueAt(i, 3));
            }

            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не создан/найден");
        }
    }

    private void saveToBinFile(File outF)
    {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(outF));

            for(int i = 0; i < data.getRowCount(); i++)
            {
                    out.writeDouble((Double)data.getValueAt(i, 0));
                    out.writeDouble((Double)data.getValueAt(i, 1));
                    out.writeDouble((Double)data.getValueAt(i, 2));
                    out.writeDouble((Double)data.getValueAt(i, 3));
            }

            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToCSVFile(File outF)
    {
        try {
            PrintStream out = new PrintStream(outF);

            for(int i = 0; i < data.getRowCount(); i++)
            {
                out.println(data.getValueAt(i, 0) + "," + data.getValueAt(i, 1) + "," +
                        data.getValueAt(i, 2) + "," + data.getValueAt(i, 3));
            }

            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не создан/найден");
        }
    }



}
