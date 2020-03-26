import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import modelo.Categoria;

public class Firebase {
	
	private Firestore db=null; 
	
	public void connectFirebase() {
	// Access
		//String projectId = "pm-soluciones";
		String dbUrl="https://pm-soluciones.firebaseio.com";
		String fileAccess = "C:\\Users\\rromero\\Documents\\FirebaseParam\\pm-soluciones-firebase-adminsdk-3y3sw-8d7d114120.json";
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream(fileAccess);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		FirebaseOptions options=null;
		try {
			options = new FirebaseOptions.Builder()
			  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			  .setDatabaseUrl(dbUrl)
			  .build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FirebaseApp.initializeApp(options);
		Firestore db = FirestoreClient.getFirestore();
		this.db=db;
		
	}
	
	public List<Categoria> findAll(String coleccion) {
        // [START fs_get_all] asynchronously retrieve all collection
        ApiFuture<QuerySnapshot> query = db.collection(coleccion).get();        // ...
        // query.get() blocks on response
        QuerySnapshot querySnapshot=null;
		try {
			querySnapshot = query.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		List<Categoria> categorias = new ArrayList<Categoria>();
        for (QueryDocumentSnapshot document : documents) {
          Categoria categoria = new Categoria(
        	 document.getId(),
	         document.getString("clase"),
	         document.getString("descripcion"),
	         document.getString("orden"),
	         document.getString("idPadre")
          );
          categorias.add(categoria);
        }
        return categorias;
        // [END fs_get_all]
	}

	public static void main(String[] args) {

		System.out.println("########## Start Project ########## ");
		String coleccion = "clases";
        Firebase firebase = new Firebase();
        firebase.connectFirebase();
        firebase.findAll(coleccion);		
	    System.out.println("########## End Project ########## ");

	}

}
