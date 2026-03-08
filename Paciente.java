public class Paciente {
    private int id;
    private String nombre;
    private int edad;
    private double peso;
    private double estatura;
    private String antecedentes;

    public Paciente(int id, String nombre, int edad, double peso, double estatura, String antecedentes) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.estatura = estatura;
        this.antecedentes = antecedentes;
    }

    public Paciente(String nombre, int edad, double peso, double estatura, String antecedentes) {
        this(0, nombre, edad, peso, estatura, antecedentes);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public double getPeso() { return peso; }
    public double getEstatura() { return estatura; }
    public String getAntecedentes() { return antecedentes; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setEstatura(double estatura) { this.estatura = estatura; }
    public void setAntecedentes(String antecedentes) { this.antecedentes = antecedentes; }

    @Override
    public String toString() { return nombre; }
}
