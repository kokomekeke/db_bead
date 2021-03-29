import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Table extends DefaultTableModel {
    JFrame f;
    JTable jt;
    DefaultTableModel tmodel;
    public Table (Object[] fieldNames, int rows) {
        super(fieldNames, rows);
        f = new JFrame();
        tmodel = new DefaultTableModel();
        jt = new JTable(tmodel);
        tmodel.addColumn("Nem Sándoor:");
        tmodel.insertRow(0, new Object[] {"Sanyi"});
        jt.setBounds(30, 40, 200, 300);
        f.add(jt);
        f.setSize(300, 400);
        f.setVisible(true);
    }

    @Override
    public boolean isCellEditable (int row, int column) {
        return super.isCellEditable(row, column);
    }

    public Class<?> getColumnClass (int index) {
        if (index == 0 || index == 3 || index == 4) return (Integer.class);
        if (index == 1 || index == 2) return (String.class);
        return null;
    }
}
