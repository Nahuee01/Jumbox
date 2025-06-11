
public class cliente extends Usuario {

	

	public cliente(int idUsuario, String nombre, String mail, String contrasena, String rol, int dni) {
		super(nombre, mail, contrasena, rol);
		this.setIdUsuario(idUsuario);

	}

	public void menu() {
		gestorproducto gestor = new gestorproducto();
		gestor.menuCliente(getIdUsuario());

	}

}
