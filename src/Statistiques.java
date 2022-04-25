import javax.swing.*;
public class Statistiques {

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
}
