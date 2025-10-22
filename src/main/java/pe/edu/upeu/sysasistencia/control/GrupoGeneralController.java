package pe.edu.upeu.sysasistencia.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysasistencia.dtos.GrupoGeneralDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import pe.edu.upeu.sysasistencia.mappers.GrupoGeneralMapper;
import pe.edu.upeu.sysasistencia.servicio.IGrupoGeneralService;
import pe.edu.upeu.sysasistencia.utils.CustomResponse;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos-generales")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class GrupoGeneralController {

    private final IGrupoGeneralService service;
    private final GrupoGeneralMapper mapper;

    @GetMapping
    public ResponseEntity<List<GrupoGeneralDTO>> findAll() {
        List<GrupoGeneralDTO> grupos = service.findAll();
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoGeneralDTO> findById(@PathVariable Long id) {
        GrupoGeneralDTO grupo = service.findById(id);
        return ResponseEntity.ok(grupo);
    }

    @PostMapping
    public ResponseEntity<GrupoGeneralDTO> save(@Valid @RequestBody GrupoGeneralDTO dto) {
        GrupoGeneralDTO grupo = service.save(dto);
        return ResponseEntity.ok(grupo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoGeneralDTO> update(@PathVariable Long id, @Valid @RequestBody GrupoGeneralDTO dto) {
        GrupoGeneralDTO grupo = service.update(id, dto);
        return ResponseEntity.ok(grupo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new CustomResponse(200, "Grupo general eliminado exitosamente"));
    }

    @GetMapping("/evento-general/{eventoGeneralId}")
    public ResponseEntity<List<GrupoGeneralDTO>> findByEventoGeneral(@PathVariable Long eventoGeneralId) {
        List<GrupoGeneralDTO> grupos = service.findByEventoGeneralId(eventoGeneralId);
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/programa/{programaId}")
    public ResponseEntity<List<GrupoGeneralDTO>> findByPrograma(@PathVariable Long programaId) {
        List<GrupoGeneralDTO> grupos = service.findByProgramaId(programaId);
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<GrupoGeneralDTO>> findByEstado(@PathVariable EstadoGrupo estado) {
        List<GrupoGeneralDTO> grupos = service.findByEstado(estado);
        return ResponseEntity.ok(grupos);
    }
}