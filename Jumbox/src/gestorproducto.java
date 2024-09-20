import java.util.LinkedList;

import javax.swing.JOptionPane;

public class gestorproducto {

	LinkedList<producto> producto = singletonproducto.getInstance();

	public gestorproducto() {
		super();
		this.producto = singletonproducto.getInstance();
	}

	public LinkedList<producto> getProducto() {
		return producto;
	}

	public void setProducto(LinkedList<producto> producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "gestorproducto [producto=" + producto + "]";
	}

	public void menuGerente() {
		String[] menu = { "Ver Graficos", "Buscar Producto", "Eliminar Producto", "Modificar Producto",
				"Agregar Producto" };

		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu,
					menu[0]);

			switch (opc) {
			case 0:
				// Implementar acción para ver gráficos
				break;
			case 1:
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del producto"));
				String resultado = buscarProducto(codigoProducto);
				JOptionPane.showMessageDialog(null, resultado);
				break;
			case 2:
				int codigoProductoEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo"));
				int cantidadAEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad"));
				String resultadoEliminacion = eliminarProducto(codigoProductoEliminar, cantidadAEliminar);
				JOptionPane.showMessageDialog(null, resultadoEliminacion);
				break;
			case 3:
				// Implementar acción para modificar producto
				break;
			case 4:
				int codigoProductoAgregar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo"));
				int cantidadAAgregar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad"));
				String resultadoAgregar = agregarProducto(codigoProductoAgregar, cantidadAAgregar);
				JOptionPane.showMessageDialog(null, resultadoAgregar);
				break;
			default:
				break;
			}
		} while (opc != -1);
	}

	public void menuEmpleado() {
		String[] menu = { "Ver Graficos", "Buscar Producto" };

		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu,
					menu[0]);

			switch (opc) {
			case 0:
				// Implementar acción para ver gráficos
				break;
			case 1:
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del producto"));
				String resultado = buscarProducto(codigoProducto);
				JOptionPane.showMessageDialog(null, resultado);
				break;
			default:
				break;
			}
		} while (opc != -1);
	}

	// -------- busqueda de productos----------//
	public String buscarProducto(int codigoProducto) {
		for (producto prod : producto) {
			if (prod.getCodigo() == codigoProducto) {
				return "Producto encontrado: " + prod.getNombre();
			}
		}
		return "Producto no encontrado.";
	}
	// -------- eliminar cantidad de productos----------//
	
	public String eliminarProducto(int codigo, int cantidad) {
		
		for (producto prod : producto) {
			if (prod.getCodigo() == codigo) {
				if (prod.getCantidad()>= cantidad) {
					prod.setCantidad(prod.getCantidad()-cantidad);
					return "Se descontaron " + cantidad + " unidades del producto " + prod.getNombre() + " " + prod.getMarca() + ". Stock actual: " + prod.getCantidad();
	            } else {
	                return "No hay suficiente stock para descontar " + cantidad + " unidades.";
	            }
	        }
	    }
	    return "Producto con código " + codigo + " no encontrado.";
	}
	
	// -------- agregar cantidad de productos----------//
	
		public String agregarProducto(int codigo, int cantidad) {
			
			for (producto prod : producto) {
				if (prod.getCodigo() == codigo) {
					prod.setCantidad(prod.getCantidad()+cantidad);
					return "Se agregaron" + cantidad + " unidades al producto" + prod.getNombre() + " " + prod.getMarca() + ". Stock actual" + prod.getCantidad();
		    }
		    
		}

			return "Producto con código " + codigo + " no encontrado.";
}
		}