package pe.edu.upeu.sysasistencia.servicio.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysasistencia.dtos.GrupoGeneralDTO;
import pe.edu.upeu.sysasistencia.modelo.GrupoGeneral;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import pe.edu.upeu.sysasistencia.mappers.GrupoGeneralMapper;
import pe.edu.upeu.sysasistencia.repositorio.IGrupoGeneralRepository;
import pe.edu.upeu.sysasistencia.servicio.IGrupoGeneralService;
import pe.edu.upeu.sysasistencia.excepciones.ModelNotFoundException;
import pe.edu.upeu.sysasistencia.excepciones.CustomResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GrupoGeneralServiceImp implements IGrupoGeneralService {

    private final IGrupoGeneralRepository repo;
    private final GrupoGeneralMapper mapper;

    @Override
    public GrupoGeneralDTO save(GrupoGeneralDTO dto) {
        GrupoGeneral entity = mapper.toEntity(dto);
        GrupoGeneral savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public GrupoGeneralDTO update(Long id, GrupoGeneralDTO dto) {
        repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        GrupoGeneral entity = mapper.toEntity(dto);
        entity.setIdGrupoGeneral(id);
        GrupoGeneral savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public List<GrupoGeneralDTO> findAll() {
        return mapper.toDTOs(repo.findAll());
    }

    @Override
    public GrupoGeneralDTO findById(Long id) {
        GrupoGeneral entity = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
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
    public List<GrupoGeneralDTO> findByEventoGeneralId(Long eventoGeneralId) {
        return mapper.toDTOs(repo.findByEventoGeneralId(eventoGeneralId));
    }

    @Override
    public List<GrupoGeneralDTO> findByProgramaId(Long programaId) {
        return mapper.toDTOs(repo.findByProgramaId(programaId));
    }

    @Override
    public List<GrupoGeneralDTO> findByEstado(EstadoGrupo estado) {
        return mapper.toDTOs(repo.findByEstado(estado));
    }
}