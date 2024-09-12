import java.sql.Date;

public class producto {

	private String categoria;
	private String nombre;
	private String marca;
	private Double precio;
	private int codigo;
	private Double peso;
	private Date vencimiento;
	
	public producto(String categoria, String nombre, String marca, Double precio, int codigo, Double peso,
			Date vencimiento) {
		super();
		this.categoria = categoria;
		this.nombre = nombre;
		this.marca = marca;
		this.precio = precio;
		this.codigo = codigo;
		this.peso = peso;
		this.vencimiento = vencimiento;
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
	public Date getVencimiento() {
		return vencimiento;
	}
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	@Override
	public String toString() {
		return "producto [categoria=" + categoria + ", nombre=" + nombre + ", marca=" + marca + ", precio=" + precio
				+ ", codigo=" + codigo + ", peso=" + peso + ", vencimiento=" + vencimiento + "]";
	}
	
	
}
