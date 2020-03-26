import java.sql.*;

import modelo.Categoria;

public class Postgres {
	
	Connection db = null;
	
    public void connectDatabase() {
        try {
            // We register the PostgreSQL driver
            // Registramos el driver de PostgresSQL
            try { 
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            Connection connection = null;
            // Database connect
            //String urlDb="jdbc:postgresql://localhost:5432/postgres";
            String urlDb="jdbc:postgresql://34.68.207.218:5432/Observadores";
            String user="usrProyecto";
            String password="proyecto";
            connection = DriverManager.getConnection(
                    urlDb,
                    user,
                    password);
 
            boolean valid = connection.isValid(50000);
            this.db=connection;
            System.out.println(valid ? "CONNECTION OK" : "CONNECTION FAIL");
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }
    
    public void exec(String sql) {
    	Statement stmt = null;
        try {
    		stmt = db.createStatement();
    		stmt.executeUpdate(sql);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}    	
    }
    
    void deleteCategoria() {
    	String sql = "TRUNCATE TABLE \"DatosMaestros\".\"cat_Categorias\"";
    	exec(sql);    	
    }
    
    void insertCategoria(Categoria categoria) {
    	String sql = "INSERT INTO \"DatosMaestros\".\"cat_Categorias\" ("
        		   + "id,"
        		   + "clase,"
        		   + "descripcion,"
        		   + "orden,"
        		   + "id_padre)"
        		   + " VALUES ('"
        		   + categoria.idClase + "','"
        		   + categoria.clase + "','"
        		   + categoria.descripcion + "','"
        		   + categoria.orden + "','"
        		   + categoria.idPadre + "')";
        System.out.println("Sql: "+ sql);
    	
    	exec(sql);
    }

    public static void main(String[] args) {
        Categoria categoria = new Categoria(
        	 "dato 10",
	         "clase",
	         "descripcion",
	         "9999",
	         "idPadre"
        );
        Postgres postgres = new Postgres();
        postgres.connectDatabase();
        postgres.deleteCategoria();
        postgres.insertCategoria(categoria);
        System.out.println("Records created successfully");
    }
}
	
