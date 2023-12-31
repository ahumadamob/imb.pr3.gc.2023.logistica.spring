package imb.pr3.estetica.controller;


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
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Iterator;


import imb.pr3.estetica.service.IProvinciaService;
import imb.pr3.estetica.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import imb.pr3.estetica.entity.Provincia;

@RestController
@RequestMapping("/api/v1")
public class ProvinciaController {

	@Autowired
	IProvinciaService service;

	


	
	@GetMapping("/provincia")
	public ResponseEntity<APIResponse<List<Provincia>>> obtenerTodasLasProvincias() {
	    List<Provincia> provincia = service.buscarTodos();
	    return provincia.isEmpty() ? ResponseUtil.notFound("No hay provincias")
	            : ResponseUtil.success(provincia);
	}
	


	
	@GetMapping("/provincia/{id}")
	public ResponseEntity<APIResponse<Provincia>> obtenerProvinciaPorId(@PathVariable("id") Integer id){
		return service.existe(id) ?
				ResponseUtil.success(service.buscarPorId(id)) :
				ResponseUtil.notFound("No se encontró la provincia con el id proporcionado");
	}
	

	
	@PostMapping("/provincia")
    public ResponseEntity<APIResponse<Provincia>> crearProvincia(@RequestBody Provincia provincia, BindingResult result) {
        return service.existe(provincia.getId()) ? ResponseUtil.badRequest("Ya existe una provincia con el identificador proporcionado")
        		: ResponseUtil.created(service.guardar(provincia));
  	
    }	
	
	

	
	/**
	 * Controlador para manejar las solicitudes PUT relacionadas con la entidad Provincia.
	 */
	/*Es un método para modificar una provincia que ya existe*/
	
	@PutMapping("/provincia") // Indica que este método manejará las solicitudes HTTP PUT dirigidas a la ruta "/api/v1/provincia".
	public ResponseEntity<APIResponse<Provincia>> modificarProvincia(@RequestBody Provincia provincia) {   // Verifica si la provincia con el ID proporcionado existe en la base de datos.
		return service.existe(provincia.getId()) ? ResponseUtil.success(service.guardar(provincia))  
				: ResponseUtil.badRequest("No existe la provincia con el identificador proporcionado");
	}
	
	/* *La expresion ? es un operador ternario que devuelve diferentes respuestas según si la provincia con el ID proporcionado existe o no.
       *ResponseUtil.success(service.guardar(provincia)): Si la provincia existe, se guarda la provincia modificada en la base de datos utilizando el servicio (service.guardar(provincia)) y se devuelve una respuesta exitosa (HTTP 200) con la provincia modificada.
       *ResponseUtil.badRequest("No existe la provincia con el identificador proporcionado"): Si la provincia con el ID proporcionado no existe, se devuelve una respuesta de error (HTTP 400) indicando que no se encontró la provincia.
	*/

	
	
	

	 @DeleteMapping("/provincia/{id}")
	 public ResponseEntity<APIResponse<Provincia>> eliminarProvincia(@PathVariable("id") Integer id) {
		    if (service.existe(id)) {
		        service.eliminar(id); // Intenta eliminar la provincia
		        return ResponseUtil.success("Provincia con ID " + id + " ha sido eliminada correctamente");
		    } else {
		        return ResponseUtil.badRequest("No existe la provincia con el identificador proporcionado");
		    }
		}
	
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Provincia>> handleException(Exception ex) {    	
    	return ResponseUtil.badRequest(ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Provincia>> handleConstraintViolationException(ConstraintViolationException ex) {
    	return ResponseUtil.handleConstraintException(ex);
    }    
	
	
    private List<Provincia> provincias = new ArrayList<>();
    
    @PutMapping("/provincia/habilitar/{id}")
    public ResponseEntity<String> habilitarProvincia(@PathVariable Integer id) {
        Provincia provincia = service.buscarPorId(id);

        if (provincia != null) {
            provincia.setHabilitado(true);
            service.guardar(provincia);  // Asegúrate de tener un método en tu servicio para guardar la provincia
            return ResponseEntity.ok("Provincia habilitada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provincia no encontrada");
        }
    }
    
    @PutMapping("/provincia/deshabilitar/{id}")
    public ResponseEntity<String> deshabilitarProvincia(@PathVariable Integer id) {
        Provincia provincia = service.buscarPorId(id);

        if (provincia != null) {
            provincia.setHabilitado(false);
            service.guardar(provincia);  // Asegúrate de tener un método en tu servicio para guardar la provincia
            return ResponseEntity.ok("Provincia deshabilitada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provincia no encontrada");
        }
    }
    
    @GetMapping("/provincia/habilitadas")
    public ResponseEntity<APIResponse<List<Provincia>>> obtenerProvinciasHabilitadas() {
        List<Provincia> provinciasHabilitadas = service.buscarTodos().stream()
                .filter(Provincia::isHabilitado)
                .collect(Collectors.toList());

        return provinciasHabilitadas.isEmpty() ?
               ResponseUtil.notFound("No hay provincias habilitadas") :
               ResponseUtil.success(provinciasHabilitadas);
    }
    
    @GetMapping("/provincia/deshabilitadas")
    public ResponseEntity<APIResponse<List<Provincia>>> obtenerProvinciasDeshabilitadas() {
        List<Provincia> provinciasDeshabilitadas = service.buscarTodos().stream()
                .filter(provincia -> !provincia.isHabilitado())
                .collect(Collectors.toList());

        return provinciasDeshabilitadas.isEmpty() ?
               ResponseUtil.notFound("No hay provincias deshabilitadas") :
               ResponseUtil.success(provinciasDeshabilitadas);
    }
    
    @DeleteMapping("/provincia/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        Provincia provincia = service.buscarPorId(id);

        if (provincia != null && !provincia.isHabilitado()) {
            service.eliminar(id);
            return ResponseEntity.ok("Provincia deshabilitada eliminada correctamente");
        } else if (provincia == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la provincia con el id proporcionado");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La provincia está habilitada y no se puede eliminar");
        }
    }
}
