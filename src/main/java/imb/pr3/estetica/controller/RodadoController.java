package imb.pr3.estetica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imb.pr3.estetica.entity.Rodado;
import imb.pr3.estetica.service.IRodadoService;
import imb.pr3.estetica.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/v1")
public class RodadoController {

    @Autowired
    IRodadoService service;

    @GetMapping("/rodado")
    public ResponseEntity<APIResponse<List<Rodado>>> buscarTodosRodados() {
        List<Rodado> rodados = service.buscarTodos();
        return rodados.isEmpty() ? ResponseUtil.notFound("No hay rodados")
	            : ResponseUtil.success(rodados);
    }

    @GetMapping("/rodado/{id}")
    public ResponseEntity<APIResponse<Rodado>> buscarRodadoPorId(@PathVariable("id") Integer id) {
    	return service.existe(id) ?
				ResponseUtil.success(service.buscarPorId(id)) :
				ResponseUtil.notFound("No se encontró rodado con el id proporcionado");
    }


    @PostMapping("/rodado")
    public ResponseEntity<APIResponse<Rodado>> crearRodado(@RequestBody Rodado rodado, BindingResult result) {
    	return service.existe(rodado.getId()) ? ResponseUtil.badRequest("Ya existe un rodado con el identificador proporcionado")
        		: ResponseUtil.created(service.guardar(rodado));
    }

    @PutMapping("/rodado")
    public ResponseEntity<APIResponse<Rodado>> actualizarRodado(@RequestBody Rodado rodado) {
    	return service.existe(rodado.getId()) ? ResponseUtil.success(service.guardar(rodado))  
				: ResponseUtil.badRequest("No existe rodado con el identificador proporcionado");
    }
    
    // Maneja la solicitud para eliminar un rodado por su ID.    
    @DeleteMapping("/rodado/{id}")
    public ResponseEntity<APIResponse<Rodado>> eliminarRodado(@PathVariable("id") Integer id) {
        // Verifica si el rodado con el ID proporcionado existe
        if (service.existe(id)) {
            // Si existe, procede a eliminarlo
            service.eliminar(id);
            // Retorna una respuesta exitosa indicando que el rodado se ha eliminado correctamente
            return ResponseUtil.success("Rodado con ID " + id + " ha sido eliminado correctamente");
        } else {
            // Si no existe el rodado con el ID proporcionado, retorna una respuesta de error
            return ResponseUtil.badRequest("No existe rodado con el identificador proporcionado");
        }
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Rodado>> handleException(Exception ex) {    	
    	return ResponseUtil.badRequest(ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Rodado>> handleConstraintViolationException(ConstraintViolationException ex) {
    	return ResponseUtil.handleConstraintException(ex);
    }    
    
}
