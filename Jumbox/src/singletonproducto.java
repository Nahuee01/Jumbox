import java.util.LinkedList;

import javax.swing.JOptionPane;

public class singletonproducto {

	private static LinkedList<producto> productos;

	private singletonproducto() {

	}

	public static LinkedList<producto> getInstance() {
		if (productos == null) {
			productos = new LinkedList<producto>();
		} else {
			JOptionPane.showMessageDialog(null, "Este producto ya existe: ");
		}
		return productos;
	}

	public static void guardar(producto prod) {
		productos.add(prod);
	}

}
