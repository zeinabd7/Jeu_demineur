import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Demineur extends JFrame implements MouseListener, WindowListener, ActionListener {
  private JPanel panneauBas = new JPanel();
  private JPanel panneauJeux = new JPanel();
  private GridBagLayout layoutPanneauJeux = new GridBagLayout();
  private JPanel panneauMines = new JPanel();
  private JPanel panneauTemps = new JPanel();
  private JLabel affMines = new JLabel(); // l'afficheur du nombre de mines
  private JButton boutonNouveau = new JButton();
  // private JCheckBox pause1 = new JCheckBox("Pause1");
  private JLabel affTemps = new JLabel(); // l'afficheur du temps �coul�
  private Border borderPanneaux;
  private JMenuBar menu = new JMenuBar();
  private JMenu partie = new JMenu("Partie");
  private JCheckBox pause = new JCheckBox("Pause");
  private JMenu help = new JMenu("?");


  JLabel labM = new JLabel("Mines:");
  JLabel labT = new JLabel("Temps:");
  private JMenuItem menuNouveau = new JMenuItem("Nouveau");
  JCheckBoxMenuItem menuDebutant = new JCheckBoxMenuItem("Débutant");
  JCheckBoxMenuItem menuIntermediaire = new JCheckBoxMenuItem("Intermediaire");
  JCheckBoxMenuItem menuExpert = new JCheckBoxMenuItem("Expert");
  private JMenuItem apropos = new JMenuItem("A propos");
  private BoxLayout layoutpanneauBas = new BoxLayout(panneauBas,
          BoxLayout.LINE_AXIS);
  private Component box2; // Boxes utilis�es dans le BoxLayout
  private Component box3;
  private Component box1;
  private Component box4;

  private Icon cool, oups, boum, win; // images du bouton

  int nDrapeau = 0; // nombres de drapeaux pos�s
  private int nMines; // nombre total de mines
  private int LARGEUR; // nombre de cases selon la largeur
  private int HAUTEUR; // nombre de cases selon la hauteur
  private int nCases; // nombre de cases non d�couvertes restantes
  DeminCase[][] jeux; // tableau des cases du jeux
  private String mines; // cha�nes de caract�re qui contient la r�partition des mines
  private int[][] casesSelectionnees = new int[8][2]; // reperages des cases selectionnees pour les deselectionnes lors
  // du relachement de la sourie
  private Temps temps = new Temps(affTemps); // timer sur l'affichage Segment affTemps
  private int TYPE;

  // constructeur en fonction du nombre de cases, de mines et du type.
  // Le type permet de selectionner le bon mode dans le menu.
  // type == 1 -> D�butant
  // type == 2 -> Interm�diaire
  // type == 3 -> Expert
  public Demineur(int hauteur, int largeur, int mines, int type) {
    HAUTEUR = hauteur;
    LARGEUR = largeur;
    nCases = HAUTEUR * LARGEUR;
    nMines = mines;
    TYPE = type;
    jeux = new DeminCase[HAUTEUR][LARGEUR];

    // R�cup�rer les gif dans le fichier .jar
    // URL imgUrl;

    // cr�ation des cases
    for (int i = 0; i < HAUTEUR; i++) {
      for (int j = 0; j < LARGEUR; j++) {
        jeux[i][j] = new DeminCase();
      }
    }

    // s�l�ction du bon mode dans le JMenu
    if (type == 1)
      menuDebutant.setSelected(true);
    if (type == 2)
      menuIntermediaire.setSelected(true);
    if (type == 3)
      menuExpert.setSelected(true);

    // initialisation
    nouveau();

    try {
      // Graphisme
      jbInit();
      this.setVisible(true);
      boutonNouveau.requestFocus();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // initialises le jeux
  public void nouveau() {
    // r�initialisation des principaux param�tres
    temps.cancel(); // Timer � 0
    boutonNouveau.setIcon(cool); // Icon par d�faut du bouton
    nDrapeau = 0;
    nCases = HAUTEUR * LARGEUR;
    affMines.setText(String.valueOf(nMines));

    affTemps.setText(String.valueOf(0));
    panneauJeux.setVisible(true); // peut �tre � false en raison de la pause
    pause.setSelected(false);

    // G�n�ration des mines
    // dans la cha�ne, 1=mine 0=rien
    // on cr�� le bon nombre de mines puis on compl�te par des cases vides jusqu'�
    // obtenir le nombre de cases total
    mines = "";
    for (int i = 0; i < nMines; i++)
      mines = mines + "1";
    while (mines.length() < HAUTEUR * LARGEUR) {
      int i = (int) (Math.random() * (mines.length() + 1));
      mines = mines.substring(0, i) + "0" + mines.substring(i);
    }

    // param�tres des cases
    for (int i = 0; i < HAUTEUR; i++) {
      for (int j = 0; j < LARGEUR; j++) {
        jeux[i][j].reset();
        jeux[i][j].removeMouseListener(this); // n�c�ssaire pour �viter un bug lors de l'appel de nouveau() une 2�me
        // fois
        jeux[i][j].addMouseListener(this); // pour les clics!!!
        if (mines.charAt(i * LARGEUR + j) == '1') {
          jeux[i][j].setMine(true);
        }
      }
    }
    repaint();

    // comptage pour chaque case du nombre de mines autour
    for (int i = 0; i < HAUTEUR; i++) {
      for (int j = 0; j < LARGEUR; j++) {
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
          jeux[i][j].setChiffre(n); // on indique � la case le nombre de mines
        }
      }
    }
  }

  private void jbInit() throws Exception {
    borderPanneaux = BorderFactory.createEtchedBorder(Color.white,
            new Color(70, 130, 255));
    box2 = Box.createGlue(); // utilis�s dans le jPanel1 pour la disposition
    box3 = Box.createGlue();
    box1 = Box.createHorizontalStrut(8);
    box1.setSize(5, 50);
    box4 = Box.createHorizontalStrut(8);
    box4.setSize(5, 50);

    this.addWindowListener(this);

    int tailleX = LARGEUR * 16 + 20; // 20 pour la marge
    int tailleY = HAUTEUR * 16 + 20;
    if (tailleX < 160)
      tailleX = 150; // taille minimum en largeur

    this.setSize(tailleX + 6, tailleY + 50 + 23 + 25); // 6=largeur du cadre de la fenetre, 25=hauteur de la barre
    // windows
    this.setTitle("Demineur");
    this.setResizable(true);

    // MENU
    partie.setMnemonic('P');
    menuNouveau.addActionListener(this);
    menuNouveau.setMnemonic('N');
    menuDebutant.addActionListener(this);
    menuDebutant.setMnemonic('D');
    menuIntermediaire.addActionListener(this);
    menuIntermediaire.setMnemonic('I');
    menuExpert.addActionListener(this);
    menuExpert.setMnemonic('E');
    partie.add(menuNouveau);
    partie.add(new JSeparator());
    partie.add(menuDebutant);
    partie.add(menuIntermediaire);
    partie.add(menuExpert);
    menu.setBorderPainted(false);
    menu.add(partie);
    pause.setMnemonic('a');
    pause.setOpaque(false);
    pause.setFocusPainted(false);
    pause.addActionListener(this);
    menu.add(pause);
    help.setMnemonic('?');
    apropos.addActionListener(this);
    apropos.setMnemonic('A');
    help.add(apropos);
    menu.add(help);
    this.setJMenuBar(menu);

    affMines.setMaximumSize(new Dimension(49, 27));
    affTemps.setMaximumSize(new Dimension(49, 27));
    boutonNouveau.setMaximumSize(new Dimension(25, 25));
    boutonNouveau.setMinimumSize(new Dimension(25, 25));
    panneauBas.setBorder(borderPanneaux);
    panneauBas.setPreferredSize(new Dimension(450, 9));
    panneauBas.setLayout(layoutpanneauBas);
    panneauJeux.setBorder(borderPanneaux);
    panneauJeux.setPreferredSize(new Dimension(tailleX, tailleY));
    panneauJeux.setLayout(layoutPanneauJeux);
    affMines.setText(String.valueOf(nMines));
    affMines.setFont(new java.awt.Font("Serif", 1, 20));
    // menu.add(boutonNouveau);
    affTemps.setText(String.valueOf(0));
    affTemps.setFont(new java.awt.Font("Serif", 1, 20));
    boutonNouveau.setPreferredSize(new Dimension(25, 25));
    boutonNouveau.setFocusPainted(false);
    boutonNouveau.setIcon(cool);
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
    // panneauBas.add(boutonNouveau, null);
    panneauBas.add(pause, null);
    panneauBas.add(box3, null);
    panneauBas.add(labT,null);
    panneauBas.add(panneauTemps, null);
    this.getContentPane().add(panneauBas, BorderLayout.CENTER);
    this.getContentPane().add(panneauJeux, BorderLayout.NORTH);

    // gr contient les graphismes de toutes les cases
    Graphisme gr = new Graphisme(this.getGraphicsConfiguration());

    // placement des cases dans la fen�tre
    for (int i = 0; i < HAUTEUR; i++) {
      for (int j = 0; j < LARGEUR; j++) {
        jeux[i][j].setGraphisme(gr); // on indique les graphismes � la cases
        panneauJeux.add(jeux[i][j],
                new GridBagConstraints(j, i, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
      }
    }
  }

  public int[] caseClic(int x, int y) {
    int offsetX = (int) jeux[0][0].getX() + 3; // d�calage par rapport au coin en haut � gauche de la fenetre
    int offsetY = (int) jeux[0][0].getY() + 22;
    int posx = -1, posy = -1;
    if (x - offsetX >= 0)
      posx = (x - offsetX) / 16;
    if (posx >= LARGEUR)
      posx = -1;
    if (y - offsetY >= 0 && posx != -1)
      posy = (y - offsetY) / 16;
    if (posy >= HAUTEUR)
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
      boutonNouveau.setIcon(oups); // bouton

      // si clic droit au dessus d'une case
      if (e.getButton() == e.BUTTON3 && coord[1] != -1 && coord[0] != -1) {
        int temp = jeux[coord[1]][coord[0]].getEtat();
        switch (temp) {
          case 0: // affichage d'un drapeau
            jeux[coord[1]][coord[0]].setEtat(2);
            nDrapeau++;
            affMines.setText(String.valueOf(nMines - nDrapeau));
            break;
          case 2: // affichage d'un ?
            jeux[coord[1]][coord[0]].setEtat(3);
            nDrapeau--;
            affMines.setText(String.valueOf(nMines - nDrapeau));
            break;
          case 3: // RAZ
            jeux[coord[1]][coord[0]].setEtat(0);
            break;
        }
        jeux[coord[1]][coord[0]].repaint();
      }

      // si clic gauche, on selectionne les cases autour

      y = coord[1];
      x = coord[0];
      if (e.getButton() == e.BUTTON1 && x != -1 && y != -1 &&
              jeux[y][x].getEtat() == 1 && jeux[y][x].getChiffre() != 0) {
        // on enregistre les coordonn�es des cases s�l�ctionn�es
        for (int i = 0; i < 7; i++) {
          for (int j = 0; j < 2; j++) {
            casesSelectionnees[i][j] = -1; // effacement de la m�moire
          }
        }
        // essai sur les huit cases autour
        try {
          if (jeux[y - 1][x - 1].getEtat() == 0) {
            jeux[y - 1][x - 1].setSelected(true);
            casesSelectionnees[0][0] = y - 1;
            casesSelectionnees[0][1] = x - 1;
          }
        } catch (Exception exc) {
        }
        try {
          if (jeux[y - 1][x].getEtat() == 0) {
            jeux[y - 1][x].setSelected(true);
            casesSelectionnees[1][0] = y - 1;
            casesSelectionnees[1][1] = x;
          }
        } catch (Exception exc) {
        }
        try {
          if (jeux[y - 1][x + 1].getEtat() == 0) {
            jeux[y - 1][x + 1].setSelected(true);
            casesSelectionnees[2][0] = y - 1;
            casesSelectionnees[2][1] = x + 1;
          }
        } catch (Exception exc) {
        }
        try {
          if (jeux[y][x - 1].getEtat() == 0) {
            jeux[y][x - 1].setSelected(true);
            casesSelectionnees[3][0] = y;
            casesSelectionnees[3][1] = x - 1;
          }
        } catch (Exception exc) {
        }
        try {
          if (jeux[y][x + 1].getEtat() == 0) {
            jeux[y][x + 1].setSelected(true);
            casesSelectionnees[4][0] = y;
            casesSelectionnees[4][1] = x + 1;
          }
        } catch (Exception exc) {
        }
        try {
          if (jeux[y + 1][x - 1].getEtat() == 0) {
            jeux[y + 1][x - 1].setSelected(true);
            casesSelectionnees[5][0] = y + 1;
            casesSelectionnees[5][1] = x - 1;
          }
        } catch (Exception exc) {
        }
        try {
          if (jeux[y + 1][x].getEtat() == 0) {
            jeux[y + 1][x].setSelected(true);
            casesSelectionnees[6][0] = y + 1;
            casesSelectionnees[6][1] = x;
          }
        } catch (Exception exc) {
        }
        try {
          if (jeux[y + 1][x + 1].getEtat() == 0) {
            jeux[y + 1][x + 1].setSelected(true);
            casesSelectionnees[7][0] = y + 1;
            casesSelectionnees[7][1] = x + 1;
          }
        } catch (Exception exc) {
        }
      }
    } catch (java.lang.ClassCastException ex) {
    } // si clic n'import o�
  }

  public void mouseReleased(MouseEvent e) {

    // Si c'est le premier clic du jeux, on d�marre le timer
    if (nCases == HAUTEUR * LARGEUR && e.getButton() == e.BUTTON1) {
      temps.cancel();
      temps = new Temps(affTemps);
      temps.start();
    }

    try {
      int x = (int) ((JPanel) e.getSource()).getLocation().getX() + e.getX() +
              3; // g�n�re des exceptions � cause du cast
      int y = (int) ((JPanel) e.getSource()).getLocation().getY() + e.getY() +
              22;
      int[] coord = caseClic(x, y); // on r�cup�re les coordonn�es
      boutonNouveau.setIcon(cool); // remise du bouton sur l'icone cool
      if (coord[0] != -1 && coord[1] != -1) { // si on est au dessus d'une case
        y = coord[1];
        x = coord[0];
        if (e.getButton() == e.BUTTON1) { // si clic gauche, on d�couvre
          decouvre(y, x);
          repaint();
        }
        jeux[y][x].setSelected(false); // on d�selctionne la case ainsi que celle de la m�moire casesSelectionnees
        try {
          jeux[casesSelectionnees[0][0]][casesSelectionnees[0][1]].setSelected(false);
        } catch (Exception exc) {
        }
        try {
          jeux[casesSelectionnees[1][0]][casesSelectionnees[1][1]].setSelected(false);
        } catch (Exception exc) {
        }
        try {
          jeux[casesSelectionnees[2][0]][casesSelectionnees[2][1]].setSelected(false);
        } catch (Exception exc) {
        }
        try {
          jeux[casesSelectionnees[3][0]][casesSelectionnees[3][1]].setSelected(false);
        } catch (Exception exc) {
        }
        try {
          jeux[casesSelectionnees[4][0]][casesSelectionnees[4][1]].setSelected(false);
        } catch (Exception exc) {
        }
        try {
          jeux[casesSelectionnees[5][0]][casesSelectionnees[5][1]].setSelected(false);
        } catch (Exception exc) {
        }
        try {
          jeux[casesSelectionnees[6][0]][casesSelectionnees[6][1]].setSelected(false);
        } catch (Exception exc) {
        }
        try {
          jeux[casesSelectionnees[7][0]][casesSelectionnees[7][1]].setSelected(false);
        } catch (Exception exc) {
        }
      }
    } catch (java.lang.ClassCastException ex) {
    } // Si le clic n'est pas au dessus d'un panneau
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  // d�clench�e par un appuie sur le bouton
  void boutonNouveau_actionPerformed(ActionEvent e) {
    if (!pause.isSelected())
      nouveau();
  }

  // m�thode pour d�couvrir les cases
  public void decouvre(int y, int x) {
    // Si la case est vide ou avec un ?
    if ((jeux[y][x].getEtat() == 0 || jeux[y][x].getEtat() == 3) && !jeux[y][x].isMine())
    {
      nCases--; // nombre de cases non decouvertes
      jeux[y][x].setEtat(1); // on indique que la case est d�couverte
      if (jeux[y][x].getChiffre() == 0) { // Si le nombre de mines autour est nul, on d�couvre les cases autour
        decouvrirPartiel1(x - 1, y - 1);
        decouvrirPartiel1(x - 1, y);
        decouvrirPartiel1(x - 1, y + 1);
        decouvrirPartiel1(x, y - 1);
        decouvrirPartiel1(x, y + 1);
        decouvrirPartiel1(x + 1, y - 1);
        decouvrirPartiel1(x + 1, y);
        decouvrirPartiel1(x + 1, y + 1);
      }
    }

    // Si on est au dessus d'un chiffre
    else if (jeux[y][x].getEtat() == 1 && jeux[y][x].getChiffre() != 0) {
      int n = 0; // on compte le nombre de drapeaux plac�s
      if (decouvrirPartiel2(x - 1, y - 1))
        n++;
      if (decouvrirPartiel2(x - 1, y))
        n++;
      if (decouvrirPartiel2(x - 1, y + 1))
        n++;
      if (decouvrirPartiel2(x, y - 1))
        n++;
      if (decouvrirPartiel2(x, y + 1))
        n++;
      if (decouvrirPartiel2(x + 1, y - 1))
        n++;
      if (decouvrirPartiel2(x + 1, y))
        n++;
      if (decouvrirPartiel2(x + 1, y + 1))
        n++;

      if (n == jeux[y][x].getChiffre()) {
        if (decouvrirPartiel3(x - 1, y - 1))
          decouvre(y - 1, x - 1);
        if (decouvrirPartiel3(x - 1, y))
          decouvre(y, x - 1);
        if (decouvrirPartiel3(x - 1, y + 1))
          decouvre(y + 1, x - 1);
        if (decouvrirPartiel3(x, y - 1))
          decouvre(y - 1, x);
        if (decouvrirPartiel3(x, y + 1))
          decouvre(y + 1, x);
        if (decouvrirPartiel3(x + 1, y - 1))
          decouvre(y - 1, x + 1);
        if (decouvrirPartiel3(x + 1, y))
          decouvre(y, x + 1);
        if (decouvrirPartiel3(x + 1, y + 1))
          decouvre(y + 1, x + 1);
      }
    }

    // Si on clique sur une mine
    else if ((jeux[y][x].getEtat() == 0 || jeux[y][x].getEtat() == 3) && jeux[y][x].isMine()) {
      temps.cancel(); // fin du timer
      jeux[y][x].setEtat(4); //met le fond du mine cliquer en rouge
      boutonNouveau.setIcon(boum);
      for (int i = 0; i < HAUTEUR; i++) {
        for (int j = 0; j < LARGEUR; j++) {
          jeux[i][j].removeMouseListener(this); // on bloque les cases restantes
          jeux[i][j].setBlocked(true);
          if (!(y == i && x == j) && mines.charAt(i * LARGEUR + j) == '1' && jeux[i][j].getEtat() != 2)
            jeux[i][j].setEtat(5); // on  affiche les mines restantes
        }
      }
      // on affiche les erreurs
      for (int i = 0; i < HAUTEUR; i++) {
        for (int j = 0; j < LARGEUR; j++) {
          if (jeux[i][j].getEtat() == 2 && !jeux[i][j].isMine())
            jeux[i][j].setEtat(6);
        }
      }
    }
    // Si on gagne, c'est � dire le nombre de cases restantes est �gal au nombre de mines restantes
    if (nCases == nMines && !jeux[0][0].isBlocked()) {
      temps.cancel(); // fin du timer
      affMines.setText(String.valueOf(0));
      boutonNouveau.setIcon(win);
      for (int i = 0; i < HAUTEUR; i++) {
        for (int j = 0; j < LARGEUR; j++) {
          jeux[i][j].removeMouseListener(this); // on bloque les cases
          jeux[i][j].setBlocked(true);
          if (jeux[i][j].isMine())
            jeux[i][j].setEtat(2); // om met des drapeaux partout!!
        }
      }
    }
  }

  // si la case existe, on la d�couvre et si necessaire, on decouvre aussi  des cases autour
  public void decouvrirPartiel1(int x, int y) {
    if (x >= 0 && y >= 0 && x < LARGEUR && y < HAUTEUR) {
      if (jeux[y][x].getEtat() == 0 && jeux[y][x].getChiffre() != 0) {
        jeux[y][x].setEtat(1);
        nCases--;
      }
      if (jeux[y][x].getEtat() == 0 && jeux[y][x].getChiffre() == 0)
        decouvre(y, x); // Si le nombre de mines autour est nul, on d�couvre les cases autour
    }
  }

  // v�rifie si la case existe et si elle porte un drapeau
  public boolean decouvrirPartiel2(int x, int y) {
    if (x >= 0 && y >= 0 && x < LARGEUR && y < HAUTEUR) {
      if (jeux[y][x].getEtat() == 2)
        return true;
    }
    return false;
  }

  // v�rifie si la case existe et si elle n'est pas d�couverte ou si elle porte un
  // '?'
  public boolean decouvrirPartiel3(int x, int y) {
    if (x >= 0 && y >= 0 && x < LARGEUR && y < HAUTEUR) {
      if (jeux[y][x].getEtat() == 0 || jeux[y][x].getEtat() == 3)
        return true;
    }
    return false;
  }

  public void windowOpened(WindowEvent e) {
  }

  public void windowClosing(WindowEvent e) {
    temps.stop(); // stop le timer avant de quitter
    System.exit(0);
  }

  public void windowClosed(WindowEvent e) {
  }

  public void windowIconified(WindowEvent e) {
    try {
      temps.suspend();
    } // pause du timer si il existe!!
    catch (Exception esc) {
    }
  }

  public void windowDeiconified(WindowEvent e) {
    try {
      temps.resume();
    } //reprise du jeu (et du timer)
    catch (Exception esc) {
    }
  }

  public void windowActivated(WindowEvent e) {
  }

  public void windowDeactivated(WindowEvent e) {
  }

  //Evenements lies au menu
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
      this.dispose(); // on d�truit la fenetre
      System.gc();
      if (TYPE == 2)
        menuIntermediaire.setSelected(true);
      Demineur demineur = new Demineur(16, 16, 40, 2);
    } else if (e.getSource() == menuIntermediaire &&
            !menuIntermediaire.isSelected())
      menuIntermediaire.setSelected(true);
    else if (e.getSource() == menuExpert && TYPE != 3) {
      this.dispose(); // on d�truit la fenetre
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
      Apropos app = new Apropos(this, "Demineur", true);
      app.setLocation((int) this.getLocation().getX() + 20,
              (int) this.getLocation().getY() + 20);
      app.setVisible(true);
    }
  }
}