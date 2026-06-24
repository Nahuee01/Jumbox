package GUI;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import BLL.Usuario;
import BLL.admin;
import BLL.cliente;
import BLL.empleado;
import BLL.gerente;
import DLL.conexion;
import DLL.controllerUsuario;
import repository.hashing;

import java.sql.ResultSet;
import java.time.LocalDate;

public class main {
    private static Connection con = conexion.getInstance().getConection();

    public static void main(String[] args) {
        String[] opciones = {"Login", "Registrarse", "Salir"};


        while (true) {
            int seleccion = JOptionPane.showOptionDialog(null, 
                    "Bienvenido. ¿Qué deseas hacer?", 
                    "Menú de Inicio",
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE, 
                    null, 
                    opciones, 
                    opciones[0]);

            if (seleccion == 0) {
                // ------------------ LOGIN ------------------
                String mail = JOptionPane.showInputDialog("Ingresa mail:");
                if (mail == null) continue; 
                
                String contrasena = JOptionPane.showInputDialog("Ingresa contraseña:");
                if (contrasena == null) continue;

                Usuario usuarioLogueado = Login(mail, contrasena);
                
                if (usuarioLogueado == null) {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                } else {
                    JOptionPane.showMessageDialog(null, "¡Bienvenido " + usuarioLogueado.getNombre() + "!");
          
                    if (usuarioLogueado.getRol().equalsIgnoreCase("gerente")) {
                        System.out.println("Es un gerente");
                        gerente nuevo = new gerente(usuarioLogueado.getNombre(), usuarioLogueado.getMail(), usuarioLogueado.getContrasena(), usuarioLogueado.getRol(), 0);
                        nuevo.menu();
                    } else if (usuarioLogueado.getRol().equalsIgnoreCase("empleado")) {
                        System.out.println("Es un empleado");
                        empleado nuevo = new empleado(usuarioLogueado.getNombre(), usuarioLogueado.getMail(), usuarioLogueado.getContrasena(), usuarioLogueado.getRol(), 0);
                        nuevo.menu();
                    } else if (usuarioLogueado.getRol().equalsIgnoreCase("admin")) {
                        System.out.println("Es un admin");
                        admin nuevo = new admin(usuarioLogueado.getNombre(), usuarioLogueado.getMail(), usuarioLogueado.getContrasena(), usuarioLogueado.getRol(), 0);
                        nuevo.menu();
                    } else {
                        System.out.println("Es un cliente");
                        cliente nuevo = new cliente(usuarioLogueado.getNombre(), usuarioLogueado.getMail(), usuarioLogueado.getContrasena(), usuarioLogueado.getRol(), usuarioLogueado.getIdUsuario());;
                        nuevo.menu();
                    }
                    break; 
                }

            } else if (seleccion == 1) {
        
                String nombre = JOptionPane.showInputDialog("Ingresa tu nombre:");
                if (nombre == null || nombre.trim().isEmpty()) continue;
                
                String mail = JOptionPane.showInputDialog("Ingresa tu mail:");
                if (mail == null || mail.trim().isEmpty()) continue;
                
                String contrasena = JOptionPane.showInputDialog("Ingresa tu contraseña:");
                if (contrasena == null || contrasena.trim().isEmpty()) continue;
                
                String[] roles = {"cliente", "empleado", "gerente", "admin"};
                String rol = (String) JOptionPane.showInputDialog(null, 
                        "Selecciona tu rol:", 
                        "Rol",
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        roles, 
                        roles[0]);
                if (rol == null) continue;

                Usuario nuevoUsuario = new Usuario(nombre, mail, contrasena, rol);
                controllerUsuario.agregarUsuario(nuevoUsuario);
                
                
            } else {

                System.out.println("Saliendo del sistema...");
                System.exit(0); 
            }
        }
    }


    public static Usuario Login(String mail, String contrasena) {
        Usuario usuario = null;
        try {
            
            PreparedStatement statement = (PreparedStatement) con
                    .prepareStatement("SELECT * FROM `usuario` WHERE `mail` = ?");
            statement.setString(1, mail);
            
            ResultSet resultSet = statement.executeQuery();
            

            if (resultSet.next()) {
                String hashBD = resultSet.getString("contrasena");
                
                if (hashing.verificar(contrasena, hashBD)) {
                    System.out.println("Rol recuperado: " + resultSet.getString("rol"));
                    
                    usuario = new Usuario(
                            resultSet.getString("nombre"), 
                            resultSet.getString("mail"),
                            hashBD,
                            resultSet.getString("rol")
                    );
                    usuario.setIdUsuario(resultSet.getInt("idusuario"));
                } else {
                    System.out.println("Error: Contraseña incorrecta para el usuario " + mail);
                }
            } else {
                System.out.println("Error: No se encontró ningún usuario con el mail " + mail);
            }
        } catch (Exception e) {
            System.out.println("Error en la consulta de login: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }
}