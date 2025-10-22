package pe.edu.upeu.sysasistencia.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import pe.edu.upeu.sysasistencia.dtos.ImportFilterDTO;
import pe.edu.upeu.sysasistencia.dtos.ImportResultDTO;
import pe.edu.upeu.sysasistencia.dtos.MatriculaDTO;
import pe.edu.upeu.sysasistencia.excepciones.CustomResponse;
import pe.edu.upeu.sysasistencia.mappers.MatriculaMapper;
import pe.edu.upeu.sysasistencia.modelo.Matricula;
import pe.edu.upeu.sysasistencia.modelo.TipoPersona;
import pe.edu.upeu.sysasistencia.servicio.IMatriculaService;
import java.time.LocalDateTime;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatriculaControllerTest {

    @Mock
    private IMatriculaService matriculaService;

    @Mock
    private MatriculaMapper matriculaMapper;

    @InjectMocks
    private MatriculaController matriculaController;

    private Matricula matricula;
    private MatriculaDTO matriculaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        matricula = new Matricula();
        matricula.setIdMatricula(1L);
        matricula.setEstado("ACTIVO");

        matriculaDTO = new MatriculaDTO();
        matriculaDTO.setIdMatricula(1L);
        matriculaDTO.setEstado("ACTIVO");
    }

    @Test
    void testFindAll() {
        when(matriculaService.findAll()).thenReturn(List.of(matricula));
        when(matriculaMapper.toDTOs(anyList())).thenReturn(List.of(matriculaDTO));

        ResponseEntity<List<MatriculaDTO>> response = matriculaController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(matriculaService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(matriculaService.findById(1L)).thenReturn(matricula);
        when(matriculaMapper.toDTO(matricula)).thenReturn(matriculaDTO);

        ResponseEntity<MatriculaDTO> response = matriculaController.findById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getIdMatricula());
    }

    @Test
    void testFindByCodigoEstudiante() {
        when(matriculaService.findByCodigoEstudiante("A001")).thenReturn(List.of(matricula));
        when(matriculaMapper.toDTOs(anyList())).thenReturn(List.of(matriculaDTO));

        ResponseEntity<List<MatriculaDTO>> response = matriculaController.findByCodigoEstudiante("A001");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testFindByFiltros() {
        when(matriculaService.findByFiltros(null, null, null, TipoPersona.ESTUDIANTE))
                .thenReturn(List.of(matricula));
        when(matriculaMapper.toDTOs(anyList())).thenReturn(List.of(matriculaDTO));

        ResponseEntity<List<MatriculaDTO>> response =
                matriculaController.findByFiltros(null, null, null, TipoPersona.ESTUDIANTE);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testSave() {
        when(matriculaMapper.toEntity(matriculaDTO)).thenReturn(matricula);
        when(matriculaService.save(matricula)).thenReturn(matricula);
        when(matriculaMapper.toDTO(matricula)).thenReturn(matriculaDTO);

        ResponseEntity<MatriculaDTO> response = matriculaController.save(matriculaDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("ACTIVO", response.getBody().getEstado());
    }

    @Test
    void testUpdate() {
        when(matriculaMapper.toEntity(matriculaDTO)).thenReturn(matricula);
        when(matriculaService.update(1L, matricula)).thenReturn(matricula);
        when(matriculaMapper.toDTO(matricula)).thenReturn(matriculaDTO);

        ResponseEntity<MatriculaDTO> response = matriculaController.update(1L, matriculaDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getIdMatricula());
    }

    @Test
    void testDelete() {


        CustomResponse mockResponse = new CustomResponse(
                200,
                LocalDateTime.now(),
                "Eliminado correctamente",
                "/matriculas/1"
        );


        when(matriculaService.delete(1L)).thenReturn(mockResponse);

        ResponseEntity<CustomResponse> response = matriculaController.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Eliminado correctamente", response.getBody().getMessage());
    }

    @Test
    void testImportarExcel_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[]{1, 2, 3});

        ImportResultDTO resultDTO = new ImportResultDTO();
        resultDTO.setTotalRegistros(5);
        resultDTO.setExitosos(5);
        resultDTO.setFallidos(0);
        resultDTO.setWarnings(Collections.singletonList("Importación correcta"));

        when(matriculaService.importarDesdeExcel(any(), any())).thenReturn(resultDTO);

        ResponseEntity<ImportResultDTO> response = matriculaController.importarExcel(file, null, null, null, TipoPersona.ESTUDIANTE);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5, response.getBody().getTotalRegistros());
        verify(matriculaService, times(1)).importarDesdeExcel(any(), any());
    }

    @Test
    void testImportarExcel_FileEmpty() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]);

        ResponseEntity<ImportResultDTO> response = matriculaController.importarExcel(emptyFile, null, null, null, TipoPersona.ESTUDIANTE);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getErrores().get(0).contains("Error general"));
    }
}
