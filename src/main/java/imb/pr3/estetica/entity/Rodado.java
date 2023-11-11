package imb.pr3.estetica.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Rodado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	private String patente;
	@ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;
	private Integer añoFabricacion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	public Marca getMarca() {
        return marca;
    }
    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    
	public Integer getAñoFabricacion() {
		return añoFabricacion;
	}
	
	public void setAñoFabricacion(Integer añoFabricacion) {
		this.añoFabricacion = añoFabricacion;
	}

}