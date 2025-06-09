
public class cliente extends Usuario {

	private int dni;
	
	
	public cliente(String nombre, String mail, String contrasena, String rol, int dni) {
		super(nombre, mail, contrasena, rol);
		this.dni= dni;
		
	}
	
	public void menu() {
		gestorproducto gestor = new gestorproducto();
		gestor.menuCliente();
	}

}
