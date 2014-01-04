import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;


public class AABRI {
	public ABRI racine;
	
	public AABRI(){
		racine = null;
	}
	
	public AABRI(String path) // lecture du fichier passé en paramètre
	{
		racine = null;
		try{
			InputStream ips = new FileInputStream(path); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne = "";
			while ((ligne = br.readLine())!=null){	// lecture de chacune des lignes
				//System.out.println(ligne);			// écriture de la ligne
				String[] strArray1 = ligne.split(";");	// split par ";" afin de récupérer les bornes min et max
				String[] strArrayMinMax = strArray1[0].split(":");	// split par ":" pour garder le min et la max
				String[] strArrayArbre = strArray1[1].split(":");	// split par ":" pour parser les différentes valeurs des noeuds
				int[] intArray1 = new int[strArrayMinMax.length];	
				int[] intArray2 = new int[strArrayArbre.length];
				
				for(int i = 0; i < strArrayMinMax.length; i++) {	// création d'un tableau d'entier des valeurs des noeuds
				    intArray1[i] = Integer.parseInt(strArrayMinMax[i]);
				}
				
				ABRI arbre = new ABRI();
				for(int i = 0; i < strArrayArbre.length; i++) {	
				    intArray2[i] = Integer.parseInt(strArrayArbre[i]);
				}
				arbre.CreerABRI(intArray1, intArray2);	 // on créé un ABRI avec le min et max et les différents noeuds			
				racine = inserer(arbre, racine);		// ensuite on insère l'arbre dans l'AABRI
				//arbre.parcoursPrefixe();				// On lit le parcours préfixe pour contrôler 
			}
			br.close(); 
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public void ecrireAABRIFichier(String savepath)
	{
		String chaine = "";
		if(this.racine != null){
			chaine = racine.ecrireToutArbreFichier();
		}		
		try {
			FileWriter fw = new FileWriter (savepath);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			fichierSortie.println (chaine + "\n"); 
			fichierSortie.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public AABRI genererAabriAleatoire(){
		int nbnoeud;
		int bornemax;
		int[] listeintervalle;
		int[] intArray1;	
		int[] intArray2;
		int nbn = 0;
		int element = 0;
		bornemax = 0;
		nbnoeud = 0;
		int nbvalpossible = 0;
		boolean existedeja = false;
		Scanner saisie = new Scanner(System.in);
		System.out.println("Nombre de noeuds ? ");
		nbnoeud = saisie.nextInt();
		System.out.println("Borne max ? ");
		bornemax = saisie.nextInt();
		listeintervalle = new int[nbnoeud*2];
		for(int j = 0; j != nbnoeud * 2; j++){   // on prend toutes les valeurs pour les intervalles
			int a = (int) (Math.random()*bornemax);
			existedeja = false;
			for(int k = 0; k != listeintervalle.length; k++){
				if(listeintervalle[k] == a){
					existedeja = true;
				}
			}
			if(existedeja == true){
				j--;
			}
			else{
				listeintervalle[j] = a;
			}
		}
		
		triBulleCroissant(listeintervalle); // on trie par ordre croissant le tableau
		for(int z=0;z != nbnoeud*2;z++){
			System.out.println(listeintervalle[z]);
		}
		
		for(int l = 0; l != nbnoeud; l++){
			ABRI abri = new ABRI();
			intArray1 = new int[2];
			intArray1[0] = listeintervalle[2*l];
			intArray1[1] = listeintervalle[(2*l)+1]; // min et max du premier intervalle
			nbvalpossible = intArray1[1] - intArray1[0];  //nb val possibles
			System.out.println("nb : " + nbvalpossible);
			while (nbn == 0 && nbvalpossible != 1){
				nbn = (int) (Math.random()*nbvalpossible);  // nb noeud dans ABRI
			}
			if(nbvalpossible == 1){
				nbn = nbvalpossible;
			}
			System.out.println(nbn);
			intArray2 = new int[nbn];  //cr�ation d'un tableau d'�l�ment
			for(int j = 0; j != nbn; j++){
				element = (int) (intArray1[0] + Math.random() * (intArray1[1] - intArray1[0] + 1));
				intArray2[j] = element;
			}
			abri.CreerABRI(intArray1, intArray2);
			racine = inserer(abri, racine);
			nbn = 0;
		}
		return this;		
	}
	
	public ABRI inserer(ABRI valeur, ABRI racine) {		// insertion d'un ABRI dans l'AABRI
		if (racine == null) return valeur;
		if (valeur.getMax() < racine.getMin()) racine.setSag(inserer(valeur, racine.getSag()));
		else if (valeur.getMin() > racine.getMax()) racine.setSad(inserer(valeur, racine.getSad()));
		return racine;
	}
	
	public void insererUnElement(){
		int element;
		element = 0;
		Scanner saisie = new Scanner(System.in);
		System.out.println("Element a ajouter : ");
		element = saisie.nextInt();
		if(this.racine != null)
		{
			racine.insererElement(element);
		}
		else
		{
			System.out.println("L'abre est vide");
		}
	}
	
	public void supprimerUnElement(){
		int element;
		element = 0;
		Scanner saisie = new Scanner(System.in);
		System.out.println("Element a supprimer : ");
		element = saisie.nextInt();
		if(this.racine != null)
		{
			racine.supprimerElement(element);
		}
		else
		{
			System.out.println("L'abre est vide");
		}
	}
	
	public void afficherAABRI(){
		String chaine = "";
		if(this.racine != null){
			chaine = racine.afficherToutArbre();
			System.out.println(chaine);
		}		
		else{
			System.out.println("L'abre est vide");
		}
	}
	
	public static void triBulleCroissant(int tableau[]) {
		int longueur = tableau.length;
		int tampon = 0;
		boolean permut; 
		do {
			permut = false;
			for (int i = 0; i < longueur - 1; i++) {
				if (tableau[i] > tableau[i + 1]) {
					tampon = tableau[i];
					tableau[i] = tableau[i + 1];
					tableau[i + 1] = tampon;
					permut = true;
				}
			}
		} while (permut);
	}

	public ABRI getValeur() {
		return racine;
	}

	public void setValeur(ABRI valeur) {
		this.racine = valeur;
	}	
}
