import java.util.ArrayList;
import java.util.List;

public class Paciente {
    String nombre;
    int edad;
    double peso;
    double estatura;
    String antecedentes;

    List<ControlSalud> controles = new ArrayList<>();

    public Paciente(String nombre, int edad, double peso, double estatura, String antecedentes) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.estatura = estatura;
        this.antecedentes = antecedentes;
    }

    public void agregarControl(ControlSalud control) {
        controles.add(control);
    }

    public void mostrarHistorial() {
        if (controles.isEmpty()) {
            System.out.println("No hay controles registrados.");
            return;
        }

        for (ControlSalud c : controles) {
            System.out.println(c);
        }
    }
}
