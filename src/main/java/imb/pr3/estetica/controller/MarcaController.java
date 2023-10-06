package imb.pr3.estetica.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imb.pr3.estetica.entity.Marca;
import imb.pr3.estetica.service.IMarcaService;

@RestController
@RequestMapping("/api/v1")
public class MarcaController {

    @Autowired
    IMarcaService marcaService;

    @GetMapping("/marca")
    public ResponseEntity<APIResponse<List<Marca>>> buscarTodas() {
        APIResponse<List<Marca>> response = new APIResponse<>(200, null, marcaService.buscarTodos());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<APIResponse<Marca>> buscarPorId(@PathVariable("id") Integer id) {
        Marca marca = marcaService.buscarPorId(id);
        if (marca == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No se encontró la Marca con el número de id = " + id.toString());
            messages.add("Revise el parámetro");
            APIResponse<Marca> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            APIResponse<Marca> response = new APIResponse<>(HttpStatus.OK.value(), null, marca);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/marca")
    public ResponseEntity<APIResponse<Marca>> crearMarca(@RequestBody Marca marca) {
        marcaService.crear(marca);
        APIResponse<Marca> response = new APIResponse<>(HttpStatus.CREATED.value(), null, marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/marca")
    public ResponseEntity<APIResponse<Marca>> actualizarMarca(@RequestBody Marca marca) {
        if (marca.getId() == null) {
            List<String> messages = new ArrayList<>();
            messages.add("El ID de la Marca no está definido");
            APIResponse<Marca> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Marca buscaMarca = marcaService.buscarPorId(marca.getId());
        if (buscaMarca == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No existe una Marca para actualizar con el ID = " + marca.getId().toString());
            APIResponse<Marca> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            marcaService.crear(marca);
            APIResponse<Marca> response = new APIResponse<>(HttpStatus.OK.value(), null, marca);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/marca/{id}")
    public ResponseEntity<APIResponse<Marca>> eliminar(@PathVariable("id") Integer id) {
        Marca buscaMarca = marcaService.buscarPorId(id);
        if (buscaMarca == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No existe una Marca para eliminar con el ID = " + id.toString());
            APIResponse<Marca> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            marcaService.eliminar(id);
            List<String> messages = new ArrayList<>();
            messages.add("La Marca que figura en el cuerpo ha sido eliminada");
            APIResponse<Marca> response = new APIResponse<>(HttpStatus.OK.value(), messages, buscaMarca);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}

