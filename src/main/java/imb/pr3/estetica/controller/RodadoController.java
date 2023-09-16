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

import imb.pr3.estetica.entity.Rodado;
import imb.pr3.estetica.service.IRodadoService;

@RestController
@RequestMapping("/api/v1")
public class RodadoController {

    @Autowired
    IRodadoService service;

    @GetMapping("/rodado")
    public ResponseEntity<APIResponse<List<Rodado>>> buscarTodos() {
        APIResponse<List<Rodado>> response = new APIResponse<>(200, null, service.buscarTodos());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/rodado/{id}")
    public ResponseEntity<APIResponse<Rodado>> buscarPorId(@PathVariable("id") Integer id) {
        Rodado rodado = service.buscarPorId(id);
        if (rodado == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No se encontró el Rodado con el número de id = " + id.toString());
            messages.add("Revise el parámetro");
            APIResponse<Rodado> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            APIResponse<Rodado> response = new APIResponse<>(HttpStatus.OK.value(), null, rodado);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/rodado")
    public ResponseEntity<APIResponse<Rodado>> crearRodado(@RequestBody Rodado rodado) {
        if (rodado.getId() != null) {
            Rodado buscaRodado = service.buscarPorId(rodado.getId());
            if (buscaRodado != null) {
                List<String> messages = new ArrayList<>();
                messages.add("Ya existe un rodado con el id = " + rodado.getId().toString());
                messages.add("Para actualizar utilice el verbo PUT");
                APIResponse<Rodado> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        service.crear(rodado);
        APIResponse<Rodado> response = new APIResponse<>(HttpStatus.CREATED.value(), null, rodado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/rodado")
    public ResponseEntity<APIResponse<Rodado>> actualizarRodado(@RequestBody Rodado rodado) {
        boolean isError;
        String idStr;
        if (rodado.getId() != null) {
            Rodado buscaRodado = service.buscarPorId(rodado.getId());
            idStr = rodado.getId().toString();
            if (buscaRodado != null) {
                // Devolver un OK
                isError = false;
            } else {
                // Devolver un Error
                isError = true;
            }
        } else {
            idStr = "<No definido>";
            // Devolver un error
            isError = true;
        }

        if (isError) {
            // Devolver el error
            List<String> messages = new ArrayList<>();
            messages.add("No existe un rodado para actualizar con el id = " + idStr);
            messages.add("Para crear un nuevo rodado utilice el verbo POST");
            APIResponse<Rodado> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            // Devolver el OK
            service.crear(rodado);
            APIResponse<Rodado> response = new APIResponse<>(HttpStatus.OK.value(), null, rodado);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/rodado/{id}")
    public ResponseEntity<APIResponse<Rodado>> eliminar(@PathVariable("id") Integer id) {
        Rodado buscaRodado = service.buscarPorId(id);
        if (buscaRodado == null) {
            // Error
            List<String> messages = new ArrayList<>();
            messages.add("No existe un rodado para eliminar con el id = " + id.toString());
            APIResponse<Rodado> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            service.eliminar(id);
            List<String> messages = new ArrayList<>();
            messages.add("El rodado que figura en el cuerpo ha sido eliminado");
            APIResponse<Rodado> response = new APIResponse<>(HttpStatus.OK.value(), messages, buscaRodado);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}