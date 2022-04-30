import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Demineur extends JFrame implements MouseListener, ActionListener {
  private JPanel panneauBas = new JPanel();
  private JPanel panneauJeux = new JPanel();
  private GridBagLayout layoutPanneauJeux = new GridBagLayout();
  private JPanel panneauMines = new JPanel();
  private JPanel panneauTemps = new JPanel();
  private JLabel affMines = new JLabel();
  private JButton boutonNouveau = new JButton();
  private JLabel affTemps = new JLabel();
  private JMenuBar menu = new JMenuBar();
  private JMenu partie = new JMenu("Partie");
  private JCheckBox pause = new JCheckBox("Pause");
  private JMenu help = new JMenu("?");
  private JButton stat = new JButton("Statisques");
  JDialog jd = new JDialog();
  JLabel jLabel = new JLabel("Vous avez perdu.");

  JLabel labM = new JLabel("Mines:");
  JLabel labT = new JLabel("Temps:");
  private JMenuItem menuNouveau = new JMenuItem("Nouveau");
  JCheckBoxMenuItem menuDebutant = new JCheckBoxMenuItem("Débutant");
  JCheckBoxMenuItem menuIntermediaire = new JCheckBoxMenuItem("Intermediaire");
  JCheckBoxMenuItem menuExpert = new JCheckBoxMenuItem("Expert");
  private JMenuItem apropos = new JMenuItem("Description");
  private BoxLayout layoutpanneauBas = new BoxLayout(panneauBas,
          BoxLayout.LINE_AXIS);
  private Component box2;
  private Component box3;
  private Component box1;
  private Component box4;


  int nDrapeau = 0;
  protected int nMines;
  private int largeur;
  private int hauteur;
  protected int nCases;
  Traitement[][] jeux;
  private String mines;
  private int[][] casesSelectionnees = new int[8][2];
  private Temps temps = new Temps(affTemps);
  private int nb_gagnee;
  private int nb_perdue;
  private int nb;//nombre de parties
  private int TYPE;



  public Demineur(int hauteur, int largeur, int mines, int type) {
    this.hauteur = hauteur;
    this.largeur = largeur;
    nCases = this.hauteur * this.largeur;
    nMines = mines;
    TYPE = type;
    jeux = new Traitement[this.hauteur][this.largeur];


    for (int i = 0; i < this.hauteur; i++) {
      for (int j = 0; j < this.largeur; j++) {
        jeux[i][j] = new Traitement();
      }
    }


    if (type == 1)
      menuDebutant.setSelected(true);
    if (type == 2)
      menuIntermediaire.setSelected(true);
    if (type == 3)
      menuExpert.setSelected(true);

    // initialisation
    nouveau();

    try {
      jbInit();
      this.setVisible(true);
      boutonNouveau.requestFocus();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public  int getNb_gagnee(){return nb_gagnee;}
  public  int getNb_perdue(){return nb_perdue;}
  public  int getNb(){return nb;}
  public static void perdre(){
    JFrame jFrame = new JFrame();
    JDialog jd = new JDialog(jFrame);
    jd.setLayout(new FlowLayout());
    jd.setBounds(500, 300, 200, 200);
    JLabel jLabel = new JLabel("VOUS AVEZ PERDU");
    JButton jButton = new JButton("FERMER");
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jd.setVisible(false);
      }
    });
    jd.add(jLabel);
    jd.add(jButton);
    jd.setVisible(true);
  }
  public static void gagner(){
    JFrame jFrame = new JFrame();
    JDialog jd = new JDialog(jFrame);
    jd.setLayout(new FlowLayout());
    jd.setBounds(500, 300, 200, 200);
    JLabel jLabel = new JLabel("VOUS AVEZ GAGNE");
    JButton jButton = new JButton("FERMER");
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jd.setVisible(false);
      }
    });
    jd.add(jLabel);
    jd.add(jButton);
    jd.setVisible(true);
  }

  public void nouveau() {
    temps.cancel();
    nDrapeau = 0;
    nCases = hauteur * largeur;
    affMines.setText(String.valueOf(nMines));
    nb++;
    affTemps.setText(String.valueOf(0));
    panneauJeux.setVisible(true);
    pause.setSelected(false);


    mines = "";
    for (int i = 0; i < nMines; i++)
      mines = mines + "1";
    while (mines.length() < hauteur * largeur) {
      int i = (int) (Math.random() * (mines.length() + 1));
      mines = mines.substring(0, i) + "0" + mines.substring(i);
    }


    for (int i = 0; i < hauteur; i++) {
      for (int j = 0; j < largeur; j++) {
        jeux[i][j].reset();
        jeux[i][j].removeMouseListener(this);
        jeux[i][j].addMouseListener(this);
        if (mines.charAt(i * largeur + j) == '1') {
          jeux[i][j].setMine(true);
        }
      }
    }
    repaint();

    for (int i = 0; i < hauteur; i++) {
      for (int j = 0; j < largeur; j++) {
        if (!jeux[i][j].isMine()) {
          int n = 0;
          try {
            if (jeux[i - 1][j - 1].isMine())
              n++;
          } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (jeux[i - 1][j].isMine())
              n++;
          } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (jeux[i - 1][j + 1].isMine())
              n++;
          } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (jeux[i][j - 1].isMine())
              n++;
          } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (jeux[i][j + 1].isMine())
              n++;
          } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (jeux[i + 1][j - 1].isMine())
              n++;
          } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (jeux[i + 1][j].isMine())
              n++;
          } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          }
          try {
            if (jeux[i + 1][j + 1].isMine())
              n++;
          } catch (java.lang.ArrayIndexOutOfBoundsException e) {
          }
          jeux[i][j].setChiffre(n);
        }
      }
    }
  }

  private void jbInit() throws Exception {
    box2 = Box.createGlue(); // utilis�s dans le jPanel1 pour la disposition
    box3 = Box.createGlue();
    box1 = Box.createHorizontalStrut(8);
    box1.setSize(5, 50);
    box4 = Box.createHorizontalStrut(8);
    box4.setSize(5, 50);

    int tailleX = largeur * 30 + 50;
    int tailleY = hauteur * 30 + 50;
    this.setSize(tailleX + 6, tailleY + 50 + 23 + 25);
    this.setTitle("Demineur");
    this.setResizable(true);

    menuNouveau.addActionListener(this);
    menuDebutant.addActionListener(this);
    menuIntermediaire.addActionListener(this);
    menuExpert.addActionListener(this);
    partie.add(menuNouveau);
    partie.add(new JSeparator());
    partie.add(menuDebutant);
    partie.add(menuIntermediaire);
    partie.add(menuExpert);
    menu.setBorderPainted(false);
    menu.add(partie);
    pause.setOpaque(false);
    pause.setFocusPainted(false);
    pause.addActionListener(this);
    menu.add(pause);
    apropos.addActionListener(this);
    help.add(apropos);
    menu.add(help);
    menu.add(stat);
    stat.addActionListener(this);
    this.setJMenuBar(menu);

    affMines.setMaximumSize(new Dimension(49, 27));
    affTemps.setMaximumSize(new Dimension(49, 27));
    panneauBas.setPreferredSize(new Dimension(450, 9));
    panneauBas.setLayout(layoutpanneauBas);
    panneauJeux.setPreferredSize(new Dimension(tailleX, tailleY));
    panneauJeux.setLayout(layoutPanneauJeux);
    affMines.setText(String.valueOf(nMines));
    affMines.setFont(new java.awt.Font("Serif", 1, 20));

    affTemps.setText(String.valueOf(0));
    affTemps.setFont(new java.awt.Font("Serif", 1, 20));
    boutonNouveau.setPreferredSize(new Dimension(25, 25));
    boutonNouveau.setFocusPainted(false);
    boutonNouveau.setMargin(new Insets(0, 0, 0, 0));
    boutonNouveau.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        boutonNouveau_actionPerformed(e);
      }
    });
    affMines.setForeground(Color.black);
    panneauMines.add(affMines);
    panneauMines.setBackground(new Color(70, 130, 255));
    panneauMines.setPreferredSize(new Dimension(5,2));

    panneauTemps.add(affTemps);
    panneauTemps.setBackground(new Color(255, 70, 70));
    panneauTemps.setPreferredSize(new Dimension(5,2));


    panneauBas.add(box1, null);
    panneauBas.add(labM,null);
    panneauBas.add(panneauMines, null);
    panneauBas.add(box2, null);
    panneauBas.add(pause, null);
    panneauBas.add(box3, null);
    panneauBas.add(labT,null);
    panneauBas.add(panneauTemps, null);
    this.getContentPane().add(panneauBas, BorderLayout.CENTER);
    this.getContentPane().add(panneauJeux, BorderLayout.NORTH);


    Design gr = new Design(this.getGraphicsConfiguration());

    for (int i = 0; i < hauteur; i++) {
      for (int j = 0; j < largeur; j++) {
        jeux[i][j].setGraphisme(gr);
        panneauJeux.add(jeux[i][j],
                new GridBagConstraints(j, i, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
      }
    }
  }

  public int[] caseClic(int x, int y) {
    int offsetX = (int) jeux[0][0].getX() + 3;
    int offsetY = (int) jeux[0][0].getY() + 22;
    int posx = -1, posy = -1;
    if (x - offsetX >= 0)
      posx = (x - offsetX) / 16;
    if (posx >= largeur)
      posx = -1;
    if (y - offsetY >= 0 && posx != -1)
      posy = (y - offsetY) / 16;
    if (posy >= hauteur)
      posy = -1;
    if (posy == -1)
      posx = -1;
    int[] retour = {
            posx, posy };
    return retour;
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    try {
      int x = (int) ((JPanel) e.getSource()).getLocation().getX() + e.getX() +
              3;
      int y = (int) ((JPanel) e.getSource()).getLocation().getY() + e.getY() +
              22;
      int[] coord = caseClic(x, y); // coordonn�es de la case enfonc�e enregistr�es dans coord

      if (e.getButton() == e.BUTTON3 && coord[1] != -1 && coord[0] != -1) {
        int temp = jeux[coord[1]][coord[0]].getEtat();
        switch (temp) {
          case 0:
            jeux[coord[1]][coord[0]].setEtat(2);
            nDrapeau++;
            affMines.setText(String.valueOf(nMines - nDrapeau));
            break;
          case 2:
            jeux[coord[1]][coord[0]].setEtat(3);
            nDrapeau--;
            affMines.setText(String.valueOf(nMines - nDrapeau));
            break;
          case 3:
            jeux[coord[1]][coord[0]].setEtat(0);
            break;
        }
        jeux[coord[1]][coord[0]].repaint();
      }

      y = coord[1];
      x = coord[0];
      if (e.getButton() == e.BUTTON1 && x != -1 && y != -1 &&
              jeux[y][x].getEtat() == 1 && jeux[y][x].getChiffre() != 0) {
      }
    } catch (java.lang.ClassCastException ex) {
    }
  }

  public void mouseReleased(MouseEvent e) {

    if (nCases == hauteur * largeur && e.getButton() == e.BUTTON1) {
      temps.cancel();
      temps = new Temps(affTemps);
      temps.start();
    }

    try {
      int x = (int) ((JPanel) e.getSource()).getLocation().getX() + e.getX() +
              3;
      int y = (int) ((JPanel) e.getSource()).getLocation().getY() + e.getY() +
              22;
      int[] coord = caseClic(x, y);
      if (coord[0] != -1 && coord[1] != -1) {
        y = coord[1];
        x = coord[0];
        if (e.getButton() == e.BUTTON1) {
          decouvre(y, x);
          repaint();
        }
        jeux[y][x].setSelected(false);
      }
    } catch (java.lang.ClassCastException ex) {
    }
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  void boutonNouveau_actionPerformed(ActionEvent e) {
    if (!pause.isSelected())
      nouveau();
  }

  public void decouvre(int y, int x) {
    // Si la case est vide ou avec un ?
    if ((jeux[y][x].getEtat() == 0 || jeux[y][x].getEtat() == 3) && !jeux[y][x].isMine())
    {
      nCases--; // nombre de cases non decouvertes
      jeux[y][x].setEtat(1);
      if (jeux[y][x].getChiffre() == 0) {
        retourner(x - 1, y - 1);
        retourner(x - 1, y);
        retourner(x - 1, y + 1);
        retourner(x, y - 1);
        retourner(x, y + 1);
        retourner(x + 1, y - 1);
        retourner(x + 1, y);
        retourner(x + 1, y + 1);
      }
    }

    else if ((jeux[y][x].getEtat() == 0 || jeux[y][x].getEtat() == 3) && jeux[y][x].isMine()) {
      temps.cancel();
      jeux[y][x].setEtat(4);
      for (int i = 0; i < hauteur; i++) {
        for (int j = 0; j < largeur; j++) {
          jeux[i][j].removeMouseListener(this);
          jeux[i][j].setBlocked(true);
          if (!(y == i && x == j) && mines.charAt(i * largeur + j) == '1' && jeux[i][j].getEtat() != 2)
            jeux[i][j].setEtat(5);
        }
      }
      perdre();
    nb_perdue++;
    }
    if (nCases == nMines && !jeux[0][0].isBlocked()) {
      temps.cancel();
      affMines.setText(String.valueOf(0));
      for (int i = 0; i < hauteur; i++) {
        for (int j = 0; j < largeur; j++) {
          jeux[i][j].removeMouseListener(this);
          jeux[i][j].setBlocked(true);
          if (jeux[i][j].isMine())
            jeux[i][j].setEtat(2);
        }
      }
      gagner();
      nb_gagnee++;
    }
  }

  public void retourner(int x, int y) {
    if (x >= 0 && y >= 0 && x < largeur && y < hauteur) {
      if (jeux[y][x].getEtat() == 0 && jeux[y][x].getChiffre() != 0) {
        jeux[y][x].setEtat(1);
        nCases--;
      }
      if (jeux[y][x].getEtat() == 0 && jeux[y][x].getChiffre() == 0)
        decouvre(y, x);
    }
  }


  public void windowClosing(WindowEvent e) {
    temps.stop();
    System.exit(0);
  }


  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == menuNouveau)
      nouveau();
    else if (e.getSource() == menuDebutant && TYPE != 1) {
      this.dispose(); // on d�truit la fenetre
      System.gc();
      if (TYPE == 1)
        menuDebutant.setSelected(true);
      Demineur demineur = new Demineur(9, 9, 10, 1); // et on en refait une
    }
    else if (e.getSource() == menuDebutant && !menuDebutant.isSelected())
      menuDebutant.setSelected(true);
    else if (e.getSource() == menuIntermediaire && TYPE != 2) {
      this.dispose();
      System.gc();
      if (TYPE == 2)
        menuIntermediaire.setSelected(true);
      Demineur demineur = new Demineur(16, 16, 40, 2);
    } else if (e.getSource() == menuIntermediaire &&
            !menuIntermediaire.isSelected())
      menuIntermediaire.setSelected(true);
    else if (e.getSource() == menuExpert && TYPE != 3) {
      this.dispose();
      System.gc();
      if (TYPE == 3)
        menuExpert.setSelected(true);
      Demineur demineur = new Demineur(16, 30, 99, 3);
    } else if (e.getSource() == menuExpert && TYPE != 4)
      menuExpert.setSelected(true);
    else if (e.getSource() == pause) {
      if (pause.isSelected()) {
        panneauJeux.setVisible(false);
        temps.suspend();
      } else {
        panneauJeux.setVisible(true);
        temps.resume();
      }
    } else if (e.getSource() == apropos) {
      Help app = new Help(this, "Demineur", true);
      app.setLocation((int) this.getLocation().getX() + 20,
              (int) this.getLocation().getY() + 20);
      app.setVisible(true);
    }
    else if (e.getSource() == stat) {
      Statistiques app = new Statistiques(this,"Statistiques",getNb(),getNb_gagnee(),getNb_perdue());
      app.setLocation((int) this.getLocation().getX() + 20,
              (int) this.getLocation().getY() + 20);
      app.setVisible(true);
    }
  }
}
class Help extends JDialog implements ActionListener {
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