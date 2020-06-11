<%-- 
    Document   : inicio
    Created on : 23/07/2019, 12:56:56 PM
    Author     : saaii
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width,user-scalable=no,
              initial-scale=1, maximum-scale=1, minimum-scale=1"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
        <spring:url value="/resources/css/Index.css" var="loginCss" />
        <link href="${loginCss}" rel="stylesheet">
        <spring:url value="/resources/imagenes/login.png" var="loginImg" />        
        <title>Venta Plata</title>        
    </head>
    <body>
        <%
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            if(session.getAttribute("login")!=null){
                response.sendRedirect("inicio.htm");
            }
        %>
	<div class="container h-100">
            <div class="d-flex justify-content-center h-100">
                <div class="user_card">
                    <div class="d-flex justify-content-center">
                        <div class="brand_logo_container">
                            <img src="${loginImg}" class="brand_logo" alt="Logo">
                        </div>
                    </div>
                    <div class="d-flex justify-content-center form_container">
                        <form autocomplete="off" action="login.htm" method="post">                            
                            <div class="input-group mb-4">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                                </div>
                                <input autocomplete="off" type="text" name="usuario" class="form-control input_user" value="" placeholder="usuario" id="usuario-login">
                            </div>
                            <div class="input-group mb-4">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                </div>
                                <input autocomplete="off" type="password" name="clave" class="form-control input_pass" value="" placeholder="contraseña" id="pwd-login">
                            </div>
                            <div class="d-flex justify-content-center mt-3 login_container">
                                <button name="button" class="btn btn-primary" id="btn-ingresar">Ingresar</button>
                            </div>
                            <div class="mt-3">
                                <div class="d-flex justify-content-center bg-danger">
                                    <div class="text-white">
                                        ${requestScope.mensaje}
                                    </div>                                    
                                </div>
                                <div class="d-flex justify-content-center links">
                                    <a href="">Olvidé mi contraseña</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
	</div>
        
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
                
        <script languaje="JavaScript" type="text/javascript">
            $(document).ready(function (){
                function noback(){
                    window.location.hash="no-back-button";	
                    window.location.hash="Again-No-back-button";
                    window.onhashchange=function(){window.location.hash="no-back-button";};
                }
                noback();
                $('#btn-ingresar').prop( "disabled", true );
                
                $(':input').keyup(function(){
                    var usuario = $('#usuario-login').val();
                    var pwd = $('#pwd-login').val();
                    if(usuario.match(/^[A-Z a-z 0-9]+$/) && pwd.match(/^[A-Z a-z 0-9]+$/)){
                        $('#btn-ingresar').prop( "disabled", false );
                    }else{
                        $('#btn-ingresar').prop( "disabled", true );
                    }
                    if(!usuario.match(/^[A-Z a-z 0-9]+$/) && usuario !== ''){
                        alert('Hay un error en el campo usuario');
                    }
                    if(!pwd.match(/^[A-Z a-z 0-9]+$/) && pwd !== ''){
                        alert('Hay un error en el campo contraseña');
                    }
                });
                
            });
        </script>
    </body>
</html>