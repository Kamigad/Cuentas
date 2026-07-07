package gm.cuentas.controlador;

import gm.cuentas.modelo.Cuenta;
import gm.cuentas.servicio.ICuentaBancariaServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Data
@ViewScoped
public class IndexControlador {

    private final ICuentaBancariaServicio iCuentaBancariaServicio;
    private List<Cuenta> cuentas;
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    public IndexControlador(ICuentaBancariaServicio iCuentaBancariaServicio){
        this.iCuentaBancariaServicio = iCuentaBancariaServicio;
    }

    @PostConstruct
    public void init(){
         cargarDatos();
    }

    public void cargarDatos(){
        this.cuentas = iCuentaBancariaServicio.listarCuentas();
        cuentas.forEach((cuenta -> logger.info(cuenta.toString())));
    }
}
