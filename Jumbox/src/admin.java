public class admin extends usuario {

	private int numlegajo;

	public admin(String nombre, String usuario, String contrasena, String rol, int numlegajo) {
		super(nombre, usuario, contrasena, rol);
		this.numlegajo = numlegajo;
	}

	@Override
	public void menu() {
		gestorproducto gestor = new gestorproducto();

		gestor.menuAdmin();
	}
}