<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Modelo.entidades.Insumo" %>

<%-- Validación de carga --%>
<% if (request.getAttribute("listaInsumos") == null) { response.sendRedirect("InsumoServlet"); return; } %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .sidebar { min-height: 100vh; background-color: #343a40; color: white; padding-top: 20px; }
        .sidebar a { color: white; text-decoration: none; display: block; padding: 15px; border-bottom: 1px solid #495057; }
        .sidebar a:hover { background-color: #6F4E37; }
    </style>
</head>
<body class="bg-light">

<div class="container-fluid">
    <div class="row">
        <!-- MENÚ LATERAL -->
        <nav class="col-md-2 sidebar">
    <h5 class="text-center">Holly's Baking</h5>
    <hr>
     <a href="DashboardServlet">🏠 Dashboard Principal</a>
            <a href="InsumoServlet">📦 Gestionar Insumos</a>
            <a href="ProductoServlet">🍰 Gestionar Productos</a>
           <a href="RecetaServlet">📖 Ver Recetas</a>
    <hr>
    <a href="login.jsp" class="text-danger">Cerrar Sesión</a>
</nav>

        <!-- CONTENIDO PRINCIPAL -->
        <main class="col-md-10 p-4">
            
            <%-- MENSAJES DE ERROR O ÉXITO --%>
            <% String error = (String) request.getAttribute("error"); String mensaje = (String) request.getAttribute("mensaje");
               if (error != null) { %>
                <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                    <strong>Error:</strong> <%= error %> <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } else if (mensaje != null) { %>
                <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
                    <strong>¡Éxito!</strong> <%= mensaje %> <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>

            <h2>Gestión de Insumos</h2>

            <!-- Formulario -->
            <div class="card p-4 mb-4 shadow-sm">
                <h5 class="mb-3">Registrar Nuevo Insumo</h5>
                <form action="InsumoServlet" method="POST" class="row g-3">
                    <div class="col-md-3"><input type="text" name="nombre" class="form-control" placeholder="Nombre" required></div>
                     <div class="col-md-3"><input type="number" step="0.01" name="cantidad" class="form-control" placeholder="Costo Unitario" required></div>                    <div class="col-md-3"><input type="text" name="unidad" class="form-control" placeholder="Unidad" required></div>
                    <div class="col-md-3"><button type="submit" class="btn btn-success w-100">Guardar Insumo</button></div>
                </form>
            </div>

            <!-- Tabla -->
            <table class="table table-striped table-hover mt-3 shadow-sm bg-white">
                <thead class="table-dark">
                    <tr><th>ID</th><th>Nombre</th><th>Costo Unitario</th><th>Unidad</th></tr>
                </thead>
                <tbody>
                    <% List<Insumo> lista = (List<Insumo>) request.getAttribute("listaInsumos");
                       if(lista != null && !lista.isEmpty()) {
                           for(Insumo i : lista) { %>
                    <tr>
                        <td><%= i.getIdInsumo() %></td>
                        <td><%= i.getNombreInsumo() %></td>
                        <td>S/ <%= i.getCostoUnidad() %></td>
                        <td><%= i.getUnidadMedida() %></td>
                    </tr>
                    <% } } else { %>
                    <tr><td colspan="4" class="text-center text-muted">No hay insumos registrados</td></tr>
                    <% } %>
                </tbody>
            </table>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>