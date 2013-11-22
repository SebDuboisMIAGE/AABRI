import java.io.*;


public class ABRI {

	private Noeud racine;
	private int max, min;
	private String chainePrefixe;

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
	
	public ABRI(String path) {
		racine = null;
		
		String liste = ReadFile(path);
		
		String[] strArray1 = liste.split(";");
		String[] strArrayMinMax = strArray1[0].split(":");
		String[] strArrayArbre = strArray1[1].split(":");
		int[] intArray1 = new int[strArrayMinMax.length];
		int[] intArray2 = new int[strArrayArbre.length];
		
		for(int i = 0; i < strArrayMinMax.length; i++) {
		    intArray1[i] = Integer.parseInt(strArrayMinMax[i]);
		}
		this.min = intArray1[0];
		this.max = intArray1[1];
		
		for(int i = 0; i < strArrayArbre.length; i++) {
		    intArray2[i] = Integer.parseInt(strArrayArbre[i]);
		}
		for (int z : intArray2){
			racine = inserer(z, racine); 
		}	
	}
	
	public Noeud inserer(int x, Noeud a) {
		if (a == null) return new Noeud(x);
		if (x > a.getValeur()) a.setSag(inserer(x, a.getSag()));
		else if (x < a.getValeur()) a.setSad(inserer(x, a.getSad())); 
		return a;
	}
	
	public Noeud rechercherEtSupprimer(int x, Noeud a) {
		if (a == null) return null;
		if (x == a.getValeur()) return supprimerRacine(a);
		if (x > a.getValeur()) a.setSag(rechercherEtSupprimer(x, a.getSag())); 
		else a.setSad(rechercherEtSupprimer(x, a.getSad())); 
		return a;
	}
	
	public Noeud supprimerRacine(Noeud a) {
		if (a.getSag() == null) return a.getSad();
		if (a.getSad() == null) return a.getSag();
		Noeud f = dernierDescendant(a.getSad());
		a.setValeur(f.getValeur()); 
		a.setSad(rechercherEtSupprimer(f.getValeur(), a.getSad())); 
		return a;
	}
	
	public Noeud dernierDescendant(Noeud a) {
		if (a.getSag()== null) return a;
		return dernierDescendant(a.getSag());
	}

	public void parcoursPrefixe() {
		System.out.println("Min : " + this.min);
		System.out.println("Max : " + this.max);
		if(racine != null)
		{
			System.out.println(racine.getValeur());
			chainePrefixe += racine.getValeur() + ":";
			if(racine.getSag() != null)
				racine.getSag().setChaine("");
				racine.getSag().parcoursPrefixe();
				chainePrefixe += racine.getSag().getChaine();
		    if(racine.getSad() != null)
		    	racine.getSad().setChaine("");
		    	racine.getSad().parcoursPrefixe();
		    	chainePrefixe += racine.getSad().getChaine();
		}
		else
		{
			System.out.println("null");
		}
	}
	
	public String ReadFile(String path){
		
		String chaine="";
			
		try{
			InputStream ips = new FileInputStream(path); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine())!=null){
				chaine += ligne;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
		return chaine;
	}
	public void WriteFile(String path){
		this.chainePrefixe = "";
		this.parcoursPrefixe();
		String chaine = this.min + ":" + this.max + ";" + chainePrefixe;
		chaine = chaine.substring(0,chaine.length()-1);
		
		try {
			FileWriter fw = new FileWriter (path);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			fichierSortie.println (chaine + "\n"); 
			fichierSortie.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
}