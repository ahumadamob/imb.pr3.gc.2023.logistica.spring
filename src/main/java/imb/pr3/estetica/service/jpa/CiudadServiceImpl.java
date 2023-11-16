package imb.pr3.estetica.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.pr3.estetica.entity.Ciudad;
import imb.pr3.estetica.repository.CiudadRepository;
import imb.pr3.estetica.service.ICiudadService;

@Service
public class CiudadServiceImpl implements ICiudadService {
	
	@Autowired
    CiudadRepository repository;
	
	@Override
	public List<Ciudad> buscarTodos() {
		return repository.findAll();
	}

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
	public Ciudad guardar(Ciudad ciudad) {
		return repository.save(ciudad);

	}

	@Override
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public boolean existe(Integer id) {
    	return (id == null) ? false: repository.existsById(id);
    }
}
