package dtos;

import Model.Deporte;
import Model.Ubicacion;
import lombok.Data;
import java.util.List;

@Data
public class UsuarioDTO {
    private String nombreUsuario;
    private Ubicacion ubicacion;
    private String correo;
    private String password;
    private List<Deporte> deportes;
}
