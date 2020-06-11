<%-- 
    Document   : usuarios
    Created on : 23/08/2019, 04:41:05 PM
    Author     : saaii
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width,user-scalable=no,
              initial-scale=1, maximum-scale=1, minimum-scale=1"/>       
        
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
        <spring:url value="/resources/css/usuarios.css" var="usuariosCSS" />
        <link href="${usuariosCSS}" rel="stylesheet">
        
        <title>Venta Plata</title>
    </head>
    <body>
        <%
            // Impide que se guarde el cache en el navegador y verifica si ya se inició sesión
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            if(session.getAttribute("login")==null){
                response.sendRedirect("index.htm");
            }
        %>        
        <div class="contenedor-principal">
            <nav class="navbar navbar-light bg-light">
                <h1 class="navbar-brand mb-0 h1">
                    <i class="fas fa-users"></i>
                    Usuarios
                </h1>
                <div class="form-inline">
                    <form action="logout.htm" method="post">
                        ${sessionScope.login.getUsuario()}
                        <button class="btn btn-white botones" title="Salir">
                            <span><i class="fas fa-sign-out-alt"></i></span>
                        </button>                 
                    </form>
                        <a href="inicio.htm" title="Inicio">
                            <span><i class="fas fa-home"></i></span>
                        </a>                        
                </div>               
            </nav>
            <div class="container mt-4">  
                <div class="input-group mb-3 busqueda-usuario">
                    <input type="text" class="form-control" placeholder="No. de usuario" id="valor-busqueda-idusuario" />
                    <input type="text" class="form-control" placeholder="Usuario" id="valor-busqueda-usuario" />
                    <div class="input-group-append">
                        <button type="button" id="btn-buscar-usuario" class="btn btn-primary" title="Buscar"><i class="fas fa-search"></i></button>
                    </div>                    
                </div>
                <div class="curved-border-table">
                    <h4>Usuarios Registrados</h4>
                    <div class="table-responsive">
                        <table class="table table-hover table-borderless">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Nombre de Usuario</th>
                                    <th>
                                        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modal-add-usuario" title="Agregar usuario">
                                            <i class="fas fa-plus"></i>
                                        </button>
                                    </th>
                                </tr>
                            </thead>
                            <tbody id="contenidoTabla">                            
                            </tbody>
                        </table>
                    </div>                    
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="modal-add-usuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Registro de usuario</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="nombreUsuario-registrar" class="col-form-label">Nombre de usuario:</label>
                                <input type="text" class="form-control" id="nombreUsuario-registrar">
                            </div>
                            <div class="form-group">
                                <label for="clave-registrar" class="col-form-label">Contraseña:</label>
                                <input autocomplete="off" type="password" class="form-control" id="clave-registrar">
                            </div>
                            <div class="form-group">
                                <label for="clave2-registrar" class="col-form-label">Confirmar contraseña:</label>
                                <input autocomplete="off" type="password" class="form-control" id="clave2-registrar">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-success" id="btn-registrar">Registrar</button>
                    </div>
                </div>
            </div>
        </div>
                        
        <div class="modal fade" id="modal-mod-usuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modificación de usuario</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="id-usr-modificar" class="col-form-label">Identificador:</label>
                                <input type="text" readonly="readonly" class="form-control" id="id-usr-modificar">
                            </div>                      
                            <div class="form-group">
                                <label for="nombreUsuario-modificar" class="col-form-label">Nombre de usuario:</label>
                                <input type="text" class="form-control" id="nombreUsuario-modificar">
                            </div>
                            <div class="form-group">
                                <label for="clave-modificar" class="col-form-label">Contraseña:</label>
                                <input autocomplete="never" type="password" class="form-control" id="clave-modificar">
                            </div>
                            <div class="form-group">
                                <label for="clave2-modificar" class="col-form-label">Confirmar contraseña:</label>
                                <input autocomplete="never" type="password" class="form-control" id="clave2-modificar">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-warning" id="btn-modificar">Modificar</button>
                    </div>
                </div>
            </div>
        </div>
                        
        <div class="modal fade" id="modal-elim-usuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Eliminación de usuario</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label class="col-form-label">
                                    ¿Esta seguro de que desea eliminar este usuario?
                                </label>
                                <input type="hidden" id="id-usr-eliminar"/>
                            </div>                 
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-danger" id="btn-eliminar">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
                        
        <span id="msjUsuarioAgregado">            
        </span>
        
        <div id="msjUsuarioModificado">
        </div>
        
        <div id="msjUsuarioEliminado">            
        </div>
               
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        
        <%-- Scripts de control de bootstrap --%>
        <spring:url value="/resources/js/usuariosBootstrap.js" var="usuariosBootstrap" />
        <script type="text/javascript" src="${usuariosBootstrap}"></script>
        
        <%-- Scripts de control de AJAX --%>
        <script type="text/javascript">
            $(document).ready(function (){                
                function obtenerTodos(){
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/usuarios/usuariosRegistrados.htm',
                        success: function (result) {
                            $('#contenidoTabla').html(result);
                        }
                    }); 
                }                         
                obtenerTodos();                
                $('#btn-registrar').click(function(){                    
                    var nombre = $('#nombreUsuario-registrar').val();
                    var clave = $('#clave-registrar').val();
                    var clave2 = $('#clave2-registrar').val();
                    if(clave === clave2){
                        $.ajax({
                            type:'POST',
                            url: '${pageContext.request.contextPath}/usuarios/registrarUsuario/'+nombre+'/'+clave+'.htm',
                            success: function (result){                        
                                obtenerTodos();
                                $('#modal-add-usuario').modal('hide');
                                $('#msjUsuarioAgregado').html(result);
                            }
                        });  
                    }else{
                        alert('Las contraseñas no coinciden');
                    }                                 
                });
                $('#btn-eliminar').click(function (){
                    var idusr = $('#id-usr-eliminar').val();
                    $.ajax({
                        type:'POST',
                        url: '${pageContext.request.contextPath}/usuarios/eliminarUsuario/'+idusr+'.htm',
                        success: function(result){
                            obtenerTodos();
                            $('#modal-elim-usuario').modal('hide');
                            $('#msjUsuarioEliminado').html(result);
                        }
                    }); 
                });
                $('#btn-modificar').click(function (){
                    var idusr = $('#id-usr-modificar').val();
                    var usrnombre = $('#nombreUsuario-modificar').val();
                    var usrclave = $('#clave-modificar').val();
                    var usrclave2 = $('#clave2-modificar').val();
                    if(usrclave === usrclave2){
                        $.ajax({
                            type:'POST',
                            url: '${pageContext.request.contextPath}/usuarios/modificarUsuario/'+idusr+'/'+usrnombre+'/'+usrclave+'.htm',
                            success: function(result){
                                obtenerTodos();
                                $('#modal-mod-usuario').modal('hide');
                                $('#msjUsuarioModificado').html(result);
                            }
                        });
                    }else{
                        alert('Las contraseñas no coinciden');
                    }
                });
                
                $('#btn-buscar-usuario').click(function(){
                    idusuario = $('#valor-busqueda-idusuario').val();
                    usuario = $('#valor-busqueda-usuario').val();
                    if(idusuario === ''){
                        idusuario = '0';
                    }
                    if(usuario === ''){
                        usuario = 'todos';
                    }
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/usuarios/filtrarUsuariosRegistrados/'+idusuario+'/'+usuario+'.htm',
                        success: function (result) {
                            $('#contenidoTabla').html(result);
                        }
                    }); 
                });
            });
        </script>
    </body>
</html>
