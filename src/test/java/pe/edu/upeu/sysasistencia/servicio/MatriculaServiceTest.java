package pe.edu.upeu.sysasistencia.servicio;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upeu.sysasistencia.modelo.Matricula;
import pe.edu.upeu.sysasistencia.modelo.Persona;
import pe.edu.upeu.sysasistencia.modelo.TipoPersona;
import pe.edu.upeu.sysasistencia.repositorio.IMatriculaRepository;
import pe.edu.upeu.sysasistencia.servicio.impl.MatriculaServiceImp;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MatriculaServiceTest {

    @Mock
    private IMatriculaRepository repo;

    @InjectMocks
    private MatriculaServiceImp matriculaService;

    Matricula matricula;

    @BeforeEach
    public void setUp() {
        Persona persona = Persona.builder()
                .idPersona(1L)
                .nombreCompleto("Juan Pérez")
                .codigoEstudiante("20230001")
                .tipoPersona(TipoPersona.ESTUDIANTE)
                .build();

        matricula = Matricula.builder()
                .idMatricula(1L)
                .persona(persona)
                .modoContrato("Regular")
                .modalidadEstudio("Presencial")
                .estado("ACTIVO")
                .build();
    }

    @Order(1)
    @DisplayName("Guardar Matrícula")
    @Test
    public void testSaveMatricula() {
        // given
        given(repo.save(matricula)).willReturn(matricula);

        // when
        Matricula result = matriculaService.save(matricula);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getPersona().getNombreCompleto()).isEqualTo("Juan Pérez");
        System.out.println("✅ Matrícula guardada correctamente: " + result.getPersona().getCodigoEstudiante());
    }

    @Order(2)
    @DisplayName("Listar Matrículas")
    @Test
    public void testListMatriculas() {
        // given
        Matricula otraMatricula = Matricula.builder()
                .idMatricula(2L)
                .modoContrato("Temporal")
                .modalidadEstudio("Virtual")
                .estado("INACTIVO")
                .build();

        given(repo.findAll()).willReturn(List.of(matricula, otraMatricula));

        // when
        List<Matricula> lista = matriculaService.findAll();

        // then
        Assertions.assertThat(lista).hasSize(2);
        Assertions.assertThat(lista.get(0).getIdMatricula()).isEqualTo(1L);
        System.out.println("📋 Total matrículas: " + lista.size());
    }

    @Order(3)
    @DisplayName("Actualizar Matrícula")
    @Test
    public void testUpdateMatricula() {
        // given
        given(repo.findById(1L)).willReturn(Optional.of(matricula));
        given(repo.save(matricula)).willReturn(matricula);

        // when
        matricula.setEstado("INACTIVO");
        Matricula actualizada = matriculaService.update(matricula.getIdMatricula(), matricula);

        // then
        Assertions.assertThat(actualizada.getEstado()).isEqualTo("INACTIVO");
        System.out.println("♻️ Matrícula actualizada con nuevo estado: " + actualizada.getEstado());
    }

    @Order(4)
    @DisplayName("Eliminar Matrícula")
    @Test
    public void testDeleteMatricula() {
        // given
        given(repo.findById(1L)).willReturn(Optional.of(matricula));

        // when
        var response = matriculaService.delete(1L);

        // then
        Assertions.assertThat(response.getMessage()).isEqualTo("true");
        System.out.println("🗑️ Matrícula eliminada correctamente");
    }

    @Order(5)
    @DisplayName("Eliminar Matrícula - ID no existente")
    @Test
    public void testDeleteMatriculaNotFound() {
        // given
        Long idInexistente = 99L;
        given(repo.findById(idInexistente)).willReturn(Optional.empty());

        // when / then
        Assertions.assertThatThrownBy(() -> matriculaService.delete(idInexistente))
                .isInstanceOf(pe.edu.upeu.sysasistencia.excepciones.ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + idInexistente);
        System.out.println("⚠️ Intento de eliminar matrícula inexistente detectado correctamente.");
    }
}