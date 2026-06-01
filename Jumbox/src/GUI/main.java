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

        // Un bucle para que el menú vuelva a aparecer si se equivocan o si se registran
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
                if (mail == null) continue; // Vuelve al menú si el usuario cancela
                
                String contrasena = JOptionPane.showInputDialog("Ingresa contraseña:");
                if (contrasena == null) continue;

                Usuario usuarioLogueado = Login(mail, contrasena);
                
                if (usuarioLogueado == null) {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                } else {
                    JOptionPane.showMessageDialog(null, "¡Bienvenido " + usuarioLogueado.getNombre() + "!");

                    // Lógica de redirección según el rol
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
                        cliente nuevo = new cliente(usuarioLogueado.getNombre(), usuarioLogueado.getMail(), usuarioLogueado.getContrasena(), usuarioLogueado.getRol(), 0);
                        nuevo.menu();
                    }
                    break; // Salimos del bucle principal porque ya inició sesión correctamente
                }

            } else if (seleccion == 1) {
                // ------------------ REGISTRO ------------------
                String nombre = JOptionPane.showInputDialog("Ingresa tu nombre:");
                if (nombre == null || nombre.trim().isEmpty()) continue;
                
                String mail = JOptionPane.showInputDialog("Ingresa tu mail:");
                if (mail == null || mail.trim().isEmpty()) continue;
                
                String contrasena = JOptionPane.showInputDialog("Ingresa tu contraseña:");
                if (contrasena == null || contrasena.trim().isEmpty()) continue;
                
                // Pedimos el rol (podrías forzar a que todos los nuevos sean 'cliente' por defecto si lo prefieres)
                String[] roles = {"cliente", "empleado", "gerente", "admin"};
                String rol = (String) JOptionPane.showInputDialog(null, 
                        "Selecciona tu rol:", 
                        "Rol",
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        roles, 
                        roles[0]);
                if (rol == null) continue;

                // Creamos el objeto y usamos tu controlador que ya tiene el hash y el INSERT a la base de datos
                Usuario nuevoUsuario = new Usuario(nombre, mail, contrasena, rol);
                controllerUsuario.agregarUsuario(nuevoUsuario);
                
                // Al terminar, el bucle while vuelve a mostrar el menú para que ahora sí puedan hacer Login
                
            } else {
                // ------------------ SALIR ------------------
                // Si eligen "Salir" o cierran la ventana de la X
                System.out.println("Saliendo del sistema...");
                System.exit(0); 
            }
        }
    }

    // Tu método Login original corregido
    public static Usuario Login(String mail, String contrasena) {
        Usuario usuario = null;
        try {
            // 1. Buscamos SOLO por el mail
            PreparedStatement statement = (PreparedStatement) con
                    .prepareStatement("SELECT * FROM `usuario` WHERE `mail` = ?");
            statement.setString(1, mail);
            
            ResultSet resultSet = statement.executeQuery();
            
            // 2. Verificamos si existe un usuario con ese mail
            if (resultSet.next()) {
                String hashBD = resultSet.getString("contrasena");
                
                // 3. Comparamos la contraseña ingresada con el hash de la base de datos
                if (hashing.verificar(contrasena, hashBD)) {
                    System.out.println("Rol recuperado: " + resultSet.getString("rol"));
                    
                    // 4. Si coinciden, instanciamos el usuario
                    usuario = new Usuario(
                            resultSet.getString("nombre"), 
                            resultSet.getString("mail"),
                            hashBD, // Guardamos el hash en el objeto por seguridad
                            resultSet.getString("rol")
                    );
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