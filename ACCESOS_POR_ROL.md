# 🔐 Sistema de Accesos por Rol - DataInitializer

## 📋 Resumen de Accesos Configurados

El `DataInitializer` ha sido actualizado para incluir **todos los accesos necesarios** para el sistema de eventos, organizados por roles con diferentes niveles de permisos.

---

## 👑 **SUPERADMIN** - Acceso Total

### Descripción
**Super Administrador** - Acceso total al sistema, gestión de usuarios, roles y configuración

### Accesos Asignados
- ✅ **Todos los accesos del sistema** (acceso automático a todo)

### Accesos Específicos Incluidos:
- 🔧 **Usuarios** (`/usuarios`) - Gestión de usuarios
- 🛡️ **Roles** (`/roles`) - Gestión de roles
- ⚙️ **Configuración** (`/configuracion`) - Configuración del sistema
- 👑 **Dashboard SuperAdmin** (`/dashboard/superadmin`) - Panel de control

---

## 🔧 **ADMIN** - Gestión Completa de Eventos

### Descripción
**Administrador** - Gestión completa de eventos, grupos, asistencias, matrículas y reportes

### Accesos del Sistema de Eventos:
- 📅 **Eventos Generales** (`/admin/eventos/generales`) - Crear, editar, eliminar eventos generales
- 📋 **Eventos Específicos** (`/admin/eventos/especificos`) - Gestionar eventos específicos
- 👥 **Grupos Generales** (`/admin/grupos/generales`) - Administrar grupos generales
- 👨‍👩‍👧‍👦 **Grupos Pequeños** (`/admin/grupos/pequenos`) - Gestionar grupos pequeños
- ✅ **Gestión de Asistencias** (`/admin/asistencias`) - Registrar y consultar asistencias
- 📊 **Reportes de Eventos** (`/admin/reportes/eventos`) - Generar reportes de eventos
- 📈 **Dashboard Admin** (`/dashboard/admin`) - Panel de administración

### Accesos del Sistema Original:
- 📝 **Matrículas** (`/matriculas`) - Gestión de matrículas
- 📊 **Importar Excel** (`/matriculas/importar`) - Importar datos desde Excel
- 🏢 **Sedes** (`/sedes`) - Gestión de sedes
- 🏛️ **Facultades** (`/facultades`) - Gestión de facultades
- 🎓 **Programas** (`/programas`) - Gestión de programas de estudio
- 📊 **Reportes** (`/reportes`) - Reportes generales

### Accesos Públicos:
- 📅 **Eventos Activos** (`/eventos`) - Ver eventos activos
- 👥 **Grupos Disponibles** (`/grupos`) - Ver grupos disponibles
- ℹ️ **Información General** (`/info`) - Información del sistema

---

## 👨‍💼 **LIDER** - Gestión de Grupos Pequeños

### Descripción
**Líder** - Gestión de grupos pequeños, participantes y registro de asistencias

### Accesos Específicos:
- 👨‍👩‍👧‍👦 **Mis Grupos** (`/lider/mis-grupos`) - Ver grupos que lidera
- 👥 **Participantes** (`/lider/participantes`) - Gestionar participantes de sus grupos
- ✅ **Registrar Asistencia** (`/lider/asistencias`) - Registrar asistencias de eventos
- 📈 **Dashboard Líder** (`/dashboard/lider`) - Panel de líder

### Accesos Públicos:
- 📅 **Eventos Activos** (`/eventos`) - Ver eventos activos
- 👥 **Grupos Disponibles** (`/grupos`) - Ver grupos disponibles
- ℹ️ **Información General** (`/info`) - Información del sistema

---

## 👤 **INTEGRANTE** - Acceso Básico

### Descripción
**Integrante** - Acceso a eventos, grupos y consulta de asistencias personales

### Accesos Específicos:
- 📅 **Mis Eventos** (`/integrante/eventos`) - Ver eventos en los que participa
- 👨‍👩‍👧‍👦 **Mi Grupo** (`/integrante/mi-grupo`) - Ver información de su grupo
- ✅ **Mi Asistencia** (`/integrante/asistencia`) - Consultar sus asistencias
- 📊 **Dashboard Integrante** (`/dashboard/integrante`) - Panel personal

### Accesos Públicos:
- 📅 **Eventos Activos** (`/eventos`) - Ver eventos activos
- 👥 **Grupos Disponibles** (`/grupos`) - Ver grupos disponibles
- ℹ️ **Información General** (`/info`) - Información del sistema

---

## 🎯 **Estructura de URLs por Rol**

### 🔐 **Rutas Administrativas** (Requieren autenticación)
```
/api/admin/eventos/**     - Solo ADMIN y SUPERADMIN
/api/superadmin/**        - Solo SUPERADMIN
```

### 🌐 **Rutas Públicas** (Sin autenticación)
```
/api/eventos/**           - Acceso público
/api/grupos/**            - Acceso público
```

### 👤 **Rutas de Usuario** (Requieren autenticación)
```
/lider/**                 - Solo LIDER
/integrante/**            - Solo INTEGRANTE
/dashboard/**             - Según rol
```

---

## 🚀 **Funcionalidades por Rol**

### 👑 **SUPERADMIN**
- ✅ Crear/editar/eliminar usuarios
- ✅ Asignar roles
- ✅ Configurar sistema
- ✅ Acceso total a todas las funcionalidades
- ✅ Gestión de eventos, grupos y asistencias
- ✅ Reportes completos

### 🔧 **ADMIN**
- ✅ Gestión completa de eventos
- ✅ Crear/editar/eliminar eventos generales y específicos
- ✅ Gestionar grupos generales y pequeños
- ✅ Registrar y consultar asistencias
- ✅ Generar reportes de eventos
- ✅ Gestión de matrículas y datos académicos

### 👨‍💼 **LIDER**
- ✅ Ver grupos que lidera
- ✅ Gestionar participantes de sus grupos
- ✅ Registrar asistencias de eventos
- ✅ Consultar información de eventos
- ❌ No puede crear eventos o grupos

### 👤 **INTEGRANTE**
- ✅ Ver eventos en los que participa
- ✅ Consultar información de su grupo
- ✅ Ver sus asistencias
- ✅ Acceso a información pública
- ❌ No puede gestionar eventos o grupos

---

## 📱 **Para el Frontend**

### Ejemplo de Menú Dinámico por Rol:

#### 👑 **SUPERADMIN**
```javascript
const menuSuperAdmin = [
  { nombre: "Usuarios", url: "/usuarios", icono: "fa-users" },
  { nombre: "Roles", url: "/roles", icono: "fa-user-shield" },
  { nombre: "Eventos Generales", url: "/admin/eventos/generales", icono: "fa-calendar-alt" },
  { nombre: "Grupos Pequeños", url: "/admin/grupos/pequenos", icono: "fa-user-friends" },
  { nombre: "Dashboard SuperAdmin", url: "/dashboard/superadmin", icono: "fa-crown" }
];
```

#### 🔧 **ADMIN**
```javascript
const menuAdmin = [
  { nombre: "Eventos Generales", url: "/admin/eventos/generales", icono: "fa-calendar-alt" },
  { nombre: "Grupos Generales", url: "/admin/grupos/generales", icono: "fa-users" },
  { nombre: "Gestión de Asistencias", url: "/admin/asistencias", icono: "fa-clipboard-check" },
  { nombre: "Matrículas", url: "/matriculas", icono: "fa-clipboard-list" },
  { nombre: "Dashboard Admin", url: "/dashboard/admin", icono: "fa-tachometer-alt" }
];
```

#### 👨‍💼 **LIDER**
```javascript
const menuLider = [
  { nombre: "Mis Grupos", url: "/lider/mis-grupos", icono: "fa-user-friends" },
  { nombre: "Participantes", url: "/lider/participantes", icono: "fa-users" },
  { nombre: "Registrar Asistencia", url: "/lider/asistencias", icono: "fa-clipboard-check" },
  { nombre: "Dashboard Líder", url: "/dashboard/lider", icono: "fa-chart-line" }
];
```

#### 👤 **INTEGRANTE**
```javascript
const menuIntegrante = [
  { nombre: "Mis Eventos", url: "/integrante/eventos", icono: "fa-calendar" },
  { nombre: "Mi Grupo", url: "/integrante/mi-grupo", icono: "fa-user-friends" },
  { nombre: "Mi Asistencia", url: "/integrante/asistencia", icono: "fa-clipboard-check" },
  { nombre: "Dashboard Integrante", url: "/dashboard/integrante", icono: "fa-chart-pie" }
];
```

---

## 🔄 **Inicialización Automática**

El `DataInitializer` se ejecuta automáticamente al iniciar la aplicación y:

1. ✅ **Crea los roles** si no existen
2. ✅ **Crea los accesos** si no existen  
3. ✅ **Asigna accesos a roles** según la configuración
4. ✅ **Registra en logs** todas las operaciones realizadas

---

## 🎯 **Próximos Pasos**

1. **Ejecutar la aplicación** para inicializar los datos
2. **Verificar en base de datos** que se crearon los accesos
3. **Implementar en frontend** el menú dinámico por rol
4. **Probar la navegación** según cada rol

---

**¡El sistema de accesos está completamente configurado!** 🎉
