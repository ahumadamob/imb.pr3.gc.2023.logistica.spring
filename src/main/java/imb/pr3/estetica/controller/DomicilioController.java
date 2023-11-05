package imb.pr3.estetica.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imb.pr3.estetica.service.IDomicilioService;
import imb.pr3.estetica.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import imb.pr3.estetica.entity.Domicilio;

@RestController
@RequestMapping("/api/v1")

public class DomicilioController {

	@Autowired
	IDomicilioService service;
	
	
	
	@GetMapping("/domicilio")
	public ResponseEntity<APIResponse<List<Domicilio>>>obtenerTodosLosDomicilios(){
	    List<Domicilio> domicilio = service.buscarTodosLosDomicilios();
	    return domicilio.isEmpty() ? ResponseUtil.notFound("No hay Domicilio") // ? IF ternario
	            :ResponseUtil.success(domicilio);
	}
	
	



	@GetMapping("/domicilio/{id}")
	public ResponseEntity<APIResponse<Domicilio>> obtenerDomicilioPorId(@PathVariable("id") Integer id) {
		Domicilio domicilio = service.buscarPorId(id);
		return domicilio == null ? ResponseUtil.notFound("No se encontró el domicilio con el id indicado")
				: ResponseUtil.success(domicilio);
	}
	


	@PostMapping("/domicilio")
	public ResponseEntity<APIResponse<Domicilio>> crearDomicilio (@RequestBody Domicilio domicilio) {
		 return service.existe(domicilio.getId()) ? ResponseUtil.badRequest("Ya existe el domicilio con el identificador indicado")
	        		: ResponseUtil.created(service.guardar(domicilio));

	}
	

	@PutMapping("/domicilio")
	public ResponseEntity<APIResponse<Domicilio>> modificarDomicilio (@RequestBody Domicilio domicilio) {
		return service.existe(domicilio.getId()) ? ResponseUtil.success(service.guardar(domicilio))  
				: ResponseUtil.badRequest("No existe el domicilo con el identificador indicado");

	}
	
	@DeleteMapping("/domicilio/{id}")
	public ResponseEntity<APIResponse<Domicilio>> eliminarDomicilio (@PathVariable("id") Integer id) {
	    if (service.existe(id)) {
	        service.eliminar(id);
	        return ResponseUtil.successDeleted("Domicilio con ID " + id + " ha sido eliminada correctamente");
	    }else {
	        return ResponseUtil.badRequest("No existe el domicilio con el id identificado");
	    }
	}
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Domicilio>> handleException(Exception ex) {    	
    	return ResponseUtil.badRequest(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Domicilio>> handleConstraintViolationException(ConstraintViolationException ex) {
    	return ResponseUtil.handleConstraintException(ex);
    } 
	

}
