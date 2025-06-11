
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class gestorproducto {
	private static Connection con = conexion.getInstance().getConection();
	private ArrayList<producto> listaProductos = new ArrayList<>();

// -------------------Menu Gerente-------------------//
	public void menuGerente() {
		String[] menu = { "Buscar Producto", "Eliminar Producto", "Modificar Producto", "Agregar Producto" };

		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu,
					menu[0]);

			switch (opc) {
			case 0:// Buscar producto
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto"));
				producto resultado = BuscarProducto(codigoProducto);
				JOptionPane.showMessageDialog(null,
						"Categoría: " + resultado.getCategoria() + "\n" + "Nombre: " + resultado.getNombre() + "\n"
								+ "Marca: " + resultado.getMarca() + "\n" + "Precio: $" + resultado.getPrecio() + "\n"
								+ "Código: " + resultado.getCodigo() + "\n" + "Peso: " + resultado.getPeso() + " kg\n"
								+ "Vencimiento: " + resultado.getVencimiento() + "\n" + "Cantidad: "
								+ resultado.getCantidad() + " unidades");
				break;
			case 1: // Eliminar producto
				int codigoProductoEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID"));
				producto resultado2 = BuscarProducto(codigoProductoEliminar);
				if (resultado2 != null) {
					String resultadoEliminacion = EliminarProducto(codigoProductoEliminar, resultado2);
					JOptionPane.showMessageDialog(null, resultadoEliminacion);

				} else {
					JOptionPane.showMessageDialog(null, "No se encontró el producto con ese ID.");
				}
				break;
			case 2:// Modificar producto
				int codigoProductoModificar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID"));
				producto resultado3 = BuscarProducto(codigoProductoModificar);
				if (resultado3 != null) {
					String productoActualizado = ActualizarProducto(codigoProductoModificar, resultado3);
					JOptionPane.showMessageDialog(null, productoActualizado);
				} else {
					JOptionPane.showMessageDialog(null, "Producto no encontrado");
				}
				break;

			case 3:// Agregar producto
				String categoria = JOptionPane.showInputDialog("Ingrese la categoría:");
				String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
				String marca = JOptionPane.showInputDialog("Ingrese la marca:");

				boolean existe = false;
				for (producto p : listaProductos) {
					if (p.getNombre().equalsIgnoreCase(nombre) && p.getMarca().equalsIgnoreCase(marca)) {
						existe = true;
						break;
					}
				}

				if (existe) {
					JOptionPane.showMessageDialog(null, "El producto con ese nombre y marca ya existe.");
				} else {
					double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio:"));
					int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código:"));
					double peso = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el peso:"));
					java.sql.Date vencimiento = java.sql.Date
							.valueOf(JOptionPane.showInputDialog("Ingrese fecha de vencimiento (YYYY-MM-DD):"));
					int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad:"));

					producto nuevoProducto = new producto(categoria, nombre, marca, precio, codigo, peso, vencimiento,
							cantidad);

					AgregarProducto(nuevoProducto);
					listaProductos.add(nuevoProducto);

					String resumen = "Producto agregado exitosamente:\n" + "Categoría: " + categoria + "\n" + "Nombre: "
							+ nombre + "\n" + "Marca: " + marca + "\n" + "Precio: $" + precio + "\n" + "Código: "
							+ codigo + "\n" + "Peso: " + peso + " kg\n" + "Vencimiento: " + vencimiento + "\n"
							+ "Cantidad: " + cantidad + " unidades";

					JOptionPane.showMessageDialog(null, resumen);
				}
				break;

			default:
				break;
			}
		} while (opc != -1);
	}

	// -------------------Menu Admin-------------------//

	public void menuAdmin() {
		String[] menu = { "Agregar Usuario", "Buscar Usuario", "Eliminar Usuario", "Actualizar Usuario" };
		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Menu Admin", 0, 0, null, menu, menu[0]);

			switch (opc) {
			case 0: // Agregar Usuario
				String nombre = JOptionPane.showInputDialog("Ingrese nombre:");
				String mail = JOptionPane.showInputDialog("Ingrese mail:");
				String contrasena = JOptionPane.showInputDialog("Ingrese contraseña:");
				String rol = JOptionPane.showInputDialog("Ingrese rol:");

				Usuario nuevo = new Usuario(nombre, mail, contrasena, rol);
				controllerUsuario.agregarUsuario(nuevo);
				break;

			case 1: // Buscar Usuario
				int idBuscar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID del usuario a buscar:"));
				Usuario encontrado = controllerUsuario.BuscarUsuario(idBuscar);
				if (encontrado != null) {
					JOptionPane.showMessageDialog(null, "Usuario encontrado:\n" + "Nombre: " + encontrado.getNombre()
							+ "\n" + "Mail: " + encontrado.getMail() + "\n" + "Rol: " + encontrado.getRol());
				} else {
					JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
				}
				break;

			case 2: // Eliminar Usuario
				int idEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID del usuario a eliminar:"));
				controllerUsuario.EliminarUsuario(idEliminar);
				break;

			case 3: // Actualizar Usuario
				int idActualizar = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese ID del usuario a actualizar:"));
				String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:");
				String nuevoRol = JOptionPane.showInputDialog("Nuevo rol:");
				String nuevaContrasena = JOptionPane.showInputDialog("Nueva contraseña:");

				Usuario actualizado = new Usuario(nuevoNombre, nuevoRol, nuevaContrasena, "");
				actualizado.setId(idActualizar);
				controllerUsuario.ActualizarUsuario(actualizado);
				break;

			default:
				break;
			}
		} while (opc != -1);
	}

	// -------------------Menu Empleado-------------------//

	public void menuEmpleado() {
		String[] menu = {  "Buscar Producto" };

		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu,
					menu[0]);

			switch (opc) {
			case 0:
				int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto"));
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
					nuevo = new producto(resultSet.getString("categoria"), resultSet.getString("nombre"),
							resultSet.getString("marca"), resultSet.getDouble("precio"), resultSet.getInt("codigo"),
							resultSet.getDouble("peso"), resultSet.getDate("vencimiento"),
							resultSet.getInt("cantidad"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al buscar el producto: " + e.getMessage());
		}
		return nuevo;
	}

	// -------- Eliminar cantidad de stock de los productos----------//

	public String EliminarProducto(int id, producto producto) {
		try {
			PreparedStatement statement = (PreparedStatement) con
					.prepareStatement("DELETE FROM `producto` WHERE idProducto= ? ");
			statement.setInt(1, id);
			int fila = statement.executeUpdate();
			if (fila > 0) {
				listaProductos.removeIf(p -> p.getCodigo() == producto.getCodigo());

				return "Se borró el producto: " + producto.getNombre() + " " + producto.getMarca() + " (Código: "
						+ producto.getCodigo() + ")";
			} else {
				return "No se encontró el producto con ese Código";
			}
		} catch (Exception e) {
			System.out.println("Error al borrar el producto: " + e.getMessage());
			return "Error al borrar el producto.";
		}
	}
	// -------- Agregar productos----------//

	public static void AgregarProducto(producto producto) {

		try {
			PreparedStatement statement = (PreparedStatement) con.prepareStatement(
					"INSERT INTO `producto`(`categoria`,`nombre`, `marca`, `precio`,`codigo`,`peso`,`vencimiento`,`cantidad`) VALUES (?,?,?,?,?,?,?,?)");
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

	public String ActualizarProducto(int id, producto producto) {

		double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio:", producto.getPrecio()));
		java.sql.Date nuevoVencimiento = java.sql.Date.valueOf(JOptionPane
				.showInputDialog("Nueva fecha de vencimiento (YYYY-MM-DD):", producto.getVencimiento().toString()));
		int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Nueva cantidad:", producto.getCantidad()));

		try {
			PreparedStatement statement = (PreparedStatement) con.prepareStatement(
					"UPDATE `producto` SET `precio`=?, `vencimiento`=?, `cantidad`=? WHERE idProducto = ?");

			statement.setDouble(1, nuevoPrecio);
			statement.setDate(2, nuevoVencimiento);
			statement.setInt(3, nuevaCantidad);
			statement.setInt(4, id);

			int fila = statement.executeUpdate();
			if (fila > 0) {
				for (producto p : listaProductos) {
					if (p.getCodigo() == producto.getCodigo()) {
						p.setPrecio(nuevoPrecio);
						p.setVencimiento(nuevoVencimiento);
						p.setCantidad(nuevaCantidad);
						break;
					}
				}
				return "Se actualizó el producto correctamente.";
			} else {
				return "No se encontró el producto con ese ID.";
			}
		} catch (Exception e) {
			System.out.println("Error al actualizar el producto: " + e.getMessage());
			return "Error al actualizar el producto.";
		}
	}

	// -------------------Menu Cliente-------------------//

	public void menuCliente(int idUsuario) {
		cargarProductosDesdeBD(); // <<--- Cargar productos desde la base de datos

		String[] menu = { "Comprar" };
		int opc;

		do {
			opc = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Menú Cliente", 0, 0, null, menu,
					menu[0]);

			switch (opc) {
			case 0: // Comprar
				cargarProductosDesdeBD(); // Actualiza lista desde la base

				if (listaProductos.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No hay productos disponibles.");
					break;
				}

				ArrayList<String> resumenCompra = new ArrayList<>();
				double totalCompra = 0;

				boolean seguirComprando = false;

				do {
					// Armar menú de opciones
					String[] opciones = new String[listaProductos.size()];
					for (int i = 0; i < listaProductos.size(); i++) {
						producto p = listaProductos.get(i);
						opciones[i] = p.getNombre() + " - $" + p.getPrecio() + " (" + p.getCantidad() + " disponibles)";
					}

					String seleccion = (String) JOptionPane.showInputDialog(null,
							"Seleccione un producto para comprar:", "Productos disponibles", JOptionPane.PLAIN_MESSAGE,
							null, opciones, opciones[0]);

					if (seleccion == null)
						break;

					// Buscar producto seleccionado
					producto prodSeleccionado = null;
					for (producto p : listaProductos) {
						String opcion = p.getNombre() + " - $" + p.getPrecio() + " (" + p.getCantidad()
								+ " disponibles)";
						if (opcion.equals(seleccion)) {
							prodSeleccionado = p;
							break;
						}
					}

					if (prodSeleccionado == null) {
						JOptionPane.showMessageDialog(null, "Error al seleccionar el producto.");
						break;
					}

					try {
						int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad a comprar:"));

						if (cantidad <= 0) {
							JOptionPane.showMessageDialog(null, "Cantidad inválida.");
						} else if (cantidad > prodSeleccionado.getCantidad()) {
							JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
						} else {
							double subtotal = prodSeleccionado.getPrecio() * cantidad;
							totalCompra += subtotal;

							// Actualiza stock
							int nuevoStock = prodSeleccionado.getCantidad() - cantidad;
							prodSeleccionado.setCantidad(nuevoStock);
							actualizarStockEnBD(prodSeleccionado.getCodigo(), nuevoStock);

							// Agrega a resumen
							resumenCompra.add(prodSeleccionado.getNombre() + " x" + cantidad + " = $" + subtotal);

							// Preguntar si quiere seguir
							int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea comprar otro producto?",
									"Continuar", JOptionPane.YES_NO_OPTION);
							seguirComprando = (respuesta == JOptionPane.YES_OPTION);
						}

					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Entrada inválida.");
						seguirComprando = false;
					}

				} while (seguirComprando);

				// Mostrar resumen final
				if (!resumenCompra.isEmpty()) {
					StringBuilder resumen = new StringBuilder("Resumen de compra:\n\n");
					for (String item : resumenCompra) {
						resumen.append(item).append("\n");
					}
					resumen.append("\nTotal a pagar: $").append(totalCompra);
					JOptionPane.showMessageDialog(null, resumen.toString());
					
					registrarCompraEnBD(idUsuario, totalCompra);
					
				} else {
					JOptionPane.showMessageDialog(null, "No se realizaron compras.");
				}
				

				break;

			default:
				break;
			}
		} while (opc != -1);
	}

	// Base de datos

	public void cargarProductosDesdeBD() {
		listaProductos.clear(); // Limpiar lista antes de volver a cargar

		String query = "SELECT * FROM producto";

		try (PreparedStatement statement = (PreparedStatement) con.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				producto p = new producto(resultSet.getString("categoria"), resultSet.getString("nombre"),
						resultSet.getString("marca"), resultSet.getDouble("precio"), resultSet.getInt("codigo"),
						resultSet.getDouble("peso"), resultSet.getDate("vencimiento"), resultSet.getInt("cantidad"));
				listaProductos.add(p);
			}

			System.out.println("Productos cargados desde la base de datos.");

		} catch (SQLException e) {
			System.out.println("Error al cargar productos desde la base de datos: " + e.getMessage());
		}
	}

	// Actualiza el stock del producto en la base de datos
	public void actualizarStockEnBD(int codigoProducto, int nuevoStock) {
		try {
			PreparedStatement statement = (PreparedStatement) con
					.prepareStatement("UPDATE producto SET cantidad = ? WHERE codigo = ?");
			statement.setInt(1, nuevoStock);
			statement.setInt(2, codigoProducto);
			int filas = statement.executeUpdate();

			if (filas <= 0) {
				JOptionPane.showMessageDialog(null, "No se pudo actualizar el stock en la base de datos.");
			}
		} catch (Exception e) {
			System.out.println("Error al actualizar stock en BD: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	

	public static void registrarCompraEnBD(int idUsuario, double total) {
	   
	    try {
	    	PreparedStatement statement = (PreparedStatement) con.prepareStatement(
	    				"INSERT INTO `compras`(`idUsuario`,`total`, `fecha`) VALUES (?,?,?)");

	        statement.setInt(1, idUsuario);
	        statement.setDouble(2, total);
	        statement.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Fecha actual
	        statement.executeUpdate();

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error al registrar la compra: " + e.getMessage());
	    } 
	}

	}

