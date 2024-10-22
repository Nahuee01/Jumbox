import javax.swing.JOptionPane;

public class empleado extends Usuario {

	private int numlegajo;

	public empleado(String nombre, String usuario, String contrasena, String rol, int numlegajo) {
		super(nombre, usuario, contrasena, rol);
		this.numlegajo = numlegajo;
	}

	@Override
	public void menu() {
		gestorproducto gestor = new gestorproducto();

		gestor.menuEmpleado();
	}
}