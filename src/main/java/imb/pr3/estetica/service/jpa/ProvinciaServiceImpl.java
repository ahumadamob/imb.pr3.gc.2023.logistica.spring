package imb.pr3.estetica.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.pr3.estetica.entity.Provincia;
import imb.pr3.estetica.repository.ProvinciaRepository;
import imb.pr3.estetica.service.IProvinciaService;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {
	
	@Autowired
    ProvinciaRepository repository;
	
	@Override
	public List<Provincia> buscarTodos() {
		return repository.findAll();
	}

	@Override
	public Provincia buscarPorId(Integer id) {
		Optional <Provincia>optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

	@Override
	public Provincia guardar(Provincia provincia) {
		return repository.save(provincia);

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
