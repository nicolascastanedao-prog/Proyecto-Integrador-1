public class EvaluadorRiesgo {

    public static String evaluar(ControlSalud c) {
        if (c.getPresionSistolica() > 140 || c.getGlucosa() > 180 || c.getFrecuenciaCardiaca() > 100) {
            return "ALTO";
        }
        if (c.getPresionSistolica() > 120 || c.getGlucosa() > 140 || c.getFrecuenciaCardiaca() > 90) {
            return "MEDIO";
        }
        return "BAJO";
    }

    public static String descripcion(String riesgo) {
        return switch (riesgo) {
            case "ALTO"  -> "RIESGO ALTO - Consulte a un médico urgente.";
            case "MEDIO" -> "RIESGO MEDIO - Mejore hábitos y monitoree.";
            default      -> "RIESGO BAJO - Continúe con hábitos saludables.";
        };
    }
}
