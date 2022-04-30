import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;
public class Statistiques extends JDialog implements ActionListener{
    private Demineur demin;
    private JPanel panel = new JPanel();
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private JLabel label = new JLabel();
    private JLabel nb = new JLabel();
    private JLabel nbg = new JLabel();
    private JLabel nbp = new JLabel();
    private Border border1;

    public Statistiques(Demineur demin,String title,int nb,int nb_gagnee,int nb_perdue) {
        try {
            jbInit(title);
            pack();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
//private int nb_parties;

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
        panel.setPreferredSize(new Dimension(430, 400));
        nbg.setForeground(Color.black);

        nbg.setPreferredSize(new Dimension(300, 300));
        nbg.setFont(new java.awt.Font("Serif", 1, 50));
        nbg.setText("<html>Gagnées: <br></html>");
        nbp.setPreferredSize(new Dimension(300, 300));
        nbp.setFont(new java.awt.Font("Serif", 1, 50));
        nbp.setText("<html>Perdues: <br></html>");
        nb.setPreferredSize(new Dimension(300, 300));
        nb.setFont(new java.awt.Font("Serif", 1, 50));
        nb.setText("<html>Parties:<br></html>");
        //panel.setBackground(SystemColor.activeCaption);
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
