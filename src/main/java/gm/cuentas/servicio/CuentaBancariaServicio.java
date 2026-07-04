package gm.cuentas.servicio;

import gm.cuentas.modelo.Cuenta;
import gm.cuentas.repositorio.CuentaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CuentaBancariaServicio implements ICuentaBancariaServicio{

    private final CuentaRepositorio cuentaRepositorio;

    public CuentaBancariaServicio(CuentaRepositorio cuentaRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
    }

    @Override
    public List<Cuenta> listarCuentas() {
        List<Cuenta> listaCuentas = cuentaRepositorio.findAll();
        return listaCuentas;
    }

    @Override
    public Cuenta buscarPorId(Integer idCuenta) {
        Cuenta cuenta = cuentaRepositorio.findById(idCuenta).orElse(null);
        return cuenta;
    }

    @Override
    public void guardarCuenta(Cuenta cuenta) {
        cuentaRepositorio.save(cuenta);
    }

    @Override
    public void eliminarCuenta(Cuenta cuenta) {
        cuentaRepositorio.delete(cuenta);
    }
}
