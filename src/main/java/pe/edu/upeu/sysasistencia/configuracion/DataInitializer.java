package pe.edu.upeu.sysasistencia.configuracion;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sysasistencia.modelo.Acceso;
import pe.edu.upeu.sysasistencia.modelo.AccesoRol;
import pe.edu.upeu.sysasistencia.modelo.Rol;
import pe.edu.upeu.sysasistencia.repositorio.IAccesoRepository;
import pe.edu.upeu.sysasistencia.repositorio.IAccesoRolRepository;
import pe.edu.upeu.sysasistencia.repositorio.IRolRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IRolRepository rolRepository;
    private final IAccesoRepository accesoRepository;

    // Necesitarás crear este repositorio
    private final IAccesoRolRepository accesoRolRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Iniciando carga de datos predeterminados...");

        // 1. Crear roles
        crearRoles();

        // 2. Crear accesos
        crearAccesos();

        // 3. Asignar accesos a roles
        asignarAccesosARoles();

        log.info("Carga de datos completada exitosamente");
    }

    private void crearRoles() {
        Arrays.stream(Rol.RolNombre.values()).forEach(rolNombre -> {
            Optional<Rol> rolExistente = rolRepository.findByNombre(rolNombre);

            if (rolExistente.isEmpty()) {
                Rol nuevoRol = Rol.builder()
                        .nombre(rolNombre)
                        .descripcion(obtenerDescripcionRol(rolNombre))
                        .build();
                rolRepository.save(nuevoRol);
                log.info("Rol creado: {}", rolNombre);
            } else {
                log.debug("Rol ya existe: {}", rolNombre);
            }
        });
    }

    private String obtenerDescripcionRol(Rol.RolNombre rolNombre) {
        return switch (rolNombre) {
            case SUPERADMIN -> "Super Administrador - Acceso total al sistema, gestión de usuarios, roles y configuración";
            case ADMIN -> "Administrador - Gestión completa de eventos, grupos, asistencias, matrículas y reportes";
            case LIDER -> "Líder - Gestión de grupos pequeños, participantes y registro de asistencias";
            case INTEGRANTE -> "Integrante - Acceso a eventos, grupos y consulta de asistencias personales";
        };
    }

    private void crearAccesos() {
        List<Acceso> accesos = Arrays.asList(
                // Accesos SUPERADMIN
                crearAcceso("Usuarios", "/usuarios", "fa-users"),
                crearAcceso("Roles", "/roles", "fa-user-shield"),
                crearAcceso("Configuración", "/configuracion", "fa-cog"),
                crearAcceso("Dashboard SuperAdmin", "/dashboard/superadmin", "fa-crown"),

                // Accesos ADMIN - Sistema de Eventos
                crearAcceso("Eventos Generales", "/admin/eventos/generales", "fa-calendar-alt"),
                crearAcceso("Eventos Específicos", "/admin/eventos/especificos", "fa-calendar-check"),
                crearAcceso("Grupos Generales", "/admin/grupos/generales", "fa-users"),
                crearAcceso("Grupos Pequeños", "/admin/grupos/pequenos", "fa-user-friends"),
                crearAcceso("Gestión de Asistencias", "/admin/asistencias", "fa-clipboard-check"),
                crearAcceso("Reportes de Eventos", "/admin/reportes/eventos", "fa-chart-bar"),
                crearAcceso("Dashboard Admin", "/dashboard/admin", "fa-tachometer-alt"),

                // Accesos ADMIN - Sistema Original
                crearAcceso("Matrículas", "/matriculas", "fa-clipboard-list"),
                crearAcceso("Importar Excel", "/matriculas/importar", "fa-file-excel"),
                crearAcceso("Sedes", "/sedes", "fa-building"),
                crearAcceso("Facultades", "/facultades", "fa-university"),
                crearAcceso("Programas", "/programas", "fa-graduation-cap"),
                crearAcceso("Reportes", "/reportes", "fa-chart-bar"),

                // Accesos LIDER
                crearAcceso("Mis Grupos", "/lider/mis-grupos", "fa-user-friends"),
                crearAcceso("Participantes", "/lider/participantes", "fa-users"),
                crearAcceso("Registrar Asistencia", "/lider/asistencias", "fa-clipboard-check"),
                crearAcceso("Dashboard Líder", "/dashboard/lider", "fa-chart-line"),

                // Accesos INTEGRANTE
                crearAcceso("Mis Eventos", "/integrante/eventos", "fa-calendar"),
                crearAcceso("Mi Grupo", "/integrante/mi-grupo", "fa-user-friends"),
                crearAcceso("Mi Asistencia", "/integrante/asistencia", "fa-clipboard-check"),
                crearAcceso("Dashboard Integrante", "/dashboard/integrante", "fa-chart-pie"),

                // Accesos PÚBLICOS (para mostrar en menús)
                crearAcceso("Eventos Activos", "/eventos", "fa-calendar"),
                crearAcceso("Grupos Disponibles", "/grupos", "fa-users"),
                crearAcceso("Información General", "/info", "fa-info-circle")
        );

        accesos.forEach(acceso -> {
            if (!accesoRepository.existsByUrl(acceso.getUrl())) {
                accesoRepository.save(acceso);
                log.info("Acceso creado: {} - {}", acceso.getNombre(), acceso.getUrl());
            }
        });
    }

    private Acceso crearAcceso(String nombre, String url, String icono) {
        return Acceso.builder()
                .nombre(nombre)
                .url(url)
                .icono(icono)
                .build();
    }

    private void asignarAccesosARoles() {
        // SUPERADMIN: acceso a todo
        asignarTodosLosAccesos(Rol.RolNombre.SUPERADMIN);

        // ADMIN: accesos específicos del sistema de eventos + sistema original
        asignarAccesosPorNombres(Rol.RolNombre.ADMIN, Arrays.asList(
                // Sistema de Eventos
                "Eventos Generales", "Eventos Específicos", "Grupos Generales", "Grupos Pequeños",
                "Gestión de Asistencias", "Reportes de Eventos", "Dashboard Admin",
                // Sistema Original
                "Matrículas", "Importar Excel", "Sedes", "Facultades",
                "Programas", "Reportes",
                // Accesos Públicos
                "Eventos Activos", "Grupos Disponibles", "Información General"
        ));

        // LIDER: accesos específicos para líderes
        asignarAccesosPorNombres(Rol.RolNombre.LIDER, Arrays.asList(
                "Mis Grupos", "Participantes", "Registrar Asistencia", "Dashboard Líder",
                "Eventos Activos", "Grupos Disponibles", "Información General"
        ));

        // INTEGRANTE: accesos específicos para integrantes
        asignarAccesosPorNombres(Rol.RolNombre.INTEGRANTE, Arrays.asList(
                "Mis Eventos", "Mi Grupo", "Mi Asistencia", "Dashboard Integrante",
                "Eventos Activos", "Grupos Disponibles", "Información General"
        ));
    }

    private void asignarTodosLosAccesos(Rol.RolNombre rolNombre) {
        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));

        List<Acceso> todosLosAccesos = accesoRepository.findAll();

        todosLosAccesos.forEach(acceso -> {
            if (!accesoRolRepository.existsByRolAndAcceso(rol, acceso)) {
                AccesoRol accesoRol = AccesoRol.builder()
                        .rol(rol)
                        .acceso(acceso)
                        .build();
                accesoRolRepository.save(accesoRol);
                log.debug("Acceso '{}' asignado a rol '{}'", acceso.getNombre(), rolNombre);
            }
        });

        log.info("Todos los accesos asignados a: {}", rolNombre);
    }

    private void asignarAccesosPorNombres(Rol.RolNombre rolNombre, List<String> nombresAccesos) {
        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));

        nombresAccesos.forEach(nombreAcceso -> {
            Acceso acceso = accesoRepository.findByNombre(nombreAcceso)
                    .orElse(null);

            if (acceso != null && !accesoRolRepository.existsByRolAndAcceso(rol, acceso)) {
                AccesoRol accesoRol = AccesoRol.builder()
                        .rol(rol)
                        .acceso(acceso)
                        .build();
                accesoRolRepository.save(accesoRol);
                log.debug("Acceso '{}' asignado a rol '{}'", nombreAcceso, rolNombre);
            }
        });

        log.info("Accesos asignados a {}: {}", rolNombre, nombresAccesos);
    }
}