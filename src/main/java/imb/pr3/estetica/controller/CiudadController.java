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

import imb.pr3.estetica.service.ICiudadService;
import imb.pr3.estetica.entity.Ciudad;

@RestController
@RequestMapping("/api/v1")
public class CiudadController {

	@Autowired
	ICiudadService service;
	
	@GetMapping("/Ciudad")
	public ResponseEntity<APIResponse<List<Ciudad>>>obtenerTodasLasCiudads(){
		APIResponse<List<Ciudad>> response = new APIResponse<List<Ciudad>> (200, null, service.buscarTodos());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/Ciudad/{id}")
	public ResponseEntity<APIResponse<Ciudad>> obtenerCiudadPorId(@PathVariable("id") Integer id) {
		Ciudad Ciudad = service.buscarPorId(id);
		if(Ciudad == null){
			List<String> messages = new ArrayList<>();
			messages.add("No se encontró la Ciudad con id = " + id.toString());
			messages.add("Revise el parámetro");
			APIResponse<Ciudad> response = new APIResponse<Ciudad>(HttpStatus.BAD_REQUEST.value(), messages, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else {
			APIResponse<Ciudad> response = new APIResponse<Ciudad>(HttpStatus.OK.value(),null,Ciudad);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	@PostMapping("/Ciudad")
	public ResponseEntity<APIResponse<Ciudad>> crearCiudad(@RequestBody Ciudad Ciudad) {
		if(Ciudad.getId() != null) {
			Ciudad buscaCiudad = service.buscarPorId(Ciudad.getId());
			if (buscaCiudad != null) {
				List<String> messages = new ArrayList<>();
				messages.add("Ya existe una Ciudad con el ID = " + Ciudad.getId().toString());
				messages.add("Para actualizar utilice el verbo PUT");
				APIResponse<Ciudad> response = new APIResponse<Ciudad>(HttpStatus.BAD_REQUEST.value(), messages, null);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
		service.guardar(Ciudad);
		APIResponse<Ciudad> response = new APIResponse<Ciudad>(HttpStatus.CREATED.value(),null,Ciudad);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@PutMapping("/Ciudad")
	public ResponseEntity<APIResponse<Ciudad>> modificarCiudad(@RequestBody Ciudad Ciudad) {
		boolean isError;
		String idStr;
		if(Ciudad.getId() != null) {
			Ciudad buscaCiudad = service.buscarPorId(Ciudad.getId());
			idStr = Ciudad.getId().toString();
			if (buscaCiudad != null) {
				//Devolver un OK
				isError = false;
			}else {
				//Devolver un Error
				isError = true;
			}
			
		}else {
			idStr = "<No definido>";
			//Devolver un error
			isError = true;
		}
		
		if(isError) {
			//Devolvemos el error
			List<String> messages = new ArrayList<>();
			messages.add("No existe una Ciudad con el ID = " + idStr);
			messages.add("Para crear una nueva utilice el verbo POST");
			APIResponse<Ciudad> response = new APIResponse<Ciudad>(HttpStatus.BAD_REQUEST.value(), messages, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else {
			//Devolvemos el OK
			service.guardar(Ciudad);
			APIResponse<Ciudad> response = new APIResponse<Ciudad>(HttpStatus.OK.value(),null,Ciudad);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
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
}
