import java.util.LinkedList;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.time.LocalDate;

public class main {
	private static Connection con = conexion.getInstance().getConection();

	public static void main(String[] args) {

	    String mail = JOptionPane.showInputDialog("Ingresa mail");
	    String contrasena = JOptionPane.showInputDialog("Ingresa contrasena");

	    Usuario Login = Login(mail, contrasena);
	    if (Login == null) {
	        JOptionPane.showMessageDialog(null, "No encontrado");
	    } else {
	        JOptionPane.showMessageDialog(null, Login);

	        if (Login.getRol().equalsIgnoreCase("gerente")) {
	            System.out.println("Es un gerente");
	            gerente nuevo = new gerente(Login.getNombre(), Login.getMail(), Login.getContrasena(), Login.getRol(), 0);
	            nuevo.menu();
	        } else if (Login.getRol().equalsIgnoreCase("empleado")) {
	            System.out.println("Es un empleado");
	            empleado nuevo = new empleado(Login.getNombre(), Login.getMail(), Login.getContrasena(), Login.getRol(), 0);
	            nuevo.menu();
	        } else if (Login.getRol().equalsIgnoreCase("admin")) {
	            System.out.println("Es un admin");
	            admin nuevo = new admin(Login.getNombre(), Login.getMail(), Login.getContrasena(), Login.getRol(), 0);
	            nuevo.menu();
	        } else {
	            System.out.println("Es un cliente");
	            cliente nuevo = new cliente(Login.getNombre(), Login.getMail(), Login.getContrasena(), Login.getRol(), 0);
	            nuevo.menu();
	        }
	    }
	}



	public static Usuario Login(String mail, String contrasena) {
	    Usuario usuario = null;
	    try {
	        PreparedStatement statement = (PreparedStatement) con.prepareStatement("SELECT * FROM `usuario` WHERE `mail` = ? AND `contrasena` = ?");
	        statement.setString(1, mail);
	        statement.setString(2, contrasena);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	        	   System.out.println("Rol recuperado: " + resultSet.getString("rol"));
	            usuario = new Usuario(resultSet.getString("nombre"), resultSet.getString("mail"), resultSet.getString("contrasena"), resultSet.getString("rol"));
	        }
	    } catch (Exception e) {
	        System.out.println("Error en la consulta: " + e.getMessage());
	    }
	    return usuario;
	}
}