package pe.edu.upeu.sysasistencia.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysasistencia.modelo.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IMatriculaRepositoryTest {

    @Autowired
    private IMatriculaRepository matriculaRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private ISedeRepository sedeRepository;

    @Autowired
    private IFacultadRepository facultadRepository;

    @Autowired
    private IProgramaEstudioRepository programaEstudioRepository;

    private static Long matriculaId;

    private Persona persona;
    private Sede sede;
    private Facultad facultad;
    private ProgramaEstudio programa;

    @BeforeEach
    public void setUp() {
        // Crear Persona
        persona = personaRepository.save(Persona.builder()
                .nombreCompleto("BRAYAN")
                .documento("12345678")
                .correo("juanperez@upeu.edu.pe")
                .tipoPersona(TipoPersona.ESTUDIANTE) // Ajusta según tu enum
                .build());

        // Crear Sede
        sede = sedeRepository.save(Sede.builder()
                .nombre("Sede Central")
                .build());

        // Crear Facultad
        facultad = facultadRepository.save(Facultad.builder()
                .nombre("Ingeniería")
                .build());

        // Crear Programa
        programa = programaEstudioRepository.save(ProgramaEstudio.builder()
                .nombre("Ingeniería de Sistemas")
                .facultad(facultad)
                .build());

        // Crear Matrícula base
        Matricula matricula = Matricula.builder()
                .persona(persona)
                .sede(sede)
                .facultad(facultad)
                .programaEstudio(programa)
                .modoContrato("Ordinario")
                .modalidadEstudio("Presencial")
                .ciclo("2025-I")
                .grupo("A")
                .fechaMatricula(LocalDateTime.now())
                .estado("Activo")
                .build();

        Matricula guardada = matriculaRepository.save(matricula);
        matriculaId = guardada.getIdMatricula();
    }

    @Test
    @Order(1)
    public void testGuardarMatricula() {
        Matricula nueva = Matricula.builder()
                .persona(persona)
                .sede(sede)
                .facultad(facultad)
                .programaEstudio(programa)
                .modoContrato("Extraordinario")
                .modalidadEstudio("Virtual")
                .ciclo("2025-II")
                .grupo("B")
                .fechaMatricula(LocalDateTime.now())
                .estado("Activo")
                .build();

        Matricula guardada = matriculaRepository.save(nueva);
        assertNotNull(guardada.getIdMatricula());
        assertEquals("Extraordinario", guardada.getModoContrato());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<Matricula> encontrada = matriculaRepository.findById(matriculaId);
        assertTrue(encontrada.isPresent());
        assertEquals("Activo", encontrada.get().getEstado());
    }

    @Test
    @Order(3)
    public void testActualizarMatricula() {
        Matricula matricula = matriculaRepository.findById(matriculaId).orElseThrow();
        matricula.setEstado("Inactivo");
        Matricula actualizada = matriculaRepository.save(matricula);
        assertEquals("Inactivo", actualizada.getEstado());
    }

    @Test
    @Order(4)
    public void testListarMatriculas() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        assertFalse(matriculas.isEmpty());
        System.out.println("Total de matrículas registradas: " + matriculas.size());
    }

    @Test
    @Order(5)
    public void testEliminarMatricula() {
        matriculaRepository.deleteById(matriculaId);
        Optional<Matricula> eliminada = matriculaRepository.findById(matriculaId);
        assertFalse(eliminada.isPresent(), "La matrícula debería haber sido eliminada");
    }
}

