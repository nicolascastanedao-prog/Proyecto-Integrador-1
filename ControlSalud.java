public class ControlSalud {
    private int id;
    private int pacienteId;
    private double presionSistolica;
    private double presionDiastolica;
    private double glucosa;
    private int frecuenciaCardiaca;
    private String fechaMedicion;
    private String riesgo;

    public ControlSalud(int id, int pacienteId, double presionSistolica, double presionDiastolica,
                        double glucosa, int frecuenciaCardiaca, String fechaMedicion, String riesgo) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.presionSistolica = presionSistolica;
        this.presionDiastolica = presionDiastolica;
        this.glucosa = glucosa;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.fechaMedicion = fechaMedicion;
        this.riesgo = riesgo;
    }

    public ControlSalud(int pacienteId, double presionSistolica, double presionDiastolica,
                        double glucosa, int frecuenciaCardiaca) {
        this(0, pacienteId, presionSistolica, presionDiastolica, glucosa, frecuenciaCardiaca, "", "");
    }

    public int getId() { return id; }
    public int getPacienteId() { return pacienteId; }
    public double getPresionSistolica() { return presionSistolica; }
    public double getPresionDiastolica() { return presionDiastolica; }
    public double getGlucosa() { return glucosa; }
    public int getFrecuenciaCardiaca() { return frecuenciaCardiaca; }
    public String getFechaMedicion() { return fechaMedicion; }
    public String getRiesgo() { return riesgo; }
    public void setRiesgo(String riesgo) { this.riesgo = riesgo; }
}
