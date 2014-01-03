import java.util.Scanner;


public class Application {

	public static String path = "C:/Users/Sébastien/Documents/Eclipse/AABRI-V2/src/listeABRI.txt";
	public static String savePath = "C:/Users/Sébastien/Documents/Eclipse/AABRI-V2/src/saveListeABRI.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		AABRI abri = new AABRI(path);
		Menu(abri);
	}
	
	public static void Menu(AABRI abri){
		int choix;
		choix = 0;
		Scanner saisie = new Scanner(System.in);
		while(choix != 7){
			System.out.println("Menu principal\n");
			System.out.println("1 : Inserer un element");
			System.out.println("2 : Supprimer un element");
			System.out.println("3 : Afficher l'AABRI");
			System.out.println("4 : Ecrire AABRI dans fichier");
			System.out.println("5 : Generer AABRI aleatoire");
			System.out.println("6 : AABRI => ABR");
			System.out.println("7 : Quitter");
			choix = saisie.nextInt();
			switch(choix){
			case 1 :
				abri.insererUnElement();
				break;
			case 2 :
				abri.supprimerUnElement();
				break;
			case 3 :
				abri.afficherAABRI();
				break;
			case 4 :
				abri.ecrireAABRIFichier(savePath);
				break;
			case 5 :
				AABRI abriAleatoire = new AABRI();
				abriAleatoire.genererAabriAleatoire();
				abriAleatoire.afficherAABRI();
				//afficher aabri aléatoire
				break;
			case 6 :
				ABR abr = new ABR();
				abr.AABRIversABR(abri);
			}
		}		
	}
}
