import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main{
    private static JTextField tf1, tf2;
    private static JButton b1, b2, b3, b4;
    private static JLabel jl1, jl2, jl3;
    private static JFrame f;
    private static JComboBox bx;

    public Main () {
        f = new JFrame();

        jl2 = new JLabel("felhasználónév");
        jl2.setBounds(20, 50, 120, 20);
        tf1 = new JTextField();
        tf1.setBounds(150, 50, 120, 20);
        jl3 = new JLabel("jelszó");
        jl3.setBounds(20, 100, 120, 20);
        tf2 = new JTextField();
        tf2.setBounds(150, 100, 120, 20);
        b1 = new JButton("bejelentkezés");
        b1.setBounds(20, 150, 120, 20);
        b2 = new JButton("regisztrálás");
        b2.setBounds(150, 150, 120, 20);
        jl1 = new JLabel();
        jl1.setBounds(20, 180, 200, 20);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(tf1.getText(), tf2.getText());
                f.getContentPane().removeAll();
                f.revalidate();
                f.repaint();
                dbMenu();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dbm.userAdd(tf1.getText(), tf2.getText())) {
                    jl1.setText("Felhasználó sikeresen regisztrálva");
                }
            }
        });
        f.add(tf1);f.add(tf2);f.add(b1);f.add(b2);f.add(jl1);f.add(jl2);f.add(jl3);

        f.setSize(300, 250);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static DbMethods dbm = new DbMethods();
    static ConsoleMethods cm = new ConsoleMethods();

    public static void login (String nev, String jel) {
        System.out.println("Bejelentkezés:\n");
        int pc = dbm.identification(nev, jel);
        if(pc == 1) {
            jl1.setText("Sikeresen bejelentkezett");
        } else {
            System.out.println("Nincs jogod az adatbázis eléréséhez!");
            System.exit(0);
        }
    }

    public static void dbMenu () {
        String[] options = {"Társasház", "Lakás"};
        String opt;
        bx = new JComboBox(options);
        bx.setBounds(20, 10, 250, 30);
        b1 = new JButton("Listázás");
        b1.setBounds(20, 50, 250, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bx.getItemAt(bx.getSelectedIndex()) == "Társasház") {
                    dbm.listHaz();
                } else dbm.listLakas();
            }
        });
        b2 = new JButton("Beszúrás");
        b2.setBounds(20, 90, 250, 30);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bx.getItemAt(bx.getSelectedIndex()) == "Társasház") {
                    dbm.insertHaz();
                } else dbm.insertLakas();
            }
        });
        b3 = new JButton("Törlés");
        b3.setBounds(20, 130, 250, 30);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bx.getItemAt(bx.getSelectedIndex()) == "Társasház") {
                    dbm.deleteHaz();
                } else dbm.deleteLakas();
            }
        });
        b4 = new JButton("Módosítás");
        b4.setBounds(20, 170, 250, 30);
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bx.getItemAt(bx.getSelectedIndex()) == "Társasház") {

                }
            }
        });
        f.add(bx);f.add(b1);f.add(b2);f.add(b3);f.add(b4);

    }

    public static void main(String[] args) {
        new Main();
        dbm.reg();
        dbm.connect();
        dbm.disconnect();
    }
}
