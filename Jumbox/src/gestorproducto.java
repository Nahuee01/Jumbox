import java.util.LinkedList;

public class gestorproducto {

	private LinkedList<producto> producto = new LinkedList<producto>();

	public LinkedList<producto> getProducto() {
		return producto;
	}

	public void setProducto(LinkedList<producto> producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "gestorproducto [producto=" + producto + "]";
	}
	
	
}
