
public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "D:/Logiciel/eclipse-standard-kepler-SR1-win32-x86_64/workspace/AABRI/src/listeABRI.txt";
		String savePath = "D:/Logiciel/eclipse-standard-kepler-SR1-win32-x86_64/workspace/AABRI/src/saveListeABRI.txt";

		ABRI abri = new ABRI(path);
		abri.parcoursPrefixe();
		abri.WriteFile(savePath);
	}
}
