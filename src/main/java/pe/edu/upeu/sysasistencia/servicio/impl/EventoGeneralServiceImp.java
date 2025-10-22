package pe.edu.upeu.sysasistencia.servicio.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysasistencia.dtos.EventoGeneralDTO;
import pe.edu.upeu.sysasistencia.modelo.EventoGeneral;
import pe.edu.upeu.sysasistencia.modelo.EstadoEvento;
import pe.edu.upeu.sysasistencia.mappers.EventoGeneralMapper;
import pe.edu.upeu.sysasistencia.repositorio.IEventoGeneralRepository;
import pe.edu.upeu.sysasistencia.servicio.IEventoGeneralService;
import pe.edu.upeu.sysasistencia.excepciones.ModelNotFoundException;
import pe.edu.upeu.sysasistencia.excepciones.CustomResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventoGeneralServiceImp implements IEventoGeneralService {

    private final IEventoGeneralRepository repo;
    private final EventoGeneralMapper mapper;

    @Override
    public EventoGeneralDTO save(EventoGeneralDTO dto) {
        EventoGeneral entity = mapper.toEntity(dto);
        EventoGeneral savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public EventoGeneralDTO update(Long id, EventoGeneralDTO dto) {
        repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        EventoGeneral entity = mapper.toEntity(dto);
        entity.setIdEventoGeneral(id);
        EventoGeneral savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public List<EventoGeneralDTO> findAll() {
        return mapper.toDTOs(repo.findAll());
    }

    @Override
    public EventoGeneralDTO findById(Long id) {
        EventoGeneral entity = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
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
    public List<EventoGeneralDTO> findByEstado(EstadoEvento estado) {
        return mapper.toDTOs(repo.findByEstado(estado));
    }

    @Override
    public List<EventoGeneralDTO> findByNombreContaining(String nombre) {
        return mapper.toDTOs(repo.findByNombreContaining(nombre));
    }

    @Override
    public List<EventoGeneralDTO> findByFechaBetween(LocalDate fecha) {
        return mapper.toDTOs(repo.findByFechaBetween(fecha));
    }
}
