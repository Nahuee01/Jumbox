import java.sql.ResultSet;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class controllerUsuario {
	private static Connection con = conexion.getInstance().getConection();
	private static LinkedList<Usuario> usuarios = new LinkedList<>();
	
	// -------- Agregar de Usuarios----------//
	
	 public static void agregarUsuario(Usuario usuario) {
	        try {
	            // 1. Verificar si el mail ya existe
	            PreparedStatement checkStmt = (PreparedStatement)
	                con.prepareStatement("SELECT * FROM usuario WHERE mail = ?");
	            checkStmt.setString(1, usuario.getMail());
	            ResultSet resultSet = checkStmt.executeQuery();

	            if (resultSet.next()) {
	                JOptionPane.showMessageDialog(null, "El usuario con ese mail ya existe.");
	                return;
	            }

	            // 2. Insertar nuevo usuario
	            PreparedStatement insertStmt = (PreparedStatement)
	                con.prepareStatement("INSERT INTO usuario(nombre, mail, contrasena, rol) VALUES (?, ?, ?, ?)");
	            insertStmt.setString(1, usuario.getNombre());
	            insertStmt.setString(2, usuario.getMail());
	            insertStmt.setString(3, usuario.getContrasena());
	            insertStmt.setString(4, usuario.getRol());

	            int filas = insertStmt.executeUpdate();
	            if (filas > 0) {
	                usuarios.add(usuario); // Agregar a la lista local
	                JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.");
	            }

	        } catch (Exception e) {
	            System.out.println("Error al agregar usuario: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    public static LinkedList<Usuario> MostrarUsuarios() {
	        usuarios.clear(); // Limpiar lista antes de volver a llenarla
	        try {
	            PreparedStatement statement = (PreparedStatement) con.prepareStatement("SELECT * FROM usuario");
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                usuarios.add(new Usuario(
	                    resultSet.getString("nombre"),
	                    resultSet.getString("rol"),
	                    resultSet.getString("contrasena"),
	                    resultSet.getString("mail")
	                ));
	            }
	        } catch (Exception e) {
	            System.out.println("No se pudo obtener los usuarios: " + e.getMessage());
	        }
	        return usuarios;
	    }
	
	
	// -------- Busqueda de Usuarios----------//
	public static Usuario BuscarUsuario(int id) {
	    Usuario nuevo = null;
	    try {
	        PreparedStatement statement = (PreparedStatement) 
	            con.prepareStatement("SELECT * FROM `usuario` WHERE idusuario= ? ");
	        statement.setInt(1, id);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            nuevo = new Usuario(
	                resultSet.getString("nombre"),
	                resultSet.getString("mail"),
	                resultSet.getString("contrasena"),
	                resultSet.getString("rol")
	            );
	            nuevo.setId(resultSet.getInt("idusuario")); // Guarda el ID también
	        } else {
	            System.out.println("No se encontró usuario con ID: " + id);
	        }
	    } catch (Exception e) {
	        System.out.println("Error al buscar usuario: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return nuevo;
	}
	
	// -------- Eliminar de Usuarios----------//
	
	public static void EliminarUsuario(int id) {
	    try {
	        PreparedStatement statement = (PreparedStatement) 
	            con.prepareStatement("DELETE FROM usuario WHERE idusuario = ?");
	        statement.setInt(1, id);
	        int fila = statement.executeUpdate();

	        if (fila > 0) {
	            // También eliminar de la lista en memoria
	            usuarios.removeIf(u -> u.getId() == id); // ← Remueve el usuario por ID
	            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
	        } else {
	            JOptionPane.showMessageDialog(null, "No se encontró usuario con ese ID.");
	        }

	    } catch (Exception e) {
	        System.out.println("Error al eliminar usuario: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	// -------- Actualizar  Usuarios----------//
	
	public static void ActualizarUsuario(Usuario usuario) {
	    try {
	        PreparedStatement statement = (PreparedStatement)
	            con.prepareStatement("UPDATE usuario SET nombre = ?, mail = ?, contrasena = ?, rol = ? WHERE idusuario = ?");
	        statement.setString(1, usuario.getNombre());
	        statement.setString(2, usuario.getMail());
	        statement.setString(3, usuario.getContrasena());
	        statement.setString(4, usuario.getRol());
	        statement.setInt(5, usuario.getId()); // ¡Faltaba esto!

	        int fila = statement.executeUpdate();
	        if (fila > 0) {
	            // Actualizar también en la LinkedList
	            for (int i = 0; i < usuarios.size(); i++) {
	                if (usuarios.get(i).getId() == usuario.getId()) {
	                    usuarios.set(i, usuario); // Reemplaza el usuario con los nuevos datos
	                    break;
	                }
	            }
	            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
	        } else {
	            JOptionPane.showMessageDialog(null, "No se encontró el usuario para actualizar.");
	        }

	    } catch (Exception e) {
	        System.out.println("Error al actualizar: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


}
