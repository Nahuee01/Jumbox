import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
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
				// ver gr�ficos
				break;
			case 1:
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el c�digo del producto"));
				String resultado = buscarProducto(codigoProducto);
				JOptionPane.showMessageDialog(null, resultado);
				break;
			case 2:
				int codigoProductoEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo"));

				String resultadoEliminacion = eliminarProducto(codigoProductoEliminar);
				JOptionPane.showMessageDialog(null, resultadoEliminacion);
				break;
			case 3:
				int codigoProductoModificar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo"));
				modificarProducto(codigoProductoModificar);
				break;
			case 4:
				agregarProducto();
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
				// ver gr�ficos
				break;
			case 1:
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el c�digo del producto"));
				String resultado = buscarProducto(codigoProducto);
				JOptionPane.showMessageDialog(null, resultado);
				break;
			default:
				break;
			}
		} while (opc != -1);
	}

	// -------- Busqueda de productos----------//
	public String buscarProducto(int codigoProducto) {
	    for (producto prod : producto) {
	        if (prod.getCodigo() == codigoProducto) {
	            
	            String stockMensaje = (prod.getCantidad() < 20) ? " (STOCK BAJO)" : "";
	            
	            return "Categoria: " + prod.getCategoria() + "\nMarca: " + prod.getMarca() + "\nProducto : "
	                    + prod.getNombre() + "\nPrecio: " + prod.getPrecio() + "\nCodigo: " + prod.getCodigo()
	                    + "\nPeso: " + prod.getPeso() + "\nVencimiento: " + prod.getVencimiento() + "\nStock Total : "
	                    + prod.getCantidad() + stockMensaje;
	        }
	    }
	    return "Producto no encontrado.";
	}
	// -------- Eliminar cantidad de stock de los productos----------//

	public String eliminarProducto(int codigo) {

		for (producto prod : producto) {
			if (prod.getCodigo() == codigo) {
				
				int confirmacion = JOptionPane.showConfirmDialog(null,
						"�Est� seguro que desea eliminar el producto " + prod.getNombre() + " " + prod.getMarca() + "?",
						"Confirmar eliminaci�n", JOptionPane.YES_NO_OPTION);

				if (confirmacion == JOptionPane.YES_OPTION) {
					producto.remove(prod);
					return "El producto " + prod.getNombre() + " " + prod.getMarca()
							+ " ha sido eliminado exitosamente.";
				} else {

					return "La eliminaci�n del producto ha sido cancelada.";
				}
			}
		}

		return "Producto con c�digo " + codigo + " no encontrado.";
	}

	// -------- Agregar productos----------//

	public String agregarProducto() {

		String[] categoria = { "Electrodomesticos", "Bebidas", "Lacteos", "Panaderia", "Perfumeria", "Pastas", };
		String cat = (String) JOptionPane.showInputDialog(null, "Selecciones la categoria", "Categorias",
				JOptionPane.PLAIN_MESSAGE, null, categoria, categoria[0]);

		String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
		String marca = JOptionPane.showInputDialog("Ingrese la marca del producto:");
		double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto:"));
		int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el c�digo del producto:"));
		double peso = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el peso del producto en gramos:"));
		int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad en stock del producto:"));
		if (cantidad < 20) {
			JOptionPane.showMessageDialog(null, "Advertencia Stock bajo.");
		}

		LocalDate vencimiento = null;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		boolean fechaValida = false;

		while (!fechaValida) {
			try {
				String fechaInput = JOptionPane.showInputDialog("Ingrese la fecha de vencimiento (dd/MM/yyyy):");
				vencimiento = LocalDate.parse(fechaInput, formato);

				if (vencimiento.isBefore(LocalDate.now())) {
					throw new Exception("La fecha de vencimiento no puede ser menor a la fecha actual.");
				}
				fechaValida = true;
			} catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(null, "Formato de fecha inv�lido. Intente nuevamente.");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}

		producto nuevoProducto = new producto(categoria[0], nombre, marca, precio, codigo, peso, vencimiento, cantidad);
		producto.add(nuevoProducto);

		JOptionPane.showMessageDialog(null, "Producto agregado con �xito!");
		return "Producto agregado con �xito";
	}
	// -------- Modificar precio de losproductos----------//

	public String modificarProducto(int codigo) {
		for (producto prod : producto) {
			if (prod.getCodigo() == codigo) {
				String[] opciones = { "Modificar Precio", "Modificar Cantidad" };
				int eleccion = JOptionPane.showOptionDialog(null, "�Qu� desea modificar?", "Modificar Producto",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

				if (eleccion == 0) {
					double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el nuevo precio:"));
					prod.setPrecio(nuevoPrecio);

					JOptionPane.showMessageDialog(null, "El nuevo precio es de: " + nuevoPrecio + " del producto "
							+ prod.getNombre() + " " + prod.getMarca());
				} else if (eleccion == 1) { // Opci�n 1 es "Modificar Cantidad"
					int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva cantidad:"));
					prod.setCantidad(nuevaCantidad);
					if (nuevaCantidad < 20) {
						JOptionPane.showMessageDialog(null,
								"Advertencia de Stock bajo " + "\nLa nueva cantidad en stock es de: " + nuevaCantidad
										+ " del producto " + prod.getNombre() + " " + prod.getMarca());
					}

				}
			}
		}

		return "Producto con c�digo " + codigo + " no encontrado.";
	}
}