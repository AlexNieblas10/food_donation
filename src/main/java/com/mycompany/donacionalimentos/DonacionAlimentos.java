/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.donacionalimentos;

import Control.*;
import Entidades.*;
import Excepciones.*;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class DonacionAlimentos {

    private static Scanner scanner = new Scanner(System.in);
    private static DonadorController donadorController = new DonadorController();
    private static OrganizacionController organizacionController = new OrganizacionController();
    private static DireccionController direccionController = new DireccionController();
    private static AlimentoController alimentoController = new AlimentoController();
    private static EntregaController entregaController = new EntregaController();
    private static DetalleEntregaController detalleEntregaController = new DetalleEntregaController();

    public static void main(String[] args) {
        System.out.println("=== Sistema de Donación de Alimentos ===");

        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcion) {
                    case 1:
                        menuDonadores();
                        break;
                    case 2:
                        menuOrganizaciones();
                        break;
                    case 3:
                        menuDirecciones();
                        break;
                    case 4:
                        menuAlimentos();
                        break;
                    case 5:
                        menuEntregas();
                        break;
                    case 6:
                        menuDetalleEntregas();
                        break;
                    case 0:
                        System.out.println("¡Gracias por usar el sistema!");
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestionar Donadores");
        System.out.println("2. Gestionar Organizaciones");
        System.out.println("3. Gestionar Direcciones");
        System.out.println("4. Gestionar Alimentos");
        System.out.println("5. Gestionar Entregas");
        System.out.println("6. Gestionar Detalle de Entregas");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void menuDonadores() throws DonadorException {
        System.out.println("\n--- GESTIÓN DE DONADORES ---");
        System.out.println("1. Crear donador");
        System.out.println("2. Listar donadores");
        System.out.println("3. Buscar donador por ID");
        System.out.println("4. Actualizar donador");
        System.out.println("5. Eliminar donador");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                crearDonador();
                break;
            case 2:
                listarDonadores();
                break;
            case 3:
                buscarDonador();
                break;
            case 4:
                actualizarDonador();
                break;
            case 5:
                eliminarDonador();
                break;
        }
    }

    private static void crearDonador() throws DonadorException {
        System.out.println("\n--- CREAR DONADOR ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido Paterno: ");
        String apellidoPaterno = scanner.nextLine();
        System.out.print("Apellido Materno: ");
        String apellidoMaterno = scanner.nextLine();
        System.out.print("Tipo de Donador: ");
        String tipoDonador = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("ID Dirección: ");
        int idDireccion = scanner.nextInt();

        Donador donador = new Donador(0, nombre, apellidoPaterno, apellidoMaterno, tipoDonador, correo, telefono, idDireccion);
        donadorController.crearDonador(donador);
        System.out.println("Donador creado exitosamente!");
    }

    private static void listarDonadores() throws DonadorException {
        System.out.println("\n--- LISTA DE DONADORES ---");
        List<Donador> donadores = donadorController.obtenerTodosDonadores();
        for (Donador donador : donadores) {
            System.out.println(donador);
        }
    }

    private static void buscarDonador() throws DonadorException {
        System.out.print("Ingrese ID del donador: ");
        int id = scanner.nextInt();
        Donador donador = donadorController.obtenerDonador(id);
        if (donador != null) {
            System.out.println(donador);
        } else {
            System.out.println("Donador no encontrado.");
        }
    }

    private static void actualizarDonador() throws DonadorException {
        System.out.print("Ingrese ID del donador a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Donador donador = donadorController.obtenerDonador(id);
        if (donador != null) {
            System.out.print("Nuevo nombre (" + donador.getNombre() + "): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) donador.setNombre(nombre);

            System.out.print("Nuevo apellido paterno (" + donador.getApellidoPaterno() + "): ");
            String apellidoPaterno = scanner.nextLine();
            if (!apellidoPaterno.isEmpty()) donador.setApellidoPaterno(apellidoPaterno);

            donadorController.actualizarDonador(donador);
            System.out.println("Donador actualizado exitosamente!");
        } else {
            System.out.println("Donador no encontrado.");
        }
    }

    private static void eliminarDonador() throws DonadorException {
        System.out.print("Ingrese ID del donador a eliminar: ");
        int id = scanner.nextInt();
        donadorController.eliminarDonador(id);
        System.out.println("Donador eliminado exitosamente!");
    }

    private static void menuOrganizaciones() throws OrganizacionException {
        System.out.println("\n--- GESTIÓN DE ORGANIZACIONES ---");
        System.out.println("1. Crear organización");
        System.out.println("2. Listar organizaciones");
        System.out.println("3. Buscar organización por ID");
        System.out.println("4. Actualizar organización");
        System.out.println("5. Eliminar organización");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                crearOrganizacion();
                break;
            case 2:
                listarOrganizaciones();
                break;
            case 3:
                buscarOrganizacion();
                break;
            case 4:
                actualizarOrganizacion();
                break;
            case 5:
                eliminarOrganizacion();
                break;
        }
    }

    private static void crearOrganizacion() throws OrganizacionException {
        System.out.println("\n--- CREAR ORGANIZACIÓN ---");
        System.out.print("Nombre de la organización: ");
        String nombreOrganizacion = scanner.nextLine();
        System.out.print("Nombre del responsable: ");
        String nombreResponsable = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("ID Dirección: ");
        int idDireccion = scanner.nextInt();

        Organizacion organizacion = new Organizacion(0, nombreOrganizacion, nombreResponsable, correo, telefono, idDireccion);
        organizacionController.crearOrganizacion(organizacion);
        System.out.println("Organización creada exitosamente!");
    }

    private static void listarOrganizaciones() throws OrganizacionException {
        System.out.println("\n--- LISTA DE ORGANIZACIONES ---");
        List<Organizacion> organizaciones = organizacionController.obtenerTodasOrganizaciones();
        for (Organizacion organizacion : organizaciones) {
            System.out.println(organizacion);
        }
    }

    private static void buscarOrganizacion() throws OrganizacionException {
        System.out.print("Ingrese ID de la organización: ");
        int id = scanner.nextInt();
        Organizacion organizacion = organizacionController.obtenerOrganizacion(id);
        if (organizacion != null) {
            System.out.println(organizacion);
        } else {
            System.out.println("Organización no encontrada.");
        }
    }

    private static void actualizarOrganizacion() throws OrganizacionException {
        System.out.print("Ingrese ID de la organización a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Organizacion organizacion = organizacionController.obtenerOrganizacion(id);
        if (organizacion != null) {
            System.out.print("Nuevo nombre de organización (" + organizacion.getNombreOrganizacion() + "): ");
            String nombreOrganizacion = scanner.nextLine();
            if (!nombreOrganizacion.isEmpty()) organizacion.setNombreOrganizacion(nombreOrganizacion);

            System.out.print("Nuevo nombre del responsable (" + organizacion.getNombreResponsable() + "): ");
            String nombreResponsable = scanner.nextLine();
            if (!nombreResponsable.isEmpty()) organizacion.setNombreResponsable(nombreResponsable);

            organizacionController.actualizarOrganizacion(organizacion);
            System.out.println("Organización actualizada exitosamente!");
        } else {
            System.out.println("Organización no encontrada.");
        }
    }

    private static void eliminarOrganizacion() throws OrganizacionException {
        System.out.print("Ingrese ID de la organización a eliminar: ");
        int id = scanner.nextInt();
        organizacionController.eliminarOrganizacion(id);
        System.out.println("Organización eliminada exitosamente!");
    }

    private static void menuDirecciones() throws DireccionException {
        System.out.println("\n--- GESTIÓN DE DIRECCIONES ---");
        System.out.println("1. Crear dirección");
        System.out.println("2. Listar direcciones");
        System.out.println("3. Buscar dirección por ID");
        System.out.println("4. Actualizar dirección");
        System.out.println("5. Eliminar dirección");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                crearDireccion();
                break;
            case 2:
                listarDirecciones();
                break;
            case 3:
                buscarDireccion();
                break;
            case 4:
                actualizarDireccion();
                break;
            case 5:
                eliminarDireccion();
                break;
        }
    }

    private static void crearDireccion() throws DireccionException {
        System.out.println("\n--- CREAR DIRECCIÓN ---");
        System.out.print("Calle: ");
        String calle = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Colonia: ");
        String colonia = scanner.nextLine();
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Código Postal: ");
        String codigoPostal = scanner.nextLine();

        Direccion direccion = new Direccion(0, calle, numero, colonia, ciudad, estado, codigoPostal);
        direccionController.crearDireccion(direccion);
        System.out.println("Dirección creada exitosamente!");
    }

    private static void listarDirecciones() throws DireccionException {
        System.out.println("\n--- LISTA DE DIRECCIONES ---");
        List<Direccion> direcciones = direccionController.obtenerTodasDirecciones();
        for (Direccion direccion : direcciones) {
            System.out.println(direccion);
        }
    }

    private static void buscarDireccion() throws DireccionException {
        System.out.print("Ingrese ID de la dirección: ");
        int id = scanner.nextInt();
        Direccion direccion = direccionController.obtenerDireccion(id);
        if (direccion != null) {
            System.out.println(direccion);
        } else {
            System.out.println("Dirección no encontrada.");
        }
    }

    private static void actualizarDireccion() throws DireccionException {
        System.out.print("Ingrese ID de la dirección a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Direccion direccion = direccionController.obtenerDireccion(id);
        if (direccion != null) {
            System.out.print("Nueva calle (" + direccion.getCalle() + "): ");
            String calle = scanner.nextLine();
            if (!calle.isEmpty()) direccion.setCalle(calle);

            System.out.print("Nuevo número (" + direccion.getNumero() + "): ");
            String numero = scanner.nextLine();
            if (!numero.isEmpty()) direccion.setNumero(numero);

            direccionController.actualizarDireccion(direccion);
            System.out.println("Dirección actualizada exitosamente!");
        } else {
            System.out.println("Dirección no encontrada.");
        }
    }

    private static void eliminarDireccion() throws DireccionException {
        System.out.print("Ingrese ID de la dirección a eliminar: ");
        int id = scanner.nextInt();
        direccionController.eliminarDireccion(id);
        System.out.println("Dirección eliminada exitosamente!");
    }

    private static void menuAlimentos() throws AlimentoException {
        System.out.println("\n--- GESTIÓN DE ALIMENTOS ---");
        System.out.println("1. Crear alimento");
        System.out.println("2. Listar alimentos");
        System.out.println("3. Buscar alimento por ID");
        System.out.println("4. Actualizar alimento");
        System.out.println("5. Eliminar alimento");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                crearAlimento();
                break;
            case 2:
                listarAlimentos();
                break;
            case 3:
                buscarAlimento();
                break;
            case 4:
                actualizarAlimento();
                break;
            case 5:
                eliminarAlimento();
                break;
        }
    }

    private static void crearAlimento() throws AlimentoException {
        System.out.println("\n--- CREAR ALIMENTO ---");
        System.out.print("Nombre del alimento: ");
        String nombreAlimento = scanner.nextLine();
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Cantidad disponible: ");
        double cantidadDisponible = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Fecha de caducidad (YYYY-MM-DD): ");
        String fechaStr = scanner.nextLine();
        Date fechaCaducidad = Date.valueOf(fechaStr);
        System.out.print("ID Donador: ");
        int idDonador = scanner.nextInt();

        Alimento alimento = new Alimento(0, nombreAlimento, categoria, cantidadDisponible, fechaCaducidad, idDonador);
        alimentoController.crearAlimento(alimento);
        System.out.println("Alimento creado exitosamente!");
    }

    private static void listarAlimentos() throws AlimentoException {
        System.out.println("\n--- LISTA DE ALIMENTOS ---");
        List<Alimento> alimentos = alimentoController.obtenerTodosAlimentos();
        for (Alimento alimento : alimentos) {
            System.out.println(alimento);
        }
    }

    private static void buscarAlimento() throws AlimentoException {
        System.out.print("Ingrese ID del alimento: ");
        int id = scanner.nextInt();
        Alimento alimento = alimentoController.obtenerAlimento(id);
        if (alimento != null) {
            System.out.println(alimento);
        } else {
            System.out.println("Alimento no encontrado.");
        }
    }

    private static void actualizarAlimento() throws AlimentoException {
        System.out.print("Ingrese ID del alimento a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Alimento alimento = alimentoController.obtenerAlimento(id);
        if (alimento != null) {
            System.out.print("Nuevo nombre (" + alimento.getNombreAlimento() + "): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) alimento.setNombreAlimento(nombre);

            System.out.print("Nueva cantidad disponible (" + alimento.getCantidadDisponible() + "): ");
            String cantidadStr = scanner.nextLine();
            if (!cantidadStr.isEmpty()) alimento.setCantidadDisponible(Double.parseDouble(cantidadStr));

            alimentoController.actualizarAlimento(alimento);
            System.out.println("Alimento actualizado exitosamente!");
        } else {
            System.out.println("Alimento no encontrado.");
        }
    }

    private static void eliminarAlimento() throws AlimentoException {
        System.out.print("Ingrese ID del alimento a eliminar: ");
        int id = scanner.nextInt();
        alimentoController.eliminarAlimento(id);
        System.out.println("Alimento eliminado exitosamente!");
    }

    private static void menuEntregas() throws EntregaException {
        System.out.println("\n--- GESTIÓN DE ENTREGAS ---");
        System.out.println("1. Crear entrega");
        System.out.println("2. Listar entregas");
        System.out.println("3. Buscar entrega por ID");
        System.out.println("4. Actualizar entrega");
        System.out.println("5. Eliminar entrega");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                crearEntrega();
                break;
            case 2:
                listarEntregas();
                break;
            case 3:
                buscarEntrega();
                break;
            case 4:
                actualizarEntrega();
                break;
            case 5:
                eliminarEntrega();
                break;
        }
    }

    private static void crearEntrega() throws EntregaException {
        System.out.println("\n--- CREAR ENTREGA ---");
        System.out.print("Fecha de entrega (YYYY-MM-DD): ");
        String fechaStr = scanner.nextLine();
        Date fechaEntrega = Date.valueOf(fechaStr);
        System.out.print("Estado de entrega: ");
        String estadoEntrega = scanner.nextLine();
        System.out.print("ID Organización: ");
        int idOrganizacion = scanner.nextInt();

        Entrega entrega = new Entrega(0, fechaEntrega, estadoEntrega, idOrganizacion);
        entregaController.crearEntrega(entrega);
        System.out.println("Entrega creada exitosamente!");
    }

    private static void listarEntregas() throws EntregaException {
        System.out.println("\n--- LISTA DE ENTREGAS ---");
        List<Entrega> entregas = entregaController.obtenerTodasEntregas();
        for (Entrega entrega : entregas) {
            System.out.println(entrega);
        }
    }

    private static void buscarEntrega() throws EntregaException {
        System.out.print("Ingrese ID de la entrega: ");
        int id = scanner.nextInt();
        Entrega entrega = entregaController.obtenerEntrega(id);
        if (entrega != null) {
            System.out.println(entrega);
        } else {
            System.out.println("Entrega no encontrada.");
        }
    }

    private static void actualizarEntrega() throws EntregaException {
        System.out.print("Ingrese ID de la entrega a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Entrega entrega = entregaController.obtenerEntrega(id);
        if (entrega != null) {
            System.out.print("Nuevo estado (" + entrega.getEstadoEntrega() + "): ");
            String estado = scanner.nextLine();
            if (!estado.isEmpty()) entrega.setEstadoEntrega(estado);

            entregaController.actualizarEntrega(entrega);
            System.out.println("Entrega actualizada exitosamente!");
        } else {
            System.out.println("Entrega no encontrada.");
        }
    }

    private static void eliminarEntrega() throws EntregaException {
        System.out.print("Ingrese ID de la entrega a eliminar: ");
        int id = scanner.nextInt();
        entregaController.eliminarEntrega(id);
        System.out.println("Entrega eliminada exitosamente!");
    }

    private static void menuDetalleEntregas() throws DetalleEntregaException {
        System.out.println("\n--- GESTIÓN DE DETALLE DE ENTREGAS ---");
        System.out.println("1. Crear detalle de entrega");
        System.out.println("2. Listar detalles de entrega");
        System.out.println("3. Buscar detalle por ID");
        System.out.println("4. Actualizar detalle de entrega");
        System.out.println("5. Eliminar detalle de entrega");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                crearDetalleEntrega();
                break;
            case 2:
                listarDetalleEntregas();
                break;
            case 3:
                buscarDetalleEntrega();
                break;
            case 4:
                actualizarDetalleEntrega();
                break;
            case 5:
                eliminarDetalleEntrega();
                break;
        }
    }

    private static void crearDetalleEntrega() throws DetalleEntregaException {
        System.out.println("\n--- CREAR DETALLE DE ENTREGA ---");
        System.out.print("ID Entrega: ");
        int idEntrega = scanner.nextInt();
        System.out.print("ID Alimento: ");
        int idAlimento = scanner.nextInt();
        System.out.print("Cantidad entregada: ");
        double cantidadEntregada = scanner.nextDouble();

        DetalleEntrega detalle = new DetalleEntrega(0, idEntrega, idAlimento, cantidadEntregada);
        detalleEntregaController.crearDetalleEntrega(detalle);
        System.out.println("Detalle de entrega creado exitosamente!");
    }

    private static void listarDetalleEntregas() throws DetalleEntregaException {
        System.out.println("\n--- LISTA DE DETALLE DE ENTREGAS ---");
        List<DetalleEntrega> detalles = detalleEntregaController.obtenerTodosDetallesEntrega();
        for (DetalleEntrega detalle : detalles) {
            System.out.println(detalle);
        }
    }

    private static void buscarDetalleEntrega() throws DetalleEntregaException {
        System.out.print("Ingrese ID del detalle de entrega: ");
        int id = scanner.nextInt();
        DetalleEntrega detalle = detalleEntregaController.obtenerDetalleEntrega(id);
        if (detalle != null) {
            System.out.println(detalle);
        } else {
            System.out.println("Detalle de entrega no encontrado.");
        }
    }

    private static void actualizarDetalleEntrega() throws DetalleEntregaException {
        System.out.print("Ingrese ID del detalle de entrega a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        DetalleEntrega detalle = detalleEntregaController.obtenerDetalleEntrega(id);
        if (detalle != null) {
            System.out.print("Nueva cantidad entregada (" + detalle.getCantidadEntregada() + "): ");
            String cantidadStr = scanner.nextLine();
            if (!cantidadStr.isEmpty()) detalle.setCantidadEntregada(Double.parseDouble(cantidadStr));

            detalleEntregaController.actualizarDetalleEntrega(detalle);
            System.out.println("Detalle de entrega actualizado exitosamente!");
        } else {
            System.out.println("Detalle de entrega no encontrado.");
        }
    }

    private static void eliminarDetalleEntrega() throws DetalleEntregaException {
        System.out.print("Ingrese ID del detalle de entrega a eliminar: ");
        int id = scanner.nextInt();
        detalleEntregaController.eliminarDetalleEntrega(id);
        System.out.println("Detalle de entrega eliminado exitosamente!");
    }
}
