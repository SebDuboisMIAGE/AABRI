public class ABRI {

	private Noeud racine;
	private ABRI sag;
	private ABRI sad;
	private int max, min;
	private String chainePrefixe;
	
	public ABRI getSag() {
		return sag;
	}

	public void setSag(ABRI sag) {
		this.sag = sag;
	}

	public ABRI getSad() {
		return sad;
	}

	public void setSad(ABRI sad) {
		this.sad = sad;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public Noeud getRacine() {
		return racine;
	}
	
	public void setRacine(Noeud racine) {
		this.racine = racine;
	}
	
	public ABRI() {
	}
	
	public Noeud inserer(int x, Noeud a) {  // Insertion d'un noeud dans l'ABRI
		if (a == null) return new Noeud(x);
		if (x > a.getValeur()) a.setSag(inserer(x, a.getSag()));
		else if (x < a.getValeur()) a.setSad(inserer(x, a.getSad())); 
		return a;
	}
	
	public ABRI CreerABRI(int[] minmax, int[] val)	// Creation d'un ABRI en fonction du tableau d'intervalle et du tableau d'element
	{
		this.setMin(minmax[0]);
		this.setMax(minmax[1]);
		for (int z : val){
			this.racine = inserer(z, this.racine); 
		}
		return this;
	}

	public Noeud rechercherEtSupprimer(int x, Noeud a) {  // Rechercher et supprimer un element
		if (a == null) return null;
		if (x == a.getValeur()) return supprimerRacine(a);
		if (x > a.getValeur()) a.setSag(rechercherEtSupprimer(x, a.getSag())); 
		else a.setSad(rechercherEtSupprimer(x, a.getSad())); 
		return a;
	}
	
	public Noeud supprimerRacine(Noeud a) { // Suppression de la racine d'un ABRI 
		if (a.getSag() == null) return a.getSad();
		if (a.getSad() == null) return a.getSag();
		Noeud f = dernierDescendant(a.getSad());
		a.setValeur(f.getValeur()); 
		a.setSad(rechercherEtSupprimer(f.getValeur(), a.getSad())); 
		return a;
	}
	
	public void insererElement(int element){	// Insertion d'un element dans l'ABRI
		if (this.min < element && this.max > element)
		{
			this.inserer(element, this.getRacine());
		}
		else 
		{
			if(this.min > element)
			{
				this.getSag().insererElement(element);
			}
			else
			{
				this.getSad().insererElement(element);
			}
		}
	}
	
	public void supprimerElement(int element){   //Suppression d'un element de l'ABRI
		if (this.min < element && this.max > element)
		{
			this.rechercherEtSupprimer(element, this.getRacine());
		}
		else 
		{
			if(this.min > element)
			{
				this.getSag().supprimerElement(element);
			}
			else
			{
				this.getSad().supprimerElement(element);
			}
		}
	}
	
	public Noeud dernierDescendant(Noeud a) { // Trouver dernier descendant de la racine pour suppression
		if (a.getSag()== null) return a;
		return dernierDescendant(a.getSag());
	}

	public String afficherToutArbre(){  // Afficher tout l'ABRI
		String chaine = "";
		chaine += this.ecrireParcoursPrefixeFichier();
		if(this.getSag() != null)
		{
			chaine += this.getSag().afficherToutArbre();
		}
		if(this.getSad() != null)
		{
			chaine += this.getSad().afficherToutArbre();
		}		
		return chaine;
	}
	
	public String ecrireToutArbreFichier()  //Recupere la chaine correspondant à tout les ABRI
	{
		String chaine = "";
		chaine += this.ecrireParcoursPrefixeFichier();
		if(this.getSag() != null)
		{
			chaine += this.getSag().ecrireToutArbreFichier();
		}
		if(this.getSad() != null)
		{
			chaine += this.getSad().ecrireToutArbreFichier();
		}		
		return chaine;
	}
	
	public String ecrireParcoursPrefixeFichier()  // Recupere la chaine correspondant a un ABRI
	{
		String chaine = "";
		chaine += this.getMin() + ":" + this.getMax() + ";";
		if(this.getRacine() != null)
		{
			chaine += racine.getValeur() + ":";
			if(racine.getSag() != null)
			{
				chaine += racine.getSag().ecrireParcoursPrefixeFichier();
			}
		    if(racine.getSad() != null)
		    {
		    	chaine += racine.getSad().ecrireParcoursPrefixeFichier();
		    }
		}
		chaine = chaine.substring(0,chaine.length()-1);
		chaine += "\n";
		return chaine;
	}
}
