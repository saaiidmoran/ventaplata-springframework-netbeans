/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('#modal-mod-cliente').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // Button that triggered the modal
    var idcliente = button.data('idcliente'); // Extract info from data-* attributes
    var nombrecliente = button.data('nombrecliente');
    var telefono = button.data('telefono');
    var correo = button.data('correo');
    var domicilio = button.data('domicilio');
    $('#id-cliente-modificar').val(idcliente);
    $('#nombreCliente-modificar').val(nombrecliente);
    $('#noTelefonico-modificar').val(telefono);
    $('#correo-modificar').val(correo);
    $('#domicilio-modificar').val(domicilio);
 });
            
$('#modal-elim-cliente').on('show.bs.modal', function (event) {
   var button = $(event.relatedTarget);
   var idcliente = button.data('idcliente');
   $('#id-cliente-eliminar').val(idcliente);
});

