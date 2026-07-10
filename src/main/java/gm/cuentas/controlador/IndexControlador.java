package gm.cuentas.controlador;

import gm.cuentas.modelo.Cuenta;
import gm.cuentas.modelo.TipoCuenta;
import gm.cuentas.servicio.ICuentaBancariaServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

@Named
@Getter
@Setter
@ViewScoped
public class IndexControlador implements Serializable {

    private final ICuentaBancariaServicio iCuentaBancariaServicio;
    private List<Cuenta> cuentas;
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);
    private static final long serialVersionUID = 1L;
    private Cuenta cuentaAgregada;

    public IndexControlador(ICuentaBancariaServicio iCuentaBancariaServicio){
        this.iCuentaBancariaServicio = iCuentaBancariaServicio;
    }

    @PostConstruct
    public void init(){
         cargarDatos();
        agregarCuenta();
    }

    public void cargarDatos(){
        this.cuentas = iCuentaBancariaServicio.listarCuentas();
        cuentas.forEach((cuenta -> logger.info(cuenta.toString())));
    }

    public TipoCuenta[] getTiposCuenta(){
        return TipoCuenta.values();
    }

    public void agregarCuenta(){
        this.cuentaAgregada = new Cuenta();
    }

    public void guardarCuenta(){
        logger.info("Cuenta a guardar: " + this.cuentaAgregada);
        Cuenta cuenta;
        if(this.cuentaAgregada.getIdCuenta() == null){
            cuenta = this.iCuentaBancariaServicio.guardarCuenta(this.cuentaAgregada);
            this.cuentas.add(cuenta);
            agregarCuenta();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cuenta Agregada"));
        }
        // ocultamos la ventana
        PrimeFaces.current().executeScript("PF('ventanaModalCuenta').hide()");
        // actualizamos la tabla
        PrimeFaces.current().ajax().update("forma-cuentas:mensajes", "forma-cuentas:cuentas-tabla");
    }
}
