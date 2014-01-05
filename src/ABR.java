
public class ABR {
	
	private Noeud racine;
	private ABR sag;
	private ABR sad;
	
	public ABR getSag() {
		return sag;
	}

	public void setSag(ABR sag) {
		this.sag = sag;
	}

	public ABR getSad() {
		return sad;
	}

	public void setSad(ABR sad) {
		this.sad = sad;
	}
	
	public Noeud getRacine() {
		return racine;
	}
	
	public void setRacine(Noeud racine) {
		this.racine = racine;
	}
	
	public ABR() {
	}
	
	public Noeud inserer(int x, Noeud a) {  //insere un element dans ABR
		if (a == null) return new Noeud(x);
		if (x < a.getValeur()) a.setSag(inserer(x, a.getSag()));
		else if (x > a.getValeur()) a.setSad(inserer(x, a.getSad())); 
		return a;
	}
	
	public void AABRIversABR(AABRI abri){	// Lit AABRI et ecrit chaque element dans ABR
		if(abri.racine != null){
			this.ConstruireABR(abri.racine);
			System.out.println(this.afficherABR());
		}		
		else{
			System.out.println("L'abre est vide");
		}
	}
	
	public void ConstruireABR(ABRI abri){		// Construction d'un ABR
		this.AjouterDansABR(abri);
		if(abri.getSag() != null)
		{
			ConstruireABR(abri.getSag());
		}
		if(abri.getSad() != null)
		{
			ConstruireABR(abri.getSad());
		}		
	}
	
	public void AjouterDansABR(ABRI abri){		//Insertion de l'element lu dans ABR
		if(abri.getRacine() != null)
		{
			racine = this.inserer(abri.getRacine().getValeur(), racine);
			if(abri.getRacine().getSag() != null)
			{
				this.ecrireNoeudDansABR(abri.getRacine().getSag());
			}
		    if(abri.getRacine().getSad() != null)
		    {
		    	this.ecrireNoeudDansABR(abri.getRacine().getSad());
		    }
		}
	}
	
	public String afficherABR(){		//Afficher l'ABR
		String chaine = "";
		chaine += this.ecrireParcoursPrefixeABR();
		if(this.getSag() != null)
		{
			chaine += this.getSag().afficherABR();
		}
		if(this.getSad() != null)
		{
			chaine += this.getSad().afficherABR();
		}		
		return chaine;
	}
	
	public String ecrireParcoursPrefixeABR()  // Recupere chaque noeud de l'ABR
	{
		String chaine = "";
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
	
	public void ecrireNoeudDansABR(Noeud noeud){		// Inserer noeud dans ABR
		racine = this.inserer(noeud.getValeur(), racine);
		if(noeud.getSag() != null)
		{
			racine = this.inserer(noeud.getSag().getValeur(), racine);
		}
	    if(noeud.getSad() != null)
	    {
	    	racine = this.inserer(noeud.getSad().getValeur(), racine);
	    }
	}
	
}
