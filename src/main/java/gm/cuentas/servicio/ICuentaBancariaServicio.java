package gm.cuentas.servicio;

import gm.cuentas.modelo.Cuenta;
import java.util.List;

public interface ICuentaBancariaServicio {
    public List<Cuenta> listarCuentas();
    public Cuenta buscarPorId(Integer idCuenta);
    public Cuenta guardarCuenta(Cuenta cuenta);
    public void eliminarCuenta(Cuenta cuenta);
}
