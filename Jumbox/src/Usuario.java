
public class Usuario {

	private int idUsuario;
	private String nombre;
	private String mail;
	private String contrasena;
	private String rol;

	public Usuario(String nombre, String mail, String contrasena, String rol) {
		super();
		this.nombre = nombre;
		this.mail = mail;
		this.contrasena = contrasena;
		this.rol = rol;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int id) {
		this.idUsuario = id;
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
		return "Bienvenido " + nombre;
	}

	public void menu() {

	}

}
