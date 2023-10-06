package imb.pr3.estetica.service;

import java.util.List;

import imb.pr3.estetica.entity.Provincia;


public interface IProvinciaService {
	List<Provincia> buscarTodos();
	Provincia buscarPorId(Integer id);
	void guardar(Provincia provincia);
	void eliminar(Integer id);

}
