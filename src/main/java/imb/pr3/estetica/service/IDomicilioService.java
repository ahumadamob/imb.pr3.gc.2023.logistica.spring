package imb.pr3.estetica.service;
import java.util.List;
import imb.pr3.estetica.entity.Domicilio;

public interface IDomicilioService {
	List<Domicilio> buscarTodosDomicilios();
	Domicilio buscarPorId(Integer id);
	void guardar(Domicilio domicilio);
	void eliminar(Integer id);
	

}
