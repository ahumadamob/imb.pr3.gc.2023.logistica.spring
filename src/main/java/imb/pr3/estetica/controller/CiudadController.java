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

import imb.pr3.estetica.service.ICiudadService;
import imb.pr3.estetica.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import imb.pr3.estetica.entity.Ciudad;

@RestController
@RequestMapping("/api/v1")
public class CiudadController {

	@Autowired
	ICiudadService service;

	


	
	@GetMapping("/Ciudad")
	public ResponseEntity<APIResponse<List<Ciudad>>> obtenerTodasLasCiudads() {
	    List<Ciudad> Ciudad = service.buscarTodos();
	    return Ciudad.isEmpty() ? ResponseUtil.notFound("No hay Ciudades")
	            : ResponseUtil.success(Ciudad);
	}
	


	
	@GetMapping("/Ciudad/{id}")
	public ResponseEntity<APIResponse<Ciudad>> obtenerCiudadPorId(@PathVariable("id") Integer id){
		return service.existe(id) ?
				ResponseUtil.success(service.buscarPorId(id)) :
				ResponseUtil.notFound("No se encontró la Ciudad con el id proporcionado");
	}
	

	
	@PostMapping("/Ciudad")
    public ResponseEntity<APIResponse<Ciudad>> crearCiudad(@RequestBody Ciudad Ciudad, BindingResult result) {
        return service.existe(Ciudad.getId()) ? ResponseUtil.badRequest("Ya existe una Ciudad con el identificador proporcionado")
        		: ResponseUtil.created(service.guardar(Ciudad));
  	
    }	
	
	
	
	@PutMapping("/Ciudad") 
	public ResponseEntity<APIResponse<Ciudad>> modificarCiudad(@RequestBody Ciudad Ciudad) { 
		return service.existe(Ciudad.getId()) ? ResponseUtil.success(service.guardar(Ciudad))  
				: ResponseUtil.badRequest("No existe la Ciudad con el identificador proporcionado");
	}
	
	
	 @DeleteMapping("/Ciudad/{id}")
	 public ResponseEntity<APIResponse<Ciudad>> eliminarCiudad(@PathVariable("id") Integer id) {
		    if (service.existe(id)) {
		        service.eliminar(id); // Intenta eliminar la Ciudad
		        return ResponseUtil.success("Ciudad con ID " + id + " ha sido eliminada correctamente");
		    } else {
		        return ResponseUtil.badRequest("No existe la Ciudad con el identificador proporcionado");
		    }
		}
	
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Ciudad>> handleException(Exception ex) {    	
    	return ResponseUtil.badRequest(ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Ciudad>> handleConstraintViolationException(ConstraintViolationException ex) {
    	return ResponseUtil.handleConstraintException(ex);
    }    
	
	
}
