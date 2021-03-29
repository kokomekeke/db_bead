import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DbMethods {
    private Statement s = null;
    private Connection c = null;
    private ResultSet rs = null;

    private JFrame jf;
    private JLabel jl1, jl2, jl3, jl4, jl5;
    private DefaultTableModel tmodel;
    private JTable jt;
    private JScrollPane jsp;
    private JTextField jtf, jtf2, jtf3, jtf4, jtf5;
    private JButton b1;
    private JComboBox bx;
    private String kod;
    private String[] options;

    public void reg (){
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Sikeres driver regisztráció");
        } catch (ClassNotFoundException e) {
            System.out.println("Hibas driver regisztráció!" + e.getMessage());
        }
    }

    public void connect () {
        try {
            String url = "jdbc:sqlite:E:/Desktop/programok/java/beadando/tarsashaz.db";
            c = DriverManager.getConnection(url);
            System.out.println("Connection OK!");
        } catch (SQLException e) {
            System.out.println("JDBC Connect: " + e.getMessage());
        }
    }

    public void disconnect () {
        try {
            c.close();
            System.out.println("Disconnection OK!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //login metódusok
    public int identification (String name, String pswd) {
        connect();

        int pc=-1;
        String sqlp= "select count(*) pc from user where name='"+name+"' and pswd='"+pswd+"';";

        try {
            s = c.createStatement();
            rs = s.executeQuery(sqlp);

            while(rs.next()) {
                pc = rs.getInt("pc");
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //disconnect();
        return pc;
    }

    public boolean userAdd (String name, String password) {
        boolean siker = false;
        connect();
        String sqlp = "insert into user values('"+name+"','"+password+"');";
        try {
            s = c.createStatement();
            s.execute(sqlp);
            System.out.println("Command OK!");
            siker = true;
        } catch (SQLException e) {
            System.out.println("Command Executed: " + e.getMessage());
        }
        disconnect();

        return siker;
    }

    public void commandExec (String command) {
        connect();
        String sqlp = command;
        try {
            s = c.createStatement();
            s.execute(sqlp);
            System.out.println("Command OK!");
        } catch (SQLException e) {
            System.out.println("Command Executed: " + e.getMessage());
        }
        disconnect();
    }

    public void listHaz () {
        jf = new JFrame();
        tmodel = new DefaultTableModel();
        jt = new JTable(tmodel);
        jsp = new JScrollPane(jt);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(jt.getModel());
        jt.setRowSorter(sorter);

        String cim = "", terulet = "";
        int haz_id = 0, kozos_koltseg = 0, szintek_szama = 0;
        String sqlp="select haz_id,cim,terulet,kozos_koltseg,szintek_szama from tarsashaz";

        tmodel.addColumn("haz_id");
        tmodel.addColumn("cim");
        tmodel.addColumn("terület");
        tmodel.addColumn("kozos_koltseg");
        tmodel.addColumn("szintek_szama");

        try {
            s = c.createStatement();
            rs = s.executeQuery(sqlp);

            while(rs.next()) {
                haz_id = rs.getInt("haz_id");
                cim = rs.getString("cim");
                terulet = rs.getString("terulet");
                kozos_koltseg = rs.getInt("kozos_koltseg");
                szintek_szama = rs.getInt("szintek_szama");

                tmodel.addRow(new Object[]{haz_id, cim, terulet, kozos_koltseg, szintek_szama});

                System.out.println(haz_id+cim+terulet+kozos_koltseg+szintek_szama+"\n");

            }
            jf.add(jsp);
            jf.setBounds(0, 0, 300, 190);
            jf.setVisible(true);
            jf.setLayout(null);
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listLakas () {
        jf = new JFrame();
        tmodel = new DefaultTableModel();
        jt = new JTable(tmodel);
        jsp = new JScrollPane(jt);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(jt.getModel());
        jt.setRowSorter(sorter);

        int lakas_id=0, hazid=0;
        String terulet="", berlo="";
        String sqlp="select lakas_id,hazid,terulet,berlo from lakas";

        tmodel.addColumn("lakas_id");
        tmodel.addColumn("terulet");
        tmodel.addColumn("berlo");
        tmodel.addColumn("hazid");

        try {
            s = c.createStatement();
            rs = s.executeQuery(sqlp);

            while(rs.next()) {
                lakas_id = rs.getInt("lakas_id");
                terulet = rs.getString("terulet");
                berlo = rs.getString("berlo");
                hazid = rs.getInt("hazid");

                tmodel.addRow(new Object[]{lakas_id, terulet, berlo, hazid});

                System.out.println(lakas_id+terulet+berlo+hazid+"\n");

            }
            jf.add(jsp);
            jf.setBounds(0, 0, 300, 190);
            jf.setVisible(true);
            jf.setLayout(null);
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertHaz () {
        jf = new JFrame();

        jl1 = new JLabel("haz_id:");
        jl1.setBounds(20, 20, 100, 30);
        jtf = new JTextField();
        jtf.setBounds(140, 20, 100, 30);

        jl2 = new JLabel("cim: ");
        jl2.setBounds(20, 50, 100, 30);
        jtf2 = new JTextField();
        jtf2.setBounds(140, 50, 100, 30);

        jl3 = new JLabel("terulet: ");
        jl3.setBounds(20, 80, 100, 30);
        jtf3 = new JTextField();
        jtf3.setBounds(140, 80, 100, 30);

        jl4 = new JLabel("kozos_koltseg: ");
        jl4.setBounds(20, 110, 100, 30);
        jtf4 = new JTextField();
        jtf4.setBounds(140, 110, 100, 30);

        jl5 = new JLabel("szintek_szama: ");
        jl5.setBounds(20, 140, 100, 30);
        jtf5 = new JTextField();
        jtf5.setBounds(140, 140, 100, 30);

        b1 = new JButton("Beszúr");
        b1.setBounds(20, 200, 220, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sqlp = "insert into tarsashaz values("+jtf.getText()+",'"+jtf2.getText()+"','"+jtf3.getText()+"',"+jtf4.getText()+","+jtf5.getText()+")";

                try {
                    s = c.createStatement();
                    s.execute(sqlp);
                    System.out.println("Insert OK!");
                } catch (SQLException ee) {
                    System.out.println("JDBC insert: " + ee.getMessage());
                }
            }
        });
        jf.add(jl1);jf.add(jtf);jf.add(jl2);jf.add(jtf2);jf.add(jl3);jf.add(jtf3);jf.add(jl4);jf.add(jtf4);jf.add(jl5);jf.add(jtf5);jf.add(b1);
        jf.setBounds(0, 0, 280, 300);
        jf.setLayout(null);
        jf.setVisible(true);
    }

    public void insertLakas () {
        jf = new JFrame();

        jl1 = new JLabel("lakas_id:");
        jl1.setBounds(20, 20, 100, 30);
        jtf = new JTextField();
        jtf.setBounds(140, 20, 100, 30);

        jl2 = new JLabel("terulet: ");
        jl2.setBounds(20, 50, 100, 30);
        jtf2 = new JTextField();
        jtf2.setBounds(140, 50, 100, 30);

        jl3 = new JLabel("berlo: ");
        jl3.setBounds(20, 80, 100, 30);
        jtf3 = new JTextField();
        jtf3.setBounds(140, 80, 100, 30);

        jl4 = new JLabel("hazid: ");
        jl4.setBounds(20, 110, 100, 30);
        jtf4 = new JTextField();
        jtf4.setBounds(140, 110, 100, 30);

        b1 = new JButton("Beszúr");
        b1.setBounds(20, 170, 220, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sqlp = "insert into lakas values("+jtf.getText()+",'"+jtf2.getText()+"','"+jtf3.getText()+"',"+jtf4.getText()+")";

                try {
                    s = c.createStatement();
                    s.execute(sqlp);
                    System.out.println("Insert OK!");
                } catch (SQLException ee) {
                    System.out.println("JDBC insert: " + ee.getMessage());
                }
            }
        });
        jf.add(jl1);jf.add(jtf);jf.add(jl2);jf.add(jtf2);jf.add(jl3);jf.add(jtf3);jf.add(jl4);jf.add(jtf4);jf.add(b1);
        jf.setBounds(0, 0, 280, 300);
        jf.setLayout(null);
        jf.setVisible(true);
    }

    public void deleteHaz () {
        jf = new JFrame();
        jtf = new JTextField("Adja meg a törlendő társasház kódját");
        jtf.setBounds(20, 30, 250, 30);
        b1 = new JButton("Törlés");
        b1.setBounds(20, 90, 250, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kod = jtf.getText();
                String sqlp = "delete from tarsashaz where haz_id="+kod+";";

                try {
                    s = c.createStatement();
                    s.executeQuery(sqlp);
                    System.out.println("Insert OK!");
                } catch (SQLException ee) {
                    System.out.println("JDBC insert: " + ee.getMessage());
                }
            }
        });
        jf.add(jtf);jf.add(b1);
        jf.setBounds(0,0,300,200);
        jf.setLayout(null);
        jf.setVisible(true);
    }

    public void deleteLakas () {
        jf = new JFrame();
        jtf = new JTextField("Adja meg a törlendő lakás kódját");
        jtf.setBounds(20, 30, 250, 30);
        b1 = new JButton("Törlés");
        b1.setBounds(20, 90, 250, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kod = jtf.getText();
                String sqlp = "delete from lakas where lakas_id="+kod+";";

                try {
                    s = c.createStatement();
                    s.executeQuery(sqlp);
                    System.out.println("Insert OK!");
                } catch (SQLException ee) {
                    System.out.println("JDBC insert: " + ee.getMessage());
                }
            }
        });
        jf.add(jtf);jf.add(b1);
        jf.setBounds(0,0,300,200);
        jf.setLayout(null);
        jf.setVisible(true);
    }

    public void modifyHaz () {
        jf = new JFrame();

        options = new String[] {"haz_id", "cim", "terulet", "kozos_koltseg", "szintek_szama"};
        bx = new JComboBox(options);

        jl1 = new JLabel();

        String sqlp = "update tarsashaz set iq="+iq+" where kod="+kod;

        try{
            s = c.createStatement();
            s.execute(sqlp);
            System.out.println("Insert OK!");
        } catch (SQLException e) {
            System.out.println("JDBC insert: " + e.getMessage());
        }
    }

    public void modifyLakas () {

    }
}
