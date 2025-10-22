package pe.edu.upeu.sysasistencia.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysasistencia.dtos.EventoEspecificoDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoEventoEspecifico;
import pe.edu.upeu.sysasistencia.mappers.EventoEspecificoMapper;
import pe.edu.upeu.sysasistencia.servicio.IEventoEspecificoService;
import pe.edu.upeu.sysasistencia.utils.CustomResponse;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/eventos-especificos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class EventoEspecificoController {

    private final IEventoEspecificoService service;
    private final EventoEspecificoMapper mapper;

    @GetMapping
    public ResponseEntity<List<EventoEspecificoDTO>> findAll() {
        List<EventoEspecificoDTO> eventos = service.findAll();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoEspecificoDTO> findById(@PathVariable Long id) {
        EventoEspecificoDTO evento = service.findById(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public ResponseEntity<EventoEspecificoDTO> save(@Valid @RequestBody EventoEspecificoDTO dto) {
        EventoEspecificoDTO evento = service.save(dto);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoEspecificoDTO> update(@PathVariable Long id, @Valid @RequestBody EventoEspecificoDTO dto) {
        EventoEspecificoDTO evento = service.update(id, dto);
        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new CustomResponse(200, "Evento específico eliminado exitosamente"));
    }

    @GetMapping("/evento-general/{eventoGeneralId}")
    public ResponseEntity<List<EventoEspecificoDTO>> findByEventoGeneral(@PathVariable Long eventoGeneralId) {
        List<EventoEspecificoDTO> eventos = service.findByEventoGeneralId(eventoGeneralId);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<EventoEspecificoDTO>> findByFecha(@PathVariable LocalDate fecha) {
        List<EventoEspecificoDTO> eventos = service.findByFechaEvento(fecha);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EventoEspecificoDTO>> findByEstado(@PathVariable EstadoEventoEspecifico estado) {
        List<EventoEspecificoDTO> eventos = service.findByEstado(estado);
        return ResponseEntity.ok(eventos);
    }
}