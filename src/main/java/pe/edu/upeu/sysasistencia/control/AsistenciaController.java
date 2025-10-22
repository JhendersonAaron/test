package pe.edu.upeu.sysasistencia.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysasistencia.dtos.AsistenciaDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoAsistencia;
import pe.edu.upeu.sysasistencia.mappers.AsistenciaMapper;
import pe.edu.upeu.sysasistencia.servicio.IAsistenciaService;
import pe.edu.upeu.sysasistencia.utils.CustomResponse;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/asistencia")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class AsistenciaController {

    private final IAsistenciaService service;
    private final AsistenciaMapper mapper;

    @GetMapping
    public ResponseEntity<List<AsistenciaDTO>> findAll() {
        List<AsistenciaDTO> asistencias = service.findAll();
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaDTO> findById(@PathVariable Long id) {
        AsistenciaDTO asistencia = service.findById(id);
        return ResponseEntity.ok(asistencia);
    }

    @PostMapping
    public ResponseEntity<AsistenciaDTO> save(@Valid @RequestBody AsistenciaDTO dto) {
        AsistenciaDTO asistencia = service.save(dto);
        return ResponseEntity.ok(asistencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsistenciaDTO> update(@PathVariable Long id, @Valid @RequestBody AsistenciaDTO dto) {
        AsistenciaDTO asistencia = service.update(id, dto);
        return ResponseEntity.ok(asistencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new CustomResponse(200, "Asistencia eliminada exitosamente"));
    }

    @GetMapping("/evento-especifico/{eventoEspecificoId}")
    public ResponseEntity<List<AsistenciaDTO>> findByEventoEspecifico(@PathVariable Long eventoEspecificoId) {
        List<AsistenciaDTO> asistencias = service.findByEventoEspecificoId(eventoEspecificoId);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<List<AsistenciaDTO>> findByPersona(@PathVariable Long personaId) {
        List<AsistenciaDTO> asistencias = service.findByPersonaId(personaId);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AsistenciaDTO>> findByEstadoAsistencia(@PathVariable EstadoAsistencia estado) {
        List<AsistenciaDTO> asistencias = service.findByEstadoAsistencia(estado);
        return ResponseEntity.ok(asistencias);
    }

    @PostMapping("/registrar/{eventoEspecificoId}")
    public ResponseEntity<CustomResponse> registrarAsistencia(@PathVariable Long eventoEspecificoId, @RequestBody List<AsistenciaDTO> asistencias) {
        service.registrarAsistencia(eventoEspecificoId, asistencias);
        return ResponseEntity.ok(new CustomResponse(200, "Asistencias registradas exitosamente"));
    }

    @GetMapping("/reporte/{eventoEspecificoId}")
    public ResponseEntity<byte[]> getReporteAsistencia(@PathVariable Long eventoEspecificoId) {
        // Implementar generación de reporte PDF
        // Por ahora retornar un array de bytes vacío
        return ResponseEntity.ok(new byte[0]);
    }
}
