import java.sql.ResultSet;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class controllerUsuario {
	private static Connection con = conexion.getInstance().getConection();
	
	public static void agregarUsuario(Usuario usuario) {
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("INSERT INTO `usuario`(`nombre`,`mail`, `contrasena`, `rol`) VALUES (?,?,?,?)");
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getMail());
			statement.setString(3, usuario.getContrasena());
			statement.setString(4, usuario.getRol());
			
			int filas = statement.executeUpdate();
			if(filas>0) {
				JOptionPane.showMessageDialog(null, "Se agregó");
			}

			
		} catch (Exception e) {
System.out.println("No se agregó");		}		
	}
	public static LinkedList<Usuario> MostrarUsuarios() {
	    LinkedList<Usuario> usuarios = new LinkedList<>();
	    try {
	        PreparedStatement statement = (PreparedStatement) con.prepareStatement("SELECT * FROM `usuario`");
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            
	            usuarios.add(new Usuario(resultSet.getString("nombre"), resultSet.getString("rol"), resultSet.getString("contrasena"), resultSet.getString("mail")));
	            System.out.println("Usuario: " + resultSet.getString("mail") + ", Rol: " + resultSet.getString("rol"));
	        }
	    } catch (Exception e) {
	        System.out.println("No se pudo obtener los usuarios: " + e.getMessage());
	    }
	    return usuarios;
	}
	public static Usuario BuscarUsuario(int id) {
		Usuario nuevo = null;
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("SELECT * FROM `usuario` WHERE id= ? ");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				nuevo = new Usuario(resultSet.getString("nombre"), resultSet.getString("rol"),resultSet.getString("contrasena"),resultSet.getString("mail") );
			}
		
		} catch (Exception e) {
			System.out.println("No se agregó");		
		}
		
		
		return nuevo;
	}
	public static void EliminarUsuario(int id) {
		Usuario nuevo = null;
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("DELETE FROM `usuario` WHERE id= ? ");
			statement.setInt(1, id);
			int fila = statement.executeUpdate();
			if (fila>0) {
				JOptionPane.showMessageDialog(null, "Se borró");
			}
		
		} catch (Exception e) {
			System.out.println("No se borró");		
		}
		
		
	}
	public static void ActualizarUsuario(Usuario usuario) {
		
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("UPDATE `usuario` SET `nombre`=?,`rol`=?,`contrasena`=? WHERE id = ?");
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getRol());
			statement.setString(3, usuario.getContrasena());
			statement.setString(4, usuario.getMail());

			int fila = statement.executeUpdate();
			if (fila>0) {
				JOptionPane.showMessageDialog(null, "Se actualizó");
			}
		
		} catch (Exception e) {
			System.out.println("No se borró");		
		}
		
		
	}

}
