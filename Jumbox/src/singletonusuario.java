import java.util.LinkedList;

import javax.swing.JOptionPane;

public class singletonusuario {

	private static LinkedList<usuario> usuarios;

	private singletonusuario() {

	}

	public static LinkedList<usuario> getInstance() {
		if (usuarios == null) {
			usuarios = new LinkedList<usuario>();
		} else {
			JOptionPane.showMessageDialog(null, "Usuario encontrado ");
		}
		return usuarios;
	}

	public static void guardar(usuario usuario) {
		usuarios.add(usuario);
	}

}
