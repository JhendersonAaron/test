package pe.edu.upeu.sysasistencia.servicio.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysasistencia.dtos.AsistenciaDTO;
import pe.edu.upeu.sysasistencia.modelo.Asistencia;
import pe.edu.upeu.sysasistencia.modelo.EstadoAsistencia;
import pe.edu.upeu.sysasistencia.mappers.AsistenciaMapper;
import pe.edu.upeu.sysasistencia.repositorio.IAsistenciaRepository;
import pe.edu.upeu.sysasistencia.servicio.IAsistenciaService;
import pe.edu.upeu.sysasistencia.excepciones.ModelNotFoundException;
import pe.edu.upeu.sysasistencia.excepciones.CustomResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AsistenciaServiceImp implements IAsistenciaService {

    private final IAsistenciaRepository repo;
    private final AsistenciaMapper mapper;

    @Override
    public AsistenciaDTO save(AsistenciaDTO dto) {
        Asistencia entity = mapper.toEntity(dto);
        Asistencia savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public AsistenciaDTO update(Long id, AsistenciaDTO dto) {
        repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        Asistencia entity = mapper.toEntity(dto);
        entity.setIdAsistencia(id);
        Asistencia savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public List<AsistenciaDTO> findAll() {
        return mapper.toDTOs(repo.findAll());
    }

    @Override
    public AsistenciaDTO findById(Long id) {
        Asistencia entity = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public CustomResponse delete(Long id) {
        repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        repo.deleteById(id);
        CustomResponse cer = new CustomResponse();
        cer.setStatusCode(200);
        cer.setDatetime(LocalDateTime.now());
        cer.setMessage("true");
        cer.setDetails("Eliminado correctamente");
        return cer;
    }

    @Override
    public List<AsistenciaDTO> findByEventoEspecificoId(Long eventoEspecificoId) {
        return mapper.toDTOs(repo.findByEventoEspecificoId(eventoEspecificoId));
    }

    @Override
    public List<AsistenciaDTO> findByPersonaId(Long personaId) {
        return mapper.toDTOs(repo.findByPersonaId(personaId));
    }

    @Override
    public List<AsistenciaDTO> findByEstadoAsistencia(EstadoAsistencia estado) {
        return mapper.toDTOs(repo.findByEstadoAsistencia(estado));
    }

    @Override
    public void registrarAsistencia(Long eventoEspecificoId, List<AsistenciaDTO> asistencias) {
        for (AsistenciaDTO asistenciaDTO : asistencias) {
            asistenciaDTO.setEventoEspecificoId(eventoEspecificoId);
            repo.save(mapper.toEntity(asistenciaDTO));
        }
    }

    @Override
    public Long countByEventoEspecificoAndEstado(Long eventoEspecificoId, EstadoAsistencia estado) {
        return repo.countByEventoEspecificoAndEstado(eventoEspecificoId, estado);
    }
}