import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;
public class Statistiques extends JDialog implements ActionListener{
    private JPanel panel = new JPanel();
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private JLabel nb = new JLabel();
    private JLabel nbg = new JLabel();
    private JLabel nbp = new JLabel();
    private Border border1;
    public Statistiques(Demineur demineur, String title, boolean modal) {
        try {
            jbInit(title);
            pack();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
//private int nb_parties;
private int nb_gagnee;
private int nb_perdue;

public int gagner(){
    return nb_gagnee++;
}
public int perdu(){
return nb_perdue++;
}
public int  parties(){
    return nb_perdue+nb_gagnee;
}
    private void jbInit(String title) throws Exception {
        border1 = BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(
                        BevelBorder.RAISED,SystemColor.info,
                        SystemColor.info,
                        new Color(109, 109, 109),
                        new Color(156, 156, 156)
                ),
                BorderFactory.createEmptyBorder(0,10,0,10)
        );
        panel.setLayout(gridBagLayout);
        panel.setPreferredSize(new Dimension(530, 400));
        nb.setFont(new java.awt.Font("Serif", 1, 20));
        //nb.setForeground(SystemColor.info);
        nb.setBorder(border1);
        nb.setPreferredSize(new Dimension(131, 50));
        nb.setText("Nombre de parties");
        nbg.setForeground(Color.black);
        nbg.setPreferredSize(new Dimension(300, 300));

        nbg.setText("Nombre de Parties gagnées:");
        nbp.setForeground(Color.red);
        nbp.setText("Nombre de parties perdues:");
        panel.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel);
        panel.add(nb,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(nbg,      new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(nbp,    new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    }
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}
