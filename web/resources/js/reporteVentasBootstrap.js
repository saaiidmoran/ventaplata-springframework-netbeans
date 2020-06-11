/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('#modal-abonar-venta').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // Button that triggered the modal
    var iddetalleventa,total,abono;
    total = button.data('totaldetalleventa');
    abono = button.data('abonodetalleventa');    
    iddetalleventa = button.data('iddetalleventa');
    $('#valor-abonar-venta').val(parseInt(total)-parseInt(abono));
    $('#valor-total-venta').val(total);
    $('#valor-abonado-venta').val(abono);
    $('#id-detalleventa-abonar').val(iddetalleventa);
    if(total === abono){
        $('#btn-abonar').prop( "disabled", true );
        $('#valor-abonar-venta').prop( "disabled", true );
        
    }else{
        $('#btn-abonar').prop( "disabled", false );
        $('#valor-abonar-venta').prop( "disabled", false );
    }
});

$('#modal-eliminar-producto-venta').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var idventa,iddv;
    idventa = button.data('idventa');
    iddv = button.data('iddv');
    $('#valor-id-venta-producto-eliminar').val(idventa);
    $('#valor-id-dv-producto-eliminar').val(iddv);
});

$('#modal-modificar-venta').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var iddv,fecha,total,abono,cliente,modalidad,estatus;
    iddv = button.data('iddetalleventa');
    fecha = button.data('fechaventa');
    total = button.data('totalventa');
    abono = button.data('abonoventa');
    cliente = button.data('clienteventa');
    modalidad = button.data('modalidadventa');
    estatus = button.data('estatus');
    $('#id-venta-modificar').val(iddv);
    $('#fecha-venta-modificar').val(fecha);
    $('#total-venta-modificar').val(total);
    $('#abono-venta-modificar').val(abono);
    $('#cliente-venta-modificar').val(cliente);
    $('#modalidad-venta-modificar').val(modalidad);
    $('#estatus-venta-modificar').val(estatus);
});

$('#modal-eliminar-venta').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var iddv;
    iddv = button.data('iddv');    
    $('#valor-idventa-eliminar').val(iddv);
});




