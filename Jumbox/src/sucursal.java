
public class sucursal {

	private String direccion;
	private String localidad;
	
	public sucursal(String direccion, String localidad) {
		super();
		this.direccion = direccion;
		this.localidad = localidad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	@Override
	public String toString() {
		return "sucursal [direccion=" + direccion + ", localidad=" + localidad + "]";
	}
	
	
	
}
