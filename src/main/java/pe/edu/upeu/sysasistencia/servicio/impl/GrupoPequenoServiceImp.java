package pe.edu.upeu.sysasistencia.servicio.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysasistencia.dtos.GrupoPequenoDTO;
import pe.edu.upeu.sysasistencia.dtos.ParticipanteGrupoDTO;
import pe.edu.upeu.sysasistencia.modelo.EstadoGrupo;
import pe.edu.upeu.sysasistencia.modelo.GrupoPequeno;
import pe.edu.upeu.sysasistencia.modelo.ParticipanteGrupo;
import pe.edu.upeu.sysasistencia.mappers.GrupoPequenoMapper;
import pe.edu.upeu.sysasistencia.mappers.ParticipanteGrupoMapper;
import pe.edu.upeu.sysasistencia.repositorio.IGrupoPequenoRepository;
import pe.edu.upeu.sysasistencia.repositorio.IParticipanteGrupoRepository;
import pe.edu.upeu.sysasistencia.repositorio.IPersonaRepository;
import pe.edu.upeu.sysasistencia.servicio.IGrupoPequenoService;
import pe.edu.upeu.sysasistencia.excepciones.ModelNotFoundException;
import pe.edu.upeu.sysasistencia.excepciones.CustomResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GrupoPequenoServiceImp implements IGrupoPequenoService {

    private final IGrupoPequenoRepository repo;
    private final IParticipanteGrupoRepository participanteRepo;
    private final IPersonaRepository personaRepo;
    private final GrupoPequenoMapper mapper;
    private final ParticipanteGrupoMapper participanteMapper;

    @Override
    public GrupoPequenoDTO save(GrupoPequenoDTO dto) {
        GrupoPequeno entity = mapper.toEntity(dto);
        GrupoPequeno savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public GrupoPequenoDTO update(Long id, GrupoPequenoDTO dto) {
        repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        GrupoPequeno entity = mapper.toEntity(dto);
        entity.setIdGrupoPequeno(id);
        GrupoPequeno savedEntity = repo.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public List<GrupoPequenoDTO> findAll() {
        return mapper.toDTOs(repo.findAll());
    }

    @Override
    public GrupoPequenoDTO findById(Long id) {
        GrupoPequeno entity = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
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
    public List<GrupoPequenoDTO> findByGrupoGeneralId(Long grupoGeneralId) {
        return mapper.toDTOs(repo.findByGrupoGeneralId(grupoGeneralId));
    }

    @Override
    public List<GrupoPequenoDTO> findByLiderId(Long liderId) {
        return mapper.toDTOs(repo.findByLiderId(liderId));
    }

    @Override
    public List<GrupoPequenoDTO> findByEstado(EstadoGrupo estado) {
        return mapper.toDTOs(repo.findByEstado(estado));
    }

    @Override
    public List<ParticipanteGrupoDTO> getParticipantes(Long grupoPequenoId) {
        return participanteMapper.toDTOs(participanteRepo.findByGrupoPequenoId(grupoPequenoId));
    }

    @Override
    public void addParticipante(Long grupoPequenoId, Long personaId) {
        Optional<ParticipanteGrupo> existingParticipante =
                participanteRepo.findByGrupoPequenoAndPersona(grupoPequenoId, personaId);

        if (existingParticipante.isPresent()) {
            throw new RuntimeException("El participante ya está registrado en este grupo");
        }

        ParticipanteGrupo participante = ParticipanteGrupo.builder()
                .grupoPequeno(repo.findById(grupoPequenoId).orElseThrow())
                .persona(personaRepo.findById(personaId).orElseThrow())
                .build();

        participanteRepo.save(participante);
    }

    @Override
    public void removeParticipante(Long grupoPequenoId, Long personaId) {
        Optional<ParticipanteGrupo> participante =
                participanteRepo.findByGrupoPequenoAndPersona(grupoPequenoId, personaId);

        if (participante.isPresent()) {
            participanteRepo.delete(participante.get());
        }
    }

    @Override
    public Long countParticipantesActivos(Long grupoPequenoId) {
        return repo.countParticipantesActivos(grupoPequenoId);
    }
}
