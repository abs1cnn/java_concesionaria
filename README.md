-------------------------------------------------------
TRABAJO PRACTICO INTEGRADOR - CONCESIONARIA

    - MATERIA: Backend de Aplicaciones (Electiva de 3er año)
    - CARRERA: Ingeniería en Sistemas de Información
-------------------------------------------------------
DESCRIPCION
- Este proyecto es un sistema para gestionar pruebas de manejo en una concesionaria de autos. 
Cuando un cliente quiere probar un modelo específico de auto, el sistema facilita la coordinación 
y seguimiento de la prueba, además de enviar notificaciones en tiempo real si ocurren situaciones
de riesgo durante el recorrido. Situaciones de riesgo corresponde a cuando el auto localizado pasa
 por zonas no deseadas o se aleja del rango de la concesionaria

TECNOLOGIAS UTILIZADAS
- Spring Boot: Para la construcción de cada microservicio.
- JPA (Java Persistence API): Para el acceso y manejo de datos en la base de datos.
- API Gateway: Para enrutamiento y balanceo de carga entre microservicios.
- API Externa: Proporcionada para obtener coordenadas de zonas peligrosas y la ubicación de la concesionaria.

CARACTERISTICAS PRINCIPALES:
- Gestión de pruebas de manejo: Los clientes pueden solicitar pruebas de manejo de vehículos específicos.
- Notificaciones de seguridad: Todos los vehículos cuentan con GPS. Si un vehículo se aleja demasiado
de la concesionaria o se encuentra en una zona peligrosa, se envía una notificación al empleado encargado.
- Gestión de incidentes: Las pruebas que registran notificaciones de zonas peligrosas o alejamiento excesivo
quedan marcadas con un incidente.
- API externa: Se consume una API proporcionada por los profesores para obtener la ubicación de la 
concesionaria y las coordenadas de zonas peligrosas.

ARQUITECTURA DEL SISTEMA
- El proyecto utiliza una arquitectura de microservicios con los siguientes componentes:
- API Gateway
Actúa como puerta de entrada central para los diferentes microservicios.
- Microservicio de Pruebas
Responsable de gestionar las pruebas de manejo, su creación, seguimiento y estado.
Gestiona el seguimiento en tiempo real de cada vehículo de prueba.
- Microservicio de Notificaciones
Maneja las notificaciones generadas por eventos de incidentes.
Guarda el registro de notificaciones en una base de datos.