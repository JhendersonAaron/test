package pe.edu.upeu.sysasistencia.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysasistencia.dtos.EventoGeneralDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoEvento;
import pe.edu.upeu.sysasistencia.mappers.EventoGeneralMapper;
import pe.edu.upeu.sysasistencia.servicio.IEventoGeneralService;
import pe.edu.upeu.sysasistencia.utils.CustomResponse;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/eventos-generales")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class EventoGeneralController {

    private final IEventoGeneralService service;
    private final EventoGeneralMapper mapper;

    @GetMapping
    public ResponseEntity<List<EventoGeneralDTO>> findAll() {
        List<EventoGeneralDTO> eventos = service.findAll();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoGeneralDTO> findById(@PathVariable Long id) {
        EventoGeneralDTO evento = service.findById(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public ResponseEntity<EventoGeneralDTO> save(@Valid @RequestBody EventoGeneralDTO dto) {
        EventoGeneralDTO evento = service.save(dto);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoGeneralDTO> update(@PathVariable Long id, @Valid @RequestBody EventoGeneralDTO dto) {
        EventoGeneralDTO evento = service.update(id, dto);
        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new CustomResponse(200, "Evento general eliminado exitosamente"));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EventoGeneralDTO>> findByEstado(@PathVariable EstadoEvento estado) {
        List<EventoGeneralDTO> eventos = service.findByEstado(estado);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EventoGeneralDTO>> findByNombreContaining(@RequestParam String nombre) {
        List<EventoGeneralDTO> eventos = service.findByNombreContaining(nombre);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<EventoGeneralDTO>> findByFechaBetween(@PathVariable LocalDate fecha) {
        List<EventoGeneralDTO> eventos = service.findByFechaBetween(fecha);
        return ResponseEntity.ok(eventos);
    }
}