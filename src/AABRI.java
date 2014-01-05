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
	
	public AABRI(String path) // lecture du fichier passee en parametre
	{
		racine = null;
		try{
			InputStream ips = new FileInputStream(path); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne = "";
			while ((ligne = br.readLine())!=null){	// lecture de chacune des lignes
				//System.out.println(ligne);			// √©criture de la ligne
				String[] strArray1 = ligne.split(";");	// split par ";" afin de r√©cup√©rer les bornes min et max
				String[] strArrayMinMax = strArray1[0].split(":");	// split par ":" pour garder le min et la max
				String[] strArrayArbre = strArray1[1].split(":");	// split par ":" pour parser les diff√©rentes valeurs des noeuds
				int[] intArray1 = new int[strArrayMinMax.length];	
				int[] intArray2 = new int[strArrayArbre.length];
				
				for(int i = 0; i < strArrayMinMax.length; i++) {	// creation d'un tableau d'entier des valeurs des noeuds
				    intArray1[i] = Integer.parseInt(strArrayMinMax[i]);
				}
				
				ABRI arbre = new ABRI();
				for(int i = 0; i < strArrayArbre.length; i++) {	
				    intArray2[i] = Integer.parseInt(strArrayArbre[i]);
				}
				arbre.CreerABRI(intArray1, intArray2);	 // on cree un ABRI avec le min et max et les diff√©rents noeuds			
				racine = inserer(arbre, racine);		// ensuite on insere l'arbre dans l'AABRI
			}
			br.close(); 
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public void ecrireAABRIFichier(String savepath) //ecrire l'AABRI dans le fichier de sortie
	{
		String chaine = "";	  // On recupere le contenu a ecrire dans une chaine
		if(this.racine != null){
			chaine = racine.ecrireToutArbreFichier(); 
		}		
		try {
			FileWriter fw = new FileWriter (savepath);	// ecriture de la chaine dans le fichier
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			fichierSortie.println (chaine + "\n"); 
			fichierSortie.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public AABRI genererAabriAleatoire(){   // Permet de generer un AABRI aleatoire
		int nbnoeud = 0;
		int bornemax = 0;
		int nbvalpossible = 0;
		boolean existedeja = false;
		int[] listeintervalle;
		int[] intArray1;	
		int[] intArray2;
		int nbn = 0;
		int element = 0;

		Scanner saisie = new Scanner(System.in);
		System.out.println("Nombre de noeuds ? ");
		nbnoeud = saisie.nextInt();   // Recupere le nombre de noeuds voulus
		System.out.println("Borne max ? ");
		bornemax = saisie.nextInt();	// Recupere la borne maximum
		listeintervalle = new int[nbnoeud*2];	// Tableau des intervalles
		for(int j = 0; j != nbnoeud * 2; j++){   // on prend toutes les valeurs pour les intervalles
			int a = (int) (Math.random()*bornemax);
			existedeja = false;		// Une borne d'intervalle ne doit pas deja exister
			for(int k = 0; k != listeintervalle.length; k++){
				if(listeintervalle[k] == a){
					existedeja = true;  
				}
			}
			if(existedeja == true){
				j--;		// si elle existe alors on ne l'ajoute pas
			}
			else{
				listeintervalle[j] = a;	// Sinon on l'ajoute au tableau d'intervalle
			}
		}
		
		/*
		 * listeintervalle = {min(c),max(a),max(b),min(a),max(c),min(b)} Dans le desordre
		 */
		
		triBulleCroissant(listeintervalle); // on trie par ordre croissant le tableau
		
		/*
		 * listeintervalle = {min(a),max(a),min(b),max(b),min(c),max(c)} 
		 */
			
		for(int l = 0; l != nbnoeud; l++){  // Pour chaque intervalle, on cree un ABRI avec des valeurs aleatoires
			ABRI abri = new ABRI();
			intArray1 = new int[2];
			intArray1[0] = listeintervalle[2*l];
			intArray1[1] = listeintervalle[(2*l)+1]; // min et max du premier intervalle
			nbvalpossible = intArray1[1] - intArray1[0];  //nb val possibles dans cet intervalle
			while (nbn == 0 && nbvalpossible != 1){
				nbn = (int) (Math.random()*nbvalpossible);  // nb noeud dans ABRI tire aleatoirement
			}
			if(nbvalpossible == 1){
				nbn = nbvalpossible;	// Si juste une valeur possible alors un seul noeud
			}
			intArray2 = new int[nbn];   //crÈation d'un tableau d'ÈlÈment de l'intervalle 
			for(int j = 0; j != nbn; j++){	//On cree nbn element que l'on ajoute dans le tableau d'element
				element = (int) (intArray1[0] + Math.random() * (intArray1[1] - intArray1[0] + 1));
				intArray2[j] = element;
			}
			abri.CreerABRI(intArray1, intArray2);  // creation de l'ABRI avec l'intervalle {min, max} et le tableau d'element
			racine = inserer(abri, racine);		// Enfin, insertion dans l'AABRI de l'ABRI
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
	
	public void insererUnElement(){  // Permet l'insertion d'un entier (noeud) dans l'AABRI 
		int element;
		element = 0;
		Scanner saisie = new Scanner(System.in);
		System.out.println("Element a ajouter : ");
		element = saisie.nextInt();		// Element a ajouter
		if(this.racine != null)
		{
			racine.insererElement(element);
		}
		else
		{
			System.out.println("L'abre est vide");
		}
	}
	
	public void supprimerUnElement(){  // Suppression d'un element de l'AABRI
		int element;
		element = 0;
		Scanner saisie = new Scanner(System.in);
		System.out.println("Element a supprimer : ");
		element = saisie.nextInt();		// Entier a supprimer de l'AABRI
		if(this.racine != null)
		{
			racine.supprimerElement(element);
		}
		else
		{
			System.out.println("L'abre est vide");
		}
	}
	
	public void afficherAABRI(){		// Affichage de l'AABRI
		String chaine = "";
		if(this.racine != null){
			chaine = racine.afficherToutArbre();
			System.out.println(chaine);
		}		
		else{
			System.out.println("L'abre est vide");
		}
	}
	
	public static void triBulleCroissant(int tableau[]) {	//Tri d'un tableau d'entier
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
