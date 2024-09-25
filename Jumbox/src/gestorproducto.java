import java.text.ParseException;
import java.time.LocalDate;
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
				int codigoModificar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el codigo"));
				int precioNuevo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo precio"));
				String resultadoModificado = modificarPrecio(codigoModificar, precioNuevo);
				JOptionPane.showMessageDialog(null, resultadoModificado);
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

	// -------- Busqueda de productos----------//
	public String buscarProducto(int codigoProducto) {
		for (producto prod : producto) {
			if (prod.getCodigo() == codigoProducto) {
				return "Categoria: " + prod.getCategoria() + "\nMarca: " + prod.getMarca() + "\nProducto : "
						+ prod.getNombre() + "\nPrecio: " + prod.getPrecio() + "\nCodigo: " + prod.getCodigo()
						+ "\nPeso: " + prod.getPeso() + "\nVencimiento: " + prod.getVencimiento() + "\nStock Total : "
						+ prod.getCantidad();
			}
		}
		return "Producto no encontrado.";
	}
	// -------- Eliminar cantidad de stock de los productos----------//

	public String eliminarProducto(int codigo, int cantidad) {

		for (producto prod : producto) {
			if (prod.getCodigo() == codigo) {
				if (prod.getCantidad() >= cantidad) {
					prod.setCantidad(prod.getCantidad() - cantidad);
					return "Se descontaron " + cantidad + " unidades del producto " + prod.getNombre() + " "
							+ prod.getMarca() + ". Stock actual: " + prod.getCantidad();
				} else {
					return "No hay suficiente stock para descontar " + cantidad + " unidades.";
				}
			}
		}
		return "Producto con código " + codigo + " no encontrado.";
	}

	// -------- Agregar cantidad de stock de los productos----------//

	public String agregarProducto() {

		 String categoria = JOptionPane.showInputDialog("Ingrese la categoría del producto:");
	        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
	        String marca = JOptionPane.showInputDialog("Ingrese la marca del producto:");
	        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto:"));
	        int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del producto:"));
	        double peso = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el peso del producto:"));
	        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad en stock del producto:"));

	        LocalDate vencimiento = null;
	        

	        boolean fechaValida = false;
	        while (!fechaValida) {
	            try {
	                String fechaInput = JOptionPane.showInputDialog("Ingrese la fecha de vencimiento (dd/MM/yyyy):");
	                

	                // Verificar si la fecha ingresada es menor que la fecha actual
	                if (vencimiento.isBefore(LocalDate.now())) {
	                    throw new Exception("La fecha de vencimiento no puede ser menor a la fecha actual.");
	                }
	                fechaValida = true; // Si no lanza ninguna excepción, la fecha es válida
	            } catch (ParseException e) {
	                JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Intente nuevamente.");
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, e.getMessage());
	            }
	        }

	        // Crear el objeto producto con los datos ingresados
	        producto nuevoProducto = new producto(categoria, nombre, marca, precio, codigo, peso, vencimiento, cantidad);
	        producto.add(nuevoProducto);

	        JOptionPane.showMessageDialog(null, "Producto agregado con éxito!");
	        return "Producto agregado con éxito!";
	    }
	
	// -------- Modificar precio de losproductos----------//

	public String modificarPrecio(int codigo, double precio) {
		for (producto prod : producto) {
			if (prod.getCodigo() == codigo) {
				prod.setPrecio(precio);
				return "El nuevo precio es de: " + precio + " del producto " + prod.getNombre() + prod.getMarca();
			}
		}

		return "Producto con código " + codigo + " no encontrado.";
	}
}