/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('#modal-mod-producto').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // Button that triggered the modal
    var idproducto = button.data('idproducto'); // Extract info from data-* attributes
    var nombreproducto = button.data('nombreproducto');
    var precioInv = button.data('precioinv');
    var precioVent = button.data('preciovent');
    var fechaProducto = button.data('fechaproducto');
    var estatusProdcuto = button.data('estatusproducto');
    $('#id-producto-modificar').val(idproducto);
    $('#producto-modificar').val(nombreproducto);
    $('#inversion-modificar').val(precioInv);
    $('#precio-modificar').val(precioVent);
    $('#fechaRegistro-modificar').val(fechaProducto);
    $('#estatusProducto-modificar').val(estatusProdcuto);
 });
 
 $('#modal-elim-producto').on('show.bs.modal', function (event) {
   var button = $(event.relatedTarget);
   var idproducto = button.data('idproducto');
   $('#id-producto-eliminar').val(idproducto);
});


