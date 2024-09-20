import java.util.LinkedList;
import javax.swing.JOptionPane;
import java.time.LocalDate;

public class main {

	public static void main(String[] args) {

		singletonusuario.getInstance();
		singletonproducto.getInstance();

		singletonusuario.guardar(new usuario("nahuel", "nahuel@gmail.com", "1234", "gerente"));
		singletonusuario.guardar(new usuario("lucas", "lucas@gmail.com", "4321", "empleado"));
		singletonproducto.guardar(new producto("Lacteos", "Leche", "Serenisima", 1200.0, 1534, 500.0, LocalDate.of(2025, 5, 24), 100));
		singletonproducto.guardar(new producto("Pastas", "Fideos", "Matarazzo", 1500.0, 2345, 500.0, LocalDate.of(2025, 12, 4), 1));

		String mail = JOptionPane.showInputDialog("Ingresa mail");
		String contrasena = JOptionPane.showInputDialog("Ingresa contrasena");

		usuario buscado = Login(mail, contrasena);
		if (buscado == null) {
			JOptionPane.showMessageDialog(null, "No encontrado");
		} else {
			JOptionPane.showMessageDialog(null, buscado);

			if (buscado.getRol().equals("gerente")) {
				gerente nuevo = new gerente(buscado.getNombre(), buscado.getMail(), buscado.getContrasena(),
						buscado.getRol(), 0);
				nuevo.menu();
			} else {
				empleado nuevo = new empleado(buscado.getNombre(), buscado.getMail(), buscado.getContrasena(),
						buscado.getRol(), 0);
				nuevo.menu();
			}
		}
	}

	public static usuario Login(String mail, String cotrasena) {

		for (usuario usuario : singletonusuario.getInstance()) {
			if (usuario.getMail().equals(mail) && usuario.getContrasena().equals(cotrasena)) {
				return usuario;
			}
		}
		return null;
	}

}
