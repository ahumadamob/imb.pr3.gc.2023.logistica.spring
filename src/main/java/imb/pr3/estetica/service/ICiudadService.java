package imb.pr3.estetica.service;

import java.util.List;

import imb.pr3.estetica.entity.Ciudad;


public interface ICiudadService {
	List<Ciudad> buscarTodos();
	Ciudad buscarPorId(Integer id);
	void guardar(Ciudad ciudad);
	void eliminar(Integer id);

}
