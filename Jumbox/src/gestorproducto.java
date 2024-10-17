import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class gestorproducto {
	private static Connection con = conexion.getInstance().getConection();

	
// -------------------Menu Gerente-------------------
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
			case 1:
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del producto"));
				String resultado = BuscarProducto(codigoProducto);
				JOptionPane.showMessageDialog(null, resultado);
				break;
			case 2:
				int codigoProductoEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo"));

				String resultadoEliminacion = EliminarProducto(codigoProductoEliminar);
				JOptionPane.showMessageDialog(null, resultadoEliminacion);
				break;
			case 3:
				int codigoProductoModificar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo"));
				ActualizarProducto(codigoProductoModificar);
				break;
			case 4:
				AgregarProducto(null);
				break;
			default:
				break;
			}
		} while (opc != -1);
	}
	// -------------------Menu Admin-------------------
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
	// -------------------Menu Empleado-------------------

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
				String resultado = BuscarProducto(codigoProducto);
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
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("SELECT * FROM `producto` WHERE id= ? ");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				nuevo = new producto(resultSet.getString("nombre"), resultSet.getString("rol"),resultSet.getString("contrasena"),resultSet.getString("mail") );
			}
		
		} catch (Exception e) {
			System.out.println("No se agregó");		
		}
		
		
		return nuevo;
	}
	// -------- Eliminar cantidad de stock de los productos----------//

	public static void EliminarProducto(int id) {
		producto nuevo = null;
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("DELETE FROM `producto` WHERE id= ? ");
			statement.setInt(1, id);
			int fila = statement.executeUpdate();
			if (fila>0) {
				JOptionPane.showMessageDialog(null, "Se borró");
			}
		
		} catch (Exception e) {
			System.out.println("No se borró");		
		}
		
		
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
			if(filas>0) {
				JOptionPane.showMessageDialog(null, "Se agregó");
			}

			
		} catch (Exception e) {
System.out.println("No se agregó");		}		
	}
	// -------- Modificar precio de losproductos----------//

public static void ActualizarProducto(producto producto) {
		
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("UPDATE `producto` SET `categoria`=?,`nombre`=?,`marca`=? ,`precio`=?,`codigo`=?,`peso`=?,`vencimiento`=?,`cantidad`=?, WHERE id = ?");
			statement.setString(1, producto.getCategoria());
			statement.setString(2, producto.getNombre());
			statement.setString(3, producto.getMarca());
			statement.setDouble(4, producto.getPrecio());
			statement.setInt(5, producto.getCodigo());
			statement.setDouble(6, producto.getPeso());
			statement.setDate(7, producto.getVencimiento());
			statement.setInt(8, producto.getCantidad());

			int fila = statement.executeUpdate();
			if (fila>0) {
				JOptionPane.showMessageDialog(null, "Se actualizó");
			}
		
		} catch (Exception e) {
			System.out.println("No se actualizo");		
		}
		
		
	}

}