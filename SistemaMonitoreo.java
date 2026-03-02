import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaMonitoreo {
     List<Paciente> pacientes = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void iniciar() {

        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE MONITOREO ECNT ===");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Registrar control de salud");
            System.out.println("3. Ver historial de paciente");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarPaciente();
                case 2 -> registrarControl();
                case 3 -> verHistorial();
                case 4 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }

    private Paciente buscarPaciente(String nombre) {
        for (Paciente p : pacientes) {
            if (p.nombre.equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    private void registrarPaciente() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Edad: ");
        int edad = scanner.nextInt();

        System.out.print("Peso (kg): ");
        double peso = scanner.nextDouble();

        System.out.print("Estatura (m): ");
        double estatura = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Antecedentes familiares: ");
        String antecedentes = scanner.nextLine();

        pacientes.add(new Paciente(nombre, edad, peso, estatura, antecedentes));
        System.out.println("Paciente registrado correctamente.");
    }

    private void registrarControl() {
        System.out.print("Nombre del paciente: ");
        String nombre = scanner.nextLine();

        Paciente paciente = buscarPaciente(nombre);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        System.out.print("Presión sistólica: ");
        double presion = scanner.nextDouble();

        System.out.print("Glucosa: ");
        double glucosa = scanner.nextDouble();

        System.out.print("Frecuencia cardíaca: ");
        double fc = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Fecha: ");
        String fecha = scanner.nextLine();

        ControlSalud control = new ControlSalud(presion, glucosa, fc, fecha);
        paciente.agregarControl(control);

        String riesgo = EvaluadorRiesgo.evaluar(control);
        System.out.println("Resultado: " + riesgo);
    }

    private void verHistorial() {
        System.out.print("Nombre del paciente: ");
        String nombre = scanner.nextLine();

        Paciente paciente = buscarPaciente(nombre);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        paciente.mostrarHistorial();
    }
}
