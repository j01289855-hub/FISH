# Fisch Macro Android

Este repositorio sirve como base inicial para una app Android que mida, calibre y administre un asistente de pesca para Fisch en dispositivos móviles.

## Estado actual
- Proyecto Android base listo para abrir en Android Studio.
- Estructura creada para apoyar:
  - captura de pantalla
  - detección visual de la barra de pesca
  - perfiles de calibración
  - servicio en segundo plano
  - automatización de toques configurables

## Requisitos
- Android Studio Iguana o superior
- JDK 17
- Android SDK 34
- Mínimo soportado: API 26 (Android 8.0)
- Recomendado para detección visual: API 30+ (Android 11+)

## Objetivo del proyecto
La app está pensada como una herramienta de apoyo personal y no como una solución de promoción masiva. La intención es ofrecer una base técnica para:
- detectar la barra de pesca por color o patrón visual
- distinguir fases del minijuego
- guardar perfiles por resolución/orientación
- seguir la lógica del juego sin depender solo de tiempos fijos
- mantener el servicio activo con notificaciones y priorización de foreground service

## Estructura
- `app/` — módulo principal de Android
- `MainActivity` — pantalla de inicio y permisos
- `FishingMacroService` — servicio principal en foreground
- `FishDetector` — lógica de análisis visual y estados del minijuego
- `CalibrationProfile` — perfil por resolución / orientación
- `ScreenCaptureManager` — gestión de proyección de pantalla

## Siguientes pasos recomendados
1. Añadir una vista de calibración visual real con capturas del juego.
2. Definir la lógica exacta por fase: cast, bite, shake, catch, fail.
3. Añadir almacenamiento persistente con `DataStore` o `Room`.
4. Añadir overlay para pruebas locales.
5. Probar en varios modelos Android para ajustar la sensibilidad.

## Aviso
El proyecto actual es una base técnica de trabajo y no incluye una versión final de automatización comercial ni un script de hackeo del juego. Está diseñado como infraestructura para un asistente personal y controlado por el usuario.
