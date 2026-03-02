public class EvaluadorRiesgo {
      public static String evaluar(ControlSalud c) {

        if (c.presionSistolica > 140 || c.glucosa > 180 || c.frecuenciaCardiaca > 100) {
            return "RIESGO ALTO - Consulte a un médico.";
        }

        if (c.presionSistolica > 120 || c.glucosa > 140 || c.frecuenciaCardiaca > 90) {
            return "RIESGO MEDIO - Mejore hábitos y monitoree.";
        }

        return "RIESGO BAJO - Continúe con hábitos saludables.";
    }
}
