package BLL;
import DLL.gestorproducto;

public class cliente extends Usuario {

	

	public cliente(String nombre, String mail, String contrasena, String rol, int idUsuario) {
		super(nombre, mail, contrasena, rol);
		
		this.setIdUsuario(idUsuario);
	}

	public void menu() {
		gestorproducto gestor = new gestorproducto();
		gestor.menuCliente(getIdUsuario());

	}

}
