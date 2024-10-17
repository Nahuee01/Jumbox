import java.sql.ResultSet;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class controllerUsuario {
	private static Connection con = conexion.getInstance().getConection();
	
	public static void agregarUsuario(usuario usuario) {
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("INSERT INTO `usuario`(`nombre`,`mail`, `contrasena`, `rol`) VALUES (?,?,?,?)");
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getMail());
			statement.setString(3, usuario.getContrasena());
			statement.setString(4, usuario.getRol());
			
			int filas = statement.executeUpdate();
			if(filas>0) {
				JOptionPane.showMessageDialog(null, "Se agreg�");
			}

			
		} catch (Exception e) {
System.out.println("No se agreg�");		}		
	}
	public static LinkedList<usuario> MostrarUsuarios() {
	    LinkedList<usuario> usuarios = new LinkedList<>();
	    try {
	        PreparedStatement statement = (PreparedStatement) con.prepareStatement("SELECT * FROM `usuario`");
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            
	            usuarios.add(new usuario(resultSet.getString("nombre"), resultSet.getString("rol"), resultSet.getString("contrasena"), resultSet.getString("mail")));
	            System.out.println("Usuario: " + resultSet.getString("mail") + ", Rol: " + resultSet.getString("rol"));
	        }
	    } catch (Exception e) {
	        System.out.println("No se pudo obtener los usuarios: " + e.getMessage());
	    }
	    return usuarios;
	}
	public static usuario BuscarUsuario(int id) {
		usuario nuevo = null;
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("SELECT * FROM `usuario` WHERE id= ? ");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				nuevo = new usuario(resultSet.getString("nombre"), resultSet.getString("rol"),resultSet.getString("contrasena"),resultSet.getString("mail") );
			}
		
		} catch (Exception e) {
			System.out.println("No se agreg�");		
		}
		
		
		return nuevo;
	}
	public static void EliminarUsuario(int id) {
		usuario nuevo = null;
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("DELETE FROM `usuario` WHERE id= ? ");
			statement.setInt(1, id);
			int fila = statement.executeUpdate();
			if (fila>0) {
				JOptionPane.showMessageDialog(null, "Se borr�");
			}
		
		} catch (Exception e) {
			System.out.println("No se borr�");		
		}
		
		
	}
	public static void ActualizarUsuario(usuario usuario) {
		
		try {
			
			PreparedStatement statement = (PreparedStatement) 
					con.prepareStatement("UPDATE `usuario` SET `nombre`=?,`rol`=?,`contrasena`=? WHERE id = ?");
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getRol());
			statement.setString(3, usuario.getContrasena());
			statement.setString(4, usuario.getMail());

			int fila = statement.executeUpdate();
			if (fila>0) {
				JOptionPane.showMessageDialog(null, "Se actualiz�");
			}
		
		} catch (Exception e) {
			System.out.println("No se borr�");		
		}
		
		
	}

}
