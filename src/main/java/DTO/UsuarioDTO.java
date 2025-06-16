package DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import Model.Nivel;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String nombreUsuario;
    private String correo;
    private String password;
    private UbicacionDTO ubicacion;
    private List<UsuarioDeporteDTO> deportes;
    private List<PartidoDTO> historial;
    private boolean sesion;
    private int cantidadPartidosJugados;
}
