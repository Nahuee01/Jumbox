import javax.swing.JOptionPane;

public class empleado extends usuario {

	private int numlegajo;

	public empleado(String nombre, String usuario, String contrasena, String rol, int numlegajo) {
		super(nombre, usuario, contrasena, rol);
		this.numlegajo = numlegajo;
	}
	
	@Override	
	public void menu() {
		String[] menuGerente= {"Abrir opciones"};
		
		gestorproducto gestor = new gestorproducto(null);
		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Abrir opciones", "Menu Principal", 0, 0, null, menuGerente, menuGerente[0]);

			switch (opc) {
			case 0:

				gestor.menuEmpleado();

				break;
							
			}
		} while (opc != -1);
	

}
	
	}

