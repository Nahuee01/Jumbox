
public class usuario  {

	private String nombre;
	private String mail;
	private String contrasena;
	private String rol;
	
	public usuario(String nombre, String mail, String contrasena, String rol) {
		super();
		this.nombre = nombre;
		this.mail = mail;
		this.contrasena = contrasena;
		this.rol = rol;
	}

	
	
public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public String getContrasena() {
		return contrasena;
	}



	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}



	public String getRol() {
		return rol;
	}



	public void setRol(String rol) {
		this.rol = rol;
	}



@Override
	public String toString() {
		return "usuario [nombre=" + nombre + ", mail=" + mail + ", contrasena=" + contrasena + ", rol=" + rol + "]";
	}



public void menu() {
		
	}
	
	
	
	
}
