package pe.edu.upeu.sysasistencia.servicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.upeu.sysasistencia.dtos.AsistenciaDTO;
import pe.edu.upeu.sysasistencia.mappers.AsistenciaMapper;
import pe.edu.upeu.sysasistencia.modelo.Asistencia;
import pe.edu.upeu.sysasistencia.modelo.EstadoAsistencia;
import pe.edu.upeu.sysasistencia.repositorio.IAsistenciaRepository;
import pe.edu.upeu.sysasistencia.servicio.impl.AsistenciaServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsistenciaServiceTest {

    @Mock
    private IAsistenciaRepository asistenciaRepository;

    @Mock
    private AsistenciaMapper asistenciaMapper;

    @InjectMocks
    private AsistenciaServiceImp asistenciaService;

    private Asistencia asistenciaEntity;
    private AsistenciaDTO asistenciaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        asistenciaEntity = Asistencia.builder()
                .idAsistencia(1L)
                .fechaAsistencia(LocalDateTime.now())
                .estadoAsistencia(EstadoAsistencia.PRESENTE)
                .observaciones("Prueba asistencia")
                .build();

        asistenciaDTO = new AsistenciaDTO(
                1L, 2L, 3L,
                LocalDateTime.now(),
                "PRESENTE",
                "Prueba asistencia",
                LocalDateTime.now()
        );
    }

    @Test
    void testSaveAsistencia() {
        when(asistenciaMapper.toEntity(asistenciaDTO)).thenReturn(asistenciaEntity);
        when(asistenciaRepository.save(asistenciaEntity)).thenReturn(asistenciaEntity);
        when(asistenciaMapper.toDTO(asistenciaEntity)).thenReturn(asistenciaDTO);

        AsistenciaDTO result = asistenciaService.save(asistenciaDTO);

        assertNotNull(result);
        assertEquals(asistenciaDTO.getIdAsistencia(), result.getIdAsistencia());
        verify(asistenciaRepository, times(1)).save(any(Asistencia.class));
    }

    @Test
    void testFindAll() {
        when(asistenciaRepository.findAll()).thenReturn(List.of(asistenciaEntity));
        when(asistenciaMapper.toDTOs(List.of(asistenciaEntity))).thenReturn(List.of(asistenciaDTO));

        List<AsistenciaDTO> result = asistenciaService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(asistenciaRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(asistenciaRepository.findById(1L)).thenReturn(Optional.of(asistenciaEntity));
        when(asistenciaMapper.toDTO(asistenciaEntity)).thenReturn(asistenciaDTO);

        AsistenciaDTO result = asistenciaService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdAsistencia());
        verify(asistenciaRepository, times(1)).findById(1L);
    }
}
