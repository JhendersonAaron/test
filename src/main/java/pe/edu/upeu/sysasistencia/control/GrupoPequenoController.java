package pe.edu.upeu.sysasistencia.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysasistencia.dtos.GrupoPequenoDTO;
import pe.edu.upeu.sysasistencia.dtos.ParticipanteGrupoDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import pe.edu.upeu.sysasistencia.mappers.GrupoPequenoMapper;
import pe.edu.upeu.sysasistencia.servicio.IGrupoPequenoService;
import pe.edu.upeu.sysasistencia.utils.CustomResponse;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos-pequenos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class GrupoPequenoController {

    private final IGrupoPequenoService service;
    private final GrupoPequenoMapper mapper;

    @GetMapping
    public ResponseEntity<List<GrupoPequenoDTO>> findAll() {
        List<GrupoPequenoDTO> grupos = service.findAll();
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoPequenoDTO> findById(@PathVariable Long id) {
        GrupoPequenoDTO grupo = service.findById(id);
        return ResponseEntity.ok(grupo);
    }

    @PostMapping
    public ResponseEntity<GrupoPequenoDTO> save(@Valid @RequestBody GrupoPequenoDTO dto) {
        GrupoPequenoDTO grupo = service.save(dto);
        return ResponseEntity.ok(grupo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoPequenoDTO> update(@PathVariable Long id, @Valid @RequestBody GrupoPequenoDTO dto) {
        GrupoPequenoDTO grupo = service.update(id, dto);
        return ResponseEntity.ok(grupo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new CustomResponse(200, "Grupo pequeño eliminado exitosamente"));
    }

    @GetMapping("/grupo-general/{grupoGeneralId}")
    public ResponseEntity<List<GrupoPequenoDTO>> findByGrupoGeneral(@PathVariable Long grupoGeneralId) {
        List<GrupoPequenoDTO> grupos = service.findByGrupoGeneralId(grupoGeneralId);
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/lider/{liderId}")
    public ResponseEntity<List<GrupoPequenoDTO>> findByLider(@PathVariable Long liderId) {
        List<GrupoPequenoDTO> grupos = service.findByLiderId(liderId);
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<GrupoPequenoDTO>> findByEstado(@PathVariable EstadoGrupo estado) {
        List<GrupoPequenoDTO> grupos = service.findByEstado(estado);
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/{grupoPequenoId}/participantes")
    public ResponseEntity<List<ParticipanteGrupoDTO>> getParticipantes(@PathVariable Long grupoPequenoId) {
        List<ParticipanteGrupoDTO> participantes = service.getParticipantes(grupoPequenoId);
        return ResponseEntity.ok(participantes);
    }

    @PostMapping("/{grupoPequenoId}/participantes/{personaId}")
    public ResponseEntity<CustomResponse> addParticipante(@PathVariable Long grupoPequenoId, @PathVariable Long personaId) {
        service.addParticipante(grupoPequenoId, personaId);
        return ResponseEntity.ok(new CustomResponse(200, "Participante agregado exitosamente"));
    }

    @DeleteMapping("/{grupoPequenoId}/participantes/{personaId}")
    public ResponseEntity<CustomResponse> removeParticipante(@PathVariable Long grupoPequenoId, @PathVariable Long personaId) {
        service.removeParticipante(grupoPequenoId, personaId);
        return ResponseEntity.ok(new CustomResponse(200, "Participante removido exitosamente"));
    }
}