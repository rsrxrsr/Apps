import java.util.List;

import modelo.Categoria;

public class Gateway {

	public static void main(String[] args) {
		System.out.println("########## Start Project ########## ");
		//Firebase
		String coleccion="clases";
        Firebase firebase = new Firebase();
        firebase.connectFirebase();
        List<Categoria> categorias = firebase.findAll(coleccion);		
	    //Postgres
	    Postgres postgres = new Postgres();
        postgres.connectDatabase();
        //
        postgres.deleteCategoria();
        //categorias.forEach(action);
        for (Categoria categoria : categorias) {
            postgres.insertCategoria(categoria);
            List<Categoria> subcategorias = firebase.findAll(coleccion+"/"+categoria.idClase+"/"+coleccion);		
            for (Categoria subcategoria : subcategorias) {
            	subcategoria.idPadre=categoria.idClase;
            	postgres.insertCategoria(subcategoria);            	
            }            
        }
        //
	    System.out.println("########## End Project ########## ");

	}

}
