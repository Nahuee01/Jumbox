import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class gestorproducto {
	private static Connection con = conexion.getInstance().getConection();

	
// -------------------Menu Gerente-------------------//
	public void menuGerente() {
		String[] menu = { "Ver Graficos", "Buscar Producto", "Eliminar Producto", "Modificar Producto",
				"Agregar Producto" };

		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu,
					menu[0]);

			switch (opc) {
			case 0:
				// ver gráficos
				break;
			case 1: // Buscar producto
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto"));
				producto resultado = BuscarProducto(codigoProducto);
				JOptionPane.showMessageDialog(null, resultado);
				break;
			case 2:// Eliminar producto
			    int codigoProductoEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID"));
			    producto resultado2 = BuscarProducto(codigoProductoEliminar);
			    if (resultado2 != null) {
			        String resultadoEliminacion = EliminarProducto(codigoProductoEliminar, resultado2);
			        JOptionPane.showMessageDialog(null, resultadoEliminacion);
			    } else {
			        JOptionPane.showMessageDialog(null, "No se encontró el producto con ese ID.");
			    }
			    break;

			case 3:
				// Modificar producto
			    int codigoProductoModificar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID"));
			    producto resultado3 = BuscarProducto(codigoProductoModificar);
			    if (resultado3 != null) {
			        String productoActualizado = ActualizarProducto(codigoProductoModificar, resultado3);
			        JOptionPane.showMessageDialog(null, productoActualizado);
			    } else {
			        JOptionPane.showMessageDialog(null, "Producto no encontrado");
			    }
			    break;

			case 4: // Agregar producto
				try {
			        String categoria = JOptionPane.showInputDialog("Ingrese la categoría:");
			        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
			        String marca = JOptionPane.showInputDialog("Ingrese la marca:");
			        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio:"));
			        int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código:"));
			        double peso = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el peso:"));
			        java.sql.Date vencimiento = java.sql.Date.valueOf(JOptionPane.showInputDialog("Ingrese fecha de vencimiento (YYYY-MM-DD):"));
			        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad:"));

			        producto nuevoProducto = new producto(categoria, nombre, marca, precio, codigo, peso, vencimiento, cantidad);
			        
			        AgregarProducto(nuevoProducto);
			        String resumen = "Producto agregado exitosamente:\n"
			                + "Categoría: " + categoria + "\n"
			                + "Nombre: " + nombre + "\n"
			                + "Marca: " + marca + "\n"
			                + "Precio: $" + precio + "\n"
			                + "Código: " + codigo + "\n"
			                + "Peso: " + peso + " kg\n"
			                + "Vencimiento: " + vencimiento + "\n"
			                + "Cantidad: " + cantidad + " unidades";

			        JOptionPane.showMessageDialog(null, resumen);
			    } catch (Exception e) {
			        JOptionPane.showMessageDialog(null, "Error al ingresar los datos: " + e.getMessage());
			    }
			    break;
				    default:
				     break;
			}
		} while (opc != -1);
	}
	
	
	// -------------------Menu Admin-------------------//
	
		public void menuAdmin() {
			String[] menu = { "Agregar Usuario", "Buscar Usuario", "Eliminar Usuario", "Actualizar Usuario",
					"Agregar Producto" };

			int opc;

			do {
				opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu,
						menu[0]);

				switch (opc) {
				case 0:
					controllerUsuario.agregarUsuario(null);
					break;
				case 1:
					controllerUsuario.BuscarUsuario(opc);
					break;
				case 2:
					controllerUsuario.EliminarUsuario(opc);
					break;
				case 3:
					controllerUsuario.ActualizarUsuario(null);
					break;
				default:
					break;
				}
			} while (opc != -1);
		}
		
		
	// -------------------Menu Empleado-------------------//

	public void menuEmpleado() {
		String[] menu = { "Ver Graficos", "Buscar Producto" };

		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu,
					menu[0]);

			switch (opc) {
			case 0:
				// ver gráficos
				break;
			case 1:
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del producto"));
				producto resultado = BuscarProducto(codigoProducto);
				JOptionPane.showMessageDialog(null, resultado);
				break;
			default:
				break;
			}
		} while (opc != -1);
	}

	
	// -------- Busqueda de productos----------//
	
	public static producto BuscarProducto(int id) {
	    producto nuevo = null;
	    String query = "SELECT * FROM producto WHERE idProducto = ?";
	    try (PreparedStatement statement = (PreparedStatement) con.prepareStatement(query)) {
	        statement.setInt(1, id);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                nuevo = new producto(
	                    resultSet.getString("categoria"),
	                    resultSet.getString("nombre"),
	                    resultSet.getString("marca"),
	                    resultSet.getDouble("precio"),
	                    resultSet.getInt("codigo"),
	                    resultSet.getDouble("peso"),
	                    resultSet.getDate("vencimiento"),
	                    resultSet.getInt("cantidad")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al buscar el producto: " + e.getMessage());
	    }
	    return nuevo;
	}
	
	// -------- Eliminar cantidad de stock de los productos----------//
	
	public static String EliminarProducto(int id, producto producto) {
	    try {
	        PreparedStatement statement = (PreparedStatement) 
	            con.prepareStatement("DELETE FROM `producto` WHERE idProducto= ? ");
	        statement.setInt(1, id);
	        int fila = statement.executeUpdate();
	        if (fila > 0) {
	            JOptionPane.showMessageDialog(null, "Se borró el producto: " + 
	        producto.getNombre() +
	        producto.getMarca() +
	        producto.getCodigo());
	        } else {
	            JOptionPane.showMessageDialog(null, "No se encontró el producto con ese Codigo");
	        }
	    } catch (Exception e) {
	        System.out.println("Error al borrar el producto: " + e.getMessage());
	    }
		return null;
	}



	// -------- Agregar productos----------//

	public static void AgregarProducto(producto producto) {
	    try {
	        PreparedStatement statement = (PreparedStatement) 
	            con.prepareStatement("INSERT INTO `producto`(`categoria`,`nombre`, `marca`, `precio`,`codigo`,`peso`,`vencimiento`,`cantidad`) VALUES (?,?,?,?,?,?,?,?)");
	        statement.setString(1, producto.getCategoria());
	        statement.setString(2, producto.getNombre());
	        statement.setString(3, producto.getMarca());
	        statement.setDouble(4, producto.getPrecio());
	        statement.setInt(5, producto.getCodigo());
	        statement.setDouble(6, producto.getPeso());
	        statement.setDate(7, producto.getVencimiento());  
	        statement.setInt(8, producto.getCantidad());

	        int filas = statement.executeUpdate();
	        if (filas > 0) {
	            JOptionPane.showMessageDialog(null, "Se agregó correctamente ");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	// -------- Modificar precio de los productos----------//

	public static String ActualizarProducto(int id, producto producto) {
	    try {
	        PreparedStatement statement = (PreparedStatement)
	            con.prepareStatement("UPDATE `producto` SET `categoria`=?,`nombre`=?,`marca`=?,`precio`=?,`codigo`=?,`peso`=?,`vencimiento`=?,`cantidad`=? WHERE id = ?");
	        
	        statement.setString(1, producto.getCategoria());
	        statement.setString(2, producto.getNombre());
	        statement.setString(3, producto.getMarca());
	        statement.setDouble(4, producto.getPrecio());
	        statement.setInt(5, producto.getCodigo());
	        statement.setDouble(6, producto.getPeso());
	        statement.setDate(7, producto.getVencimiento());
	        statement.setInt(8, producto.getCantidad());
	        
	        // se coloca el id al final
	        statement.setInt(9, id);

	        int fila = statement.executeUpdate();
	        if (fila > 0) {
	            JOptionPane.showMessageDialog(null, "Se actualizó el producto correctamente");
	        } else {
	            JOptionPane.showMessageDialog(null, "No se encontró el producto con ese ID");
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error al actualizar el producto: " + e.getMessage());
	    }
		return null;
	}


}