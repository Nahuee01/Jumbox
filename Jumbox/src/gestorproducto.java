import java.util.LinkedList;

import javax.swing.JOptionPane;

public class gestorproducto {

    private LinkedList<producto> productos;

    // Constructor que acepta una lista de productos
    public gestorproducto(LinkedList<producto> productos) {
        this.productos = productos;
    }

    public LinkedList<producto> getProductos() {
        return productos;
    }

    public void setProductos(LinkedList<producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "gestorproducto [productos=" + productos + "]";
    }

    public void menuGerente() {
        String[] menu = {"Ver Graficos", "Buscar Producto", "Eliminar Producto", "Modificar Producto", "Agregar Producto"};
        
        int opc;

        do {
            opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu, menu[0]);

            switch (opc) {
            case 0:
                // Implementar acción para ver gráficos
                break;
            case 1:
                int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del producto"));
                String resultado = buscarProducto(codigoProducto);
                JOptionPane.showMessageDialog(null, resultado);
                break;
            case 2:
                // Implementar acción para eliminar producto
                break;
            case 3:
                // Implementar acción para modificar producto
                break;
            case 4:
                // Implementar acción para agregar producto
                break;
            default:
                break;
            }
        } while (opc != -1);
    }

    public void menuEmpleado() {
        String[] menu = {"Ver Graficos", "Buscar Producto"};

        int opc;

        do {
            opc = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Menu Principal", 0, 0, null, menu, menu[0]);

            switch (opc) {
            case 0:
                // Implementar acción para ver gráficos
                break;
            case 1:
                int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del producto"));
                String resultado = buscarProducto(codigoProducto);
                JOptionPane.showMessageDialog(null, resultado);
                break;
            default:
                break;
            }
        } while (opc != -1);
    }

    public String buscarProducto(int codigoProducto) {
        for (producto prod : productos) {
            if (prod.getCodigo() == codigoProducto) {
                return "Producto encontrado: " + prod.getNombre();
            }
        }
        return "Producto no encontrado.";
    }
}