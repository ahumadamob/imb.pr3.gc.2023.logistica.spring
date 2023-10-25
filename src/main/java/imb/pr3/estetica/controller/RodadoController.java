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
    public ResponseEntity<APIResponse<List<Rodado>>> buscarTodosRodados() {
        List<Rodado> rodados = service.buscarTodos();
        if (rodados.isEmpty()) {
            List<String> messages = new ArrayList<>();
            messages.add("No se encontraron rodados.");
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, messages);
        } else {
            return ResponseUtil.createSuccessResponse(HttpStatus.OK, rodados);
        }
    }

    @GetMapping("/rodado/{id}")
    public ResponseEntity<APIResponse<Rodado>> buscarRodadoPorId(@PathVariable("id") Integer id) {
        Rodado rodado = service.buscarPorId(id);

        if (rodado == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No se encontró el Rodado con el número de id = " + id.toString());
            messages.add("Revise el parámetro");
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, messages);
        } else {
            return ResponseUtil.createSuccessResponse(HttpStatus.OK, rodado);
        }
    }


    @PostMapping("/rodado")
    public ResponseEntity<APIResponse<Rodado>> crearRodado(@RequestBody Rodado rodado) {
        if (rodado.getPatente() == null || rodado.getPatente().isEmpty()) {
            List<String> messages = new ArrayList<>();
            messages.add("La patente es obligatoria y no se proporcionó.");
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, messages);
        }

        if (rodado.getId() != null) {
            Rodado buscaRodado = service.buscarPorId(rodado.getId());
            if (buscaRodado != null) {
                List<String> messages = new ArrayList<>();
                messages.add("Ya existe un rodado con el id = " + rodado.getId().toString());
                messages.add("Para actualizar utilice el verbo PUT");
                return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, messages);
            }
        }

        service.crear(rodado);
        return ResponseUtil.createSuccessResponse(HttpStatus.CREATED, rodado);
    }

    
    @PutMapping("/rodado/{id}")
    public ResponseEntity<APIResponse<Rodado>> actualizarRodado(@PathVariable Integer id,@RequestBody Rodado rodado){
    	boolean isError;
        String idStr;
        if (id != null) {
            Rodado buscaRodado = service.buscarPorId(id);
            idStr = id.toString();
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
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, messages);
        } else {
            // Devolver el OK
            service.crear(rodado);
            return ResponseUtil.createSuccessResponse(HttpStatus.OK, rodado);
        }
    }
    

    /*@PutMapping("/rodado")
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
    }*/

    @DeleteMapping("/rodado/{id}")
    public ResponseEntity<APIResponse<Rodado>> eliminarRodado(@PathVariable("id") Integer id) {
        // Búsqueda del Rodado por ID
        Rodado buscaRodado = service.buscarPorId(id);
        
        // Comprobación de existencia del Rodado
        if (buscaRodado == null) {
            // Si no se encuentra el Rodado, se genera una respuesta de error
            
            //Crear una lista de mensajes de error
            List<String> messages = new ArrayList<>();
            messages.add("No existe un rodado para eliminar con el id = " + id.toString());
            
            // Crear una respuesta de error
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, messages);
        } else {
            // El Rodado existe, proceder con la eliminación
            
            // Llamar al servicio para eliminar el Rodado por su ID
            service.eliminar(id);
            
            // Crear una lista de mensajes de éxito
            List<String> messages = new ArrayList<>();
            messages.add("El rodado que figura en el cuerpo ha sido eliminado");
            
            // Crear una respuesta de éxito con el código de estado HTTP 200 (OK)
            return ResponseUtil.createSuccessResponse(HttpStatus.OK, messages, buscaRodado);
        }
    }
    
    @GetMapping("/rodado/existe/{id}")
    public ResponseEntity<APIResponse<String>> existe(@PathVariable Integer id) {
        boolean existe = service.existePorId(id);
        String mensaje;

        if (existe) {
            mensaje = "El rodado con el ID " + id + " existe.";
        } else {
            mensaje = "El rodado con el ID " + id + " no existe.";
        }

        List<String> messages = new ArrayList<>();
        messages.add(mensaje);

        return ResponseUtil.createSuccessResponse(HttpStatus.OK, messages, mensaje);
    }

    
}
