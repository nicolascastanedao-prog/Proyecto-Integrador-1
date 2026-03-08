import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControlSaludDAO {

    public static boolean insertar(ControlSalud c) {
        String sql = "INSERT INTO Mediciones (paciente_id, presion_sistolica, presion_diastolica, glucosa, frecuencia_cardiaca, riesgo) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getPacienteId());
            ps.setDouble(2, c.getPresionSistolica());
            ps.setDouble(3, c.getPresionDiastolica());
            ps.setDouble(4, c.getGlucosa());
            ps.setInt(5, c.getFrecuenciaCardiaca());
            ps.setString(6, c.getRiesgo());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar medición: " + e.getMessage());
            return false;
        }
    }

    public static List<ControlSalud> obtenerPorPaciente(int pacienteId) {
        List<ControlSalud> lista = new ArrayList<>();
        String sql = "SELECT id, paciente_id, presion_sistolica, presion_diastolica, glucosa, " +
                     "frecuencia_cardiaca, CONVERT(VARCHAR, fecha_medicion, 120) AS fecha, riesgo " +
                     "FROM Mediciones WHERE paciente_id=? ORDER BY fecha_medicion DESC";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pacienteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ControlSalud(
                    rs.getInt("id"),
                    rs.getInt("paciente_id"),
                    rs.getDouble("presion_sistolica"),
                    rs.getDouble("presion_diastolica"),
                    rs.getDouble("glucosa"),
                    rs.getInt("frecuencia_cardiaca"),
                    rs.getString("fecha"),
                    rs.getString("riesgo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener mediciones: " + e.getMessage());
        }
        return lista;
    }
}
