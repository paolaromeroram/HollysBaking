<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container d-flex justify-content-center align-items-center vh-100">
        
        <div class="card shadow p-4" style="width: 25rem;">
            <div class="card-body text-center">
                
                <h2 class="mb-4" style="color: #6F4E37;">Holly's Baking</h2>
                <p class="text-muted">Crea tu cuenta como cliente</p>

                <form action="RegistroServlet" method="POST">
                    
                    <div class="mb-3 text-start">
                        <label for="nombre" class="form-label">Nombre Completo</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>

                    <div class="mb-3 text-start">
                        <label for="correo" class="form-label">Correo Electrónico</label>
                        <input type="email" class="form-control" id="correo" name="correo" required>
                    </div>
                    
                    <div class="mb-3 text-start">
                        <label for="clave" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" id="clave" name="clave" required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary w-100 mt-4" style="background-color: #6F4E37; border: none;">
                        Crear Cuenta
                    </button>
                    
                </form>

                <div class="mt-3">
                    <% if(request.getParameter("error") != null) { %>
                        <div class="alert alert-danger p-2" role="alert">
                            <small>Error: El correo ya existe o hubo un fallo.</small>
                        </div>
                    <% } %>
                    <small>¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión aquí</a></small>
                </div>
            </div>
        </div>
    </div>

</body>
</html>