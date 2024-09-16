import java.util.LinkedList;
import javax.swing.JOptionPane;
import java.time.LocalDate;

public class main {

    public static void main(String[] args) {

        LinkedList<usuario> usuarios = new LinkedList<usuario>();
        LinkedList<producto> productos = new LinkedList<producto>();

        usuarios.add(new usuario("nahuel", "nahuel@gmail.com", "1234", "gerente"));
        usuarios.add(new usuario("lucas", "lucas@gmail.com", "4321", "empleado"));
        productos.add(new producto("Lacteos", "Leche", "Serenisima", 1200.0, 1534, 500.0, LocalDate.of(2025, 5, 24), 100));    
        productos.add(new producto("Pastas", "Fideos", "Matarazzo", 1500.0, 2345, 500.0, LocalDate.of(2025, 12, 4), 1));    

        String mail = JOptionPane.showInputDialog("Ingresa mail");
        String contrasena = JOptionPane.showInputDialog("Ingresa contrasena");

        usuario buscado = login(usuarios, mail, contrasena);
        if (buscado == null) {
            JOptionPane.showMessageDialog(null, "No encontrado");
        } else {
            JOptionPane.showMessageDialog(null, buscado);
            gestorproducto gestor = new gestorproducto(productos); // Pasar lista de productos
            if (buscado.getRol().equals("gerente")) {
                gerente nuevo = new gerente(buscado.getNombre(), buscado.getMail(), buscado.getContrasena(), buscado.getRol(), 0);
                nuevo.menu();
            } else {
                empleado nuevo = new empleado(buscado.getNombre(), buscado.getMail(), buscado.getContrasena(), buscado.getRol(), 0);
                nuevo.menu();
            }
        }
    }

    public static usuario login(LinkedList<usuario> usuarios, String mail, String contrasena) {
        for (usuario usuario : usuarios) {
            if (usuario.getMail().equals(mail) && usuario.getContrasena().equals(contrasena)) {
                System.out.println("Encontrado");
                return usuario;
            }
        }
        return null;
    }
}

