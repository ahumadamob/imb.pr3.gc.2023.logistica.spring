package imb.pr3.estetica.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.pr3.estetica.entity.Marca;
import imb.pr3.estetica.repository.MarcaRepository;
import imb.pr3.estetica.service.IMarcaService;

@Service
public class MarcaService implements IMarcaService {
	
	@Autowired
    private MarcaRepository marcaRepository;

    @Override
    public List<Marca> buscarTodos() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca buscarPorId(Integer id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public void crear(Marca marca) {
        marcaRepository.save(marca);
    }

    @Override
    public void eliminar(Integer id) {
        marcaRepository.deleteById(id);
    }

}
