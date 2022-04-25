import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;


public class Help extends JDialog implements ActionListener {
  private JPanel panel = new JPanel();
  private GridBagLayout gridBagLayout = new GridBagLayout();
  private JLabel titre = new JLabel();
  private JLabel info = new JLabel();
  private JLabel lien = new JLabel();
  private Border border1;

  public Help(Demineur demineur, String title, boolean modal) {
    try {
      jbInit(title);
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
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
    titre.setFont(new java.awt.Font("Serif", 1, 20));
    titre.setForeground(SystemColor.info);
    titre.setBorder(border1);
    titre.setPreferredSize(new Dimension(131, 50));
    titre.setText(title);
    info.setForeground(Color.black);
    info.setPreferredSize(new Dimension(300, 300));

    info.setText("<html>Ceci est un jeu demineur implemente en Java<br>" +
            "Le Démineur est un jeu vidéo de réflexion dont le but est de decouvrir toutes les cases en faisant attention a <br>" +
            "ne decouvrir ceux contenant des mines" +"<br>" +
                    "Le champ de mines du Démineur est représenté par une grille, qui peut avoir différentes formes : deux ou trois dimensions, pavage rectangulaire ou non, etc.\n" +
                    "Chaque case de la grille peut soit cacher une mine, soit être vide. Le but du jeu est de découvrir toutes les cases libres sans faire exploser les mines, c'est-à-dire sans cliquer sur les cases qui les dissimulent.\n" +
                    "Lorsque le joueur clique sur une case libre comportant au moins une mine dans l'une de ses cases avoisinantes, un chiffre apparaît, indiquant ce nombre de mines. Si en revanche toutes les cases adjacentes sont vides, une case vide est affichée et la même opération est répétée sur ces cases, et ce jusqu'à ce que la zone vide soit entièrement délimitée par des chiffres. En comparant les différentes informations récoltées, le joueur peut ainsi progresser dans le déminage du terrain. S'il se trompe et clique sur une mine, il a perdu."+
            "</html>");
    lien.setForeground(Color.red);
    lien.setText("https://fr.wikipedia.org/wiki/D%C3%A9mineur_(genre_de_jeu_vid%C3%A9o)");
    panel.setBackground(SystemColor.activeCaption);
    getContentPane().add(panel);
    panel.add(titre,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel.add(info,      new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel.add(lien,    new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

  }

  public void actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}
