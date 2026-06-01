package BLL;
import javax.swing.JOptionPane;

import DLL.gestorproducto;

public class gerente extends Usuario {

	

	public gerente(String nombre, String usuario, String contrasena, String rol, int numlegajo) {
		super(nombre, usuario, contrasena, rol);
		
	}

	@Override
	public void menu() {
		gestorproducto gestor = new gestorproducto();

		gestor.menuGerente();
	}
}
