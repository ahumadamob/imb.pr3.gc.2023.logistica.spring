package imb.pr3.estetica.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.pr3.estetica.entity.Rodado;
import imb.pr3.estetica.repository.RodadoRepository;
import imb.pr3.estetica.service.IRodadoService;

@Service
public class RodadoService implements IRodadoService{
	
	@Autowired
	RodadoRepository repository;
	
	@Override
	public List<Rodado> buscarTodos() {
		return repository.findAll();
	}

	@Override
	public Rodado buscarPorId(Integer id) {
		Optional<Rodado> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	@Override
	public Rodado guardar(Rodado rodado) {
		return repository.save(rodado);

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
