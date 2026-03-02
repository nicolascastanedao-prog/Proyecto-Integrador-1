public class ControlSalud {
    double presionSistolica;
    double glucosa;
    double frecuenciaCardiaca;
    String fecha;

    public ControlSalud(double presionSistolica, double glucosa, double frecuenciaCardiaca, String fecha) {
        this.presionSistolica = presionSistolica;
        this.glucosa = glucosa;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Fecha: " + fecha +
                " | Presión: " + presionSistolica +
                " | Glucosa: " + glucosa +
                " | Frecuencia Cardíaca: " + frecuenciaCardiaca;
    }
}
