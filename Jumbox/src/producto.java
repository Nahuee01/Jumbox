import java.time.LocalDate;

public class producto {

	private String categoria;
	private String nombre;
	private String marca;
	private Double precio;
	private int codigo;
	private Double peso;
	private LocalDate vencimiento;
	private int cantidad;

	public producto(String categoria, String nombre, String marca, Double precio, int codigo, Double peso,
			LocalDate vencimiento, int cantidad) {
		super();
		this.categoria = categoria;
		this.nombre = nombre;
		this.marca = marca;
		this.precio = precio;
		this.codigo = codigo;
		this.peso = peso;
		this.vencimiento = vencimiento;
		this.cantidad = cantidad;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public LocalDate getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(LocalDate vencimiento) {
		this.vencimiento = vencimiento;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "producto [categoria=" + categoria + ", nombre=" + nombre + ", marca=" + marca + ", precio=" + precio
				+ ", codigo=" + codigo + ", peso=" + peso + ", vencimiento=" + vencimiento + ", cantidad=" + cantidad
				+ "]";
	}

}
