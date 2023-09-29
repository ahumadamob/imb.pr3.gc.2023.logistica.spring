package imb.pr3.estetica.service.jpa;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.pr3.estetica.entity.Ciudad;
import imb.pr3.estetica.entity.Domicilio;
import imb.pr3.estetica.entity.Provincia;
import imb.pr3.estetica.repository.CiudadRepository;
import imb.pr3.estetica.repository.DomicilioRepository;
import imb.pr3.estetica.service.ICiudadService;
import imb.pr3.estetica.service.IDomicilioService;

@Service
public class CiudadServiceImpl  implements ICiudadService  {
	@Autowired
    CiudadRepository repository;


	@Override
	public Ciudad buscarPorId(Integer id) {
		Optional <Ciudad>optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

	@Override
	public void guardar(Ciudad ciudad) {
		repository.save(ciudad);

	}

	@Override
	public void eliminar(Integer id) {
		repository.deleteById(id);

	}
 
	@Override
	public List<Ciudad> buscarTodos() {
		return repository.findAll();
	}
	
}
