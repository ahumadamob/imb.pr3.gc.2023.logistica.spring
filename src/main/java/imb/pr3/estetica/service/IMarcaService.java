package imb.pr3.estetica.service;

import java.util.List;

import imb.pr3.estetica.entity.Marca;

public interface IMarcaService {
	
	List<Marca> buscarTodos();
	Marca buscarPorId(Integer id);
	void crear(Marca marca);
	void eliminar(Integer id);

}
