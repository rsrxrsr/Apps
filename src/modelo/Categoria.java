package modelo;

public class Categoria {

	public String idClase; 
	public String clase;  
	public String descripcion;  
	public String orden;
	public String idPadre;

	public Categoria(
		String idClase, 
		String clase,  
		String descripcion,  
		String orden,
		String idPadre
		) {
		this.idClase=idClase;
		this.clase=clase;
		this.descripcion=descripcion;
		this.orden=orden;
		this.idPadre=idPadre;
	}

}