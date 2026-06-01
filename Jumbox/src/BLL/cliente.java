package BLL;
import DLL.gestorproducto;

public class cliente extends Usuario {

	

	public cliente(String nombre, String mail, String contrasena, String rol, int dni) {
		super(nombre, mail, contrasena, rol);
		

	}

	public void menu() {
		gestorproducto gestor = new gestorproducto();
		gestor.menuCliente(getIdUsuario());

	}

}
