package imb.pr3.estetica.service.jpa;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import imb.pr3.estetica.entity.Domicilio;
import imb.pr3.estetica.repository.DomicilioRepository;
import imb.pr3.estetica.service.IDomicilioService;

@Service
public class DomicilioServiceImpl  implements IDomicilioService  {
	@Autowired
    DomicilioRepository repository;

	@Override
	public List<Domicilio> buscarTodosDomicilios() {
		return repository.findAll();
	}

	@Override
	public Domicilio buscarPorId(Integer id) {
		Optional <Domicilio>optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

	@Override
	public void guardar(Domicilio domicilio) {
		repository.save(domicilio);

	}

	@Override
	public void eliminar(Integer id) {
		repository.deleteById(id);

	}
	
	

}
