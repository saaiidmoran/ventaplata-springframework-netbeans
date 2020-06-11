/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('#modal-mod-usuario').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // Button that triggered the modal
    var idusr = button.data('idusr'); // Extract info from data-* attributes
    var nombreusr = button.data('nombreusr');
    var clave = button.data('claveusr');
    $('#id-usr-modificar').val(idusr);
    $('#nombreUsuario-modificar').val(nombreusr);
    $('#clave-modificar').val(clave);
    $('#clave2-modificar').val(clave);
 });
            
$('#modal-elim-usuario').on('show.bs.modal', function (event) {
   var button = $(event.relatedTarget);
   var idusr = button.data('idusr');
   $('#id-usr-eliminar').val(idusr);
});


