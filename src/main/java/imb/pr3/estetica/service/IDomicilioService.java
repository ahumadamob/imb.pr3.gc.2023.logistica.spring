package imb.pr3.estetica.service;
import java.util.List;
import imb.pr3.estetica.entity.Domicilio;

public interface IDomicilioService {
	List<Domicilio> buscarTodosDomicilios();
	Domicilio buscarPorId(Integer id);
	Domicilio guardar(Domicilio domicilio);
	void eliminar(Integer id);
	public boolean existe(Integer id);
	


}
