package pe.edu.upeu.sysasistencia.servicio.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysasistencia.dtos.EventoEspecificoDTO;
import pe.edu.upeu.sysasistencia.modelo.EventoEspecifico;
import pe.edu.upeu.sysasistencia.modelo.EstadoEventoEspecifico;
import pe.edu.upeu.sysasistencia.mappers.EventoEspecificoMapper;
import pe.edu.upeu.sysasistencia.repositorio.IEventoEspecificoRepository;
import pe.edu.upeu.sysasistencia.servicio.IEventoEspecificoService;
import pe.edu.upeu.sysasistencia.excepciones.ModelNotFoundException;
import pe.edu.upeu.sysasistencia.excepciones.CustomResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventoEspecificoServiceImp implements IEventoEspecificoService {

    private final IEventoEspecificoRepository repo;
    private final EventoEspecificoMapper mapper;

    @Override
    public EventoEspecificoDTO save(EventoEspecificoDTO dto) {
        EventoEspecifico entity = mapper.toEntity(dto);
        EventoEspecifico savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public EventoEspecificoDTO update(Long id, EventoEspecificoDTO dto) {
        repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        EventoEspecifico entity = mapper.toEntity(dto);
        entity.setIdEventoEspecifico(id);
        EventoEspecifico savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public List<EventoEspecificoDTO> findAll() {
        return mapper.toDTOs(repo.findAll());
    }

    @Override
    public EventoEspecificoDTO findById(Long id) {
        EventoEspecifico entity = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
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
    public List<EventoEspecificoDTO> findByEventoGeneralId(Long eventoGeneralId) {
        return mapper.toDTOs(repo.findByEventoGeneralId(eventoGeneralId));
    }

    @Override
    public List<EventoEspecificoDTO> findByFechaEvento(LocalDate fecha) {
        return mapper.toDTOs(repo.findByFechaEvento(fecha));
    }

    @Override
    public List<EventoEspecificoDTO> findByEstado(EstadoEventoEspecifico estado) {
        return mapper.toDTOs(repo.findByEstado(estado));
    }
}