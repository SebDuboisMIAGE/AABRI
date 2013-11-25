import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AABRI {
	private ABRI racine;
	
	public AABRI(String path)
	{
		racine = null;
		try{
			InputStream ips = new FileInputStream(path); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne = "";
			while ((ligne = br.readLine())!=null){	
				System.out.println(ligne);
				String[] strArray1 = ligne.split(";");
				String[] strArrayMinMax = strArray1[0].split(":");
				String[] strArrayArbre = strArray1[1].split(":");
				int[] intArray1 = new int[strArrayMinMax.length];
				int[] intArray2 = new int[strArrayArbre.length];
				
				for(int i = 0; i < strArrayMinMax.length; i++) {
				    intArray1[i] = Integer.parseInt(strArrayMinMax[i]);
				}
				
				ABRI arbre = new ABRI();
				for(int i = 0; i < strArrayArbre.length; i++) {
				    intArray2[i] = Integer.parseInt(strArrayArbre[i]);
				}
				arbre.CreerABRI(intArray1, intArray2);				
				racine = inserer(arbre, racine);
				arbre.parcoursPrefixe();
			}
			br.close(); 
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public ABRI inserer(ABRI valeur, ABRI racine) {
		if (racine == null) return valeur;
		if (valeur.getMax() < racine.getMin()) racine.setSag(inserer(valeur, racine.getSag()));
		else if (valeur.getMin() > racine.getMax()) racine.setSad(inserer(valeur, racine.getSad()));
		return racine;
	}

	public ABRI getValeur() {
		return racine;
	}

	public void setValeur(ABRI valeur) {
		this.racine = valeur;
	}	
}
