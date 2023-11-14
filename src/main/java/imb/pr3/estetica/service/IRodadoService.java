 package imb.pr3.estetica.service;

import java.util.List;

import imb.pr3.estetica.entity.Rodado;

public interface IRodadoService {
	
	List<Rodado> buscarTodos();
	Rodado buscarPorId(Integer id);
	Rodado guardar(Rodado rodado);
	void eliminar(Integer id);
	public boolean existe(Integer id);

}
