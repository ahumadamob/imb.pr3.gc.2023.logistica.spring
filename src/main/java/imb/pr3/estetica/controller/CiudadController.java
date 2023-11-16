package imb.pr3.estetica.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@GetMapping("/ciudad")
	public ResponseEntity<APIResponse<List<Ciudad>>> obtenerTodasLasCiudades() {
	    List<Ciudad> ciudad = service.buscarTodos();
	    return ciudad.isEmpty() ? ResponseUtil.notFound("No hay ciudades")
	            : ResponseUtil.success(ciudad);
	}
	
	@GetMapping("/ciudad/{id}")
	public ResponseEntity<APIResponse<Ciudad>> obtenerCiudadPorId(@PathVariable("id") Integer id){
		Ciudad ciudad = service.buscarPorId(id);
		return ciudad == null ? ResponseUtil.notFound("No se encontró la ciudad con el id proporcionado")
				: ResponseUtil.success(ciudad);	
	}
	
	/* *el metodo GET se utiliza para recuperar datos del servidor. Cuando un cliente, como un navegador web, 
	realiza una solicitud GET a un servidor, 
	generalmente está pidiendo al servidor que envíe una representación de un recurso específico,
	como una página web o un archivo, de vuelta al cliente. Esta solicitud se realiza generalmente a través de una URL en el navegador.
	*/
	/* *La expresion ? es un operador ternario que devuelve diferentes respuestas según si la ciudad con el ID proporcionado existe o no. */
	
	@PostMapping("/ciudad")
    public ResponseEntity<APIResponse<Ciudad>> crearCiudad(@RequestBody Ciudad ciudad, BindingResult result) {
        return service.existe(ciudad.getId()) ? ResponseUtil.badRequest("Ya existe una ciudad con el identificador proporcionado")
        		: ResponseUtil.created(service.guardar(ciudad));

    }	
	
	@PutMapping("/ciudad") 
	public ResponseEntity<APIResponse<Ciudad>> modificarCiudad(@RequestBody Ciudad ciudad) {  
		return service.existe(ciudad.getId()) ? ResponseUtil.success(service.guardar(ciudad))  
				: ResponseUtil.badRequest("No existe la ciudad con el identificador proporcionado");
	}
	
	@DeleteMapping("/Ciudad/{id}")
	public ResponseEntity<APIResponse<Ciudad>> eliminarCiudad(@PathVariable("id") Integer id) {
		Ciudad buscaCiudad = service.buscarPorId(id);
		if(buscaCiudad == null) {
			//Error
			List<String> messages = new ArrayList<>();
			messages.add("No existe una Ciudad con el ID = " + id.toString());
			APIResponse<Ciudad> response = new APIResponse<Ciudad>(HttpStatus.BAD_REQUEST.value(),messages,null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else {
			service.eliminar(id);
			List<String> messages = new ArrayList<>();
			messages.add("La Ciudad que figura en el cuerpo ha sido eliminada");
			APIResponse<Ciudad> response = new APIResponse<Ciudad>(HttpStatus.OK.value(),messages,buscaCiudad);
			return ResponseEntity.status(HttpStatus.OK).body(response);
			//eliminar
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
