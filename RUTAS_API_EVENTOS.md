# 🎯 Sistema de Eventos - Rutas API para Frontend

## 📋 Resumen de Rutas Creadas

Se han creado **4 controladores principales** con diferentes niveles de acceso para gestionar el sistema de eventos:

### 🔐 **Niveles de Acceso**
- **Público**: Sin autenticación requerida
- **Admin**: Requiere rol ADMIN o SUPERADMIN
- **SuperAdmin**: Requiere rol SUPERADMIN únicamente

---

## 🌐 **RUTAS PÚBLICAS** (Sin autenticación)

### 📅 **Eventos Públicos** - `/api/eventos`

#### Eventos Generales
- `GET /api/eventos/generales` - Listar eventos generales activos
- `GET /api/eventos/generales/{id}` - Obtener evento general por ID
- `GET /api/eventos/generales/buscar?nombre={nombre}` - Buscar eventos por nombre
- `GET /api/eventos/generales/fecha/{fecha}` - Eventos por fecha específica

#### Eventos Específicos
- `GET /api/eventos/especificos` - Listar eventos específicos activos
- `GET /api/eventos/especificos/evento-general/{eventoGeneralId}` - Eventos específicos por evento general
- `GET /api/eventos/especificos/fecha/{fecha}` - Eventos específicos por fecha

#### Grupos Generales
- `GET /api/eventos/grupos-generales` - Listar grupos generales activos
- `GET /api/eventos/grupos-generales/evento/{eventoGeneralId}` - Grupos por evento general
- `GET /api/eventos/grupos-generales/programa/{programaId}` - Grupos por programa

#### Grupos Pequeños
- `GET /api/eventos/grupos-pequenos` - Listar grupos pequeños activos
- `GET /api/eventos/grupos-pequenos/grupo-general/{grupoGeneralId}` - Grupos pequeños por grupo general
- `GET /api/eventos/grupos-pequenos/lider/{liderId}` - Grupos pequeños por líder

#### Información General
- `GET /api/eventos/info` - Información general de eventos activos
- `GET /api/eventos/proximos` - Eventos próximos a realizarse

### 👥 **Grupos Públicos** - `/api/grupos`

#### Grupos Generales
- `GET /api/grupos/generales` - Listar grupos generales activos
- `GET /api/grupos/generales/{id}` - Obtener grupo general por ID
- `GET /api/grupos/generales/evento/{eventoGeneralId}` - Grupos por evento general
- `GET /api/grupos/generales/programa/{programaId}` - Grupos por programa

#### Grupos Pequeños
- `GET /api/grupos/pequenos` - Listar grupos pequeños activos
- `GET /api/grupos/pequenos/{id}` - Obtener grupo pequeño por ID
- `GET /api/grupos/pequenos/grupo-general/{grupoGeneralId}` - Grupos pequeños por grupo general
- `GET /api/grupos/pequenos/lider/{liderId}` - Grupos pequeños por líder
- `GET /api/grupos/pequenos/{grupoPequenoId}/participantes` - Participantes de un grupo pequeño

#### Información General
- `GET /api/grupos/info` - Información general de grupos activos
- `GET /api/grupos/buscar?nombre={nombre}` - Buscar grupos por nombre

---

## 🔑 **RUTAS ADMINISTRATIVAS** (ADMIN + SUPERADMIN)

### 🎯 **Gestión de Eventos** - `/api/admin/eventos`

#### Eventos Generales
- `POST /api/admin/eventos/generales` - Crear evento general
- `PUT /api/admin/eventos/generales/{id}` - Actualizar evento general
- `DELETE /api/admin/eventos/generales/{id}` - Eliminar evento general
- `GET /api/admin/eventos/generales` - Listar todos los eventos generales
- `GET /api/admin/eventos/generales/{id}` - Obtener evento general por ID
- `GET /api/admin/eventos/generales/estado/{estado}` - Filtrar eventos por estado

#### Eventos Específicos
- `POST /api/admin/eventos/especificos` - Crear evento específico
- `PUT /api/admin/eventos/especificos/{id}` - Actualizar evento específico
- `DELETE /api/admin/eventos/especificos/{id}` - Eliminar evento específico
- `GET /api/admin/eventos/especificos` - Listar todos los eventos específicos
- `GET /api/admin/eventos/especificos/evento-general/{eventoGeneralId}` - Eventos específicos por evento general
- `GET /api/admin/eventos/especificos/fecha/{fecha}` - Filtrar eventos por fecha

#### Grupos Generales
- `POST /api/admin/eventos/grupos-generales` - Crear grupo general
- `PUT /api/admin/eventos/grupos-generales/{id}` - Actualizar grupo general
- `DELETE /api/admin/eventos/grupos-generales/{id}` - Eliminar grupo general
- `GET /api/admin/eventos/grupos-generales` - Listar todos los grupos generales
- `GET /api/admin/eventos/grupos-generales/evento/{eventoGeneralId}` - Grupos por evento general

#### Grupos Pequeños
- `POST /api/admin/eventos/grupos-pequenos` - Crear grupo pequeño
- `PUT /api/admin/eventos/grupos-pequenos/{id}` - Actualizar grupo pequeño
- `DELETE /api/admin/eventos/grupos-pequenos/{id}` - Eliminar grupo pequeño
- `GET /api/admin/eventos/grupos-pequenos` - Listar todos los grupos pequeños
- `GET /api/admin/eventos/grupos-pequenos/grupo-general/{grupoGeneralId}` - Grupos pequeños por grupo general
- `GET /api/admin/eventos/grupos-pequenos/{grupoPequenoId}/participantes` - Participantes de un grupo pequeño
- `POST /api/admin/eventos/grupos-pequenos/{grupoPequenoId}/participantes/{personaId}` - Agregar participante
- `DELETE /api/admin/eventos/grupos-pequenos/{grupoPequenoId}/participantes/{personaId}` - Remover participante

---

## 👑 **RUTAS SUPERADMIN** (Solo SUPERADMIN)

### 🏛️ **Gestión del Sistema** - `/api/superadmin`

#### Facultades
- `POST /api/superadmin/facultades` - Crear facultad
- `PUT /api/superadmin/facultades/{id}` - Actualizar facultad
- `DELETE /api/superadmin/facultades/{id}` - Eliminar facultad
- `GET /api/superadmin/facultades` - Listar facultades

#### Sedes
- `POST /api/superadmin/sedes` - Crear sede
- `PUT /api/superadmin/sedes/{id}` - Actualizar sede
- `DELETE /api/superadmin/sedes/{id}` - Eliminar sede
- `GET /api/superadmin/sedes` - Listar sedes

#### Programas de Estudio
- `POST /api/superadmin/programas-estudio` - Crear programa de estudio
- `PUT /api/superadmin/programas-estudio/{id}` - Actualizar programa de estudio
- `DELETE /api/superadmin/programas-estudio/{id}` - Eliminar programa de estudio
- `GET /api/superadmin/programas-estudio` - Listar programas de estudio
- `GET /api/superadmin/programas-estudio/facultad/{facultadId}` - Programas por facultad

#### Personas
- `POST /api/superadmin/personas` - Crear persona
- `PUT /api/superadmin/personas/{id}` - Actualizar persona
- `DELETE /api/superadmin/personas/{id}` - Eliminar persona
- `GET /api/superadmin/personas` - Listar personas

#### Matrículas
- `POST /api/superadmin/matriculas` - Crear matrícula
- `PUT /api/superadmin/matriculas/{id}` - Actualizar matrícula
- `DELETE /api/superadmin/matriculas/{id}` - Eliminar matrícula
- `GET /api/superadmin/matriculas` - Listar matrículas
- `GET /api/superadmin/matriculas/persona/{personaId}` - Matrículas por persona

#### Usuarios
- `POST /api/superadmin/usuarios` - Crear usuario
- `PUT /api/superadmin/usuarios/{id}` - Actualizar usuario
- `DELETE /api/superadmin/usuarios/{id}` - Eliminar usuario
- `GET /api/superadmin/usuarios` - Listar usuarios

#### Operaciones de Sistema
- `GET /api/superadmin/dashboard` - Dashboard del sistema
- `GET /api/superadmin/exportar` - Exportar datos del sistema

---

## 🔒 **Configuración de Seguridad**

### Permisos por Ruta:
- **Públicas**: `/api/eventos/**`, `/api/grupos/**` (GET)
- **Admin**: `/api/admin/**` (ADMIN + SUPERADMIN)
- **SuperAdmin**: `/api/superadmin/**` (Solo SUPERADMIN)

### Autenticación:
- Todas las rutas administrativas requieren token JWT válido
- Los roles se validan automáticamente por Spring Security

---

## 📱 **Para el Frontend**

### Ejemplos de Uso:

#### 1. **Obtener eventos activos** (Público)
```javascript
fetch('/api/eventos/generales')
  .then(response => response.json())
  .then(eventos => console.log(eventos));
```

#### 2. **Crear evento** (Admin)
```javascript
fetch('/api/admin/eventos/generales', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + token
  },
  body: JSON.stringify({
    nombreEvento: 'Nuevo Evento',
    descripcion: 'Descripción del evento',
    fechaInicio: '2024-01-01',
    fechaFin: '2024-01-31',
    lugar: 'Auditorio Principal',
    estado: 'ACTIVO'
  })
});
```

#### 3. **Obtener grupos de un evento** (Público)
```javascript
fetch('/api/grupos/generales/evento/1')
  .then(response => response.json())
  .then(grupos => console.log(grupos));
```

---

## 🚀 **Próximos Pasos**

1. **Probar las rutas** con Postman o similar
2. **Integrar con el frontend** usando las rutas públicas
3. **Implementar autenticación** para rutas administrativas
4. **Crear interfaces de usuario** para cada nivel de acceso

---

**¡El sistema está listo para ser probado en el frontend!** 🎉
